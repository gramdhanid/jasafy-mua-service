package com.jasafy.muaservice.services.profiles.wrapper;


public record ProfilesResponse(
        Long userId,
        String businessName,
        String bio,
        Integer yearsExperience,
        Integer averageRating,
        Integer totalReviews,
        Boolean active,
        Boolean verified) {
}
