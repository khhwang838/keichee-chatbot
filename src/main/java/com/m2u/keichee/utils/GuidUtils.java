package com.m2u.keichee.utils;

import java.util.UUID;
import java.util.regex.Pattern;

public class GuidUtils {

	private final static GuidUtils instance = new GuidUtils();
	
	public static GuidUtils instance() {
		return instance;
	}
	
	public String createGuid() {
		return UUID.randomUUID().toString().replaceAll(Pattern.quote("-"), "");
	}
}
