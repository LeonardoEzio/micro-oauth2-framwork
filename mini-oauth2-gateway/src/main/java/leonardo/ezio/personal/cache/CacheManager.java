package leonardo.ezio.personal.cache;

import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.feign.entity.OauthUrlValidator;
import cn.hutool.core.collection.CollUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import leonardo.ezio.personal.feign.client.RemoteOauthUrlClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:30
 */
@Component
public class CacheManager{

    @Autowired
    private RemoteOauthUrlClient oauthUrlClient;

    private static final String OAUTH_URL_KEY = "oauthUrl";

    private static final Cache<String, List<OauthUrlValidator>> oauthUrlCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public List<OauthUrlValidator> getOauthUrl(){
        List<OauthUrlValidator> oauthUrls = oauthUrlCache.getIfPresent(OAUTH_URL_KEY);
        if (CollUtil.isEmpty(oauthUrls)){
            CommonResult<List<OauthUrlValidator>> result = oauthUrlClient.syncOauthUrl();
            oauthUrls = result.getData();
            if (CollUtil.isNotEmpty(oauthUrls)){
                oauthUrlCache.put(OAUTH_URL_KEY, oauthUrls);
            }
        }
        return oauthUrls;
    }
}
