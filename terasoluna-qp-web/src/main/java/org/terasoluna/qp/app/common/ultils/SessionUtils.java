package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP.Folder;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;

public class SessionUtils {

	public static final String ACCOUNT_PROFILE = "ACCOUNT_PROFILE";
	public static final String ACCOUNT_INFOR = "ACCOUNT_INFOR";
	public static final String THEME_INFOR = "THEME_INFOR";
	public static final String PAGESIZE_INFOR = "PAGESIZE_INFOR";

	public static final String CURRENT_PROJECT = "CURRENT_PROJECT";
	public static final String OLD_PROJECT_ID = "OLD_PROJECT_ID";

	public static final String CURRENT_LANGUAGE_ID = "CURRENT_LANGUAGE_ID";
	public static final String CURRENT_LANGUAGE_DESIGN = "CURRENT_LANGUAGE";
	public static final String TEMPORARY_FOLDER_PATH = "TEMPORARY_FOLDER_PATH";
	private static Map<String, Object> singleSessionData = new HashMap<String, Object>();

	private static Map<String, Long> mAccountProjects = new ConcurrentHashMap<String, Long>(16, 0.9f, 1);

	public static Object get(String objectName) {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		if (request == null) {
			return singleSessionData.get(objectName);
		} else {
			HttpSession session = request.getSession(true);
			if (session == null)
				return null;
			return session.getAttribute(objectName);
		}
	}

	public static void remove(String objectName) {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(true);
			if (session != null)
				session.removeAttribute(objectName);
		}

	}

	public static void set(String objectName, Object object) {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(true);
			if (session != null)
				session.setAttribute(objectName, object);
		} else {
			singleSessionData.put(objectName, object);
		}
	}

	public static Account getCurrentAccount() {
		Account account = (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR);

		if (account == null) {
			// DungNN - 20160306 - if null then login again
			SecurityContextHolder.clearContext();
			throw new SystemException("", "");
		}
		return account;
	}

	public static AccountProfile getCurrentAccountProfile() {
		AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
		if (accountProfile == null) {
			// DungNN - 20160306 - if null then login again
			SecurityContextHolder.clearContext();
		}
		return accountProfile;
	}

	public static Long getAccountId() {
		return getCurrentAccount().getAccountId();
	}

	public static LanguageDesign getDefaultLanguageDesign() {
		return new LanguageDesign("en", "English", "US");
	}

	public static LanguageDesign getDefaultOtherLanguageDesign() {
		return new LanguageDesign("ja", "Japanese", "JP");
	}

	public static Language getDefaultLanguage() {
		return new Language("en", "English", "US");
	}

	/**
	 * 
	 * @return
	 * @author dungnn1
	 */
	public static Project getCurrentProject() {
		Project project = (Project) SessionUtils.get(SessionUtils.CURRENT_PROJECT);
		if (project == null)
			throw new SystemException("ERR_NOT_FOUND_PROJECT", "You need choice a project!");
		return project;
	}

	public static String getCurrentDatabaseType() {
		Project project = getCurrentProject();
		return String.valueOf(project.getDbType());
	}

	public static Long getCurrentProjectId() {
		Project project = (Project) SessionUtils.get(SessionUtils.CURRENT_PROJECT);
		if (project == null)
			return null;
		return project.getProjectId();
	}

	public static Long getCurrentLanguageId() {
		Long currentLanguageID = null;
		try {
			LanguageDesign obj = getCurrentLanguageDesign();
			currentLanguageID = obj.getLanguageId();
		} catch (Exception ex) {
			throw ex;
		}

		return currentLanguageID;
	}

	public static LanguageDesign getCurrentLanguageDesign() {
		LanguageDesign obj = (LanguageDesign) SessionUtils.get(SessionUtils.CURRENT_LANGUAGE_DESIGN);
		if (obj == null) {
			throw new SystemException("ERR_NOT_FOUND_LANGUAGE_DESIGN", "Not found language design default!");
		}

		return obj;
	}

	public static String generateTempFolderPath() {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		HttpSession session = request.getSession(true);
		return session.getServletContext().getRealPath("/META-INF/" + Folder.TEMPLATE + File.separator);
	}
	
	public static String getRootPath() {
		ServletContext context = HttpServletRequestUtils.getRequest().getServletContext();
		return context.getRealPath("");
	}

	public static String getBatchJobPath() {
		String rootPath = getRootPath();
		File file = new File(rootPath);
		String generateBatPath = file.getParentFile().getAbsolutePath() + File.separator + "batch";
		return generateBatPath;
	}
	
	private static String getPath(String module) {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		String path = module;
		if (request != null) {
			HttpSession session = request.getSession(true);
			path = session.getId() + module;
		}
		return path;
	}
	
	/**
	 * 
	 * @param module
	 * @param projectId
	 */
	public static void setOldProjectId(String module, Long projectId) {
		String key = SessionUtils.getPath(module);
		Long oldProjectId = mAccountProjects.get(key);
		if (oldProjectId == null || DataTypeUtils.notEquals(projectId, oldProjectId)) {
			mAccountProjects.put(key, projectId);
		}
	}

	public static Long getOldProjectId(String module) {
		return mAccountProjects.get(SessionUtils.getPath(module));
	}
	
	public static void resetCacheAfterLogout() {
		HttpServletRequest request = HttpServletRequestUtils.getRequest();
		if (request != null) {
			HttpSession session = request.getSession(true);
			Iterator<Map.Entry<String, Long>> iterator = mAccountProjects.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Long> entry = iterator.next();
				if (entry.getKey().startsWith(session.getId()))
					iterator.remove();
			}
		}
	}
}
