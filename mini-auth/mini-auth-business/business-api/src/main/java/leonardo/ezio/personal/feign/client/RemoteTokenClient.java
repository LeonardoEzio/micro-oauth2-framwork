package leonardo.ezio.personal.feign.client;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.constant.FeignClientName;
import leonardo.ezio.personal.feign.request.TokenVerifyRequest;
import leonardo.ezio.personal.feign.entity.Oauth2TokenInfo;
import leonardo.ezio.personal.feign.factory.RemoteTokenFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(name = FeignClientName.AUTH_SERVER , fallbackFactory = RemoteTokenFallBackFactory.class)
public interface RemoteTokenClient {

    @PostMapping("/oauth/verify")
    CommonResult<Oauth2TokenInfo> verifyAccessToken(@RequestBody TokenVerifyRequest tokenVerifyRequest);
}
