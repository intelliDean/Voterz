package com.api.voterz.services.voter_services;

import com.api.voterz.data.dtos.requests.VoterRegisterRequest;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Voter;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VoterService {
    RegisterResponse register(VoterRegisterRequest voterRequest);
    RegisterResponse completeRegistration(Long voterId, MultipartFile voterImage);
    Voter getVoterById(Long voterId);
    List<Voter> getAllVoters();
    UpdateResponse updateVoter(Long voterId, JsonPatch updatePatch);
    void deleteVoterById(Long voterId);
    void deleteAll();
    Voter saveVoter(Voter voter);

}