package com.unc.hospitalschool.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unc.hospitalschool.dao.CountyDao;
import com.unc.hospitalschool.dao.GenderDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.init.HospitalschoolApplication;
import com.unc.hospitalschool.model.Student;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalschoolApplication.class)
@AutoConfigureMockMvc
@Transactional
public class StudentControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private StudentDao studentDao;
	
	private String base = "/student/";
	
	public static boolean setUp = false;
	private static Logger logger = LoggerFactory.getLogger("LOGGER");

	@Before
	public void setUpStudents() {
		List<Student> students = studentDao.findAll();
		Student student1 = copyStudent(students.get(0), 9999998);
		Student student2 = copyStudent(students.get(1), 9999999);
		try {
			student1.setFirstName("aaaaaa");
			student2.setFirstName("aaaaaa");
			student1.setLastName("zzzzzz");
			student2.setLastName("bbbbbb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		studentDao.save(student1);
		studentDao.save(student2);

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
			result.setId(newId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllStudents() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("students"));
	}
	
	@Test
	public void getAllStudentsUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllStudentsByFName() throws Exception{
		MvcResult result = mockMvc.perform(get(base + "fname=aaaaaa")).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		int numberOfStudents = StringUtils.countOccurrencesOf(text, "lastName");
		assertTrue(text.contains("students"));
		assertEquals(2, numberOfStudents);
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllStudentsByLName() throws Exception{
		MvcResult result = mockMvc.perform(get(base + "lname=zzzzzz")).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		int numberOfStudents = StringUtils.countOccurrencesOf(text, "lastName");
		assertTrue(text.contains("students"));
		assertEquals(1, numberOfStudents);
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllStudentsByFNameAndLName() throws Exception{
		MvcResult result = mockMvc.perform(get(base + "lname=bbbbbb/fname=aaaaaa")).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		int numberOfStudents = StringUtils.countOccurrencesOf(text, "lastName");
		assertTrue(text.contains("students"));
		assertEquals(1, numberOfStudents);
	}
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudent() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void createStudentUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentWithOptionals() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		input.put("studentNotes", "notes");
		input.put("permissionDate", "2019-03-21");
		input.put("label", "label");
		input.put("psLabel", "1");
		input.put("currentTeacher", "1");
		input.put("secondTeacher", "1");
		

		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentMissingRequiredKey() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
	}
	
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentGradeNonIntegerKey() throws Exception{
		String impropperField = "grade";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"a");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentCountyNonIntegerKey() throws Exception{
		String impropperField = "county";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"a");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentDistrictNonIntegerKey() throws Exception{
		String impropperField = "district";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"a");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentSchoolNonIntegerKey() throws Exception{
		String impropperField = "school";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"a");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentserviceAreaNonIntegerKey() throws Exception{
		String impropperField = "serviceArea";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"a");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentraceEthNonIntegerKey() throws Exception{
		String impropperField = "raceEthnicity";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"a");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentgenderNonIntegerKey() throws Exception{
		String impropperField = "gender";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"a");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"false");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createStudentHispanicNonBooleanKey() throws Exception{
		String impropperField = "hispanic";
		Map<String, String> input = new HashMap<String, String>();
		input.put("lastName",	"lName");
		input.put("firstName",	"fName");
		input.put("dob",	"2019-03-21");
		input.put("gender",	"1");
		input.put("raceEthnicity",	"1");
		input.put("serviceArea",	"1");
		input.put("school",	"1");
		input.put("district",	"1");
		input.put("county",	"1");
		input.put("grade",	"1");
		input.put("hispanic",	"falseq");
		

		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Field " + impropperField + " must be boolean", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateStudent() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("firstName", "my new FirstName");
		
		String json = new ObjectMapper().writeValueAsString(input);
		int id = studentDao.findByFirstName("aaaaaa").get(0).getSid();
		MvcResult result = mockMvc.perform(put(base + id).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
		
		
	}
	
	@Test
	public void updateStudentUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("firstName", "my new FirstName");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "9999999").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
		
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantStudent() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("firstName", "my new FirstName");
		
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(put(base + "-1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		
		assertEquals("Unable to update - student with sid: -1 not found", result.getResponse().getContentAsString());
	}
	
	
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteStudent() throws Exception{
		int id = studentDao.findByFirstName("aaaaaa").get(0).getSid();
		MvcResult res = mockMvc.perform(delete(base + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		logger.info(res.getResponse().getContentAsString());
			
	}
	
	@Test
	public void deleteStudentUnauthenticated() throws Exception{
		mockMvc.perform(delete(base + "9999999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantStudent() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Unable to delete - student with sid: -1 not found", result.getResponse().getContentAsString());
		
	}
	
	
	
	
	
	
	
	
	
	
}
