package leonardo.ezio.personal.exception;

import leonardo.ezio.personal.api.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理Oauth2抛出的异常
 * Created by macro on 2020/7/17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult handleCommonException(Exception e) {
        return CommonResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public CommonResult handleOauth2Exception(AuthenticationException e) {
        return CommonResult.failed(e.getMessage());
    }
}
