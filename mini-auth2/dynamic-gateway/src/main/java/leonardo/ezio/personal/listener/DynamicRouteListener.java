package leonardo.ezio.personal.listener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import leonardo.ezio.personal.config.DynamicRouteConfig;
import leonardo.ezio.personal.service.DynamicRouteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Description : 动态路由配置监听
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 11:59
 */
@Configuration
public class DynamicRouteListener implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(DynamicRouteListener.class);

    @Autowired
    private DynamicRouteConfig dynamicRouteConfig;

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @Override
    public void afterPropertiesSet() throws Exception {
        listenerConfigUpdate();
    }


    private void listenerConfigUpdate(){
        try {
            Properties properties = new Properties();
            properties.put("serverAddr", dynamicRouteConfig.getAddress());
            properties.put("namespace", dynamicRouteConfig.getNamespace());
            ConfigService configService = NacosFactory.createConfigService(properties);

            //初始化路由配置
            String initConfig = configService.getConfig(dynamicRouteConfig.getDataId(), dynamicRouteConfig.getGroupId(), dynamicRouteConfig.getTimeout());
            log.info("init router config from dataId : {} ; groupId : {} ; config info : {}",dynamicRouteConfig.getDataId(),dynamicRouteConfig.getGroupId(),initConfig);
            List<RouteDefinition> routeDefinitions = JSONObject.parseArray(initConfig, RouteDefinition.class);
            dynamicRouteService.add(routeDefinitions);

            //监听路由变更
            configService.addListener(dynamicRouteConfig.getDataId(), dynamicRouteConfig.getGroupId(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("receive router config from dataId : {} ; groupId : {} ; config info : {} ",dynamicRouteConfig.getDataId(),dynamicRouteConfig.getGroupId(),configInfo);
                    List<RouteDefinition> routeDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
                    dynamicRouteService.update(routeDefinitions);
                }
            });
        } catch (NacosException e) {
            log.error("config update listener failed !!! exception info : {}", ExceptionUtils.getStackTrace(e));
        }
    }
}
