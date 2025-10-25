package com.jasafy.muaservice.services.profiles;

import com.jasafy.helper.dto.BaseResponseExternal;
import com.jasafy.helper.util.apiresponse.PagingUtil;
import com.jasafy.helper.util.exception.CustomException;
import com.jasafy.helper.util.exception.ErrorCode;
import com.jasafy.helper.util.messages.BaseMessages;
import com.jasafy.muaservice.client.AuthServiceClient;
import com.jasafy.muaservice.client.dto.ResponseUser;
import com.jasafy.muaservice.model.Location;
import com.jasafy.muaservice.model.Profiles;
import com.jasafy.muaservice.repository.ProfilesRepository;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesPagingResponse;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesRequest;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
public class ProfilesService {

    private final ProfilesRepository profilesRepository;
    private final AuthServiceClient authServiceClient;

    public ProfilesService(ProfilesRepository profilesRepository, AuthServiceClient authServiceClient) {
        this.profilesRepository = profilesRepository;
        this.authServiceClient = authServiceClient;
    }

    public ProfilesResponse getProfilesResponse(Long muaId) throws CustomException {
        log.info("getProfilesResponse called");
        Profiles profiles = profilesRepository.findByIdAndDeletedAndActive(muaId, false, true)
                .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
        log.info("getProfilesResponse returning profiles {} response", muaId);
        return toResponse(profiles);
    }


    @Transactional
    public ProfilesResponse createUpdateProfiles(ProfilesRequest profilesDTO) throws CustomException {
        try {
            log.info("createUpdateProfiles called");
            Profiles profiles = new Profiles();
            BaseResponseExternal<ResponseUser> responseUser = authServiceClient.getUserId(profilesDTO.getUserId());
            if (ObjectUtils.isEmpty(responseUser.getData())) {
                throw new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE);
            }
            profiles.setUserId(responseUser.getData().getUserId());
            if (profilesDTO.getMuaId() != null) {
                profiles = profilesRepository
                        .findByIdAndDeletedAndActive(profilesDTO.getMuaId(), false, true)
                        .orElseThrow(() -> new CustomException(BaseMessages.USER_NOT_FOUND, ErrorCode.GENERIC_FAILURE));
            } else {
                profiles.setActive(true);
                profiles.setVerified(false);
                profiles.setDeleted(false);
            }
            profiles.setUserId(profilesDTO.getUserId());
            profiles.setBusinessName(profilesDTO.getBusinessName());
            profiles.setBio(profilesDTO.getBio());
            // location akan berbeda api
            profiles.setYearsExperience(profilesDTO.getYearsExperience());
            profiles.setAverageRating(profilesDTO.getAverageRating());
            profiles.setTotalReviews(profilesDTO.getTotalReviews());
            profiles.setBio(profilesDTO.getBio());
            profilesRepository.save(profiles);
            log.info("createUpdateProfiles returning profiles {} response", profiles.getId());
            return toResponse(profiles);
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    public Page<ProfilesPagingResponse> getPagingRespone (String muaName, Integer startPage, Integer pageSize, String sortBy,
                                            String sortDir) {
        try {
            log.info("Fetching profiles page: {}, size: {}, search: {}", startPage, pageSize, muaName);
            Pageable pageable = PageRequest.of(startPage, pageSize, PagingUtil.sortDirection(sortBy, sortDir));
            Page<Profiles> getPageable = profilesRepository
                    .findByBusinessNameContainsIgnoreCaseAndActiveAndDeleted(muaName, true, false, pageable);
            List<ProfilesPagingResponse> dtos = getPageable.getContent()
                    .stream()
                    .map(this::toDto)
                    .toList();
            return new PageImpl<>(dtos, PageRequest.of(startPage, pageSize), getPageable.getTotalElements());
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    private ProfilesPagingResponse toDto(Profiles profiles) {
        ProfilesPagingResponse dto = new ProfilesPagingResponse();
        dto.setTotalReviews(profiles.getTotalReviews());
        dto.setMuaLocation(profiles.getMuaLocation());
        dto.setAverageRating(profiles.getAverageRating());
        dto.setVerified(profiles.getVerified());
        dto.setBio(profiles.getBio());
        dto.setActive(profiles.getActive());
        dto.setYearsExperience(profiles.getYearsExperience());
        return dto;
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
                profiles.getId(),
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
