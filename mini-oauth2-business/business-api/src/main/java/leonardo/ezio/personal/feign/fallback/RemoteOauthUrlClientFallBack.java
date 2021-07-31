package leonardo.ezio.personal.feign.fallback;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.entity.OauthUrlValidator;
import leonardo.ezio.personal.feign.client.RemoteOauthUrlClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 16:11
 */
@Slf4j
@Service
public class RemoteOauthUrlClientFallBack implements RemoteOauthUrlClient {

    @Setter
    private Throwable cause;

    @Override
    public CommonResult<List<OauthUrlValidator>> syncOauthUrl() {
        return CommonResult.failed(cause.getMessage());
    }
}
