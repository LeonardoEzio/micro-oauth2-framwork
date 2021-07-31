package leonardo.ezio.personal.feign.factory;

import leonardo.ezio.personal.feign.client.RemoteOauthUrlClient;
import leonardo.ezio.personal.feign.fallback.RemoteOauthUrlClientFallBack;
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
