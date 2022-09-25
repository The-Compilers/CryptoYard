package org.compilers.cryptoyard.security;

import org.compilers.cryptoyard.model.Role;
import org.compilers.cryptoyard.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Contains authentication information, needed by UserDetailsService
 */
public class AccessUserDetails implements UserDetails {
    private final String username;
    private final String hashedPassword;
    private final boolean isActive;
    private final List<GrantedAuthority> authorities = new LinkedList<>();

    public AccessUserDetails(User user) {
        this.username = user.getUsername();
        this.hashedPassword = user.getHashedPassword();
        this.isActive = user.isActive();
        this.convertRoles(user.getRoles());
    }

    private void convertRoles(Set<Role> roles) {
        authorities.clear();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
