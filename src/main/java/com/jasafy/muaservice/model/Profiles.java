package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mua_sys_profile")
public class Profiles extends ReferenceBase {
    private Long userId;
    private String username;
    @Column(name = "business_name", length = 100)
    private String businessName;
    @Column(length = 300)
    private String bio;
    @ManyToOne
    @JoinColumn
    private Location muaLocation;
    private Integer yearsExperience;
    private Double averageRating;
    private Integer totalReviews;
    private Boolean active;
    private Boolean verified;
}
