package com.macro.cloud.web.controller;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.entity.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 20:20
 */
@Slf4j
@RestController
@RequestMapping("log")
public class LogController {

    @PostMapping("/save")
    public CommonResult<Boolean> saveLog(@RequestBody LogInfo logInfo) {
        log.info("save log info : {}",logInfo);
        return CommonResult.success(null);
    }

}
