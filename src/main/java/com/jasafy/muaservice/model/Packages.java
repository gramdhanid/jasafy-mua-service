package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mua_sys_packages")
public class Packages extends ReferenceBase {
    @ManyToOne
    @JoinColumn
    private Profiles profiles;
    @Column(length = 100)
    private String packageName;
    private BigDecimal price;
    private LocalDateTime duration;
    private String features;
    private Boolean active;
}
