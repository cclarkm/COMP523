package com.unc.hospitalschool.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.dao.GenderDao;
import com.unc.hospitalschool.init.HospitalschoolApplication;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
	
	@Autowired
	private GenderDao genderDao;
	
	private String base = "/gender/";

	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllGenders() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("genders"));
	}
	
	@Test
	public void getAllGendersUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender",	"test gender");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createGenderUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender",	"test gender");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createInvalidGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender", "my new gender");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
		
		assertEquals("my new gender", genderDao.findById(1).getGender());
	}
	
	@Test
	public void updateGenderUnAuthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender", "my new gender");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403));
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantGender() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("gender", "my new gender");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "-100").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - gender with id: -100 not found", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateGenderBadRequest() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - gender. Incorrect request field", result.getResponse().getContentAsString());
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteGender() throws Exception{
		mockMvc.perform(delete(base + "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
			
	}
	
	@Test
	public void deleteGenderUnauthenticated() throws Exception{
		mockMvc.perform(delete(base + "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantGender() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Unable to delete - gender with id: -1 not found", result.getResponse().getContentAsString());
		
	}
	
	
	
	
	
}
