package com.jasafy.muaservice.services.packages;

import com.jasafy.helper.util.exception.CustomException;
import com.jasafy.helper.util.exception.ErrorCode;
import com.jasafy.muaservice.model.Packages;
import com.jasafy.muaservice.repository.PackagesRepository;
import com.jasafy.muaservice.repository.ProfilesRepository;
import com.jasafy.muaservice.services.packages.wrapper.PackagesRequest;
import com.jasafy.muaservice.services.packages.wrapper.PackagesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PackagesService {

    private final PackagesRepository packagesRepository;
    private final ProfilesRepository profilesRepository;

    public PackagesService(PackagesRepository packagesRepository, ProfilesRepository profilesRepository) {
        this.packagesRepository = packagesRepository;
        this.profilesRepository = profilesRepository;
    }

    public PackagesResponse submit(PackagesRequest packagesRequest, UserDetails userDetails) {
        log.info("Submit package request by {}", userDetails.getUsername());

        Packages packages = new Packages();
        if (packagesRequest.getPackageId() != null) {
            packages = packagesRepository.findByIdAndProfiles_UsernameAndDeletedAndActive(
                            packagesRequest.getPackageId(), userDetails.getUsername(), false, true)
                    .orElseThrow(() -> new CustomException("Package not found", ErrorCode.NOT_FOUND));
        }
        packages.setProfiles(profilesRepository.findByIdAndDeletedAndActive(packagesRequest.getMuaId(), false, true)
                .orElseThrow(() -> new CustomException("MUA Profile not found", ErrorCode.NOT_FOUND)));

        packages.setPackageName(packagesRequest.getPackageName());
        packages.setPrice(packagesRequest.getPrice());
        packages.setDuration(packagesRequest.getDuration());
        packages.setFeatures(packagesRequest.getFeatures());
        packages.setActive(true);
        packages.setDeleted(false);

        packagesRepository.save(packages);
        log.info("Submit package success for packageId={}", packages.getId());
        return toDto(packages);
    }

    public PackagesResponse getPackages(Long packageId) {
        Packages packages = packagesRepository
                .findByIdAndDeletedAndActive(packageId, false, true)
                .orElseThrow(() -> new CustomException("Package not found", ErrorCode.NOT_FOUND));
        return toDto(packages);
    }

    public List<PackagesResponse> getListOfPackages(String username) {
        return packagesRepository.findByProfiles_UsernameAndDeletedAndActive(username, false, true)
                .stream().map(this::toDto).toList();
    }

    public Boolean deletePackage(Long packageId) {
        Packages packages = packagesRepository.findByIdAndDeletedAndActive(packageId, false, true)
                .orElseThrow(() -> new CustomException("Package not found", ErrorCode.NOT_FOUND));
        packages.setDeleted(true);
        packages.setActive(false);
        packagesRepository.save(packages);
        return true;
    }

    private PackagesResponse toDto(Packages entity) {
        PackagesResponse response = new PackagesResponse();
        response.setPackageId(entity.getId());
        response.setPackageName(entity.getPackageName());
        response.setPrice(entity.getPrice());
        response.setDuration(entity.getDuration());
        response.setFeatures(entity.getFeatures());
        response.setActive(entity.getActive());
        return response;
    }
}
