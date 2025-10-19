package com.jasafy.muaservice.services.schedule.wrapper;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private Long scheduleId;
    private Long profileId;
    private Integer dayOfWeek;
    private LocalTime fromTimeSlot;
    private LocalTime toTimeSlot;
    private Integer slotDuration;
    private Boolean active;

}
