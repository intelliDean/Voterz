package com.api.voterz.controller;

import com.api.voterz.data.dtos.responses.CollationResponse;
import com.api.voterz.services.result_services.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/result")
public class ResultController {
    private ResultService resultService;

    @GetMapping("collate")
    public ResponseEntity<?> collateResult(){
        CollationResponse response = resultService.collateResult();
        return ResponseEntity.ok(response);
    }

    @GetMapping("result")
    public ResponseEntity<?> getResult() {
        var result = resultService.result();
        return ResponseEntity.ok(result);
    }
}
