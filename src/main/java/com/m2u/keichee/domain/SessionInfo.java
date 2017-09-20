package com.m2u.keichee.domain;

import java.util.Locale;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.m2u.keichee.IConstants;
import com.m2u.keichee.IConstants.DEFAULT;
import com.m2u.keichee.IConstants.MDC_INFO;

@Component
public class SessionInfo implements IConstants {

	final Logger logger = LoggerFactory.getLogger(getClass());

	final ThreadLocal<String> userId = new ThreadLocal<String>();
	final ThreadLocal<String> guid = new ThreadLocal<String>();
	final ThreadLocal<String> roleId = new ThreadLocal<String>();
	final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

	@Autowired
	private MessageSource ms;

	public String getCodeMessage(String code) {
		Locale locale = getLocale();
		return ms.getMessage(code, null, locale);
	}
	public String getUserId() {
		return userId.get();
	}
	public String getGuid() {
		return guid.get();
	}
	public String getRoleId() {
		return roleId.get();
	}
	public Locale getLocale() {
		return locale.get() == null ? DEFAULT.LOCALE : locale.get();
	}
	public String getLangCd() {
		return locale.get() == null ? DEFAULT.LOCALE.getLanguage() : locale.get().getLanguage();
	}
	public void setInfo(String guid, String userId, String roleId, Locale locale) {
		MDC.put(MDC_INFO.GUID, new StringBuilder().append("[").append(guid).append("]").toString());
		MDC.put(MDC_INFO.USER_ID, new StringBuilder().append("[").append(userId).append("]").toString());
		MDC.put(MDC_INFO.ROLE_ID, new StringBuilder().append("[").append(roleId).append("]").toString());
		MDC.put(MDC_INFO.LOCALE, new StringBuilder().append("[").append(locale).append("]").toString());
		this.guid.set(guid);
		this.userId.set(userId);
		this.roleId.set(roleId);
		this.locale.set(locale);
	}
	public void clear() {
		MDC.remove(MDC_INFO.GUID);
		MDC.remove(MDC_INFO.USER_ID);
		guid.remove();
		userId.remove();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}