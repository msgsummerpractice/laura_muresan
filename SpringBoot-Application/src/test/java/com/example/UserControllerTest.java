package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.Controller.UserController;
import com.example.Service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
   @MockitoBean
   private UserService userService;
   @Autowired
   private MockMvc mvc;
   @Test
   public void testEndpoint() throws Exception {
       Mockito.when(userService.getAll()).thenReturn(java.util.Collections.emptyList());
       mvc.perform(MockMvcRequestBuilders.get("/users"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string("[]"))
          .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"));
   }

   
}