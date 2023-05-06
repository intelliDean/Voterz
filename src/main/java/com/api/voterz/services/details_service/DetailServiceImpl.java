package com.api.voterz.services.details_service;

import com.api.voterz.data.models.Details;
import com.api.voterz.data.repositories.DetailsRepository;
import com.api.voterz.exceptions.ImageUploadException;
import com.api.voterz.utilities.cloud_image.CloudImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.api.voterz.utilities.config.Constants.INCOMPLETE_REG;

@AllArgsConstructor
@Service
public class DetailServiceImpl implements DetailService {
    private final DetailsRepository detailsRepository;
    private final CloudImageService cloudImageService;

    public Details uploadImage(Details details, MultipartFile candidateImage) {
        String imageUploaded = cloudImageService.upload(candidateImage);
        if (imageUploaded == null) {
            throw new ImageUploadException(INCOMPLETE_REG);
        }
        details.setImage(imageUploaded);
        return details;
    }
}
