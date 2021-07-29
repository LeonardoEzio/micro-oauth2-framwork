package com.macro.cloud.feign.factory;

import com.macro.cloud.feign.client.RemoteTokenClient;
import com.macro.cloud.feign.fallback.RemoteTokenClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-29 16:52
 */
@Component
public class RemoteTokenFallBackFactory implements FallbackFactory<RemoteTokenClient> {

    @Override
    public RemoteTokenClient create(Throwable throwable) {
        RemoteTokenClientFallBack remoteTokenClientFallBack = new RemoteTokenClientFallBack();
        remoteTokenClientFallBack.setCause(throwable);
        return remoteTokenClientFallBack;
    }
}
