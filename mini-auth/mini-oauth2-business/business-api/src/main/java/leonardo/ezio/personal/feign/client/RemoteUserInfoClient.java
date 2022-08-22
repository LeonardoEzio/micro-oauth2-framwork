package leonardo.ezio.personal.feign.client;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.constant.FeignClientName;
import leonardo.ezio.personal.feign.entity.UserInfo;
import leonardo.ezio.personal.feign.factory.RemoteUserInfoFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 14:40
 */
@FeignClient(contextId = "user" ,value = FeignClientName.BUSINESS_SERVER,fallbackFactory = RemoteUserInfoFallBackFactory.class)
public interface RemoteUserInfoClient {

    @GetMapping("/api/user/getByName")
    CommonResult<UserInfo> getUserByName(@RequestParam(value = "name") String name);
}
