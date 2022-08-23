package leonardo.ezio.personal.web.controller;

import leonardo.ezio.personal.web.dto.AuthDto;
import org.springframework.web.bind.annotation.*;

/**
 * @Description :
 * @Author : LeonardoEzio
 * @Date: 2022-08-22 16:41
 */
@RestController
public class AuthController {

    @GetMapping(value = "test")
    public String test(@RequestParam String msg){
        return "test success";
    }

    @DeleteMapping(value = "delete/{id}")
    public String delete(@PathVariable Integer id){
        return "delete success";
    }

    @RequestMapping("auth")
    public String auth(@RequestBody AuthDto authDto){
        return "鉴权成功！";
    }
}
