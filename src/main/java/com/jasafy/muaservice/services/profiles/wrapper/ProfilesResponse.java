package com.jasafy.muaservice.services.profiles.wrapper;


public record ProfilesResponse(
        Long userId,
        String businessName,
        String bio,
        Integer yearsExperience,
        Double averageRating,
        Integer totalReviews,
        Boolean active,
        Boolean verified) {
}
