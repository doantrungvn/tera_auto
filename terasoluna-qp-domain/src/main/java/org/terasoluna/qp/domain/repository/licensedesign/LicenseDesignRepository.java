package org.terasoluna.qp.domain.repository.licensedesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.LicenseDesign;

@Repository
public interface LicenseDesignRepository {

	long countBySearchCriteria(@Param("criteria") LicenseDesignCriteria criteria);

	List<LicenseDesign> findPageBySearchCriteria(@Param("criteria") LicenseDesignCriteria criteria, @Param("pageable") Pageable pageable);

	List<LicenseDesign> findAllLicenseDesignByProjectId(@Param("projectId") Long projectId);

	void register(LicenseDesign licenseDesign);

	Long countNameCodeByLicenseId(LicenseDesign licenseDesign);

	LicenseDesign findLicenseDesignById(Long licenseId);

	int modify(LicenseDesign licenseDesign);

	int delete(LicenseDesign licenseDesign);

	Long getListUsingLD(Long licenseId);

	Long getSequencesLicenseDesign(@Param("size") Integer size);

	int registerLstLicenseDesign(@Param("lstLicenseDesign") List<LicenseDesign> lstLicenseDesign);
}
