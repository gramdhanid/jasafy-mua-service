package com.jasafy.muaservice.services.profiles.wrapper;

import com.jasafy.muaservice.model.Location;
import lombok.Data;

@Data
public class ProfilesPagingResponse {
	private Integer totalReviews;
	private Location muaLocation;
	private Double averageRating;
	private Boolean verified;
	private String bio;
	private Boolean active;
	private Integer yearsExperience;
}