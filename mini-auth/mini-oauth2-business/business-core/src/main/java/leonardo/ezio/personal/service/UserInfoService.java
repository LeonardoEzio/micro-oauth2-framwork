package leonardo.ezio.personal.service;

import leonardo.ezio.personal.dao.entity.SysUser;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:28
 */
public interface UserInfoService {

    SysUser addUser(SysUser user);

    SysUser findUserByName(String userName);

    boolean disableUser(SysUser user);

    boolean deleteUser(SysUser user);

}
