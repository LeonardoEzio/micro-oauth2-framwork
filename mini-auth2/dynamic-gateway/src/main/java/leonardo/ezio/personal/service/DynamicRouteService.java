package leonardo.ezio.personal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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


    public void add(List<RouteDefinition> routeDefinitions){
        routeDefinitions.forEach(this::add);
        this.eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }


    public void update(List<RouteDefinition> routeDefinitions){
        routeDefinitions.forEach(this::update);
        this.eventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private void add(RouteDefinition routeDefinition){
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();

    }

    private void update(RouteDefinition routeDefinition){
        routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
    }

}
