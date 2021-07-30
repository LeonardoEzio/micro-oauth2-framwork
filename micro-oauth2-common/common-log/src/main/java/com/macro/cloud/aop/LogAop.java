

package com.macro.cloud.aop;


import com.alibaba.fastjson.JSONObject;
import com.macro.cloud.annotation.LogModule;
import com.macro.cloud.annotation.SysLog;
import com.macro.cloud.api.CommonResult;
import com.macro.cloud.api.ResultCode;
import com.macro.cloud.common.CommonConstant;
import com.macro.cloud.config.LogAopProperties;
import com.macro.cloud.entity.ClientInfo;
import com.macro.cloud.entity.RequestInfo;
import com.macro.cloud.enums.LogType;
import com.macro.cloud.enums.OperationType;
import com.macro.cloud.feign.client.RemoteLogClient;
import com.macro.cloud.feign.entity.LogInfo;
import com.macro.cloud.feign.entity.UserToken;
import com.macro.cloud.security.constant.SecurityConstant;
import com.macro.cloud.security.util.JwtTokenExtract;
import com.macro.cloud.util.ClientInfoUtil;
import com.macro.cloud.util.IpUtil;
import com.macro.cloud.utils.DateUtil;
import com.macro.cloud.utils.Jackson;
import com.macro.cloud.utils.UUIDUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;



/**
 * <p>
 * Controller Aop 抽象类
 * 获取响应结果信息
 * <p>
 * 日志输出类型：print-type
 * 1. 请求和响应分开，按照执行顺序打印
 * 2. ThreadLocal线程绑定，方法执行结束时，连续打印请求和响应日志
 * 3. ThreadLocal线程绑定，方法执行结束时，同时打印请求和响应日志
 * </p>
 *
 */
