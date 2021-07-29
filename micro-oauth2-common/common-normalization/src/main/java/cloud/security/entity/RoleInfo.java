package cloud.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 16:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleInfo implements Serializable {

    private String roleName;

    private Integer platId;

}
