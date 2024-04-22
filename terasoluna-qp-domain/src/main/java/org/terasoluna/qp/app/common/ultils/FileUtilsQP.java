package org.terasoluna.qp.app.common.ultils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.qp.app.common.constants.DbDomainConst;

public class FileUtilsQP extends FileUtils {

	private static final Logger log = LoggerFactory.getLogger(FileUtilsQP.class);

	public static final class Folder {
		public static final String UPLOAD = "upload";
		public static final String EXPORT = "export";
		public static final String TEMP_FOLDER = "temp";
		public static final String IMPORT = "import";
		public static final String TEMPLATE = "template";
		public static final String TEMPLATE_SRC = "template/source";
		public static final String EXCEL = "excel";
		public static final String EXCEL_RD = "excel/rd";
		public static final String EXCEL_ED = "excel/ed";

		public static final String RD = "rd";
		public static final String ED = "ed";
		public static final String ED_SRCEEN_DESIGN = "ed/screendesign";
		public static final String ED_SRCEEN_TRANSITION = "ed/screentransition";
		public static final String CAPTURE = "capture";
		/*public static final String MAVEN = "apache-maven/bin";*/
		public static final String MAVEN_HOME = "apache-maven";
		public static final String JDK = "jdk1.8";
		public static final String GENERATEDSOURCE = "generatedSource";
		public static final String FOLDER_QP = "qp_export";
		public static final String META_INF = "META-INF";
		public static final String WEB_INF = "WEB-INF";
		public static final String LICENSE = "license";
	}

	private static String webAppPath = null;

	public static final class FileType {
		public static final String ZIP = ".zip";
		public static final String WAR = ".war";
		public static final String LOG = ".log";
	}

	public static String getWebAppPath() {
		if (StringUtils.isBlank(webAppPath)) {
			String path = FileUtilsQP.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			if (path.contains("WEB-INF")) {
				String[] arr = path.split("WEB-INF");
				webAppPath = StringUtils.appendIfMissing(decodePath(arr[0]), File.separator, File.separator);
			} else {
				//for bath
				webAppPath = StringUtils.appendIfMissing(getRootPath(), File.separator, File.separator);
			}
			log.info("webAppPath = " + webAppPath);
		}
		return webAppPath;
	}

	public static String getRootPath() {
		URL url = FileUtilsQP.class.getProtectionDomain().getCodeSource().getLocation(); // Gets the path
		String jarPath = null;
		String parentPath = null;
		try {
			log.info("url = " + url);
			jarPath = URLDecoder.decode(url.getFile(), DbDomainConst.CHARACTER_ENCODING); // Should fix it to be read correctly by the system
			log.info("jarPath = " + jarPath);
			parentPath = new File(jarPath).getParentFile().getParentFile().getPath(); // Path of the jar
			log.info("parentPath = " + parentPath);
		} catch (Exception e) {
			e.printStackTrace();
			parentPath = FileUtilsQP.decodePath(FileUtilsQP.getTempDirectoryPath());
		}

		log.info("return parentPath = " + parentPath);
		return parentPath;
	}

	public static String getWebInfAppPath() {
		StringBuilder path = new StringBuilder(getWebAppPath());
		path.append(Folder.WEB_INF);
		path.append(File.separator);
		return path.toString();
	}

	public static String getMetaInfAppPath() {
		StringBuilder path = new StringBuilder(getWebAppPath());
		path.append(Folder.META_INF);
		path.append(File.separator);
		return path.toString();
	}

	public static String getRootPathByTemp() {
		StringBuilder path = new StringBuilder(getWebAppPath());
		path.append(Folder.FOLDER_QP);
		path.append(File.separator);
		return path.toString();
	}

	public static String generateTemporaryFolderPath() {
		return getPathOfFolder(GenerateUniqueKey.generateAutoCode());
	}

	public static String getPathOfFolder(String folderName) {
		StringBuilder path = new StringBuilder(getRootPathByTemp());
		path.append(getFolderName());
		path.append(File.separator);
		path.append(folderName);
		return normalizedPath(path.toString());
	}

	public static String getUploadFolder() {
		return getPathOfFolder(Folder.UPLOAD);
	}

	public static String getExportFolder() {
		return getPathOfFolder(Folder.EXPORT);
	}

	public static String getTempFolderForExtract() {
		return getPathOfFolder(Folder.TEMP_FOLDER);
	}
	
	public static String normalizedPath(String path) {
		return decodePath(StringUtils.appendIfMissing(path, File.separator, File.separator));
	}

	public static byte[] readFileToByteArray(String path) throws IOException {
		return readFileToByteArray(new File(path));
	}

	public static String getSourceGenerationFolderPath() {
		return getPathOfFolder(Folder.GENERATEDSOURCE);
	}
	
	public static String getLicenseFolderPath() {
		StringBuilder path = new StringBuilder(getWebAppPath());
		path.append(Folder.META_INF);
		path.append(File.separator);
		path.append(Folder.LICENSE);
		path.append(File.separator);
		return path.toString();
	}

	/**
	 * path template folder
	 * @return
	 */
	public static String getSourceTemplateFolder() {
		return getPathOfFolder(Folder.TEMPLATE_SRC);
	}

	public static void createDirectory(String sourcePath) {
		try {
			FileUtils.forceMkdir(new File(sourcePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createTempFile(String prefix, String suffix) throws IOException {
		return createTempFile(prefix, suffix, FileUtilsQP.getTempFolderForExtract()) ;
	}
	
	public static File createTempFile(String prefix, String suffix, String tempFolder) throws IOException {
		createDirectory(tempFolder);

		StringBuilder fileName = new StringBuilder(tempFolder);
		fileName.append(prefix);
		fileName.append(GenerateUniqueKey.generateAutoCode());
		fileName.append(suffix);
		
		File temp = new File(fileName.toString());
		temp.createNewFile();

		return temp ;
	}
	
	

	public static String decodePath(String path) {
		try {
			if (StringUtils.isBlank(path))
				return path;
			return URLDecoder.decode(path, DbDomainConst.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return path;
		}
	}
	
	
	public static boolean isExists(String path) {
		if (StringUtils.isBlank(path))
			return false;
		
		return new File(path).exists();
	}
	
	
	public static List<String> listChildsFileName(String filePath) {
		File[] subdirs = new File(filePath).listFiles();
		if (ArrayUtils.isEmpty(subdirs)) {
			return null;
		}
		List<String> listFiles = new ArrayList<String>();
		for (File file : subdirs) {
			listFiles.add(normalizedPath(filePath) + file.getName());
		}
		return listFiles;

	}

	public synchronized static void cleanup(String targetDirectory) {
		// collect sub folder
		File[] subdirs = new File(targetDirectory).listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
		if (ArrayUtils.isEmpty(subdirs)) {
			return;
		}
		// delete old.
		for (File folder : subdirs) {
			if (checkOldFolder(folder.getName())) {
				try {
					log.info("deleteDirectory: " + folder);
					FileUtils.deleteDirectory(folder);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void deleteTempFile(Map<String, File> sheets) {
		for (Map.Entry<String, File> entry : sheets.entrySet()) {
			FileUtils.deleteQuietly(entry.getValue());
		}
	}
	
	/**
	 * xoa folder older than 2 days
	 * 
	 * @param folder
	 * @return
	 */
	private static boolean checkOldFolder(String folder) {
		long longDate = Long.valueOf(folder);
		if (getFolderName() - 2 > longDate)
			return true;
		return false;
	}

	public static long getFolderName() {
		Date dNow = DateTime.now().toDate();
		return Long.valueOf(GenerateUniqueKey.formatter.format(dNow));
	}

}
