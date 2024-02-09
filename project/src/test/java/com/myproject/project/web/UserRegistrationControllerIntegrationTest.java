package com.myproject.project.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private GreenMail greenMail;

    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.username}")
    private String username;

    @BeforeEach
    void setUp(){
        this.greenMail = new GreenMail(new ServerSetup(port, host, protocol));
        this.greenMail.start();
        this.greenMail.setUser(username, password);
    }

    @AfterEach
    void tearDown(){
        this.greenMail.stop();
    }

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine")
            .withUsername("testUser")
            .withPassword("password")
            .withDatabaseName("testDB");


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    void testRegistrationPageShown() throws Exception {
        this.mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @Sql(value = "/data.sql")
    void testRegistration() throws Exception {
        String firstName = "Test";
        String lastName = "Testov";
        String email = "example@exam.cam";
        this.mockMvc.perform(post("http://localhost/users/register")
                        .param("email", email)
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("age", "18")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        MimeMessage[] receivedMessages = this.greenMail.getReceivedMessages();
        Assertions.assertEquals(1, receivedMessages.length);
        MimeMessage welcomeMessage = receivedMessages[0];

        Assertions.assertTrue(welcomeMessage
                .getContent()
                .toString()
                .contains(firstName));

    }
}