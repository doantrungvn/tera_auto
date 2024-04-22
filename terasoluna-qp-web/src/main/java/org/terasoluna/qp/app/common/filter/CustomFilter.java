package org.terasoluna.qp.app.common.filter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openpgp.PGPException;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.utils.license.LicenseCheckFunction;

@Component
public class CustomFilter implements Filter {

	public static long activeTime = 0;
	private Boolean licenseValid = false;

	private static List<String> ignoreUrl;

	static {
		init();
	}

	private static void init() {
		ignoreUrl = new ArrayList<String>();
		ignoreUrl.add("licensemanagement");
		ignoreUrl.add("login");
		ignoreUrl.add("resources");
		ignoreUrl.add("jsMsgSource");
		ignoreUrl.add("capture");
		ignoreUrl.add("Autocomplete");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (checkIgnoreUrl(httpRequest.getRequestURI()) || SessionUtils.get(SessionUtils.ACCOUNT_INFOR) == null) {
			chain.doFilter(request, response);
		} else {
			
			try {
				if (activeTime < System.currentTimeMillis()) {
					initActiveTime();
					licenseValid = LicenseCheckFunction.checkLicense(FileUtilsQP.getLicenseFolderPath());
				}
			} catch (PGPException | ParseException e) {
				e.printStackTrace();
			}

			if (licenseValid) {
				chain.doFilter(request, response);
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/licensemanagement/search");
			}
		}
	}

	@Override
	public void destroy() {
		// do nothing
	}

	public void initActiveTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(FunctionCommon.getCurrentTime());
		calendar.add(Calendar.HOUR_OF_DAY, 6);
		activeTime = calendar.getTimeInMillis();
	}

	private boolean checkIgnoreUrl(String urlCheck) {

		if (StringUtils.isBlank(urlCheck)) {
			return false;
		}

		for (String url : ignoreUrl) {
			if (StringUtils.containsIgnoreCase(urlCheck, url)) {
				return true;
			}
		}

		return false;
	}

}
