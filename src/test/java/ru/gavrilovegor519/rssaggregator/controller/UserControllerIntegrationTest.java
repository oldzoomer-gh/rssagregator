package ru.gavrilovegor519.rssaggregator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilovegor519.rssaggregator.config.TestContainersConfig;
import ru.gavrilovegor519.rssaggregator.dto.input.user.LoginDto;
import ru.gavrilovegor519.rssaggregator.dto.input.user.RegDto;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.repo.UserRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest extends TestContainersConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testLogin() throws Exception {
        createUser("test@example.com", "password");

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        createUser("test@example.com", "password");

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("wrongpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testRegistration() throws Exception {
        RegDto regDto = new RegDto();
        regDto.setEmail("newuser@example.com");
        regDto.setPassword("newpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/user/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail("newuser@example.com").orElse(null);
        assertTrue(user != null);
        assertEquals("newuser@example.com", user.getEmail());
    }

    @Test
    public void testRegistrationWithDuplicateEmail() throws Exception {
        createUser("test@example.com", "password");

        RegDto regDto = new RegDto();
        regDto.setEmail("test@example.com");
        regDto.setPassword("newpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0/user/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andExpect(status().isConflict());
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
