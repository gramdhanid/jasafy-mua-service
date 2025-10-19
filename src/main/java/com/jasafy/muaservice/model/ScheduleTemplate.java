package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "mua_sys_schedule_template")
public class ScheduleTemplate extends ReferenceBase {
    @ManyToOne
    @JoinColumn
    private Profiles profiles;
    private Integer dayOfWeek;
    @Column(name = "avail_from")
    private LocalTime fromTime;
    @Column(name = "avail_to")
    private LocalTime toTime;
    private Integer slotDuration;
    private Boolean active;
}
