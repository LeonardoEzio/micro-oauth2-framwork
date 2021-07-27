package com.macro.cloud.authorization;

import cn.hutool.core.util.StrUtil;
import com.macro.cloud.api.CommonResult;
import com.macro.cloud.client.TokenClient;
import com.macro.cloud.domain.security.Oauth2TokenInfo;
import com.macro.cloud.domain.security.TokenVerifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2020/6/19.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private TokenClient tokenClient;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        if (uri.getPath().equals("/test/denied")){
            return mono.just(new AuthorizationDecision(false));
        }
        String token = authorizationContext.getExchange().getRequest().getHeaders().getFirst("Authorization");
        if (!StrUtil.isBlank(token)){
            CommonResult<Oauth2TokenInfo> tokenVerify = tokenClient.verifyAccessToken(new TokenVerifyRequest().setAccessToken(token));
            System.out.println(tokenVerify);
        }
        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
        return Mono.just(new AuthorizationDecision(true));
    }

}
