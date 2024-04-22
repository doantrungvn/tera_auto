package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

@Component
public class UploadHelper {

	private int savedPeriodMinutes = 30;
	
	@Inject
	JodaTimeDateFactory dateFactory;

	public String saveTemporaryFile(MultipartFile multipartFile)
			throws IOException {

		String uploadTemporaryFileId = UUID.randomUUID().toString();

		File uploadTemporaryFile = new File(FileUtilsQP.getUploadFolder(),
				uploadTemporaryFileId);

		FileUtilsQP.copyInputStreamToFile(multipartFile.getInputStream(),
				uploadTemporaryFile);

		return uploadTemporaryFileId;
	}

	public void cleanup() {
		// calculate cutoff date.
		Date cutoffDate = dateFactory.newDateTime().minusMinutes(savedPeriodMinutes).toDate();

		// collect target files.
		File targetDirectory = new File(FileUtilsQP.getUploadFolder());
		IOFileFilter fileFilter = FileFilterUtils.ageFileFilter(cutoffDate);
		Collection<File> targetFiles = FileUtilsQP.listFiles(targetDirectory, fileFilter, null);

		if (targetFiles.isEmpty()) {
			return;
		}

		// delete files.
		for (File targetFile : targetFiles) {
			FileUtilsQP.deleteQuietly(targetFile);
		}
	}

	public void deleteFile (String uploadTemporaryFileId) {
		if (StringUtils.isBlank(uploadTemporaryFileId))
			return;

		File temporaryFile = new File(FileUtilsQP.getUploadFolder(),
				uploadTemporaryFileId);
		FileUtilsQP.deleteQuietly(temporaryFile);
	}
	
}
