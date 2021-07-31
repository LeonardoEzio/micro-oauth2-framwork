package com.macro.cloud.component;

import cn.hutool.json.JSONUtil;
import com.macro.cloud.api.CommonResult;
import com.macro.cloud.api.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;


/**
 * 服务被拒绝访问时 自定义返回结果
 * Created by macro on 2018/4/26.
 */
@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        String responseBody = "";
        String message = denied.getMessage();
        if (isResultCode(message)){
            long resultCode = Long.valueOf(message);
            ResultCode code = ResultCode.findByCode(resultCode);
            if (null != code){
                responseBody= JSONUtil.toJsonStr(CommonResult.failed(code));
            }
        } else {
            responseBody= JSONUtil.toJsonStr(CommonResult.failed(denied.getMessage()));
        }

        DataBuffer buffer =  response.bufferFactory().wrap(responseBody.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }

    private boolean isResultCode(String message){
        if (StringUtils.isNotEmpty(message)){
            for (char c : message.toCharArray()) {
                if (!Character.isDigit(c)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}


