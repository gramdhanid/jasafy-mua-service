package com.jasafy.muaservice.services.schedule.wrapper;

import com.jasafy.muaservice.model.Profiles;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ScheduleResponse(
        Profiles profiles,
        LocalTime dateSlot,
        LocalTime timeSlot) {
}
