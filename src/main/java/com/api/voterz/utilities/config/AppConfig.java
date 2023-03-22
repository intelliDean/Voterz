package com.api.voterz.utilities.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static final String TEST_IMAGE="C:\\Users\\Dean\\IdeaProjects\\voterz\\src\\main\\resources\\WhatsApp Image 2023-03-19 at 20.22.58.jpg";
    public static final int MAX_PAGE_NUMBER=3;
    public static final String NOT_QUALIFIED="You are not qualified";
    public static final String CANDIDATE_SAVED="Candidate saved! Upload image to complete registration";
    public static final String CANDIDATE_REGISTRATION_COMPLETED="Candidate registration could not be completed";
    public static final String CANDIDATE_NOT_FOUND="Candidate could not be found";
    public static final String UPDATED="Updated successfully";
    public static final String IMAGE_UPLOAD_FAILED="Image upload failed";
    public static final String DELETED_SUCCESSFULLY="Deleted successfully";
    public static final String ALL_DELETED="All Deleted successfully";


    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",cloudName,
                        "api_key",apiKey, "api_secret",apiSecret));
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
