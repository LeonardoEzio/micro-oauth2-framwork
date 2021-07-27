package com.macro.cloud.domain.security;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 18:13
 *
 * token 校验请求
 *
 */
@Data
@Accessors(chain = true)
public class TokenVerifyRequest implements Serializable {

    private String accessToken;

}
