package com.api.voterz.services.voter_services;

import com.api.voterz.data.dtos.requests.VoterRegisterRequest;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Voter;
import com.api.voterz.data.repositories.VoterRepository;
import com.api.voterz.exceptions.ImageUploadException;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class VoterServiceImpl implements VoterService {
    private final VoterRepository voterRepository;
    private CloudImageService cloudImageService;


    @Override
    public RegisterResponse register(VoterRegisterRequest voterRequest) {
        //validate age
        boolean invalidAge = voterRequest.getAge() < 18;
        if (voterRequest.getName() == null || invalidAge) {
            throw new VoterzException("Registration failed");
        }

        //get user data and save in the db
        Voter voter = Voter.builder()
                .name(voterRequest.getName())
                .age(voterRequest.getAge())
                .build();
        Voter savedVoter = voterRepository.save(voter);

        return RegisterResponse.builder()
                .id(savedVoter.getId())
                .statusCode(HttpStatus.CREATED.value())
                .message("Voter saved! Upload picture to complete registration")
                .registered(true)
                .build();
    }

    @Override
    public RegisterResponse completeRegistration(Long voterId, MultipartFile voterImage) {
        Voter voter = getVoterById(voterId);

        String imageUrl = uploadImage(voterImage);

        return endRegistration(voter, imageUrl);
    }

    @Override
    public Voter getVoterById(Long voterId) {
        return voterRepository.findById(voterId)
                .orElseThrow(()->new VoterzException("Voter could not be found"));
    }

    @Override
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    @Override
    public UpdateResponse updateVoter(Long voterId, JsonPatch updatePatch) {
        Voter voter = getVoterById(voterId);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.convertValue(voter, JsonNode.class);
        try {
            JsonNode updatedNode = updatePatch.apply(node);
            Voter updatedVoter = mapper.convertValue(updatedNode, Voter.class);
            Voter savedVoter = voterRepository.save(updatedVoter);
            return UpdateResponse.builder()
                    .message("Voter updated successfully")
                    .updateTime(LocalDateTime.now().toString())
                    .build();
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteVoterById(Long voterId) {
        voterRepository.deleteById(voterId);
    }

    @Override
    public void deleteAll() {
        voterRepository.deleteAll();
    }

    @Override
    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    private RegisterResponse endRegistration(Voter voter, String imageUrl) {
        voter.setImage(imageUrl);
        voter.setRegistered(true);

        Voter registeredVoter = voterRepository.save(voter);
        return RegisterResponse.builder()
                .id(registeredVoter.getId())
                .statusCode(HttpStatus.CREATED.value())
                .message("Voter registered successfully")
                .registered(true)
                .build();
    }

    private String uploadImage(MultipartFile voterImage) {
        //upload image
        String imageUrl = cloudImageService.upload(voterImage);
        //if image not uploaded, data saved but registration incomplete
        if (imageUrl == null) {
            throw new ImageUploadException("Registration incomplete! Upload your image to complete registration");
        }
        return imageUrl;
    }


}