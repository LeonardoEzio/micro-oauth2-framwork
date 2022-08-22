package leonardo.ezio.personal.feign.factory;

import leonardo.ezio.personal.feign.client.RemoteLogClient;
import leonardo.ezio.personal.feign.fallback.RemoteLogClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:21
 */
@Component
public class RemoteLogFallBackFactory implements FallbackFactory<RemoteLogClient> {

    @Override
    public RemoteLogClient create(Throwable throwable) {
        RemoteLogClientFallBack remoteLogClientFallBack = new RemoteLogClientFallBack();
        remoteLogClientFallBack.setCause(throwable);
        return remoteLogClientFallBack;
    }
}
