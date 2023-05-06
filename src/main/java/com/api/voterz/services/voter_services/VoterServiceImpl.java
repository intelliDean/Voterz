package com.api.voterz.services.voter_services;

import com.api.voterz.data.dtos.requests.VoterRegisterRequest;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.models.Details;
import com.api.voterz.data.models.Voter;
import com.api.voterz.data.repositories.VoterRepository;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.services.details_service.DetailService;
import com.api.voterz.utilities.Utilities;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class VoterServiceImpl implements VoterService {
    private final VoterRepository voterRepository;
    private final CloudImageService cloudImageService;
    private final DetailService detailService;


    @Override
    public RegisterResponse register(VoterRegisterRequest voterRequest) {
        int age = Utilities.calculateAge(voterRequest.getDateOfBirth());
        boolean invalidAge = age < 18;
        if (invalidAge) throw new VoterzException("Registration failed");

        Voter voter = Voter.builder()
                .details(Details.builder()
                        .firstName(voterRequest.getFirstName())
                        .lastName(voterRequest.getLastName())
                        .email(voterRequest.getEmail())
                        .password(voterRequest.getPassword())
                        .age(age)
                        .build())
                .build();
        Details details = detailService.uploadImage(voter.getDetails(), voterRequest.getImage());
        details.setRegistered(true);
        voterRepository.save(voter);

        return RegisterResponse.builder()
                .message("Registration completed successfully")
                .registered(true)
                .build();
    }

    @Override
    public Voter getVoterById(Long voterId) {
        return voterRepository.findById(voterId)
                .orElseThrow(() -> new VoterzException("Voter could not be found"));
    }

    @Override
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    @Override
    public GlobalApiResponse updateVoter(Long voterId, JsonPatch updatePatch) {
        Voter voter = getVoterById(voterId);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.convertValue(voter, JsonNode.class);
        try {
            JsonNode updatedNode = updatePatch.apply(node);
            Voter updatedVoter = mapper.convertValue(updatedNode, Voter.class);
            Voter savedVoter = voterRepository.save(updatedVoter);
            return GlobalApiResponse.builder()
                    .message("Voter updated successfully")
                    .build();
        } catch (JsonPatchException e) {
            throw new VoterzException("Update failed");
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
    public void saveVoter(Voter voter) {
        voterRepository.save(voter);
    }
}