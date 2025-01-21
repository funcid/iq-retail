package me.func.controller;

import me.func.infrastructure.dao.EmailData;
import me.func.infrastructure.dao.PhoneData;
import me.func.infrastructure.dao.User;
import me.func.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        createTestUsers();
    }

    @Test
    @WithMockUser
    void shouldSearchUsersByDateOfBirth() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("dateOfBirth", "2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("User2"));
    }

    @Test
    @WithMockUser
    void shouldSearchUsersByPhone() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("phone", "71234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("User1"));
    }

    @Test
    @WithMockUser
    void shouldSearchUsersByName() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("name", "User"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    @WithMockUser
    void shouldSearchUsersByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .param("email", "user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("User1"));
    }

    private void createTestUsers() {
        User user1 = new User();
        user1.setName("User1");
        user1.setDateOfBirth(Date.valueOf(LocalDate.of(2000, 1, 1)));
        
        EmailData email1 = new EmailData();
        email1.setEmail("user1@example.com");
        email1.setUser(user1);
        
        PhoneData phone1 = new PhoneData();
        phone1.setPhone("71234567890");
        phone1.setUser(user1);
        
        user1.setEmails(List.of(email1));
        user1.setPhones(List.of(phone1));

        User user2 = new User();
        user2.setName("User2");
        user2.setDateOfBirth(Date.valueOf(LocalDate.of(2025, 1, 1)));
        
        EmailData email2 = new EmailData();
        email2.setEmail("user2@example.com");
        email2.setUser(user2);
        
        PhoneData phone2 = new PhoneData();
        phone2.setPhone("79876543210");
        phone2.setUser(user2);
        
        user2.setEmails(List.of(email2));
        user2.setPhones(List.of(phone2));

        userRepository.saveAll(List.of(user1, user2));
    }
} 