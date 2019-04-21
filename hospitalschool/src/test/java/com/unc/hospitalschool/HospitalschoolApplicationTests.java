package com.unc.hospitalschool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.unc.hospitalschool.init.HospitalschoolApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalschoolApplication.class)
public class HospitalschoolApplicationTests {

    @Test
    public void contextLoads() throws Exception {
    }

}