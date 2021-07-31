package leonardo.ezio.personal.feign.factory;

import leonardo.ezio.personal.feign.client.RemoteTokenClient;
import leonardo.ezio.personal.feign.fallback.RemoteTokenClientFallBack;
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
