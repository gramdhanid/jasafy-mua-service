package com.jasafy.muaservice.services.schedule.wrapper;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SchedulaAvail {
    private LocalDate date;
    private List<TimeAndAvailableSchedule> slots;
}
