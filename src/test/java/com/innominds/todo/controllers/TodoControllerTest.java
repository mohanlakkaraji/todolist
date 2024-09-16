package com.innominds.todo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innominds.todo.entity.Task;
import com.innominds.todo.repository.CRUDRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TodoControllerTest {
	
	@LocalServerPort
	private int randomServerPort;
	
	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	private CRUDRepository repository;
	
	@BeforeEach
	public void setup() throws Exception {
		objectMapper = new ObjectMapper();
	}
	@Test
	void createTaskTest() throws JsonProcessingException, Exception {
		
		Task task = new Task();
		task.setTitle("My task 1");
		task.setDescription("setup meeting invite");
		task.setStatus("open");
		task.setUserId("mohan");
		
	
		
		 mockMvc.perform(post("/todo").contentType(MediaType.
				  APPLICATION_JSON_VALUE)
				  .content(objectMapper.writeValueAsString(task))
				 .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().
				  is2xxSuccessful ())
				 .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists());
	}
	
	@Test
	void updateTaskHandlingLock() throws JsonProcessingException, Exception {
		
		
		Task task = new Task();
		task.setTitle("My task 1");
		task.setDescription("setup meeting invite");
		task.setStatus("open");
		task.setUserId("mohan");
		Task createdTask = repository.insert(task);
		Task updatedTask = repository.save(createdTask);
		updatedTask.setVersion(1);
		
		 mockMvc.perform(put("/todo/"+updatedTask.getId()).contentType(MediaType.
				  APPLICATION_JSON_VALUE)
				  .content(objectMapper.writeValueAsString(createdTask))
				 .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isLocked()
				);
		
	}
	
	@Test
	void updateTask() throws JsonProcessingException, Exception {
		
		
		Task task = new Task();
		task.setTitle("My task 1");
		task.setDescription("setup meeting invite");
		task.setStatus("open");
		task.setUserId("mohan");
		
		Task createdTask = repository.insert(task);
		createdTask.setStatus("pending");
		
		
		
		 mockMvc.perform(put("/todo/"+task.getId()).contentType(MediaType.
				  APPLICATION_JSON_VALUE)
				  .content(objectMapper.writeValueAsString(createdTask))
				 .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().
				  is2xxSuccessful ())
				 .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("pending"));
	}

}
