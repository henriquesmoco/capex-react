package com.hsmoco.capex.capexbackend.security;

import com.hsmoco.capex.capexbackend.region.RegionPermission;
import com.hsmoco.capex.capexbackend.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> viewRegions = user.getRegionPermissions().stream()
                .filter(RegionPermission::isCanView)
                .map(p -> p.getRegion().getCode())
                .collect(Collectors.toSet());

        Set<String> editRegions = user.getRegionPermissions().stream()
                .filter(RegionPermission::isCanEdit)
                .map(p -> p.getRegion().getCode())
                .collect(Collectors.toSet());

        Collection<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities,
                viewRegions,
                editRegions
        );
    }
}
