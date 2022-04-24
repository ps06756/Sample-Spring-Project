package com.prepfortech.security;

import com.prepfortech.accessor.models.AuthDTO;
import com.prepfortech.accessor.models.UserDTO;
import com.prepfortech.service.AuthService;
import com.prepfortech.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import io.jsonwebtoken.Claims;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static com.prepfortech.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    private AuthService authService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        ServletContext servletContext = req.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
        if (userService == null) {
            userService = (UserService) webApplicationContext.getBean("userService");
        }

        if (authService == null) {
            authService = (AuthService) webApplicationContext.getBean("authService");
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication = " + authentication.getName());
            chain.doFilter(req, res);
        } catch (MalformedJwtException ex) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(HEADER_STRING);
        String token = "";
        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith(TOKEN_PREFIX)) {
                token = authorizationHeader.replace(TOKEN_PREFIX, "");
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET.getBytes())
                        .parseClaimsJws(token)
                        .getBody();

                Date expirationDate = claims.getExpiration();
                if (expirationDate.before(new Date(System.currentTimeMillis()))) {
                    AuthDTO authDTO = authService.findByToken(claims.getAudience());
                    if (authDTO != null) {
                        UserDTO userDTO = userService.getUserByEmail(claims.getSubject());
                        if (userDTO != null) {
                            return new UsernamePasswordAuthenticationToken(userDTO,
                                    null, Arrays.asList(new SimpleGrantedAuthority(userDTO.getRole().toString())));
                        }
                    }
                }
            }
        }
        return new UsernamePasswordAuthenticationToken(null, null,
                Arrays.asList(new SimpleGrantedAuthority(Roles.Anonymous)));
    }
}
