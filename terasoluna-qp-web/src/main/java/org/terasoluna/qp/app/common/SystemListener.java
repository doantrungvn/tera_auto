package org.terasoluna.qp.app.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;

public class SystemListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		try {
			String temporaryFolderPath = StringUtils.appendIfMissing(FileUtilsQP.getRootPathByTemp(), File.separator, File.separator);
			FileUtilsQP.deleteDirectory(new File(temporaryFolderPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
