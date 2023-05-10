package com.api.voterz.services.voter_services;

import com.api.voterz.data.dtos.requests.VoterRegisterRequest;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.models.Details;
import com.api.voterz.data.models.Voter;
import com.api.voterz.data.repositories.VoterRepository;
import com.api.voterz.exceptions.VoterzException;
import com.api.voterz.services.details_service.DetailService;
import com.api.voterz.utilities.Paginate;
import com.api.voterz.utilities.Utilities;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

import static com.api.voterz.utilities.Utilities.MAX_PER_PAGE;

@AllArgsConstructor
@Slf4j
@Service
public class VoterServiceImpl implements VoterService {
    private final VoterRepository voterRepository;
    private final CloudImageService cloudImageService;
    private final DetailService detailService;
    private final ModelMapper modelMapper;


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
                        .lg(voterRequest.getLg())
                        .constituency(voterRequest.getConstituency())
                        .state(voterRequest.getState())
                        .build())
                .build();
        voter.getDetails().setRegistered(true);
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
    public Page<Voter> getVoters(int pageNumber) {
        pageNumber = pageNumber < 0 ? 1 : pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, MAX_PER_PAGE);
        Page<Voter> voters = voterRepository.findAll(pageable);
        Type type = new TypeToken<Paginate<Voter>>() {
        }.getType();
        return modelMapper.map(voters, type);
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
            voter = mapper.convertValue(updatedNode, Voter.class);
            voterRepository.save(voter);
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