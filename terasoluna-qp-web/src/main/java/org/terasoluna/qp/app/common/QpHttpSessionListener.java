package org.terasoluna.qp.app.common;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.SessionUtils;

public class QpHttpSessionListener implements HttpSessionListener {

	private static int totalActiveSessions;

	public static int getTotalActiveSession() {
		return totalActiveSessions;
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		totalActiveSessions++;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		totalActiveSessions--;
		SessionUtils.resetCacheAfterLogout();
		FileUtilsQP.cleanup(FileUtilsQP.getRootPathByTemp());
	}
}