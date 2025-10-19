package com.jasafy.muaservice.services.profiles;

import com.jasafy.helper.util.exception.CustomException;
import com.jasafy.helper.util.exception.ErrorCode;
import com.jasafy.helper.util.messages.BaseMessages;
import com.jasafy.muaservice.model.Location;
import com.jasafy.muaservice.model.Profiles;
import com.jasafy.muaservice.repository.ProfilesRepository;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesRequest;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfilesService {

    private final ProfilesRepository profilesRepository;

    public ProfilesService(ProfilesRepository profilesRepository) {
        this.profilesRepository = profilesRepository;
    }

    public ProfilesResponse getProfilesResponse(Long userId) throws CustomException {
        log.info("getProfilesResponse called");
        Profiles profiles = profilesRepository.findByIdAndDeletedAndActive(userId, false, true)
                .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
        log.info("getProfilesResponse returning profiles {} response", userId);
        return toResponse(profiles);
    }

    @Transactional
    public ProfilesResponse createUpdateProfiles(ProfilesRequest profilesDTO) throws CustomException {
        try {
            Profiles profiles = new Profiles();
            if (profilesDTO.getUserId() != null) {
                profiles = profilesRepository
                        .findByIdAndDeletedAndActive(profilesDTO.getUserId(), false, true)
                        .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
            } else {
                profiles.setActive(false);
                profiles.setVerified(false);
                profiles.setDeleted(false);
            }
            profiles.setUserId(profilesDTO.getUserId());
            profiles.setBusinessName(profilesDTO.getBusinessName());
            profiles.setBio(profilesDTO.getBio());
            // location akan berbeda api
            profiles.setYearsExperience(profilesDTO.getYearsExperience());
            profiles.setAverageRating(profilesDTO.getAverageRating());
            profiles.setTotalReviews(profiles.getTotalReviews());
            profiles.setBio(profilesDTO.getBio());
            profilesRepository.save(profiles);
            return toResponse(profiles);
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    private static Location setlocation(Profiles profiles) {
        Location location = new Location();
        location.setProfiles(profiles);
        location.setProvinceId(profiles.getMuaLocation().getProvinceId());
        location.setRegencyId(profiles.getMuaLocation().getRegencyId());
        location.setDistrictId(profiles.getMuaLocation().getDistrictId());
        location.setVillagesId(profiles.getMuaLocation().getVillagesId());
        location.setAlamatLengkap(profiles.getMuaLocation().getAlamatLengkap());
        location.setDeleted(false);
        return location;
    }

    private ProfilesResponse toResponse(Profiles profiles) {
        return new ProfilesResponse(
                profiles.getUserId(),
                profiles.getBusinessName(),
                profiles.getBio(),
                profiles.getYearsExperience(),
                profiles.getAverageRating(),
                profiles.getTotalReviews(),
                profiles.getActive(),
                profiles.getVerified()
        );
    }
}
