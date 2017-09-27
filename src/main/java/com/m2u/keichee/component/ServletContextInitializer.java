package com.m2u.keichee.component;

/**
 * Author : Kihyun Hwang
 * Date   : 2017. 09. 16.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ServletContextInitializer implements ServletContextListener {

	private final static Logger logger = LoggerFactory.getLogger(ServletContextInitializer.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		// create a default bot
		BotManager.instance.createDefaultBot();

		logger.debug("Congratz !!! Context initialized !!!");
	}

}