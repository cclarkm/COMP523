package com.unc.hospitalschool.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.dao.AdmissionDao;
import com.unc.hospitalschool.dao.CountyDao;
import com.unc.hospitalschool.dao.GenderDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.init.HospitalschoolApplication;
import com.unc.hospitalschool.model.Admission;
import com.unc.hospitalschool.model.LogType;
import com.unc.hospitalschool.model.Student;
import com.unc.hospitalschool.model.Teacher;
import com.unc.hospitalschool.model.VisitInformation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
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
public class AdmissionControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AdmissionDao admitDao;
	
	@Autowired
	private StudentDao studentDao;
	
	private String base = "/admission/";
	
	private static Student student1;
	private static Student student2;
	
	private static Admission admit1;
	private static Admission admit2;
	private static Admission admit3;

	
	@Before
	public void setUpVisits() {
		List<Student> students = studentDao.findAll();
		student1 = copyStudent(students.get(0), 9999998);
		student2 = copyStudent(students.get(0), 9999999);
		try {
			student1.setFirstName("aaaaaa");
			student1.setLastName("zzzzzz");
			
			student2.setFirstName("aaa");
			student2.setLastName("lastName");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		studentDao.save(student1);
		studentDao.save(student2);

		admit1 = new Admission(student1, "2019-03-21", "2019-03-21");
		admit2 = new Admission(student1, "2018-03-21", "2019-03-15");
		admit3 = new Admission(student2, "2019-03-21", "2019-03-21");
		admitDao.save(admit1);
		admitDao.save(admit2);
		admitDao.save(admit3);
	}
	
	public Student copyStudent(Student stud, int newId) {
		Student result = new Student();
		try {
			result.setLastName(stud.getLastName());
			result.setFirstName(stud.getFirstName());
			result.setDob(stud.getDob());
			result.setGender(stud.getGender());
			result.setRaceEth(stud.getRaceEth());
			result.setServiceArea(stud.getServiceArea());
			result.setSchool(stud.getSchool());
			result.setDistrict(stud.getDistrict());
			result.setCounty(stud.getCounty());
			result.setGrade(stud.getGrade());
			result.setHispanic(stud.getHispanic());
			//result.setId(newId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllAdmissions() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("admissions"));
	}
	
	@Test
	public void getAllAdmissionsUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllAdmissionsByStudent() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("admissions"));
		assertEquals(2, StringUtils.countOccurrencesOf(text, "admissionDate"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllAdmissionsByStudentPageable() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() + "/page=0/size=1")).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("admissions"));
		assertEquals(1, StringUtils.countOccurrencesOf(text, "admissionDate"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllAdmissionsByStudentInvalidPage() throws Exception{
		mockMvc.perform(get(base + admit1.getId() + "/page=-1/size=1")).andExpect(status().isBadRequest()).andReturn();
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllAdmissionsByStudentInvalidSize() throws Exception{
		mockMvc.perform(get(base + admit1.getId() + "/page=0/size=0")).andExpect(status().isBadRequest()).andReturn();
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createAdmission() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019-03-21");
		input.put("discharge", "2019-03-21");
		input.put("student", Integer.toString(student1.getSid()));
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createAdmissionUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019-03-21");
		input.put("discharge", "2019-03-21");
		input.put("student", Integer.toString(student1.getSid()));
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createAdmissionMissingDischarge() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019-03-21");
		input.put("student", Integer.toString(student1.getSid()));
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createAdmissionInvalidStudent() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019-03-21");
		input.put("discharge", "2019-03-21");
		input.put("student", "-1");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Student with sid -1 could not be found", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createAdmissionDatesOutOfOrder() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019-03-21");
		input.put("discharge", "2019-02-21");
		input.put("student", Integer.toString(student1.getSid()));
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createAdmissionDatesPoorFormat() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("admission",	"2019/03/21");
		input.put("discharge", "2019-02-21");
		input.put("student", Integer.toString(student1.getSid()));
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateAdmission() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("discharge", "2019-04-21");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + admit1.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	
	}
	
	@Test
	public void updateAdmissionUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("discharge", "2019-04-21");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + admit1.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403));
	
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantAdmission() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("discharge", "2019-04-21");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + "-1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNotFound());
	
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateAdmissionBadDates() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("discharge", "2013-04-21");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + admit1.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
	
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteAdmission() throws Exception{
		mockMvc.perform(delete(base + admit1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
			
	}
	
	@Test
	public void deleteAdmissionUnauthenticated() throws Exception{
		mockMvc.perform(delete(base + admit1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantAdmission() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
		
	}
	
	
	
	
	
	
}
