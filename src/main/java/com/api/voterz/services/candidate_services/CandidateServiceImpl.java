package com.api.voterz.services.candidate_services;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.requests.Email;
import com.api.voterz.data.dtos.requests.EmailRequest;
import com.api.voterz.data.dtos.responses.CandidateDTO;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.data.models.Details;
import com.api.voterz.data.repositories.CandidateRepository;
import com.api.voterz.exceptions.ImageUploadException;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.services.details_service.DetailService;
import com.api.voterz.services.notification.MailService;
import com.api.voterz.utilities.Paginate;
import com.api.voterz.utilities.Utilities;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.api.voterz.utilities.config.Constants.*;
import static java.util.Calendar.MARCH;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CloudImageService cloudImageService;
    private final DetailService detailService;
    private final MailService mailService;
    private final ModelMapper mapper;

    @Override
    public GlobalApiResponse register(@Valid CandidateRegisterRequest register) {
        int age = Utilities.calculateAge(register.getDateOfBirth());
        boolean invalidAge = age < 25 || age > 65;
        if (invalidAge) throw new VoterzException(NOT_QUALIFIED);

        Candidate candidate = mapper.map(register, Candidate.class);

        Details details = detailService.uploadImage(
                candidate.getDetails(),
                register.getImage()
        );
        details.setRegistered(true);
        String fullName = String.format("%s %s", details.getFirstName(), details.getLastName());
        String mailTemplate = Utilities.getCandidateMail();
        String content = String.format(
                mailTemplate,
                fullName,
                LocalDate.of(2023, MARCH, 19),//will be changed later
                LocalTime.of(8, 0),
                fullName,
                details.getEmail(),
                details.getAge(),
                candidate.getParty(),
                candidate.getElectionType());
        //fullName, date, time(from), time(to), fullName, email, phoneNumber, party, election type
        String mailUrl = mailService.sendHTMLMail(new EmailRequest(
                new Email(fullName, details.getEmail()),
                "Successful Registration",
                content
        ));
        if (mailUrl == null) {
            throw new VoterzException("Registration failed");
        }
        candidateRepository.save(candidate);
        return GlobalApiResponse.builder()
                .message(COMPLETE_REG)
                .completed(true)
                .build();
    }

    @Override
    public CandidateDTO getCandidateById(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new VoterzException(CANDIDATE_NOT_FOUND));
        return mapper.map(candidate, CandidateDTO.class);
    }

    @Override
    public Candidate getCandidate(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new VoterzException(CANDIDATE_NOT_FOUND));
    }

    @Override
    public Paginate<CandidateDTO> getAllCandidates(int pageNumber) {
        pageNumber = pageNumber < 1 ? 0 : pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, MAX_PAGE_NUMBER); //Sort.by(sortBy)
        Page<Candidate> candidates = candidateRepository.findAll(pageable);
        Type type = new TypeToken<Paginate<CandidateDTO>>() {
        }.getType();
        return mapper.map(candidates, type);
    }

    @Override
    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public GlobalApiResponse updateCandidate(Long candidateId, JsonPatch jsonPatch) {
        Candidate candidate = getCandidate(candidateId);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.convertValue(candidate, JsonNode.class);
        try {
            JsonNode updatedNode = jsonPatch.apply(jsonNode);
            Candidate updatedcandidate = objectMapper.convertValue(updatedNode, Candidate.class);

            candidateRepository.save(updatedcandidate);
            return GlobalApiResponse.builder()
                    .message(UPDATED)
                    .build();
        } catch (JsonPatchException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public GlobalApiResponse changeCandidateImage(Long candidateId, MultipartFile candidateImage) {
        Candidate candidate = getCandidate(candidateId);

        String imageUrl = cloudImageService.upload(candidateImage);

        if (imageUrl.isEmpty()) throw new ImageUploadException(IMAGE_UPLOAD_FAILED);

        candidate.getDetails().setImage(imageUrl);
        candidateRepository.save(candidate);

        return GlobalApiResponse.builder()
                .imageUrl(imageUrl)
                .message(UPDATED)
                .build();
    }

    @Override
    public GlobalApiResponse deleteById(Long candidateId) {
        candidateRepository.deleteById(candidateId);
        return GlobalApiResponse.builder()
                .message(DELETED_SUCCESSFULLY)
                .build();
    }

    @Override
    public GlobalApiResponse deleteAll() {
        candidateRepository.deleteAll();
        return GlobalApiResponse.builder()
                .message(ALL_DELETED)
                .build();
    }

    @Override
    public Long numberOfCandidates() {
        return candidateRepository.count();
    }
}