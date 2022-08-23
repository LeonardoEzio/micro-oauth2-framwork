package leonardo.ezio.personal.common;

import java.io.Serializable;

/**
 * @Description : 网关日志实体
 * @Author : LeonardoEzio
 * @Date: 2022-08-23 10:25
 */
public class AccessLog implements Serializable {

    /**
     * 请求路径
     * */
    private String requestPath;

    /**
     * 请求服务
     * */
    private String requestServer;

    /**
     * 请求方式 GET or POST
     * */
    private String requestMethod;

    /**
     * 请求时间
     * */
    private Long requestTime;

    /**
     * 请求参数
     * */
    private String requestParam;

    /**
     * 操作人
     * */
    private String operatorUser;

    /**
     * ip信息
     * */
    private String ip;

    /**
     * 响应时间
     * */
    private Long responseTime;

    /**
     * 响应信息
     * */
    private String responseBody;

    /**
     * 请求耗时
     * */
    private Long executeTime;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestServer() {
        return requestServer;
    }

    public void setRequestServer(String requestServer) {
        this.requestServer = requestServer;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(String operatorUser) {
        this.operatorUser = operatorUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Long executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return "AccessLog{" +
                "requestPath='" + requestPath + '\'' +
                ", requestServer='" + requestServer + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", requestParam='" + requestParam + '\'' +
                ", operatorUser='" + operatorUser + '\'' +
                ", ip='" + ip + '\'' +
                ", responseTime='" + responseTime + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", executeTime=" + executeTime +
                '}';
    }
}
