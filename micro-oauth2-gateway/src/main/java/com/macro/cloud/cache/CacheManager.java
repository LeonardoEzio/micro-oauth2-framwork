package com.macro.cloud.cache;

import cn.hutool.core.collection.CollUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.macro.cloud.client.OauthUrlClient;
import com.macro.cloud.security.entity.OauthUrlValidator;
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
    private OauthUrlClient oauthUrlClient;

    private static final String OAUTH_URL_KEY = "oauthUrl";

    private static final Cache<String, List<OauthUrlValidator>> oauthUrlCache = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();


    public List<OauthUrlValidator> getOauthUrl(){
        List<OauthUrlValidator> oauthUrls = oauthUrlCache.getIfPresent(OAUTH_URL_KEY);
        if (CollUtil.isEmpty(oauthUrls)){
            oauthUrls = oauthUrlClient.syncOauthUrl().getData();
            if (CollUtil.isNotEmpty(oauthUrls)){
                oauthUrlCache.put(OAUTH_URL_KEY, oauthUrls);
            }
        }
        return oauthUrls;
    }
}
