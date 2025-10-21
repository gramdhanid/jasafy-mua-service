package com.jasafy.muaservice.services.profiles.wrapper;

import lombok.Data;

@Data
public class ProfilesRequest {
    private Long userId;
    private Long muaId;
    private String businessName;
    private String bio;
    private Integer yearsExperience;
    private Double averageRating;
    private Integer totalReviews;
    private Boolean active;
    private Boolean verified;
}