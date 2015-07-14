package com.jumpbyte.test.springrest.secure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, String[]> parms = request.getParameterMap();

        if (parms.containsKey("token")) {
            String strToken = parms.get("token")[0]; // grab the first "token" parameter
            System.out.println("Token: " + strToken);

            if (strToken.equals("test")) {
                System.out.println("valid token found");
                
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test");
                token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
                Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test", authorities); //this.authenticationProvider.authenticate(token);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                System.out.println("invalid token");
            }
        } else {
            System.out.println("no token found");
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}
