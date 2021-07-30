package com.macro.cloud.feign.fallback;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.entity.UserInfo;
import com.macro.cloud.feign.client.RemoteUserInfoClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:41
 */
@Slf4j
@Service
public class RemoteUserInfoClientFallBack implements RemoteUserInfoClient {

    @Setter
    private Throwable cause;

    @Override
    public CommonResult<UserInfo> getUserByName(@RequestParam(value = "name") String name) {
        return CommonResult.failed(cause.getMessage());
    }
}
