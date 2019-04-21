package com.unc.hospitalschool.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.init.HospitalschoolApplication;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalschoolApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GenderControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String base = "/gender/";

	
	@Test
	public void getAllGenders() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("genders"));
	}
	
	@Test
	public void createGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender",	"test gender");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createInvalidGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void updateGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		//input.put(key, value)
	}
	
	
	
	
	
	
	
	
	
}
