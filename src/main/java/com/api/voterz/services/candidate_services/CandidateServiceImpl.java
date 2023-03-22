package com.api.voterz.services.candidate_services;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.responses.CandidateResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.data.repositories.CandidateRepository;
import com.api.voterz.exceptions.ImageUploadException;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.api.voterz.utilities.config.AppConfig.*;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CloudImageService cloudImageService;
    private final ModelMapper mapper;

    @Override
    public RegisterResponse register(@Valid CandidateRegisterRequest register) {
        boolean invalidAge = register.getAge() < 25 || register.getAge() > 65;
        if (invalidAge) throw new VoterzException(NOT_QUALIFIED);

        Candidate candidate = Candidate.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .party(register.getParty())
                .age(register.getAge())
                .ElectionType(register.getElectionType())
                .build();

        var updatedCandidate = uploadImage(candidate, register.getCandidateImage());

        Candidate savedCandidate = candidateRepository.save(updatedCandidate);

        return RegisterResponse.builder()
                .id(savedCandidate.getId())
                .message(CANDIDATE_SAVED)
                .statusCode(HttpStatus.CREATED.value())
                .registered(true)
                .build();
    }

    private Candidate uploadImage(Candidate candidate, MultipartFile candidateImage) {

        String imageUploaded = cloudImageService.upload(candidateImage);
        if (imageUploaded == null) {
            throw new ImageUploadException(CANDIDATE_REGISTRATION_COMPLETED);
        }
        candidate.setCandidateImage(imageUploaded);
        candidate.setRegistered(true);

        return candidate;
    }

    @Override
    public CandidateResponse getCandidateById(Long candidateId) {
        var candidate = candidateRepository.findById(candidateId)
                .orElseThrow(()->new VoterzException(CANDIDATE_NOT_FOUND));
        return mapper.map(candidate, CandidateResponse.class);
    }

    @Override
    public Candidate getCandidate(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(()->new VoterzException(CANDIDATE_NOT_FOUND));
    }

    @Override
    public Page<CandidateResponse> getAllCandidates(int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber -= 1;
        //Get the paginated entities from the repository
        Pageable pageable = PageRequest.of(pageNumber, MAX_PAGE_NUMBER); //Sort.by(sortBy)
        var candidates = candidateRepository.findAll(pageable);
        //map the entities to the DTO
        List<CandidateResponse> candidateDTO = candidates.getContent()
                .stream()
                .map(candidate->mapper.map(candidate, CandidateResponse.class))
                .collect(Collectors.toList());
        //return a new page object
        return new PageImpl<>(candidateDTO, pageable, candidates.getTotalElements());
    }

    @Override
    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }


    @Override
    public UpdateResponse updateCandidate(Long candidateId, JsonPatch jsonPatch) {
        Candidate candidate = getCandidate(candidateId);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.convertValue(candidate, JsonNode.class);
        try {
            JsonNode updatedNode = jsonPatch.apply(jsonNode);
            Candidate updatedcandidate = objectMapper.convertValue(updatedNode, Candidate.class);

            var savedCandidate = candidateRepository.save(updatedcandidate);
            return UpdateResponse.builder()
                    .message(UPDATED)
                    .updateTime(LocalDateTime.now().toString())
                    .build();
        } catch (JsonPatchException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public UpdateResponse changeCandidateImage(Long candidateId, MultipartFile candidateImage) {
        Candidate candidate = getCandidate(candidateId);

        String imageUrl = cloudImageService.upload(candidateImage);

        if (imageUrl.isEmpty()) throw new ImageUploadException(IMAGE_UPLOAD_FAILED);

        candidate.setCandidateImage(imageUrl);
        candidateRepository.save(candidate);

        return UpdateResponse.builder()
                .imageUrl(imageUrl)
                .updateTime(LocalDateTime.now().toString())
                .build();
    }

    @Override
    public UpdateResponse deleteById(Long candidateId) {
        candidateRepository.deleteById(candidateId);
        return UpdateResponse.builder()
                .message(DELETED_SUCCESSFULLY)
                .updateTime(LocalDateTime.now().toString())
                .build();
    }

    @Override
    public UpdateResponse deleteAll() {
        candidateRepository.deleteAll();
        return UpdateResponse.builder()
                .message(ALL_DELETED)
                .updateTime(LocalDateTime.now().toString())
                .build();
    }

    @Override
    public Long numberOfCandidates() {
        return candidateRepository.count();
    }
}