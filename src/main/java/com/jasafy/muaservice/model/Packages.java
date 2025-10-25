package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "mua_sys_packages")
public class Packages extends ReferenceBase {
    @ManyToOne
    @JoinColumn(name = "mua_id")
    private Profiles profiles;
    @Column(length = 100)
    private String packageName;
    private BigDecimal price;
    private LocalTime duration;
    private String features;
    private Boolean active;
}
