package com.jasafy.muaservice.services.schedule;

import com.jasafy.helper.util.exception.CustomException;
import com.jasafy.helper.util.exception.ErrorCode;
import com.jasafy.helper.util.messages.BaseMessages;
import com.jasafy.muaservice.model.ScheduleTemplate;
import com.jasafy.muaservice.repository.ProfilesRepository;
import com.jasafy.muaservice.repository.ScheduleRepository;
import com.jasafy.muaservice.services.schedule.wrapper.ScheduleRequest;
import com.jasafy.muaservice.services.schedule.wrapper.ScheduleResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfilesRepository profilesRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ProfilesRepository profilesRepository) {
        this.scheduleRepository = scheduleRepository;
        this.profilesRepository = profilesRepository;
    }

    @Transactional
    public ScheduleResponse createUpdateSchedule(ScheduleRequest request) {
        try {
            ScheduleTemplate schedule = new ScheduleTemplate();
            if (request.getScheduleId() != null) {
                schedule = scheduleRepository.findByIdAndDeleted(request.getScheduleId(), false)
                        .orElseThrow(() -> new CustomException(BaseMessages.SCHEDULE_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
            }
            // check hari dan jam nya apakah di range jam tersebut sudah ada atau belum
            schedule.setProfiles(profilesRepository
                    .findByIdAndDeletedAndActive(request.getProfileId(), false, true)
                    .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE)));
            schedule.setFromTime(request.getFromTimeSlot());
            schedule.setToTime(request.getToTimeSlot());
            schedule.setSlotDuration(request.getSlotDuration());
            schedule.setActive(request.getActive());
            return toScheduleResponse(scheduleRepository.save(schedule));
        } catch (Exception e){
            throw new CustomException(BaseMessages.FAILED_CREATE_UPDATE_SERVICE, ErrorCode.GENERIC_FAILURE);
        }
    }

    public List<ScheduleResponse> getListScheduleMua(Long muaId) {
        log.info("getListSchedule for muaId: {} called", muaId);
        if (muaId != null) {
            profilesRepository.findById(muaId)
                    .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
            return toListScheduleResponse(scheduleRepository.findByProfiles_IdAndActiveAndDeleted(muaId, true, false));
        } else {
            throw new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE);
        }
    }

    private ScheduleResponse toScheduleResponse(ScheduleTemplate schedule) {
        return new ScheduleResponse(
                schedule.getProfiles(),
                schedule.getFromTime(),
                schedule.getToTime()
        );
    }

    private List<ScheduleResponse> toListScheduleResponse(List<ScheduleTemplate> schedules) {
        List<ScheduleResponse> responses = new ArrayList<>();
        for (ScheduleTemplate schedule : schedules) {
            responses.add(toScheduleResponse(schedule));
        }
        return responses;
    }
}
