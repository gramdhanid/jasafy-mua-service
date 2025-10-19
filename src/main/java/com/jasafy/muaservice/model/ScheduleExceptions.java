package com.jasafy.muaservice.model;

import com.jasafy.helper.model.ReferenceBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mua_sys_schedule_exception")
public class ScheduleExceptions extends ReferenceBase {
    @ManyToOne
    @JoinColumn
    private Profiles profiles;
    private LocalDateTime exceptionDate;
    private ExceptionType exceptionType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;

    public enum ExceptionType {
        CUSTOM_HOURS, UNAVAILABLE
    }

}
