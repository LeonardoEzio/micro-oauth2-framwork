package leonardo.ezio.personal.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description : 日志采集过滤器
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 12:06
 */
@Component
public class LogFiler implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LogFiler.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("request path : {}",path);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10161;
    }
}
