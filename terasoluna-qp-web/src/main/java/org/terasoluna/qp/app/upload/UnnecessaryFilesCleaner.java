package org.terasoluna.qp.app.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UnnecessaryFilesCleaner {
	@Autowired
	ServletContext servletContext;
	private static final long OFFSET_MILLIS = 108000; // 30 minutes
	public void cleanup() throws IOException, FileNotFoundException {
		File directory = new File(servletContext.getRealPath("/WEB-INF/temporary/"));
		for (File file : directory.listFiles()) {
			cleanUnnecessaryFiles(file);
		}
	}

	private void cleanUnnecessaryFiles(File file) {
		if (file.isDirectory() && file.listFiles().length > 0) {
			for (File f : file.listFiles()) {
				cleanUnnecessaryFiles(f);
			}
		}
		// Check file out of date
		Date currentDate = new Date(System.currentTimeMillis());
		Date fileDateWithOffset = new Date(file.lastModified() + OFFSET_MILLIS);
		if (currentDate.after(fileDateWithOffset)) {
			FileUtils.deleteQuietly(file); // Delete out of date file
		}
	}
}