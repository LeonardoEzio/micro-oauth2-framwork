package com.macro.cloud.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:40
 *
 * url权限校验等级配置
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UrlValidatorLevel extends BaseFiled{

    private String url;

    private String remark;

    private Integer platId;

    private Integer authorityLevel;
}
