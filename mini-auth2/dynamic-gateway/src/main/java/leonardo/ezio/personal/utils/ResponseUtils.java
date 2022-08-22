package leonardo.ezio.personal.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.nio.charset.StandardCharsets;

/**
 * @Description : 网关响应工具类 - 构建网关层响应信息
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 16:30
 */
public class ResponseUtils {

    public static DataBuffer getResponseBuffer(ServerHttpResponse response, Object data){
        String jsonStr = JSONObject.toJSONString(data);
        byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        return response.bufferFactory().wrap(bytes);
    }
}
