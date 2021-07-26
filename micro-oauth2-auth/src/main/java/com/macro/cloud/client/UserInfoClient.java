package com.macro.cloud.client;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.fallback.UserInfoClientImpl;
import com.macro.cloud.domain.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(name = "micro-oauth2-api",fallback = UserInfoClientImpl.class)
public interface UserInfoClient {

    @GetMapping("/user/getUser")
    CommonResult<UserDTO> getUserByName(@RequestParam(value = "name") String name);
}
