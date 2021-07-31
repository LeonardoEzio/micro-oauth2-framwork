package leonardo.ezio.personal.feign.client;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.constant.FeignClientName;
import leonardo.ezio.personal.feign.entity.OauthUrlValidator;
import leonardo.ezio.personal.feign.factory.RemoteOauthUrlFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:52
 */
@FeignClient(contextId = "oauth", value = FeignClientName.BUSINESS_SERVER , fallbackFactory = RemoteOauthUrlFallBackFactory.class)
public interface RemoteOauthUrlClient {

    @GetMapping("/api/oauthUrl/sync")
    CommonResult<List<OauthUrlValidator>> syncOauthUrl();

}
