package org.terasoluna.qp.domain.service.licensedesign;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.utils.license.GenerateLicenseUtil;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LicenseDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.LicenseDesign;
import org.terasoluna.qp.domain.repository.licensedesign.LicenseDesignCriteria;
import org.terasoluna.qp.domain.repository.licensedesign.LicenseDesignRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generateddl.GenerateDDLService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class LicenseDesignServiceImpl implements LicenseDesignService {
	/**
	 * Author NhatDN
	 */

	@Inject
	LicenseDesignRepository licenseDesignRepository;

	@Inject
	PasswordEncoder passwordEncoder;

	@Inject
	ModuleService moduleService;

	@Inject
	SystemService systemService;
	
	@Inject
	ProjectService projectService;

	@Override
	public Page<LicenseDesign> searchLicenseDesign(LicenseDesignCriteria criteria, Pageable pageable) {
		long totalCount = licenseDesignRepository.countBySearchCriteria(criteria);

		List<LicenseDesign> licenseDesigns;
		if (0 < totalCount) {
			licenseDesigns = licenseDesignRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			licenseDesigns = Collections.emptyList();
		}
		Page<LicenseDesign> page = new PageImpl<LicenseDesign>(licenseDesigns, pageable, totalCount);

		return page;
	}

	@Override
	public void register(LicenseDesign licenseDesign) {
		// Chek duplicate customer code/name
		/*Long totalCount = licenseDesignRepository.countNameCodeByLicenseId(licenseDesign);
		ResultMessages resultMessages = ResultMessages.error();

		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0000));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0000));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0001));
		}

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {*/

		Timestamp systemDate = FunctionCommon.getCurrentTime();
		//licenseDesign.setCreatedBy(accountId);
		licenseDesign.setCreatedDate(systemDate);
		licenseDesign.setUpdatedBy(licenseDesign.getCreatedBy());
		licenseDesign.setUpdatedDate(systemDate);
		licenseDesignRepository.register(licenseDesign);
	}

	@Override
	public LicenseDesign findLicenseDesignById(Long licenseId) {
		LicenseDesign licenseDesign = licenseDesignRepository.findLicenseDesignById(licenseId);
		if (licenseDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		}
		return licenseDesign;
	}

	@Override
	public void modify(LicenseDesign licenseDesign) {
		licenseDesign.setSysDateTime(FunctionCommon.getCurrentTime());
		LicenseDesign fd = licenseDesignRepository.findLicenseDesignById(licenseDesign.getLicenseId());
		if (fd == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		}

		// Check duplicate name and code
		/*Long totalCount = licenseDesignRepository.countNameCodeByLicenseId(licenseDesign);
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0001));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0000));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0000));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0001));
		}

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {*/

		if (licenseDesignRepository.modify(licenseDesign) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
	}

	@Override
	public void delete(LicenseDesign licenseDesign) {
		if (licenseDesignRepository.delete(licenseDesign) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		}
	}

	@Override
	public List<LicenseDesign> findAllLicenseDesignByProjectId(Long projectId) {
		return licenseDesignRepository.findAllLicenseDesignByProjectId(projectId);
	}

	@Override
	public String generateLicense(Long licenseId) {
		String path = FileUtilsQP.getExportFolder();
		String fileName = GenerateUniqueKey.generateWithDatePrefix() + LicenseDesignMessageConst.LICENSE_EXTENT_FILE;
		File temp = null;
		try {
			LicenseDesign licenseDesign = findLicenseDesignById(licenseId);
			if (licenseDesign != null) {
				final GenerateLicenseUtil generateLicenseUtil = new GenerateLicenseUtil();
				generateLicenseUtil.setLicense(LicenseDesignMessageConst.LICENSE_INPUT_FILE);
				generateLicenseUtil.setFeature("customerName", licenseDesign.getCustomerName());
				generateLicenseUtil.setFeature("customerCode", licenseDesign.getCustomerCode());
				generateLicenseUtil.setFeature("projectName", licenseDesign.getProjectName());
				generateLicenseUtil.setFeature("projectCode", licenseDesign.getProjectCode());

				if (licenseDesign.getTel() == null || licenseDesign.getTel() == "") {
					generateLicenseUtil.setFeature("tel", "");
				} else {
					generateLicenseUtil.setFeature("tel", licenseDesign.getTel());
				}
				generateLicenseUtil.setFeature("email", licenseDesign.getEmail());

				if (licenseDesign.getAddress() == null || licenseDesign.getAddress() == "") {
					generateLicenseUtil.setFeature("address", "");
				} else {
					generateLicenseUtil.setFeature("address", licenseDesign.getAddress());
				}
				generateLicenseUtil.setFeature("num", Integer.toString(licenseDesign.getNum()));
				if (licenseDesign.getVersion() == null || licenseDesign.getVersion() == "") {
					generateLicenseUtil.setFeature("version", "");
				} else {
					generateLicenseUtil.setFeature("version", licenseDesign.getVersion());
				}
				generateLicenseUtil.setFeature("startDate", String.valueOf(licenseDesign.getStartDate().getTime()));
				generateLicenseUtil.setFeature("expiredDate", String.valueOf(licenseDesign.getExpiredDate().getTime()));
				/*generateLicenseUtil.setFeature("status", "1");
				generateLicenseUtil.setFeature("projectId", licenseDesign.getProjectId().toString());*/

				AccountProfile accountProfile = systemService.getDefaultProfile();
				InputStream is = null;
				is = GenerateDDLService.class.getResourceAsStream("/META-INF/template/license/secring.gpg");
				temp = FileUtilsQP.createTempFile("key", ".gpg");
				FileUtilsQP.copyInputStreamToFile(is, temp);
				IOUtils.closeQuietly(is);

				generateLicenseUtil.loadKey(temp, accountProfile.getLicenseKey());
				final String encoded = generateLicenseUtil.encodeLicense(accountProfile.getLicensePassword());
				FileUtilsQP.writeStringToFile(new File(path, fileName), encoded);
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				throw new BusinessException(((BusinessException) e).getResultMessages());
			} else {
				throw new BusinessException("Error");
			}
		} finally {
			if (temp != null) {
				temp.delete();
			}
		}
		return fileName;
	}
}