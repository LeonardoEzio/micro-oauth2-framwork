package leonardo.ezio.personal.authorization;

import cn.hutool.core.util.StrUtil;
import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.api.ResultCode;
import leonardo.ezio.personal.cache.CacheManager;
import leonardo.ezio.personal.common.RedisBusinessKey;
import leonardo.ezio.personal.feign.client.RemoteTokenClient;
import leonardo.ezio.personal.feign.entity.Oauth2TokenInfo;
import leonardo.ezio.personal.feign.entity.OauthUrlValidator;
import leonardo.ezio.personal.feign.entity.UserToken;
import leonardo.ezio.personal.feign.request.TokenVerifyRequest;
import leonardo.ezio.personal.security.constant.AuthorityLevel;
import leonardo.ezio.personal.security.constant.SecurityConstant;
import leonardo.ezio.personal.security.util.JwtTokenExtract;
import leonardo.ezio.personal.util.UrlPatternMatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
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
    private CacheManager cacheManager;

    @Autowired
    private RemoteTokenClient tokenClient;

    @Autowired
    private RedisTemplate redisTemplate;

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
                    throw new AccessDeniedException(String.valueOf(ResultCode.TOKEN_EXPIRED.getCode()));
                }
            }else {
                throw new AccessDeniedException(String.valueOf(ResultCode.UN_LOGGING.getCode()));
            }

            //若是只验证token 则放行
            if (authorityLevel == AuthorityLevel.TOKEN_ONLY.getValue()){
                return mono.just(new AuthorizationDecision(true));
            }

            //2. 菜单校验 从token中解析出用户名 并从redis缓存中获取用户的菜单权限进行校验
            UserToken userToken = JwtTokenExtract.extractToken(token);
            List<String> accessPaths = redisTemplate.opsForList().range(String.format(RedisBusinessKey.USER_ACCESS_MENU, userToken.getUserName()), 0, -1);
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String path : accessPaths) {
                if (pathMatcher.match(path,requestUri)) {
                    return mono.just(new AuthorizationDecision(true));
                }
            }

            throw new AccessDeniedException(String.valueOf(ResultCode.FORBIDDEN.getCode()));
        }
    }
}
