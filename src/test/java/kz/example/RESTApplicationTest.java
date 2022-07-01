package kz.example;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.example.dto.AuthTokenDto;
import kz.example.dto.MessageDto;
import kz.example.dto.UserDto;
import kz.example.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RESTApplication.class)
@WebAppConfiguration
public class RESTApplicationTest {

    private final UserDto testUser;
    private final MessageDto testMessage;
    private static String createdUserId;

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public RESTApplicationTest() {
        String randomString = RandomStringUtils.randomAlphanumeric(10);

        testUser = new UserDto();
        testUser.setName("TestUser");
        testUser.setPassword("testP@ss");

        testMessage = new MessageDto();
        testMessage.setName("TestUser");
        testMessage.setMessage(randomString);
    }

    @Test
    public void t1_createUser() throws Exception {
        String uri = "/userauth/register";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(testUser)))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        User jsonResp = mapFromJson(content, User.class);
        createdUserId = String.valueOf(jsonResp.getId());
        assertEquals(jsonResp.getUsername(), testUser.getName());
    }

    @Test
    public void t2_checkUser() throws Exception {
        String uri = "/userauth/get-token";
        String userJson = mapToJson(testUser);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        AuthTokenDto jsonResp = mapFromJson(content, AuthTokenDto.class);
        String token = jsonResp.getToken();

        assertEquals(token.substring(0,2), "ey");
    }

    @Test
    public void t3_sendMessage() throws Exception {
        String uri = "/message/send";
        String messageJson = mapToJson(testMessage);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(messageJson))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Message saved succesfully");
    }

    @Test
    public void t4_deleteMessage() throws Exception {
        String uri = "/message/delete/"+createdUserId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Messages deleted");
    }

    @Test
    public void t5_deleteUser() throws Exception {
        String uri = "/userauth/delete/"+createdUserId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "User deleted");
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}