package com.unc.hospitalschool.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.dao.CountyDao;
import com.unc.hospitalschool.dao.GenderDao;
import com.unc.hospitalschool.dao.PSLabelDao;
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
public class PSLabelControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PSLabelDao psLabelDao;
	
	private String base = "/psLabel/";

	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllPSLabels() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		System.out.println("==================================================");
		System.out.println(text);
		System.out.println("==================================================");
		assertTrue(text.contains("PSLabels"));
	}
	
	@Test
	public void getAllPSLabelsUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createPSLabel() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("code", "my new code");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createPSLabelUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("code", "my new code");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createInvalidPSLabel() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("label/code field not provided", result.getResponse().getContentAsString());
	}
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updatePSLabel() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("code", "my new code");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	
		assertEquals("my new label", psLabelDao.findByLid(1).getLabel());
		assertEquals("my new code", psLabelDao.findByLid(1).getCode());
	}
	
	@Test
	public void updatePSLabelUnAuthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("code", "my new code");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403));
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantPSLabel() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("code", "my new code");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "-100").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - psLabel with lid: -100 not found", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updatePSLabelBadRequest1() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - psLabel. Incorrect request field", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updatePSLabelBadRequest2() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("label", "my new label");
		input.put("key", "value");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - psLabel. Incorrect request field", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updatePSLabelBadRequest3() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("code", "my new code");
		input.put("key", "value");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - psLabel. Incorrect request field", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deletePSLabel() throws Exception{
		mockMvc.perform(delete(base + "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
			
	}
	
	@Test
	public void deletePSLabelUnauthenticated() throws Exception{
		mockMvc.perform(delete(base + "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantPSLabel() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Unable to delete - psLabel with lid: -1 not found", result.getResponse().getContentAsString());
		
	}
	
	
	
	
	
	
}
