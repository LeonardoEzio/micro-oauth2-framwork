package leonardo.ezio.personal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Description : 动态路由服务
 *
 *    动态更新路由配置
 *
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 11:53
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void add(RouteDefinition routeDefinition){
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void update(RouteDefinition routeDefinition){
        routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

}
