package com.unc.hospitalschool.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.dao.WithdrawDao;
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
public class WithdrawControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WithdrawDao withdrawDao;
	
	private String base = "/withdraw/";

	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllWithdraws() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("withdraws"));
	}
	
	@Test
	public void getAllWithdrawsUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createWithdraw() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("withdraw",	"test withdraw");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createWithdrawUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("withdraw",	"test withdraw");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createInvalidWithdraw() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("withdraw field not provided", result.getResponse().getContentAsString());
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateWithdraw() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("withdraw", "my new withdraw");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	
		assertEquals("my new withdraw", withdrawDao.findByWid(1).getWithdraw());
	}
	
	@Test
	public void updateWithdrawUnAuthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("withdraw", "my new withdraw");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403));
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantWithdraw() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("withdraw", "my new withdraw");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "-100").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - withdraw with wid: -100 not found", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateWithdrawBadRequest() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("key", "value");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - withdraw. Incorrect request field", result.getResponse().getContentAsString());
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteWithdraw() throws Exception{
		mockMvc.perform(delete(base + "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
			
	}
	
	@Test
	public void deleteWithdrawUnauthenticated() throws Exception{
		mockMvc.perform(delete(base + "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantWithdraw() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Unable to delete - withdraw with wid: -1 not found", result.getResponse().getContentAsString());
		
	}
	
	
	
	
	
	
}
