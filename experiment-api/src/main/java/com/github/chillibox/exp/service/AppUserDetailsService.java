package com.github.chillibox.exp.service;

import com.github.chillibox.exp.entity.SysUser;
import com.github.chillibox.exp.repository.SysUserRepository;
import com.github.chillibox.exp.utils.Constants;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Created on 2017/7/15.</p>
 *
 * @author Gonster
 */

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Value("${app.admin.username}")
    private String ADMIN_USERNAME;

    @Value("${app.admin.password}")
    private String ADMIN_PASSWORD;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AppUserDetails defaultUser;

    @PostConstruct
    public void init() {
        SysUser user = new SysUser();
        user.setRole(Constants.ROLE_ADMIN);
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        user.setUsername(ADMIN_USERNAME);

        defaultUser = new AppUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equalsIgnoreCase(ADMIN_USERNAME)) return defaultUser;

        SysUser user = sysUserRepository.findByUsernameOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AppUserDetails(user);
    }

    public static class AppUserDetails implements UserDetails {

        private static final long serialVersionUID = 2543238128975411933L;

        private SysUser user;

        public AppUserDetails() {
        }

        public AppUserDetails(SysUser user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authz = new ArrayList<>(1);
            if (user != null && !Strings.isNullOrEmpty(user.getRole()))
                authz.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + user.getRole()));
            return authz;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
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
            return true;
        }
    }
}
