package leonardo.ezio.personal.feign.fallback;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.request.TokenVerifyRequest;
import leonardo.ezio.personal.feign.entity.Oauth2TokenInfo;
import leonardo.ezio.personal.feign.client.RemoteTokenClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:41
 */
@Slf4j
@Component
public class RemoteTokenClientFallBack implements RemoteTokenClient {

    @Setter
    private Throwable cause;

    @Override
    public CommonResult<Oauth2TokenInfo> verifyAccessToken(TokenVerifyRequest tokenVerifyRequest) {
        log.error("auth service fallback !!! {}",cause.getMessage());
        return CommonResult.failed(cause.getMessage());
    }
}
