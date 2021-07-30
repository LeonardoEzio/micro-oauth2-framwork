package com.macro.cloud.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:54
 */
public class RequestInfo {

    private static final long serialVersionUID = 1421424612944015973L;

    /**
     * 请求ID
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String requestId;

    /**
     * 请求路径
     * /api/foobar/add
     */
    private String path;

    /**
     * 请求实际路径
     * /foobar/add
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String realPath;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * 请求方式，GET/POST
     */
    private String requestMethod;

    /**
     * 请求内容类型
     */
    private String contentType;

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     */
    private Boolean requestBody;

    /**
     * 请求参数对象
     */
    private Object param;

    /** 请求时戳*/
    private Long requestTime;

    /**
     * 请求时间字符串
     */
    private String requestTimeStr;

    /** 请求用户 */
    private String userName;

    /**
     * 请求token
     */
    private String token;

    /**
     * 请求token MD5值
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String tokenMd5;

    /**
     * 用户代理字符串
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String userAgent;

    /**
     * requiresRoles值
     */
    private String requiresRoles;

    /**
     * requiresPermissions值
     */
    private String requiresPermissions;

    /**
     * requiresAuthentication
     */
    private Boolean requiresAuthentication;

    /**
     * requiresUser
     */
    private Boolean requiresUser;

    /**
     * requiresGuest
     */
    private Boolean requiresGuest;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Boolean getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Boolean requestBody) {
        this.requestBody = requestBody;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestTimeStr() {
        return requestTimeStr;
    }

    public void setRequestTimeStr(String requestTimeStr) {
        this.requestTimeStr = requestTimeStr;
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
