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
import com.unc.hospitalschool.dao.LogTypeDao;
import com.unc.hospitalschool.dao.StudentDao;
import com.unc.hospitalschool.dao.TeacherDao;
import com.unc.hospitalschool.dao.VisitInformationDao;
import com.unc.hospitalschool.init.HospitalschoolApplication;
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
public class VisitInformationControllerTests {
	
	@Autowired
    protected WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private LogTypeDao logTypeDao;
	
	@Autowired
	private VisitInformationDao visitInfoDao;
	
	private String base = "/visitInformation/";
	
	private static Logger logger = LoggerFactory.getLogger("LOGGER");
	
	private static VisitInformation visit1;
	private static VisitInformation visit2;
	private static VisitInformation visit3;
	private static VisitInformation visit4;
	
	private static Student student1;
	private static Student student2;
	
	private static Teacher teacher1;
	private static Teacher teacher2;
	
	private static LogType type1;
	private static LogType type2;
	
	private static int logsForStudent1 = 3;
	private static int logsForStudent2 = 1;
	
	private static int logsForStudent1Teacher1 = 2;
	private static int logsForStudent1Teacher2 = 1;
	private static int logsForStudent2Teacher1 = 1;
	private static int logsForStudent2Teacher2 = 0;
	
	private static int logsForStudent1Type1 = 2;
	private static int logsForStudent1Type2 = 1;
	private static int logsForStudent2Type1 = 1;
	private static int logsForStudent2Type2 = 0;
	
	private static int logsForStudent1Type1Teacher1 = 1;
	private static int logsForStudent1Type1Teacher2 = 1;
	private static int logsForStudent1Type2Teacher1 = 1;
	private static int logsForStudent1Type2Teacher2 = 0;
	
	private static int logsForStudent2Type1Teacher1 = 1;
	private static int logsForStudent2Type1Teacher2 = 0;
	private static int logsForStudent2Type2Teacher1 = 0;
	private static int logsForStudent2Type2Teacher2 = 0;

	
	@Before
	public void setUpVisits() {
		logger.info("+++ SETTING UP +++");
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
		logger.info("student is " + (studentDao.findByFirstNameAndLastName("aaaaaa", "zzzzzz") == null));
		
		List<Teacher> teachers = teacherDao.findAll();
		teacher1 = teachers.get(0);
		teacher2 = teachers.get(1);
		
		
		List<LogType> logTypes = logTypeDao.findAll();
		type1 = logTypes.get(0);
		type2 = logTypes.get(1);

		
		visit1 = new VisitInformation(student1, "2019-03-21", "notes", teacher1, type1, false);
		visit2 = new VisitInformation(student1, "2019-03-21", "notes", teacher1, type2, false);
		visit3 = new VisitInformation(student1, "2019-03-21", "notes", teacher2, type1, true);
		visit4 = new VisitInformation(student2, "2019-03-21", "notes", teacher1, type1, false);
		
		visitInfoDao.save(visit1);
		visitInfoDao.save(visit2);
		visitInfoDao.save(visit3);
		visitInfoDao.save(visit4);
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
	public void getAllVisitInformations() throws Exception{
		MvcResult result = mockMvc.perform(get(base)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		
		assertTrue(text.contains("visitInfos"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudent() throws Exception{
		int student1Id = student1.getSid();
		MvcResult result = mockMvc.perform(get(base + student1Id)).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacher() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() + "/tid=" + teacher1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacher2() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() + "/tid=" + teacher2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacher3() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() + "/tid=" + teacher1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacher4() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() + "/tid=" + teacher2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndType() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() + "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndType2() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() + "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndType3() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() + "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndType4() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() + "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() +"/tid=" + teacher1.getId() +  "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type1Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType2() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() +"/tid=" + teacher1.getId() +  "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type2Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType3() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() +"/tid=" + teacher2.getId() +  "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type1Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType4() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student1.getSid() +"/tid=" + teacher2.getId() +  "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent1Type2Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType5() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() +"/tid=" + teacher1.getId() +  "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type1Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType6() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() +"/tid=" + teacher1.getId() +  "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type2Teacher1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType7() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() +"/tid=" + teacher2.getId() +  "/lid=" + type1.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type1Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsByStudentAndTeacherAndType8() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() +"/tid=" + teacher2.getId() +  "/lid=" + type2.getId())).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(logsForStudent2Type2Teacher2, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsPageable() throws Exception{
		MvcResult result = mockMvc.perform(get(base + student2.getSid() + "/page=0/size=1")).andExpect(status().isOk()).andReturn();
		String text = result.getResponse().getContentAsString();
		assertTrue(text.contains("visits"));
		assertEquals(1, StringUtils.countOccurrencesOf(text, "logType"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsPageableInvalidPage() throws Exception{
		mockMvc.perform(get(base + student2.getSid() + "/page=-1/size=1")).andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void getAllVisitInformationsPageableInvalidSize() throws Exception{
		mockMvc.perform(get(base + student2.getSid() + "/page=1/size=0")).andExpect(status().isBadRequest()).andReturn();
	}
	
	
	
	
	@Test
	public void getAllVisitInformationsUnauthenticated() throws Exception{
		mockMvc.perform(get(base)).andExpect(status().is(403));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformation() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	Integer.toString(student1.getSid()));
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount + 1, endCount);
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformationInvalidStudent() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	"-1");
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount, endCount);
		assertEquals("Provided student does not exist", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformationInvalidTeacher() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	Integer.toString(student1.getSid()));
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	"-1");
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount, endCount);
		assertEquals("Provided teacher does not exist", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformationInvalidType() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	Integer.toString(student1.getSid()));
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	"-1");
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount, endCount);
		assertEquals("Provided log type does not exist", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformationKeyNotInteger() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	"a");
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount, endCount);
		assertEquals("Field sid must be int", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void createVisitInformationinvalidBoolean() throws Exception{
		int initialCount = (int)visitInfoDao.count();
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	Integer.toString(student1.getSid()));
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"a");
		String json = new ObjectMapper().writeValueAsString(input);
		MvcResult result = mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
		int endCount = (int) visitInfoDao.count();
		assertEquals(initialCount, endCount);
		assertEquals("Field clinic must be boolean", result.getResponse().getContentAsString());
	}
	
	
	@Test
	public void createVisitInformationUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("sid",	Integer.toString(student1.getSid()));
		input.put("dov",	"2019-03-21");
		input.put("notes",	"test notes");
		input.put("tid",	Integer.toString(teacher1.getId()));
		input.put("logType",	Integer.toString(type1.getId()));
		input.put("clinic",	"false");
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(post(base).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403)).andReturn();
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateVisitInformation() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("notes", "my new notes");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + visit1.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
	
		assertEquals("my new notes", visitInfoDao.findById(visit1.getId()).getNotes());
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void updateNonExistantVisitInformation() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("notes", "my new notes");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + -1).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
	
	}
	
	@Test
	public void updateVisitInformationUnauthenticated() throws Exception{
		Map<String, String> input = new HashMap<String, String>();
		input.put("notes", "my new notes");
		
		String json = new ObjectMapper().writeValueAsString(input);
		mockMvc.perform(put(base + visit1.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is(403));
	
	}
	
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteVisitInformation() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + visit1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
	}
	
	@Test
	public void deleteNonExistantVisitInformationUnauthenticated() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + visit1.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403)).andReturn();
		
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void deleteNonExistantVisitInformation() throws Exception{
		MvcResult result = mockMvc.perform(delete(base + "-1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		assertEquals("Unable to delete - visitInformation with id: -1 not found", result.getResponse().getContentAsString());
		
	}
	
	
	
	
	
	
}
