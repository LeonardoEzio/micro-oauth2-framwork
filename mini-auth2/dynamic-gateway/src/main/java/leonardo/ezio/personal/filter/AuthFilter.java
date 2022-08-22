package leonardo.ezio.personal.filter;

import leonardo.ezio.personal.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description : 鉴权过滤器
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 12:05
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String path = exchange.getRequest().getURI().getPath();

        log.info("request path : {}",path);
        if (path.equals("/test")){
            return exchange.getResponse().writeWith(Flux.just(ResponseUtils.getResponseBuffer(exchange.getResponse(), "用户未登录")));
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 10160;
    }
}
