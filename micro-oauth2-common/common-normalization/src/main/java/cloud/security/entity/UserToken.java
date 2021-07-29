package cloud.security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-28 12:08
 */
@Data
public class UserToken implements Serializable {

    @JSONField(name = "user_name")
    private String userName;

    private List<String> scope;

    private List<RoleInfo> roles;

    private Long exp;

    private List<String> authorities;

    private String jti;

    @JSONField(name = "client_id")
    private String clientId;
}
