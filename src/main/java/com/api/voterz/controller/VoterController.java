package com.api.voterz.controller;

import com.api.voterz.data.dtos.requests.VoterRegisterRequest;
import com.api.voterz.data.dtos.responses.GlobalApiResponse;
import com.api.voterz.data.dtos.responses.RegisterResponse;
import com.api.voterz.data.dtos.responses.UpdateResponse;
import com.api.voterz.data.models.Voter;
import com.api.voterz.services.voter_services.VoterService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/voters")
public class VoterController {
    private final VoterService voterService;


    @PostMapping("/register")
    public ResponseEntity<?> registerVoters(@RequestBody VoterRegisterRequest voterRegisterRequest) {
        RegisterResponse response = voterService.register(voterRegisterRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{voterId}")
    public ResponseEntity<?> getVoterById(@PathVariable Long voterId) {
        Voter voter = voterService.getVoterById(voterId);
        return ResponseEntity.ok(voter);
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllVoters() {
        List<Voter> voters = voterService.getAllVoters();
        return ResponseEntity.ok(voters);
    }
    @PatchMapping("{voterId}")
    public ResponseEntity<GlobalApiResponse> updateVoter(@PathVariable Long voterId, @RequestBody JsonPatch updatePatch) {
        GlobalApiResponse response = voterService.updateVoter(voterId, updatePatch);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("{voterId}")
    public ResponseEntity<UpdateResponse> deleteVoterById(@PathVariable Long voterId) {
        voterService.deleteVoterById(voterId);
        UpdateResponse response = UpdateResponse.builder()
                .message("Voter deleted successfully")
                .updateTime(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        voterService.deleteAll();
        UpdateResponse response = UpdateResponse.builder()
                .message("All Voters deleted successfully")
                .updateTime(LocalDateTime.now().toString())
                .build();
        return ResponseEntity.ok(response);
    }
}
