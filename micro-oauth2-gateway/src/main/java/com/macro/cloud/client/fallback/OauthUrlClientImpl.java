package com.macro.cloud.client.fallback;

import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.OauthUrlClient;
import com.macro.cloud.security.entity.OauthUrlValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 16:11
 */
@Slf4j
@Service
public class OauthUrlClientImpl implements OauthUrlClient {

    @Override
    public CommonResult<List<OauthUrlValidator>> syncOauthUrl() {
        return CommonResult.failed("business service not available");
    }
}
