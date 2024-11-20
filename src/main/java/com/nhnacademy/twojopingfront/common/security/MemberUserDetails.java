package com.nhnacademy.twojopingfront.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberUserDetails implements UserDetails {
    private final Long customerId;
    private final String password;
    private final String nickname;
    private final Collection<GrantedAuthority> authorities;

    public MemberUserDetails(Long customerId, String password, String nickname,
                             Collection<GrantedAuthority> authorities) {
        this.customerId = customerId;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    public Long getId() {
        return customerId;
    }
}
