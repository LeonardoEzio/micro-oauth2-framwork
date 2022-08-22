package leonardo.ezio.personal.web.controller;


import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.security.constant.AuthorityLevel;
import leonardo.ezio.personal.feign.entity.OauthUrlValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 16:17
 */
@RestController
@RequestMapping("oauthUrl")
public class OauthUrlController {

    @GetMapping("sync")
    public CommonResult<List<OauthUrlValidator>> syncOauthUrl(){
        List<OauthUrlValidator> oauthUrlValidators = Arrays.asList(new OauthUrlValidator()
                        .setId(1)
                        .setAuthorityLevel(AuthorityLevel.TOKEN_ONLY.getValue())
                        .setPlatId(1)
                        .setUrl("/api/user/currentUser"),
                new OauthUrlValidator()
                        .setId(2)
                        .setAuthorityLevel(AuthorityLevel.LOWEST.getValue())
                        .setPlatId(1)
                        .setUrl("/api/hello"),
                new OauthUrlValidator()
                        .setId(3)
                        .setAuthorityLevel(AuthorityLevel.TOKEN_AND_MENU.getValue())
                        .setPlatId(1)
                        .setUrl("/api/user/disable"),
                new OauthUrlValidator()
                        .setId(4)
                        .setAuthorityLevel(AuthorityLevel.TOKEN_AND_MENU.getValue())
                        .setPlatId(1)
                        .setUrl("/api/user/add")
        );
        return CommonResult.success(oauthUrlValidators);
    }

}
