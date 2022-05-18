package com.example.gateway_demo.model;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class User implements UserDetails {

    private String userSeq;
    private String userId;
    private String userPw;
    private String userNa;
    private Long expireTime;

    private Set<String> authorities = Sets.newHashSet();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userSeq;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
