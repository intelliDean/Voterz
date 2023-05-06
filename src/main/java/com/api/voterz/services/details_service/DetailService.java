package com.api.voterz.services.details_service;

import com.api.voterz.data.models.Details;
import org.springframework.web.multipart.MultipartFile;

public interface DetailService {
    Details uploadImage(Details details, MultipartFile candidateImage);
}
