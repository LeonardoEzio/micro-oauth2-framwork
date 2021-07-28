package com.macro.cloud.util;


import com.macro.cloud.security.constant.AuthorityLevel;
import com.macro.cloud.security.entity.OauthUrlValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.util.List;
import java.util.Set;

/**
 * *@author          lx
 * *@Createdate      2020-06-17-11:59
 * *@desc
 * *@UpdateUser      lx
 * *@UpdateDate      2020-06-17-11:59
 * *@UpdateRemark    修改内容
 * *@Version         1.0
 **/
@Slf4j
public class UrlPatternMatchUtils extends PatternMatchUtils {

    /**
     * 比较str是否在patterns所在的URL模板里，如果不在则返回Integer.MAX_VALUE。如果在则返回该URL pattern对应的authority level
     *
     * @param patterns patterns
     * @param str      str
     * @return return
     */
    public static int simpleMatch(List<OauthUrlValidator> patterns, String str) {
        if (patterns != null) {
            for (OauthUrlValidator oauthUrlValidatorDO : patterns) {
                String pattern = oauthUrlValidatorDO.getUrl();
                if (simpleMatch(pattern, str) || simpleMatch("/api" + pattern, str)) {
                    return oauthUrlValidatorDO.getAuthorityLevel();
                }
            }
        }
        return AuthorityLevel.TOKEN_AND_MENU.getValue();
    }

    /**
     * 匹配url获取平台标识
     *
     * @param patterns patterns
     * @param str      str
     * @return int
     */
    public static int simpleMatchUrlPlats(Set<OauthUrlValidator> patterns, String str) {
        if (patterns != null) {
            for (OauthUrlValidator oauthUrlValidatorDO : patterns) {
                if (oauthUrlValidatorDO.getUrl().equals(str)) {
                    return oauthUrlValidatorDO.getPlatId();
                }
            }
        }
        return -1;
    }
}
