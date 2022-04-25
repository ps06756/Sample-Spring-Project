package com.prepfortech.security;

import com.prepfortech.accessor.models.UserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserSecurityService implements UserDetailsService {
    private com.prepfortech.service.UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByEmail(username);

        if (userDTO != null) {
            return new User(userDTO.getEmail(), userDTO.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(userDTO.getRole().name())));
        }
        else {
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }
    }
}
