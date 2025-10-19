package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mua_sys_location")
public class Location extends ReferenceBase {
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profiles profiles;
    private Long provinceId;
    private Long regencyId;
    private Long districtId;
    private Long villagesId;
    @Column(length = 300)
    private String alamatLengkap;
}
