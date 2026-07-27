package com.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
   @MockitoBean
   private UserService userService;
   @Autowired
   private MockMvc mvc;
   @Test
   public void endpoint_statusOk_whenUsersEndpointIsCalled() throws Exception {
       Mockito.when(userService.getAll()).thenReturn(java.util.Collections.emptyList());
       mvc.perform(MockMvcRequestBuilders.get("/users"))
          .andExpect(MockMvcResultMatchers.status().isOk());
   }
   @Test
   public void endpoint_contentEmptyList_whenUsersEndpointIsCalled() throws Exception {
       Mockito.when(userService.getAll()).thenReturn(java.util.Collections.emptyList());
       mvc.perform(MockMvcRequestBuilders.get("/users"))
          .andExpect(MockMvcResultMatchers.content().string("[]"));
   }
   @Test
   public void endpoint_contentTypeJson_whenUsersEndpointIsCalled() throws Exception {
       Mockito.when(userService.getAll()).thenReturn(java.util.Collections.emptyList());
       mvc.perform(MockMvcRequestBuilders.get("/users"))
         .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"));
   }

   @Test
   public void getAll_returnsListOfUsers() throws Exception {
       java.util.List<com.example.model.User> users = java.util.Arrays.asList(
           new com.example.model.User("1", 25, "John Doe"),
           new com.example.model.User("2", 30, "Jane Doe")
       );
       Mockito.when(userService.getAll()).thenReturn(users);

       mvc.perform(MockMvcRequestBuilders.get("/users"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(users.size()));

   }

   @Test void add_returnsStatusOk_whenValidUserIsAdded() throws Exception {
       com.example.model.User user = new com.example.model.User("3", 22, "Alice Smith");
       String userJson = String.format("{\"id\":\"%s\",\"age\":%d,\"name\":\"%s\"}", user.getId(), user.getAge(), user.getName());

       mvc.perform(MockMvcRequestBuilders.post("/users")
               .contentType("application/json")
               .content(userJson))
          .andExpect(MockMvcResultMatchers.status().isOk());
   }
}