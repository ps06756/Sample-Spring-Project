package com.prepfortech.security;

import com.prepfortech.accessor.UserAccessor;
import com.prepfortech.accessor.model.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RoleBasedAuthenticationManager implements AuthenticationManager {

    private final UserAccessor userAccessor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        List<GrantedAuthority> allowedRoles = new ArrayList<>(authentication.getAuthorities());

        UserDTO userDTO = userAccessor.getUserByEmail(email);
        if (userDTO != null) {
            for(int i = 0; i < allowedRoles.size(); i++) {
                if (allowedRoles.get(i).equals(userDTO.getRole().toString())) {
                    return new UsernamePasswordAuthenticationToken(
                            new User(email, userDTO.getPassword(), allowedRoles),
                            "", allowedRoles);
                }
            }
        }

        throw new BadCredentialsException("Role not allowed!");
    }
}
