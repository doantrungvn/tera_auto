package org.terasoluna.qp.app.common.utils.license;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openpgp.PGPException;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.service.generateddl.GenerateDDLService;
import org.terasoluna.qp.domain.service.licensemanagement.LicenseManagementConst;

public class LicenseCheckFunction {

	public static boolean checkLicense(String path) throws FileNotFoundException, IOException, PGPException, ParseException {

		boolean check = false;
		File temp = null;
		File file = new File(path + LicenseManagementConst.FILE_NAME);

		if (!file.exists())
			return check;

		final GenerateLicenseUtil generateLicenseUtil = new GenerateLicenseUtil();
		InputStream is = null;
		try {
			is = GenerateDDLService.class.getResourceAsStream("/META-INF/template/license/pubring.gpg");
			temp = FileUtilsQP.createTempFile("key", ".gpg");
			FileUtilsQP.copyInputStreamToFile(is, temp);

			if (generateLicenseUtil.loadKeyRing(temp, null).setLicenseEncodedFromFile(file.getAbsolutePath(), null).isVerified()) {

				if (StringUtils.isBlank(generateLicenseUtil.getFeature("expiredDate"))) {
					return check;
				}

				Timestamp expiredDate = new Timestamp(Long.valueOf(generateLicenseUtil.getFeature("expiredDate")));
				if (expiredDate.compareTo(FunctionCommon.getCurrentTime()) >= 0) {
					check = true;
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

		return check;
	}
}
