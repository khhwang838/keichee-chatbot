package com.m2u.keichee;

import java.util.Locale;

public interface IConstants {

	interface DEFAULT {
		String PWD = "1234";
		int PAGE_SIZE = 10;
		int PAGE_NUMBER = 1;
		Locale LOCALE = Locale.KOREA;
		String BOT_NAME = "alice2";
	}
	
	interface SESSION_INFO{
		String GUID = "guid";
		String USER_ID = "userId";
		String ROLE_ID = "roleId";
		String LOCALE = "locale";
	}
	
	interface MDC_INFO {
		String GUID = "guid";
		String USER_ID = "userId";
		String ROLE_ID = "roleId";
		String LOCALE = "locale";
	}
}