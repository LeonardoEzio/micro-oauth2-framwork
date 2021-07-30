package com.macro.cloud.feign.fallback;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.feign.client.RemoteLogClient;
import com.macro.cloud.feign.entity.LogInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:19
 */
@Slf4j
@Service
public class RemoteLogClientFallBack implements RemoteLogClient {

    @Setter
    private Throwable cause;

    @Override
    public CommonResult<Boolean> saveLog(@RequestBody LogInfo logInfo) {
        log.error("保存日志信息失败 ： {}  失败原因 ：{}",logInfo,cause.getMessage());
        return CommonResult.failed(cause.getMessage());
    }
}
