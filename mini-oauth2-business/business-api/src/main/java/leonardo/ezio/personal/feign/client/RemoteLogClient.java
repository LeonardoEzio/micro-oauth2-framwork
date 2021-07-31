package leonardo.ezio.personal.feign.client;

import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.constant.FeignClientName;
import leonardo.ezio.personal.feign.entity.LogInfo;
import leonardo.ezio.personal.feign.factory.RemoteLogFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:18
 */
@FeignClient(contextId = "log", value = FeignClientName.BUSINESS_SERVER , fallbackFactory = RemoteLogFallBackFactory.class)
public interface RemoteLogClient {

    @PostMapping("/api/log/save")
    CommonResult<Boolean> saveLog(@RequestBody LogInfo logInfo);

}
