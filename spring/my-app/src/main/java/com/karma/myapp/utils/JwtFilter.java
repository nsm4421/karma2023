package com.karma.myapp.utils;

import com.karma.myapp.domain.dto.CustomPrincipal;
import com.karma.myapp.service.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserAccountService userAccountService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        final String header;
        final String token;
        try {
            // validate header
            header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) {
                log.error("Header is invalid(request url : {})", request.getRequestURI());
                chain.doFilter(request, response);
                return;
            } else {
                token = header.split(" ")[1].trim();
            }

            // extract claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // check token is expired
            Date expires = claims.getExpiration();
            if (expires.before(new Date())){
                log.error("Token is expired");
                chain.doFilter(request, response);
                return;
            }

            // get principal from DB
            String username = claims.get("username", String.class);
            CustomPrincipal principal = userAccountService.loadByUsername(username);

            // return authentication
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal, null,
                    principal.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            log.error("JWT filter error : {}", e.toString());
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}
