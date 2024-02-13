package com.myproject.project.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SeleniumTest {
    @Autowired
    private MockMvc mockMvc;
    private GreenMail greenMail;

    private static final String BASE_URL = "http://host.testcontainers.internal";

    @LocalServerPort
    private Integer localServerPort;

    @Value("${spring.mail.port}")
    private Integer mailPort;
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
        this.greenMail = new GreenMail(new ServerSetup(mailPort, host, protocol));
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


    public  BrowserWebDriverContainer<?> chrome =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions());

    @BeforeEach
    void startWDC(){
        org.testcontainers.Testcontainers.exposeHostPorts(localServerPort);
        chrome.start();
    }
    @AfterEach
    void stopWDC(){
        chrome.stop();
    }

    @Test
//    @Sql(value = "/data.sql")
    void shouldLoadIndexPageWT(){
        String indexURI = "/";
        RemoteWebDriver driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
        driver.get(String.format("%s:%d%s", BASE_URL, localServerPort, indexURI));
        String myProject = driver.getTitle();

        Assertions.assertEquals(myProject, "My project");

    }
}
