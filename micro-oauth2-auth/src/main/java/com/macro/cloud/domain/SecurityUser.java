package com.macro.cloud.domain;


import com.macro.cloud.security.entity.RoleInfo;
import com.macro.cloud.security.entity.UserInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 登录用户信息
 * Created by macro on 2020/6/19.
 */
@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Boolean enabled;


    private Collection<RoleInfo> roleInfos;

    /**
     * 权限url
     *
     * 2021、07、29弃用 url过多导致token过长
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser() {

    }

    public SecurityUser(UserInfo userInfo) {
        this.setId(userInfo.getId());
        this.setUsername(userInfo.getUserName());
        this.setPassword(userInfo.getPassword());
        this.setEnabled(userInfo.getStatus() == 0);
        if (userInfo.getRoles() != null){
            roleInfos = new ArrayList<>();
            userInfo.getRoles().forEach(role -> {
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setRoleName(role);
                roleInfos.add(roleInfo);
            });
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
