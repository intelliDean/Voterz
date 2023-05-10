package com.api.voterz.services.notification;

import com.api.voterz.data.dtos.requests.EmailRequest;
import com.api.voterz.utilities.config.MailConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    private final MailConfig mailConfig;
    private final WebClient webClient;

    @Override
//    public String sendHTMLMail(EmailRequest request) {
//        String url = mailConfig.getMailUrl();
//        String apiKey = mailConfig.getMailApiKey();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("api-key", apiKey);
//
//
//
//        return webClient.post()
//                .uri(url)
//                .headers(header -> header.addAll(headers))
//                .body(BodyInserters.fromValue(request))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//    }

    public String sendHTMLMail(EmailRequest request) {
//    WebClient client = WebClient.builder()
//            .baseUrl(mailConfig.getMailUrl())
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .defaultHeader("api-key", mailConfig.getMailApiKey())
//            .build();

    return webClient.post()
            .bodyValue(request)
            .retrieve()
            .bodyToMono(String.class)
            .block();
}

}
