package org.terasoluna.qp.domain.repository.licensemanagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.LicenseManagement;

@Repository
public interface LicenseManagementRepository {

	long countBySearchCriteria(
			@Param("criteria") LicenseManagementCriteria criteria);

	List<LicenseManagement> findPageBySearchCriteria(
			@Param("criteria") LicenseManagementCriteria criteria,
			@Param("pageable") Pageable pageable);

	void registerLicenseManagement(LicenseManagement licenseManagement);

	LicenseManagement findLicenseManagementById(Long licenseId);

	int changeStatus();

	int delete(LicenseManagement licenseManagement);

}
