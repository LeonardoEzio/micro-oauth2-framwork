

package com.macro.cloud.aop;

import com.macro.cloud.annotation.SysLog;
import com.macro.cloud.entity.RequestInfo;
import com.macro.cloud.feign.entity.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Controller Aop
 * 获取响应结果信息
 * </p>
 *
 * @
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(value = {"log.aop.enable"}, matchIfMissing = true)
public class DefaultLogAop extends LogAop{


    @Override
    @Around("@annotation(sysLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, SysLog sysLog) throws Throwable {
        return super.handle(joinPoint,sysLog);
    }

    @Override
    @AfterThrowing(value = "@annotation(sysLog)",throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, SysLog sysLog, Exception exception) {
        super.handleAfterThrowing(exception);
    }

    @Override
    protected void setRequestId(RequestInfo requestInfo) {
        super.handleRequestId(requestInfo);
    }

    @Override
    protected void getRequestInfo(RequestInfo requestInfo) {
        // 处理请求参数日志
        super.handleRequestInfo(requestInfo);
    }

    @Override
    protected void getResponseResult(Object result) {
        // 处理响应结果日志
        super.handleResponseResult(result);
    }

    @Override
    protected void finish(RequestInfo requestInfo, LogInfo logInfo, Object result, Exception exception) {
        super.saveLogInfo(requestInfo,logInfo,result,exception);
    }



}
