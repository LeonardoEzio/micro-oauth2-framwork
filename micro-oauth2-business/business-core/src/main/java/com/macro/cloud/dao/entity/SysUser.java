package com.macro.cloud.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:11
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseFiled{

    private String userName;

    private String workCardId;

    private String password;

    private String email;

    private String phone;

    private Integer status;

    private Integer isDelete;

}
