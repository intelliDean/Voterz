package com.api.voterz.utilities.config;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Constants {
    public static final String TEST_IMAGE="C:\\Users\\Dean\\IdeaProjects\\voterz\\src\\main\\resources\\WhatsApp Image 2023-03-19 at 20.22.58.jpg";
    public static final int MAX_PAGE_NUMBER=5;
    public static final String NOT_QUALIFIED="You are not qualified";
    public static final String COMPLETE_REG ="Registration Completed";
    public static final String INCOMPLETE_REG ="Candidate registration could not be completed";
    public static final String CANDIDATE_NOT_FOUND="Candidate could not be found";
    public static final String UPDATED="Updated successfully";
    public static final String IMAGE_UPLOAD_FAILED="Image upload failed";
    public static final String DELETED_SUCCESSFULLY="Deleted successfully";
    public static final String ALL_DELETED="All Deleted successfully";

}
