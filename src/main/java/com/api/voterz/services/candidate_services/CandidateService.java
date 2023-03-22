package com.api.voterz.services.candidate_services;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.responses.CandidateResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Candidate;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
   RegisterResponse register(CandidateRegisterRequest candidateRegister);
   CandidateResponse getCandidateById(Long candidateId);
   Candidate getCandidate(Long candidateId);
   Page<CandidateResponse> getAllCandidates(int pageNumber);
   List<Candidate> getCandidates();
   UpdateResponse updateCandidate(Long candidateId, JsonPatch jsonPatch);
   UpdateResponse changeCandidateImage(Long candidateId, MultipartFile candidateImage);
   UpdateResponse deleteById(Long candidateId);
   UpdateResponse deleteAll();
   Long numberOfCandidates();
}
