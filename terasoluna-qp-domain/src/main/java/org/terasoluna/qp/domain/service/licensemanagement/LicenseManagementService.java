package org.terasoluna.qp.domain.service.licensemanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.LicenseManagement;
import org.terasoluna.qp.domain.repository.licensemanagement.LicenseManagementCriteria;

@Service
public interface LicenseManagementService{
	Page<LicenseManagement> searchLicenseManagement(LicenseManagementCriteria criteria, Pageable pageable);

	void importLicense(LicenseManagement licenseManagement);
	
	void apply(LicenseManagement licenseManagement);

	LicenseManagement findLicenseManagementById(Long licenseId);

	void delete(LicenseManagement licenseManagement);
	boolean checkLicense(String path) throws Exception;
}
