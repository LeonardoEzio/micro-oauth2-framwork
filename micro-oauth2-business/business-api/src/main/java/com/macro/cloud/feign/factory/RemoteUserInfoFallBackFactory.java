package com.macro.cloud.feign.factory;

import com.macro.cloud.feign.client.RemoteUserInfoClient;
import com.macro.cloud.feign.fallback.RemoteUserInfoClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-29 16:58
 */
@Component
public class RemoteUserInfoFallBackFactory implements FallbackFactory<RemoteUserInfoClient> {

    @Override
    public RemoteUserInfoClient create(Throwable throwable) {
        RemoteUserInfoClientFallBack remoteUserInfoClientFallBack = new RemoteUserInfoClientFallBack();
        remoteUserInfoClientFallBack.setCause(throwable);
        return remoteUserInfoClientFallBack;
    }
}
