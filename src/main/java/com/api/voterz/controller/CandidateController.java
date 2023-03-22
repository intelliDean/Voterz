package com.api.voterz.controller;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import com.api.voterz.data.dtos.responses.CandidateResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.services.candidate_services.CandidateService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping("register")
    public ResponseEntity<?> registerCandidate(@ModelAttribute CandidateRegisterRequest registerCandidate) {
        RegisterResponse response = candidateService.register(registerCandidate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        CandidateResponse candidate = candidateService.getCandidateById(candidateId);
        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/getAll/{pageNumber}")
    public ResponseEntity<?> getAllCandidates(@PathVariable int pageNumber) {
        var candidates = candidateService.getAllCandidates(pageNumber);
        return ResponseEntity.ok(candidates.getContent());
    }
    @GetMapping("getAll")
    public  ResponseEntity<?> getAll() {
        var response = candidateService.getCandidates();
        return ResponseEntity.ok(response);
    }


    @PatchMapping(value = "{candidateId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCandidate(@PathVariable Long candidateId, @RequestBody JsonPatch updatedPatch) {
        try {
            var response = candidateService.updateCandidate(candidateId, updatedPatch);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PostMapping("changeImage/{candidateId}")
    public ResponseEntity<?> changeCandidateImage(@PathVariable Long candidateId, @ModelAttribute MultipartFile candidateImage) {
        var response = candidateService.changeCandidateImage(candidateId, candidateImage);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{candidateId}")
    public ResponseEntity<?> deleteCandidateById(@PathVariable Long candidateId) {
        var response = candidateService.deleteById(candidateId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllCandidates() {
       var response = candidateService.deleteAll();
        return ResponseEntity.ok(response);
    }
}
