package com.api.voterz.services.candidate_services;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.responses.CandidateDTO;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.utilities.Paginate;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
   GlobalApiResponse register(CandidateRegisterRequest candidateRegister);
   CandidateDTO getCandidateById(Long candidateId);
   Candidate getCandidate(Long candidateId);
   Paginate<CandidateDTO> getAllCandidates(int pageNumber);
   List<Candidate> getCandidates();
   GlobalApiResponse updateCandidate(Long candidateId, JsonPatch jsonPatch);
   GlobalApiResponse changeCandidateImage(Long candidateId, MultipartFile candidateImage);
   GlobalApiResponse deleteById(Long candidateId);
   GlobalApiResponse deleteAll();
   Long numberOfCandidates();
}
