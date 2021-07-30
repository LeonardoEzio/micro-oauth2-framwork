package com.macro.cloud.feign.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 15:41
 *
 * 日志信息
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 跟踪id */
    private String traceId;

    /** 日志所属业务系统 */
    private String logPlat;

    /** 模块名称*/
    private String moduleName;

    /** 日志类型*/
    private String logType;

    /** 日志名称 */
    private String logName;

    /** 操作类型 */
    private String operatorType;

    /** 请求路径*/
    private String requestPath;

    /** 是否成功 0 否 , 1 是*/
    private Integer success;

    /** 请求参数 */
    private String requestParam;

    /** 请求时间*/
    private String requestTime;

    /** 请求耗时*/
    private Long requestCost;

    /** 响应状态码 */
    private String responseCode;

    /** 响应信息 */
    private String responseInfo;

    /** 异常信息*/
    private String exceptionMessage;

    /** 用户名称 */
    private String username;

    /** 浏览器名称*/
    private String browserName;

    /** 浏览器版本*/
    private String browserVersion;

    /** 浏览器引擎名称*/
    private String engineName;

    /** 浏览器引擎版本 */
    private String engineVersion;

    /** 系统名称*/
    private String osName;

    /** 平台名称*/
    private String platformName;

    /** 是否是手机,0:否,1:是 */
    private Integer mobile;

    /** 移动端设备名称 */
    private String deviceName;

    /** 移动端设备型号 */
    private String deviceModel;

    /** 备注 */
    private String remark;

}
