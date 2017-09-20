package com.m2u.keichee.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.alicebot.ab.aiml.AIMLProcessor;
import org.alicebot.ab.aiml.PCAIMLProcessorExtension;
import org.alicebot.ab.constants.MagicStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.m2u.keichee.domain.Message;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	MainService mainService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	@ResponseBody
	public String chat(@RequestBody Message msg) {
		
		logger.info("Input Message : {}", msg);
		
		MagicStrings.root_path = System.getProperty("user.dir");
		logger.info("Working Directory = " + MagicStrings.root_path);
		AIMLProcessor.extension = new PCAIMLProcessorExtension();
		String[] options = new String[] {};
		String response = mainService.mainFunction(msg, options);
		
		logger.info("Output Message : {}", response);
		return response;
	}
	
}
