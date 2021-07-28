package com.macro.cloud.authorization;

import cn.hutool.core.util.StrUtil;
import com.macro.cloud.api.CommonResult;
import com.macro.cloud.cache.CacheManager;
import com.macro.cloud.client.TokenClient;
import com.macro.cloud.feign.request.TokenVerifyRequest;
import com.macro.cloud.security.constant.AuthorityLevel;
import com.macro.cloud.security.constant.SecurityConstant;
import com.macro.cloud.security.entity.Oauth2TokenInfo;
import com.macro.cloud.security.entity.OauthUrlValidator;
import com.macro.cloud.security.entity.UserToken;
import com.macro.cloud.security.util.JwtTokenExtract;
import com.macro.cloud.util.UrlPatternMatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 *
 * 白名单请求不会进入鉴权管理器
 *
 * Created by macro on 2020/6/19.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private TokenClient tokenClient;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        //判断url有没有设置单独的验证级别
        String requestUri = request.getURI().getPath();
        List<OauthUrlValidator> oauthUrls = cacheManager.getOauthUrl();
        int authorityLevel = UrlPatternMatchUtils.simpleMatch(oauthUrls, requestUri);

        if (authorityLevel == AuthorityLevel.LOWEST.getValue()){
            return mono.just(new AuthorizationDecision(true));
        }else {
            //验证 token 以及菜单权限
            //1.验证 token
            String token = request.getHeaders().getFirst(SecurityConstant.AUTHORIZATION_HEAD);
            if (!StrUtil.isBlank(token)){
                token = request.getHeaders().getFirst(SecurityConstant.AUTHORIZATION_HEAD).replace(SecurityConstant.TOKEN_TYPE, "");
                CommonResult<Oauth2TokenInfo> tokenVerify = tokenClient.verifyAccessToken(new TokenVerifyRequest().setAccessToken(token));
                if (tokenVerify.getCode() == 500){
                    return mono.just(new AuthorizationDecision(false));
                }
            }else {
                return mono.just(new AuthorizationDecision(false));
            }

            //若是只验证token 则放行
            if (authorityLevel == AuthorityLevel.TOKEN_ONLY.getValue()){
                return mono.just(new AuthorizationDecision(true));
            }

            //2. 菜单校验 解析出token中的菜单信息进行校验
            UserToken userToken = JwtTokenExtract.extractToken(token);
            List<String> authedPath = userToken.getAuthorities();
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String path : authedPath) {
                if (pathMatcher.match(path,requestUri)) {
                    return mono.just(new AuthorizationDecision(true));
                }
            }

            return Mono.just(new AuthorizationDecision(false));
        }
    }
}
