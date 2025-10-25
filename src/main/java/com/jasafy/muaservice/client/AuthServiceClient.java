package com.jasafy.muaservice.client;

import com.jasafy.helper.dto.BaseResponseExternal;
import com.jasafy.muaservice.client.dto.ResponseUser;
import com.jasafy.muaservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", path = "/api/auth", configuration = FeignConfig.class)
public interface AuthServiceClient {

    /**
     * Get User by ID
     *
     * @param userId User ID
     * @return Username and userId
     */
    @GetMapping("/user")
    BaseResponseExternal<ResponseUser> getUserId(@RequestParam("userId") Long userId);

}
