package com.m2u.keichee.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class TempController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	private void test(String arg) {
		
		
		logger.info("{}", arg);
	}
}
