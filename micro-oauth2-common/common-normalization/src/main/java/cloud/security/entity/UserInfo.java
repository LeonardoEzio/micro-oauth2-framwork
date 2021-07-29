package cloud.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo implements Serializable {

    private Long id;

    private String userName;

    private String workCardId;

    private String password;

    private String email;

    private String phone;

    private Integer status;

    private Integer isDelete;

    private List<String> roles;

    private List<String> menus;
}
