package org.terasoluna.qp.app.common.ultils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpServletRequestUtils {
	public static HttpServletRequest getRequest() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			return ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
		} else {
			return null;
		}
	}
	
	public static HttpServletResponse getResponse() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			return ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getResponse();
		} else {
			return null;
		}
	}
}
