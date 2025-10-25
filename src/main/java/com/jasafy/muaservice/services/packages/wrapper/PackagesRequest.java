package com.jasafy.muaservice.services.packages.wrapper;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class PackagesRequest {
    private Long muaId;
    private Long packageId;
    private String packageName;
    private BigDecimal price;
    private LocalTime duration;
    private String features;
}
