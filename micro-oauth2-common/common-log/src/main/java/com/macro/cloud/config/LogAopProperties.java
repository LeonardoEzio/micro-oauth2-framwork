package com.macro.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 14:47
 */
@Configuration
@ConfigurationProperties(prefix = "aop")
public class LogAopProperties {

    @NestedConfigurationProperty
    private LogAopConfig log = new LogAopConfig();

    @NestedConfigurationProperty
    private OperationLogConfig operationLog = new OperationLogConfig();

    @NestedConfigurationProperty
    private LoginLogConfig loginLog = new LoginLogConfig();

    public static class AopConfig{

        /** 是否启用标识 */
        private boolean enable = true;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }


    public static class LogAopConfig extends AopConfig{

        /** 是否启用 request id*/
        private boolean enableRequestId = true;

        /** 日志打印类型 */
        private LogPrintType printType = LogPrintType.ORDER;

        /** 请求ID生成方式 */
        private RequestIdType requestIdType = RequestIdType.UUID;

        /** 请求日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false */
        private boolean formatRequest = true;

        /** 响应日志在控制台是否格式化输出，local环境建议开启，服务器环境设置为false */
        private boolean formatResponse = true;

        /** 排除路径 */
        private Set<String> excludePaths;

        public boolean isEnableRequestId() {
            return enableRequestId;
        }

        public void setEnableRequestId(boolean enableRequestId) {
            this.enableRequestId = enableRequestId;
        }

        public LogPrintType getPrintType() {
            return printType;
        }

        public void setPrintType(LogPrintType printType) {
            this.printType = printType;
        }

        public RequestIdType getRequestIdType() {
            return requestIdType;
        }

        public void setRequestIdType(RequestIdType requestIdType) {
            this.requestIdType = requestIdType;
        }

        public boolean isFormatRequest() {
            return formatRequest;
        }

        public void setFormatRequest(boolean formatRequest) {
            this.formatRequest = formatRequest;
        }

        public boolean isFormatResponse() {
            return formatResponse;
        }

        public void setFormatResponse(boolean formatResponse) {
            this.formatResponse = formatResponse;
        }

        public Set<String> getExcludePaths() {
            return excludePaths;
        }

        public void setExcludePaths(Set<String> excludePaths) {
            this.excludePaths = excludePaths;
        }
    }

    public static class LoginLogConfig extends AopConfig{

    }

    public static class OperationLogConfig extends AopConfig{

        /** 是否启用查询日志 */
        private boolean enableQuery = false;

        /** 排除路径 */
        private Set<String> excludePaths;

        public boolean isEnableQuery() {
            return enableQuery;
        }

        public void setEnableQuery(boolean enableQuery) {
            this.enableQuery = enableQuery;
        }

        public Set<String> getExcludePaths() {
            return excludePaths;
        }

        public void setExcludePaths(Set<String> excludePaths) {
            this.excludePaths = excludePaths;
        }
    }

    public enum LogPrintType {

        /**
         * 不打印日志
         */
        NONE,
        /**
         * 请求和响应日志，按照执行顺序分开打印
         */
        ORDER,
        /**
         * 方法执行结束时，连续分开打印请求和响应日志
         */
        LINE,
        /**
         * 方法执行结束时，合并请求和响应日志，同时打印
         */
        MERGE;

    }

    /**
     * 请求ID生成类型
     *
     * @
     * @date 2020/3/25
     **/
    public enum RequestIdType {
        /**
         * 生成UUID无中横线
         */
        UUID
    }

    public LogAopConfig getLog() {
        return log;
    }

    public void setLog(LogAopConfig log) {
        this.log = log;
    }

    public OperationLogConfig getOperationLog() {
        return operationLog;
    }

    public void setOperationLog(OperationLogConfig operationLog) {
        this.operationLog = operationLog;
    }

    public LoginLogConfig getLoginLog() {
        return loginLog;
    }

    public void setLoginLog(LoginLogConfig loginLog) {
        this.loginLog = loginLog;
    }
}
