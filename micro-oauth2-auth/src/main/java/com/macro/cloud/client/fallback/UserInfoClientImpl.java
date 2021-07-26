package com.macro.cloud.client.fallback;


import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.UserInfoClient;
import com.macro.cloud.domain.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:41
 */
@Slf4j
@Service
public class UserInfoClientImpl implements UserInfoClient {

    @Override
    public CommonResult<UserDTO> getUserByName(@RequestParam(value = "name") String name) {
        UserDTO userDTO = new UserDTO(1L, String.format("user %s from fallback", name), "234567", null, null);
        return CommonResult.success(userDTO);
    }
}
