package org.terasoluna.qp.domain.service.licensemanagement;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.utils.license.GenerateLicenseUtil;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LicenseManagementMessageConst;
import org.terasoluna.qp.domain.model.LicenseManagement;
import org.terasoluna.qp.domain.repository.licensemanagement.LicenseManagementCriteria;
import org.terasoluna.qp.domain.repository.licensemanagement.LicenseManagementRepository;
import org.terasoluna.qp.domain.service.generateddl.GenerateDDLService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class LicenseManagementServiceImpl implements LicenseManagementService {
	/**
	 * Author NhatDN
	 */

	@Inject
	LicenseManagementRepository licenseManagementRepository;

	@Inject
	ProjectService projectService;

	@Override
	public Page<LicenseManagement> searchLicenseManagement(LicenseManagementCriteria criteria, Pageable pageable) {

		//update expired license
		licenseManagementRepository.changeStatus();
		
		long totalCount = licenseManagementRepository.countBySearchCriteria(criteria);

		List<LicenseManagement> licenseManagements;

		if (0 < totalCount) {
			licenseManagements = licenseManagementRepository.findPageBySearchCriteria(criteria, pageable);
			/*for (LicenseManagement licenseManagement : licenseManagements) {
				licenseManagementRepository.changeStatus(licenseManagement);
			}
			licenseManagements = licenseManagementRepository.findPageBySearchCriteria(criteria, pageable);*/
		} else {
			licenseManagements = Collections.emptyList();
		}
		Page<LicenseManagement> page = new PageImpl<LicenseManagement>(licenseManagements, pageable, totalCount);

		return page;
	}

	@Override
	public void importLicense(LicenseManagement licenseManagement) {
		ResultMessages resultMessages = ResultMessages.error();
		File temp = null;
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			try {
				final GenerateLicenseUtil generateLicenseUtil = new GenerateLicenseUtil();
				InputStream is = null;
				is = GenerateDDLService.class.getResourceAsStream("/META-INF/template/license/pubring.gpg");
				temp = FileUtilsQP.createTempFile("key", ".gpg");
				FileUtilsQP.copyInputStreamToFile(is, temp);
				IOUtils.closeQuietly(is);

				if (generateLicenseUtil.loadKeyRing(temp, null).setLicenseEncodedFromFile(licenseManagement.getFilePath(), null).isVerified()) {
					licenseManagement.setCustomerCode(generateLicenseUtil.getFeature("customerCode"));
					licenseManagement.setCustomerName(generateLicenseUtil.getFeature("customerName"));
					licenseManagement.setProjectCode(generateLicenseUtil.getFeature("projectCode"));
					licenseManagement.setProjectName(generateLicenseUtil.getFeature("projectName"));
					/*licenseManagement.setProjectId(Long.parseLong(generateLicenseUtil.getFeature("projectId")));
					licenseManagement.setStatus(Integer.parseInt(generateLicenseUtil.getFeature("status")));*/
					licenseManagement.setTel(generateLicenseUtil.getFeature("tel"));
					licenseManagement.setEmail(generateLicenseUtil.getFeature("email"));
					licenseManagement.setAddress(generateLicenseUtil.getFeature("address"));
					licenseManagement.setNum(Integer.parseInt(generateLicenseUtil.getFeature("num")));
					licenseManagement.setVersion(generateLicenseUtil.getFeature("version"));
					
					/*Timestamp ts = new Timestamp(millis)*/
					
					Timestamp startDate = new Timestamp(Long.valueOf(generateLicenseUtil.getFeature("startDate")));
					licenseManagement.setStartDate(startDate);
					
					Timestamp expiredDate = new Timestamp(Long.valueOf(generateLicenseUtil.getFeature("expiredDate")));
					licenseManagement.setExpiredDate(expiredDate);
					
					Timestamp currentTime = FunctionCommon.getCurrentTime();
					licenseManagement.setAppliedDate(currentTime);
					licenseManagement.setLicenseFileName(licenseManagement.getLicenseFileName());

					if (/*DateUtils.compare(startDate, currentTime) == -1 &&*/ DateUtils.compare(currentTime, expiredDate) == -1) {
						licenseManagement.setStatus(DbDomainConst.YesNoFlg.YES);
					} else {
						licenseManagement.setStatus(DbDomainConst.YesNoFlg.NO);
					}

					// HttpServletRequest request = HttpServletRequestUtils.getRequest();
					// HttpSession session = request.getSession(true);
					// String path = session.getServletContext().getRealPath("/META-INF/" + "license");
					//
					// FileUtilsQP.createDirectory(path);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void apply(LicenseManagement licenseManagement) {
		licenseManagementRepository.registerLicenseManagement(licenseManagement);
	}

	@Override
	public LicenseManagement findLicenseManagementById(Long licenseId) {
		LicenseManagement licenseManagement = licenseManagementRepository.findLicenseManagementById(licenseId);
		if (licenseManagement == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0011)));
		}
		return licenseManagement;
	}

	@Override
	public void delete(LicenseManagement licenseManagement) {
		if (licenseManagementRepository.delete(licenseManagement) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0011)));
		}
	}

	

	@Override
	public boolean checkLicense(String path) throws Exception {
		boolean check = false;
		File temp = null;
		File file = new File(path);
		File[] listOfFiles = file.listFiles();
		final GenerateLicenseUtil generateLicenseUtil = new GenerateLicenseUtil();
		InputStream is = null;
		is = GenerateDDLService.class.getResourceAsStream("/META-INF/template/license/pubring.gpg");
		temp = FileUtilsQP.createTempFile("key", ".gpg");
		FileUtilsQP.copyInputStreamToFile(is, temp);
		IOUtils.closeQuietly(is);
		if (generateLicenseUtil.loadKeyRing(temp, null).setLicenseEncodedFromFile(listOfFiles[0].getAbsolutePath(), null).isVerified()) {
			if (DateUtils.parse(generateLicenseUtil.getFeature("expiredDate")).compareTo(FunctionCommon.getCurrentTime()) >= 0) {
				check = true;
			}
		}
		System.out.println(check);
		return check;
	}
}