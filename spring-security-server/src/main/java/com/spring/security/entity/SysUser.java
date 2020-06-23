package com.spring.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-11
 * @Time: 16:59
 * @Version: eoms-micro-service 1.0
 */
public class SysUser extends User {

    private SysUserAuths sysUserAuths;

    public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SysUser(String username, String password, Collection<? extends GrantedAuthority> authorities, SysUserAuths sysUserAuths) {
        super(username, password, authorities);
        this.sysUserAuths = sysUserAuths;
    }

    public SysUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public SysUserAuths getSysUserAuths() {
        return sysUserAuths;
    }

    public void setSysUserAuths(SysUserAuths sysUserAuths) {
        this.sysUserAuths = sysUserAuths;
    }
}
