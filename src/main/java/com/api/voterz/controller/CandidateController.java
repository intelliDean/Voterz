package com.api.voterz.controller;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.responses.CandidateDTO;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Candidate;
import com.api.voterz.services.candidate_services.CandidateService;
import com.api.voterz.utilities.Paginate;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/candidate")
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<?> registerCandidate(@ModelAttribute CandidateRegisterRequest registerCandidate) {
        GlobalApiResponse response = candidateService.register(registerCandidate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{candidateId}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable Long candidateId) {
        CandidateDTO candidate = candidateService.getCandidateById(candidateId);
        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/getAll/{pageNumber}")
    public ResponseEntity<Paginate<CandidateDTO>> getAllCandidates(@PathVariable int pageNumber) {
        Paginate<CandidateDTO> candidates = candidateService.getAllCandidates(pageNumber);
        return ResponseEntity.ok(candidates);
    }
    @GetMapping("getAll")
    public  ResponseEntity<?> getAll() {
        List<Candidate> response = candidateService.getCandidates();
        return ResponseEntity.ok(response);
    }
    @PatchMapping("{candidateId}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long candidateId, @RequestBody JsonPatch updatedPatch) {
        try {
            GlobalApiResponse response = candidateService.updateCandidate(candidateId, updatedPatch);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("changeImage/{candidateId}")
    public ResponseEntity<GlobalApiResponse> changeCandidateImage(@PathVariable Long candidateId, @ModelAttribute MultipartFile candidateImage) {
        var response = candidateService.changeCandidateImage(candidateId, candidateImage);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{candidateId}")
    public ResponseEntity<GlobalApiResponse> deleteCandidateById(@PathVariable Long candidateId) {
        var response = candidateService.deleteById(candidateId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<GlobalApiResponse> deleteAllCandidates() {
       var response = candidateService.deleteAll();
        return ResponseEntity.ok(response);
    }
}
