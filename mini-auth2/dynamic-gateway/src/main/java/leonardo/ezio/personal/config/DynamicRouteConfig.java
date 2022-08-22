package leonardo.ezio.personal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 动态路由配置信息，从哪里读取路由配置
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 11:56
 */
@Configuration
@ConfigurationProperties(prefix = "dynamic.route")
public class DynamicRouteConfig {

    private String address;

    private String dataId;

    private String namespace = "public";

    private String groupId = "DEFAULT_GROUP";

    private Long timeout = 5000L;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
