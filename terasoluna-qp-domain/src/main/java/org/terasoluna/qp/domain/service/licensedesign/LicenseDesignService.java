package org.terasoluna.qp.domain.service.licensedesign;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.LicenseDesign;
import org.terasoluna.qp.domain.repository.licensedesign.LicenseDesignCriteria;

@Service
public interface LicenseDesignService {
	Page<LicenseDesign> searchLicenseDesign(LicenseDesignCriteria criteria, Pageable pageable);
	
	List<LicenseDesign> findAllLicenseDesignByProjectId(Long projectId);
	
	void register(LicenseDesign licenseDesign);
	
	LicenseDesign findLicenseDesignById(Long licenseId);
	
	void modify(LicenseDesign licenseDesign);
	
	String generateLicense(Long licenseId);
	
	void delete(LicenseDesign licenseDesign);
}
