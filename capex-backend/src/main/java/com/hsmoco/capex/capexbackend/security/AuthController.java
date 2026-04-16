package com.hsmoco.capex.capexbackend.security;

import com.hsmoco.capex.capexbackend.security.jwt.JwtService;
import com.hsmoco.capex.capexbackend.user.User;
import com.hsmoco.capex.capexbackend.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationProvider authenticationProvider,
                          UserDetailsService userDetailsService,
                          UserRepository userRepository,
                          JwtService jwtService,
                          RefreshTokenService refreshTokenService) {
        this.authenticationProvider = authenticationProvider;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Authentication auth = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password)
        );

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String accessToken = jwtService.generateAccessToken(principal);

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // send refresh token as httpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(Duration.between(Instant.now(), refreshToken.getExpiresAt()))
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new TokenResponse(accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken) {
        User user = refreshTokenService.validateAndGetUser(refreshToken);

        // reload principal with current region permissions
        UserPrincipal principal = (UserPrincipal) userDetailsService
                .loadUserByUsername(user.getUsername());

        String newAccessToken = jwtService.generateAccessToken(principal);

        return ResponseEntity.ok(new TokenResponse(newAccessToken));
    }

    public record LoginRequest(String username, String password) {
    }

    public record TokenResponse(String accessToken) {
    }
}