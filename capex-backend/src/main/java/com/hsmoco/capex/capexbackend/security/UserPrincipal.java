package com.hsmoco.capex.capexbackend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class UserPrincipal implements UserDetails {
    @Getter
    private final Long userId;
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private final Collection<? extends GrantedAuthority> authorities;
    @Getter
    private final Set<String> viewRegionCodes;
    @Getter
    private final Set<String> editRegionCodes;

    public UserPrincipal(
            Long userId,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Set<String> viewRegionCodes,
            Set<String> editRegionCodes
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = Collections.unmodifiableCollection(authorities);
        this.viewRegionCodes = Collections.unmodifiableSet(viewRegionCodes);
        this.editRegionCodes = Collections.unmodifiableSet(editRegionCodes);
    }
}
