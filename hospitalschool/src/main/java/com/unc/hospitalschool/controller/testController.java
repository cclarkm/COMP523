package com.unc.hospitalschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {

	
	@GetMapping("/")
	@ResponseBody
	public String helloWorld() {
		return "Hello World";
	}
}
