package com.jasafy.muaservice.controller;

import com.jasafy.helper.util.apiresponse.CustomResponse;
import com.jasafy.helper.util.apiresponse.CustomResponseGenerator;
import com.jasafy.muaservice.services.profiles.ProfilesService;
import com.jasafy.muaservice.services.profiles.wrapper.ProfilesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ProfilesController {

    private final ProfilesService profilesService;
    private final CustomResponseGenerator customResponseGenerator;

    public ProfilesController(ProfilesService profilesService, CustomResponseGenerator customResponseGenerator) {
        this.profilesService = profilesService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @GetMapping
    public CustomResponse<Object> getProfiles(@RequestParam Long userId) {
        try {
            return customResponseGenerator.successResponse(profilesService.getProfilesResponse(userId), HttpStatus.OK.toString());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @PostMapping
    public CustomResponse<Object> createUpdateProfileMua(@RequestBody ProfilesRequest profilesRequest) {
        try {
            return customResponseGenerator.successResponse(profilesService.createUpdateProfiles(profilesRequest), HttpStatus.CREATED.toString());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }


}
