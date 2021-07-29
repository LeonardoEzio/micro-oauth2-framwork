package com.macro.cloud.feign.factory;

import com.macro.cloud.feign.client.RemoteOauthUrlClient;
import com.macro.cloud.feign.fallback.RemoteOauthUrlClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-29 16:55
 */
@Component
public class RemoteOauthUrlFallBackFactory implements FallbackFactory<RemoteOauthUrlClient> {

    @Override
    public RemoteOauthUrlClient create(Throwable throwable) {
        RemoteOauthUrlClientFallBack remoteOauthUrlClientFallBack = new RemoteOauthUrlClientFallBack();
        remoteOauthUrlClientFallBack.setCause(throwable);
        return remoteOauthUrlClientFallBack;
    }
}
