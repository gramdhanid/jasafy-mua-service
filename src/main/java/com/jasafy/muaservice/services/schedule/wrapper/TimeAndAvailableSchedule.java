package com.jasafy.muaservice.services.schedule.wrapper;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeAndAvailableSchedule {
    private LocalTime time;
    private Boolean available;
}
