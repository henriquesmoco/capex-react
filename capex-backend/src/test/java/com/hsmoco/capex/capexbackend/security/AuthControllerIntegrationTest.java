package com.hsmoco.capex.capexbackend.security;

import com.hsmoco.capex.capexbackend.region.Region;
import com.hsmoco.capex.capexbackend.region.RegionPermission;
import com.hsmoco.capex.capexbackend.region.RegionRepository;
import com.hsmoco.capex.capexbackend.user.User;
import com.hsmoco.capex.capexbackend.user.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        regionRepository.deleteAll();
        userRepository.deleteAll();

        Region na = new Region();
        na.setCode("NA");
        na = regionRepository.save(na);

        Region ca = new Region();
        ca.setCode("CA");
        ca = regionRepository.save(ca);

        User user = new User();
        user.setUsername("john");
        user.setName("John");
        user.setPassword(passwordEncoder.encode("password"));

        RegionPermission p1 = new RegionPermission();
        p1.setUser(user);
        p1.setRegion(na);
        p1.setCanView(true);
        p1.setCanEdit(true);

        RegionPermission p2 = new RegionPermission();
        p2.setUser(user);
        p2.setRegion(ca);
        p2.setCanView(true);
        p2.setCanEdit(false);

        userRepository.save(user);
    }

    @Test
    void login_returnsAccessTokenAndRefreshCookie() throws Exception {
        String body = """
            {"username":"john","password":"password"}
            """;

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andReturn();
    }

    @Test
    void refresh_returnsNewAccessToken() throws Exception {
        // first login to get refresh cookie
        String body = """
            {"username":"john","password":"password"}
            """;

        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        String refreshToken = Objects.requireNonNull(loginResult.getResponse().getCookie("refreshToken")).getValue();


        mockMvc.perform(post("/api/auth/refresh")
                        .cookie(new Cookie("refreshToken", refreshToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }
}