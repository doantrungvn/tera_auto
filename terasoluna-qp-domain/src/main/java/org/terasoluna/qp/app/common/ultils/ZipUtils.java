package org.terasoluna.qp.app.common.ultils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;

/**
 * @author dungnn1
 */
public class ZipUtils {

	//private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);
	/**
	 * Creates a zip file at the specified path with the contents of the specified directory. NB:
	 * 
	 * @param directoryPath
	 *            The path of the directory where the archive will be created. eg. c:/temp
	 * @param zipPath
	 *            The full path of the archive to create. eg. c:/temp/archive.zip
	 * @throws IOException
	 *             If anything goes wrong
	 */
	public static void Zip(String directoryPath, String zipPath) throws IOException {
		FileOutputStream fOut = null;
		BufferedOutputStream bOut = null;
		ZipArchiveOutputStream tOut = null;

		try {
			fOut = new FileOutputStream(new File(zipPath));
			bOut = new BufferedOutputStream(fOut);
			tOut = new ZipArchiveOutputStream(bOut);
			tOut.setEncoding(DbDomainConst.CHARACTER_ENCODING);
			addFileToZip(tOut, directoryPath, "");
		} finally {
			tOut.finish();
			IOUtils.closeQuietly(tOut);
			IOUtils.closeQuietly(bOut);
			IOUtils.closeQuietly(fOut);
			
		}
	}

	public static void Zip(List<String> directoryPaths, String zipPath) throws IOException {

		if (CollectionUtils.isEmpty(directoryPaths)) {
			return;
		}

		FileOutputStream fOut = null;
		BufferedOutputStream bOut = null;
		ZipArchiveOutputStream tOut = null;

		try {
			fOut = new FileOutputStream(new File(zipPath));
			bOut = new BufferedOutputStream(fOut);
			tOut = new ZipArchiveOutputStream(bOut);
			tOut.setEncoding(DbDomainConst.CHARACTER_ENCODING);
			for (String directoryPath : directoryPaths) {
				addFileToZip(tOut, directoryPath, "");
			}
		} finally {
			tOut.finish();
			IOUtils.closeQuietly(tOut);
			IOUtils.closeQuietly(bOut);
			IOUtils.closeQuietly(fOut);
			Runtime.getRuntime().gc();
		}
	}

	/**
	 * Creates a zip entry for the path specified with a name built from the base passed in and the file/directory name. If the path is a directory, a recursive call is made such that the full directory is added to the zip.
	 * 
	 * @param zOut
	 *            The zip file's output stream
	 * @param path
	 *            The filesystem path of the file/directory being added
	 * @param base
	 *            The base prefix to for the name of the zip file entry
	 * @throws IOException
	 *             If anything goes wrong
	 */
	private static void addFileToZip(ZipArchiveOutputStream zOut, String path, String base) throws IOException {
		File f = new File(path);
		String entryName = base + f.getName();
		ZipArchiveEntry zipEntry = new ZipArchiveEntry(f, entryName);

		zOut.putArchiveEntry(zipEntry);

		if (f.isFile()) {
			FileInputStream fInputStream = null;
			try {
				fInputStream = new FileInputStream(f);
				IOUtils.copy(fInputStream, zOut);
				zOut.closeArchiveEntry();
			} finally {
				IOUtils.closeQuietly(fInputStream);
			}

		} else {
			zOut.closeArchiveEntry();
			File[] children = f.listFiles();

			if (children != null) {
				for (File child : children) {
					addFileToZip(zOut, child.getAbsolutePath(), entryName + "/");
				}
			}
		}
	}

	/**
	 * @param zipfile
	 * @param outdir
	 * @throws Exception 
	 */
	public static void extract(InputStream zipfile, String outdir, String tempFolder) throws Exception {
		File tmpZip = null;
		ZipFile zipFile = null;

		try {
			System.setProperty("file.encoding", DbDomainConst.CHARACTER_ENCODING);
			tmpZip = FileUtilsQP.createTempFile("rep", ".zip", tempFolder);
			File restoreDir = new File(outdir);
			FileUtilsQP.copyInputStreamToFile(zipfile, tmpZip);
			zipFile = new ZipFile(tmpZip, DbDomainConst.CHARACTER_ENCODING);
			
			final Enumeration<ZipArchiveEntry> zipEntries = zipFile.getEntries();
			while (zipEntries.hasMoreElements()) {
				final ZipArchiveEntry zipEntry = zipEntries.nextElement();
				
				final File entryFile = new File(restoreDir, zipEntry.getName());
				if (zipEntry.isDirectory()) {
					entryFile.mkdirs();
				} else {
					entryFile.getParentFile().mkdirs();
					try (FileOutputStream target = new FileOutputStream(entryFile)) {
						try (InputStream in = zipFile.getInputStream(zipEntry)) {
							IOUtils.copy(in, target);
						}
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			IOUtils.closeQuietly(zipFile);
			FileUtilsQP.deleteQuietly(tmpZip);
			Runtime.getRuntime().gc();
		}

	}

	public static void main(String[] args) {
		/*
		 * String strRoot = "D:\\DungNN\\Workspace2Q\\Deploy\\webapps\\terasoluna-qp-web\\WEB-INF\\export\\"; String strTemp = "D:\\DungNN\\Projects\\J15041\\trunk\\Source\\terasoluna-qp\\terasoluna-qp-web\\src\\main\\webapp\\WEB-INF\\templete\\prototype\\"; String zipFile = strTemp + "media.zip"; String zipFileNew = strRoot + "mediaNew_123.zip"; String path = strRoot + "DungNN QP20151015_e5302f7807894024ba2685af31a47c67\\"; List<String> includeSource = new ArrayList<String>(); includeSource.add(path); ZipUtils zipUtil = new ZipUtils(); try { zipUtil.addFilesToExistingZip(new File(zipFile), includeSource, zipFileNew); } catch (Exception ex) { // some errors occurred ex.printStackTrace(); } FileUtilsQP.deleteQuietly(new File(strRoot + "DungNN QP20151016_aa2b8c1762594b22a045795fe264899d\\"));
		 */
	}
}
