package leonardo.ezio.personal.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description :
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 16:41
 */
@RestController
public class AuthController {

    @RequestMapping("test")
    public String test(){
        return "test success";
    }

    @RequestMapping("auth")
    public String auth(){
        return "鉴权成功！";
    }
}
