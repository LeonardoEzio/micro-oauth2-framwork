package leonardo.ezio.personal.feign.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 15:35
 *
 * url验证配置，供特殊权限校验使用
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OauthUrlValidator implements Serializable {

    private static final long serialVersionUID = 1776390653391227433L;

    private Integer id;

    private String url;

    private String remark;

    private Integer platId;

    private Integer authorityLevel;
}
