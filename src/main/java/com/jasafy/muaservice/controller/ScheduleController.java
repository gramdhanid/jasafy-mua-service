package com.jasafy.muaservice.controller;

import com.jasafy.helper.util.apiresponse.CustomResponse;
import com.jasafy.helper.util.apiresponse.CustomResponseGenerator;
import com.jasafy.helper.util.exception.CustomException;
import com.jasafy.muaservice.services.schedule.ScheduleService;
import com.jasafy.muaservice.services.schedule.wrapper.ScheduleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CustomResponseGenerator customResponseGenerator;

    public ScheduleController(ScheduleService scheduleService, CustomResponseGenerator customResponseGenerator) {
        this.scheduleService = scheduleService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @GetMapping
    public CustomResponse<Object> getSchedule(@RequestParam Long muaId) throws CustomException {
        try {
            return customResponseGenerator.successResponse(scheduleService.getListScheduleMua(muaId), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @PostMapping
    public CustomResponse<Object> createSchedule(@RequestBody ScheduleRequest scheduleRequest) throws CustomException {
        try {
            return customResponseGenerator.successResponse(scheduleService.createUpdateSchedule(scheduleRequest), HttpStatus.CREATED.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
