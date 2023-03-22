package com.api.voterz.utilities.cloud_image;

import org.springframework.web.multipart.MultipartFile;

public interface CloudImageService {
    String upload(MultipartFile image);
}
