package com.jasafy.muaservice.client;

import com.jasafy.helper.dto.BaseResponseExternal;
import com.jasafy.muaservice.dto.external.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthServiceClientFallback implements AuthServiceClient {

    @Override
    public BaseResponseExternal<ResponseUser> getUserId(Long userId) {
        log.warn("Fallback: getUserId called for userId: {}", userId);
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUserId(userId);
        responseUser.setUsername("Belum tersedia");
        return new BaseResponseExternal<>();
    }
}
