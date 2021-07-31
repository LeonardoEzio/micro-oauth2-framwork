package leonardo.ezio.personal.service;

import leonardo.ezio.personal.api.CommonResult;
import leonardo.ezio.personal.common.RedisBusinessKey;
import leonardo.ezio.personal.feign.entity.UserInfo;
import leonardo.ezio.personal.constant.MessageConstant;
import leonardo.ezio.personal.domain.SecurityUser;
import leonardo.ezio.personal.feign.client.RemoteUserInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户管理业务类
 * Created by macro on 2020/6/19.
 */
@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RemoteUserInfoClient userInfoClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CommonResult<UserInfo> userResult = userInfoClient.getUserByName(username);
        if (null == userResult.getData()){
            throw new UsernameNotFoundException(MessageConstant.USER_NOT_FIND);
        }
        SecurityUser securityUser = new SecurityUser(userResult.getData());
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        //缓存用户的菜单权限
        redisTemplate.delete(String.format(RedisBusinessKey.USER_ACCESS_MENU, securityUser.getUsername()));
        redisTemplate.opsForList().rightPushAll(String.format(RedisBusinessKey.USER_ACCESS_MENU, securityUser.getUsername()),userResult.getData().getMenus());
        return securityUser;
    }

}
