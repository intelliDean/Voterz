package com.api.voterz.services.candidate_services;

import com.api.voterz.data.dtos.requests.CandidateRegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.api.voterz.data.models.ElectionType.PRESIDENT;
import static com.api.voterz.data.models.Party.LP;
import static com.api.voterz.utilities.config.Constants.TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


@SpringBootTest
class CandidateServiceImplTest {
    @Autowired
    private CandidateService candidateService;

    @Test
    void registerCandidate() throws IOException {
        MockMultipartFile file = new MockMultipartFile("candidateImage", new FileInputStream(TEST_IMAGE));

        CandidateRegisterRequest request = CandidateRegisterRequest.builder()
                .firstName("Peter")
                .lastName("Obi")
                .dateOfBirth("23/03/1990")
                .party(LP)
                .electionType(PRESIDENT)
                .image(file)
                .build();
        var response = candidateService.register(request);
        assertThat(response).isNotNull();


    }

}