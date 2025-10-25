package com.jasafy.muaservice.controller;

import com.jasafy.helper.util.apiresponse.CustomResponse;
import com.jasafy.helper.util.apiresponse.CustomResponseGenerator;
import com.jasafy.muaservice.services.packages.PackagesService;
import com.jasafy.muaservice.services.packages.wrapper.PackagesRequest;
import com.jasafy.muaservice.services.packages.wrapper.PackagesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackagesController {

    private final PackagesService packagesService;
    private final CustomResponseGenerator customResponseGenerator;

    public PackagesController(PackagesService packagesService, CustomResponseGenerator customResponseGenerator) {
        this.packagesService = packagesService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @PostMapping("/submit")
    public ResponseEntity<CustomResponse<PackagesResponse>> submit(@RequestBody PackagesRequest packagesRequest,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        PackagesResponse resp = packagesService.submit(packagesRequest, userDetails);
        CustomResponse<PackagesResponse> body = customResponseGenerator
                .successResponse(resp, HttpStatus.CREATED.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/list/{username}")
    CustomResponse<List<PackagesResponse>> getListOfPackages(@PathVariable String username) {
        try {
            return customResponseGenerator.successResponse(
                    packagesService.getListOfPackages(username), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping()
    ResponseEntity<CustomResponse<PackagesResponse>> getDetailPackage(@RequestParam Long packageId) {
        PackagesResponse response = packagesService.getPackages(packageId);
        CustomResponse<PackagesResponse> body = customResponseGenerator
                .successResponse(response, HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @DeleteMapping()
    ResponseEntity<CustomResponse<Boolean>> deletePackage(@RequestParam Long packageId) {
        Boolean response = packagesService.deletePackage(packageId);
        CustomResponse<Boolean> body = customResponseGenerator
                .successResponse(response, HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
