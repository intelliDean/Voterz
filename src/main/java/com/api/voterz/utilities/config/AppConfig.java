package com.api.voterz.utilities.config;

import com.api.voterz.utilities.JwtUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class AppConfig {
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String cloudApiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${mail.api.key}")
    private String mailApiKey;
    @Value("${sendinblue.mail.url}")
    private String mailUrl;
    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", cloudApiKey, "api_secret", apiSecret));
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
     @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(jwtSecret);
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper;
    }

    @Bean
    public WebClient getWebClientBuilder() {
        return WebClient.builder()
            .baseUrl(mailUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("api-key", mailApiKey)
            .build();
    }
    @Bean
    public MailConfig mailConfig() {
        return new MailConfig(mailApiKey, mailUrl);
    }
}