public abstract class LogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    /**
     * 零
     */
    private static final int ZERO = 0;

    @Autowired
    protected RemoteLogClient remoteLogClient;

    /**
     * 请求ID
     */
    private static final String REQUEST_ID = "requestId";

    /**
     * 截取字符串的最多长度
     */
    private static final int MAX_LENGTH = 300;
    /**
     * 登录日志：登录类型
     */
    private static final int LOGIN_TYPE = 1;
    /**
     * 登录日志：登出类型
     */
    private static final int LOGOUT_TYPE = 2;

    /**
     * 项目上下文路径
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 是否启用请求ID
     */
    protected boolean enableRequestId;

    /**
     * Aop日志配置
     */
    protected LogAopProperties.LogAopConfig logAopConfig;

    /**
     * 日志打印类型
     */
    protected LogAopProperties.LogPrintType logPrintType;


    /**
     * requestId生成类型
     */
    protected LogAopProperties.RequestIdType requestIdType;

    /**
     * Aop操作日志配置
     */
    protected LogAopProperties.OperationLogConfig operationLogConfig;

    /**
     * Aop登录日志配置
     */
    protected LogAopProperties.LoginLogConfig loginLogConfig;

    /**
     * 本地线程变量，保存请求参数信息到当前线程中
     */
    protected static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    protected static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<>();

    protected static ThreadLocal<LogInfo> logInfoThreadLocal = new ThreadLocal<>();

    @Autowired
    public void setLogAopConfig(LogAopProperties logAopProperties) {
        logAopConfig = logAopProperties.getLog();
        logPrintType = logAopConfig.getPrintType();
        enableRequestId = logAopConfig.isEnableRequestId();
        requestIdType = logAopConfig.getRequestIdType();
        operationLogConfig = logAopProperties.getOperationLog();
        loginLogConfig = logAopProperties.getLoginLog();
        LOGGER.debug("logAopConfig = " + logAopConfig);
        LOGGER.debug("logPrintType = " + logPrintType);
        LOGGER.debug("enableRequestId = " + enableRequestId);
        LOGGER.debug("requestIdType = " + requestIdType);
        LOGGER.debug("operationLogConfig = " + operationLogConfig);
        LOGGER.debug("loginLogConfig = " + loginLogConfig);
        LOGGER.debug("contextPath = " + contextPath);
    }

    /**
     * 环绕通知
     * 方法执行前打印请求参数信息
     * 方法执行后打印响应结果信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public abstract Object doAround(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable;


    /**
     * 异常通知方法
     *
     * @param joinPoint
     * @param exception
     */
    public abstract void afterThrowing(JoinPoint joinPoint, SysLog sysLog ,Exception exception);


    /**
     * 请求响应处理完成之后的回调方法
     *
     * @param requestInfo
     * @param logInfo
     * @param result
     * @param exception
     */
    protected abstract void finish(RequestInfo requestInfo, LogInfo logInfo, Object result, Exception exception);

    /**
     * 设置请求ID
     *
     * @param requestInfo
     */
    protected abstract void setRequestId(RequestInfo requestInfo);

    /**
     * 获取请求信息对象
     *
     * @param requestInfo
     */
    protected abstract void getRequestInfo(RequestInfo requestInfo);

    /**
     * 获取响应结果对象
     *
     * @param result
     */
    protected abstract void getResponseResult(Object result);

    /**
     * 处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object handle(ProceedingJoinPoint joinPoint,SysLog sysLog) throws Throwable {
        // 获取请求相关信息
        try {

            // HTTP请求信息对象
            RequestInfo requestInfo = new RequestInfo();

            // 获取当前的HttpServletRequest对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 获取登录用户信息
            String token = request.getHeader(SecurityConstant.AUTHORIZATION_HEAD);
            UserToken userToken = JwtTokenExtract.extractToken(token);
            requestInfo.setUserName(userToken.getUserName());

            // 请求路径 /api/foobar/add
            String path = request.getRequestURI();
            requestInfo.setPath(path);
            // 获取实际路径 /foobar/add
            String realPath = getRealPath(path);
            requestInfo.setRealPath(realPath);

            // 排除路径
            Set<String> excludePaths = logAopConfig.getExcludePaths();
            // 请求路径
            if (handleExcludePaths(excludePaths, realPath)) {
                return joinPoint.proceed();
            }

            // 获取请求类名和方法名称
            Signature signature = joinPoint.getSignature();

            // 获取真实的方法对象
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // 处理操作日志信息
            handlerBasicLogInfo(method,sysLog);

            // IP地址
            String ip = IpUtil.getRequestIp();
            requestInfo.setIp(ip);

            // 获取请求方式
            String requestMethod = request.getMethod();
            requestInfo.setRequestMethod(requestMethod);

            // 获取请求内容类型
            String contentType = request.getContentType();
            requestInfo.setContentType(contentType);

            // 判断控制器方法参数中是否有RequestBody注解
            Annotation[][] annotations = method.getParameterAnnotations();
            boolean isRequestBody = isRequestBody(annotations);
            requestInfo.setRequestBody(isRequestBody);

            // 设置请求参数
            Object requestParamObject = getRequestParamObject(joinPoint, request, requestMethod, contentType, isRequestBody);
            requestInfo.setParam(requestParamObject);
            requestInfo.setRequestTime(System.currentTimeMillis());
            requestInfo.setRequestTimeStr(DateUtil.getDateTimeString(new Date()));

            // 用户浏览器代理字符串
            requestInfo.setUserAgent(request.getHeader(CommonConstant.USER_AGENT));

            // 记录请求ID
            setRequestId(requestInfo);

            // 调用子类重写方法，控制请求信息日志处理
            getRequestInfo(requestInfo);
        } catch (Exception e) {
            LOGGER.error("请求日志AOP处理异常", e);
        }

        // 执行目标方法,获得返回值
        // 方法异常时，会调用子类的@AfterThrowing注解的方法，不会调用下面的代码，异常单独处理
        Object result = joinPoint.proceed();

        try {
            // 调用子类重写方法，控制响应结果日志处理
            getResponseResult(result);
        } catch (Exception e) {
            LOGGER.error("处理响应结果异常", e);
        } finally {
            handleAfterReturn(result, null);
        }
        return result;
    }

    /**
     * 正常调用返回或者异常结束后调用此方法
     *
     * @param result
     * @param exception
     */
    protected void handleAfterReturn(Object result, Exception exception) {
        // 获取RequestInfo
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        // 获取LogInfo
        LogInfo logInfo = logInfoThreadLocal.get();
        logInfo.setRequestCost(System.currentTimeMillis() - requestInfo.getRequestTime());

        // 调用抽象方法，是否保存日志操作，需要子类重写该方法，手动调用saveSysOperationLog
        finish(requestInfo, logInfo, result, null);
        // 释放资源
        remove();
    }

    /**
     * 处理异常
     *
     * @param exception
     */
    public void handleAfterThrowing(Exception exception) {
        // 获取RequestInfo
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        // 获取LogInfo
        LogInfo logInfo = logInfoThreadLocal.get();
        logInfo.setRequestCost(System.currentTimeMillis() - requestInfo.getRequestTime());

        // 调用抽象方法，是否保存日志操作，需要子类重写该方法，手动调用saveSysOperationLog
        finish(requestInfo, logInfo, null, exception);
        // 释放资源
        remove();
    }

    /***
    * 解析日志信息
    *
    * @name : handlerBasicLogInfo
    * @param : method  被调用方法
    * @param : sysLog  {@link SysLog}
    * @return : * @return {@link  }
    * @date : 2021/7/30 18:16
    *
    **/
    private void handlerBasicLogInfo(Method method, SysLog sysLog) {
        LogInfo logInfo = new LogInfo();

        // 获取Module类注解
        Class<?> controllerClass = method.getDeclaringClass();
        LogModule module = controllerClass.getAnnotation(LogModule.class);
        if (module != null) {
            String moduleName = module.name();
            if (StringUtils.isNotBlank(moduleName)) {
                //设置模块名
                logInfo.setModuleName(moduleName);
            }
            String platInfo = module.plat().getDesc();
            logInfo.setLogPlat(platInfo);
        }

        //解析 Syslog 信息
        if (sysLog != null) {
            String logType = sysLog.type().getDesc();
            logInfo.setLogType(logType);
            String logName = sysLog.name();
            if (StringUtils.isNotBlank(logName)) {
                logInfo.setLogName(logName);
            }
            logInfo.setOperatorType(sysLog.operation().getDesc());

            String remark = sysLog.remark();
            if (StringUtils.isNotEmpty(remark)){
                logInfo.setRemark(remark);
            }
        }

        //将log信息保存在线程本地变量中
        logInfoThreadLocal.set(logInfo);
    }


    /**
     * 处理请求ID
     *
     * @param requestInfo
     */
    protected void handleRequestId(RequestInfo requestInfo) {
        if (!enableRequestId) {
            return;
        }
        String requestId = UUIDUtil.getUuid();;
        // 设置请求ID
        MDC.put(REQUEST_ID, requestId);
        requestInfo.setRequestId(requestId);
    }

    /**
     * 处理请求参数
     *
     * @param requestInfo
     */
    protected void handleRequestInfo(RequestInfo requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
        if (LogAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        // 获取请求信息
        String requestInfoString = formatRequestInfo(requestInfo);
        // 如果打印方式为顺序打印，则直接打印，否则，保存的threadLocal中
        if (LogAopProperties.LogPrintType.ORDER == logPrintType) {
            printRequestInfoString(requestInfoString);
        } else {
            threadLocal.set(requestInfoString);
        }
    }

    /**
     * 处理响应结果
     *
     * @param result
     */
    protected void handleResponseResult(Object result) {
        if (LogAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        if (result != null && result instanceof CommonResult) {
            CommonResult<?> result1 = (CommonResult<?>) result;
            long code = result1.getCode();
            // 获取格式化后的响应结果
            String responseResultString = formatResponseResult(result1);
            if (LogAopProperties.LogPrintType.ORDER == logPrintType) {
                printResponseResult(code, responseResultString);
            } else {
                // 从threadLocal中获取线程请求信息
                String requestInfoString = threadLocal.get();
                // 如果是连续打印，则先打印请求参数，再打印响应结果
                if (LogAopProperties.LogPrintType.LINE == logPrintType) {
                    printRequestInfoString(requestInfoString);
                    printResponseResult(code, responseResultString);
                } else if (LogAopProperties.LogPrintType.MERGE == logPrintType) {
                    printRequestResponseString(code, requestInfoString, responseResultString);
                }
            }
        }
    }

    /**
     * 同时打印请求和响应信息
     *
     * @param code
     * @param requestInfoString
     * @param responseResultString
     */
    protected void printRequestResponseString(long code, String requestInfoString, String responseResultString) {
        if (code == ResultCode.SUCCESS.getCode()) {
            LOGGER.info(requestInfoString + "\n" + responseResultString);
        } else {
            LOGGER.error(requestInfoString + "\n" + responseResultString);
        }
    }


    /**
     * 格式化请求信息
     *
     * @param requestInfo
     * @return
     */
    protected String formatRequestInfo(RequestInfo requestInfo) {
        String requestInfoString = null;
        try {
            if (logAopConfig.isFormatRequest()) {
                requestInfoString = "\n" + Jackson.toJsonStringNonNull(requestInfo, true);
            } else {
                requestInfoString = Jackson.toJsonStringNonNull(requestInfo);
            }
        } catch (Exception e) {
            LOGGER.error("格式化请求信息日志异常", e);
        }
        return requestInfoString;
    }

    /**
     * 打印请求信息
     *
     * @param requestInfoString
     */
    protected void printRequestInfoString(String requestInfoString) {
        LOGGER.info(requestInfoString);
    }

    /**
     * 格式化响应信息
     *
     * @param result
     * @return
     */
    protected String formatResponseResult(CommonResult<?> result) {
        String responseResultString = "responseResult:";
        try {
            if (logAopConfig.isFormatResponse()) {
                responseResultString += "\n" + Jackson.toJsonString(result, true);
            } else {
                responseResultString += Jackson.toJsonString(result);
            }
        } catch (Exception e) {
            LOGGER.error("格式化响应日志异常", e);
        }
        return responseResultString;
    }

    /**
     * 打印响应信息
     *
     * @param code
     * @param responseResultString
     */
    protected void printResponseResult(Long code, String responseResultString) {
        if (code == ResultCode.SUCCESS.getCode()) {
            LOGGER.info(responseResultString);
        } else {
            LOGGER.error(responseResultString);
        }
    }

    /**
     * 获取请求参数JSON字符串
     *
     * @param joinPoint
     * @param request
     * @param requestMethod
     * @param contentType
     * @param isRequestBody
     */
    protected Object getRequestParamObject(ProceedingJoinPoint joinPoint, HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        Object paramObject = null;
        if (isRequestBody) {
            // POST,application/json,RequestBody的类型,简单判断,然后序列化成JSON字符串
            Object[] args = joinPoint.getArgs();
            paramObject = getArgsObject(args);
        } else {
            // 获取getParameterMap中所有的值,处理后序列化成JSON字符串
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getParamJSONObject(paramsMap);
        }
        return paramObject;
    }

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     *
     * @param annotations
     * @return
     */
    protected boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    /**
     * 请求参数拼装
     *
     * @param args
     * @return
     */
    protected Object getArgsObject(Object[] args) {
        if (args == null) {
            return null;
        }
        // 去掉HttpServletRequest和HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return realArgs.get(0);
        } else {
            return realArgs;
        }
    }


    /**
     * 获取参数Map的JSON字符串
     *
     * @param paramsMap
     * @return
     */
    protected JSONObject getParamJSONObject(Map<String, String[]> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            // 没有值
            if (values == null) {
                jsonObject.put(key, null);
            } else if (values.length == 1) {
                // 一个值
                jsonObject.put(key, values[0]);
            } else {
                // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }

    /**
     * 处理排除路径，匹配返回true，否则返回false
     *
     * @param excludePaths 排除路径
     * @param realPath     请求实际路径
     * @return
     */
    protected boolean handleExcludePaths(Set<String> excludePaths, String realPath) {
        if (CollectionUtils.isEmpty(excludePaths) || StringUtils.isBlank(realPath)) {
            return false;
        }
        // 如果是排除路径，则跳过
        if (excludePaths.contains(realPath)) {
            return true;
        }
        return false;
    }

    /**
     * 获取实际路径
     *
     * @param requestPath
     * @return
     */
    private String getRealPath(String requestPath) {
        // 如果项目路径不为空，则去掉项目路径，获取实际访问路径
        if (StringUtils.isNotBlank(contextPath)) {
            return requestPath.substring(contextPath.length());
        }
        return requestPath;
    }

    /**
     * 异步保存系统操作日志
     *
     * @param requestInfo
     * @param logInfo
     * @param result
     * @param exception
     */
    @Async
    protected void saveLogInfo(RequestInfo requestInfo, LogInfo logInfo, Object result, Exception exception) {
        try {

            // 排除路径 不记录日志
            Set<String> excludePaths = operationLogConfig.getExcludePaths();
            if (handleExcludePaths(excludePaths, requestInfo.getRealPath())) {
                return;
            }

            //判断日志类型
            //1.如果是登录日志 并且不记录 则退出
            if (logInfo.getLogType().equals(LogType.LOGGING.getDesc()) && !loginLogConfig.isEnable()){
                return;
            }
            //2.如果是业务日志
            if (logInfo.getLogType().equals(LogType.BUSINESS.getDesc())){
                // 如果不记录操作日志，则跳过
                if (!operationLogConfig.isEnable()) {
                    return;
                }
                // 判断是否记录查询日志
                if (!operationLogConfig.isEnableQuery() && isQueryLog(logInfo)) {
                    return;
                }
            }

            //完善日志信息
            //1.设置请求参数相关信息
            if (null != requestInfo){
                //requestId设置
                logInfo.setTraceId(requestInfo.getRequestId());
                logInfo.setRequestPath(requestInfo.getPath());
                logInfo.setRequestTime(requestInfo.getRequestTimeStr());
                logInfo.setRequestParam(Jackson.toJsonStringNonNull(requestInfo.getParam()));

                //2.设置客户端信息
                ClientInfo clientInfo = ClientInfoUtil.get(requestInfo.getUserAgent(), false);
                if (null != clientInfo){
                    logInfo.setBrowserName(clientInfo.getBrowserName());
                    logInfo.setBrowserVersion(clientInfo.getBrowserversion());
                    logInfo.setEngineName(clientInfo.getEngineName());
                    logInfo.setEngineVersion(clientInfo.getEngineVersion());
                    logInfo.setOsName(clientInfo.getOsName());
                    logInfo.setPlatformName(clientInfo.getPlatformName());
                    logInfo.setMobile(clientInfo.isMobile()? 1 : 0);
                    logInfo.setDeviceName(clientInfo.getDeviceName());
                    logInfo.setDeviceModel(clientInfo.getDeviceModel());
                }

            }

            //3.设置响应结果
            if (result != null && result instanceof CommonResult){
                CommonResult commonResult = (CommonResult<?>) result;
                long resultCode = commonResult.getCode();
                logInfo.setSuccess(resultCode == ResultCode.SUCCESS.getCode() ? 1 : 0);
                logInfo.setResponseCode(String.valueOf(resultCode));
                logInfo.setResponseInfo(null == commonResult.getData() ? commonResult.getMessage() : Jackson.toJsonStringNonNull(commonResult.getData()));
            }

            //4.设置异常信息
            if (null != exception){
                String exceptionMessage = exception.getMessage();
                if (StringUtils.isNotBlank(exceptionMessage)) {
                    exceptionMessage = StringUtils.substring(exceptionMessage, ZERO, MAX_LENGTH);
                }
                logInfo.setExceptionMessage(exceptionMessage);
                logInfo.setSuccess(0);
            }

            //5.设置用户信息
            logInfo.setUsername(requestInfo.getUserName());

            //6.保存操作日志
            remoteLogClient.saveLog(logInfo);

        } catch (Exception e) {
            LOGGER.error("保存系统操作日志失败 : {}", e);
        }
    }


    public boolean isQueryLog(LogInfo logInfo) {
        String operatorType = logInfo.getOperatorType();
        return operatorType.equals(OperationType.INFO.getDesc())
                || operatorType.equals(OperationType.LIST.getDesc())
                || operatorType.equals(OperationType.PAGE.getDesc())
                || operatorType.equals(OperationType.OTHER_QUERY.getDesc());
    }


    /**
     * 释放资源
     */
    protected void remove() {
        threadLocal.remove();
        requestInfoThreadLocal.remove();
        logInfoThreadLocal.remove();
        MDC.clear();
    }

}
