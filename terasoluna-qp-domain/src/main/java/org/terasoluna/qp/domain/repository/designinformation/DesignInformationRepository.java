package org.terasoluna.qp.domain.repository.designinformation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.DesignInformation;
import org.terasoluna.qp.domain.model.DesignInformationDetail;
import org.terasoluna.qp.domain.model.DesignRelationSetting;

public interface DesignInformationRepository {

	long countBySearchCriteria(@Param("criteria") DesignInformationCriteria criteria);
	
	List<DesignInformation> findPageBySearchCriteria(@Param("criteria") DesignInformationCriteria criteria, @Param("pageable") Pageable pageable);
	
	void registerDesignInformation(DesignInformation designInformation);
	
	void registerDesignInformationDetail(@Param("designInformationDetail") List<DesignInformationDetail> designInformationDetail);
	
	void registerDesignRelationSetting(@Param("designRelationSetting") List<DesignRelationSetting> designRelationSetting);
	
	Long designInformationDetailGetSequences(@Param("size") Integer size);
	
	List<DesignRelationSetting> findDesignRelationSettingById(DesignInformation designInformation);
	
	List<DesignInformationDetail> findDesignInformationDetailById(DesignInformation designInformation);
	
	DesignInformation findDesignInformationById(Long designInformationId);

	Long findDesignInformationByName(DesignInformation designInformation);
	
	int modifyDesignInformation(DesignInformation designInformation);
	
	void modifyDesignInformationDetail(@Param("designInformationDetails") List<DesignInformationDetail> designInformationDetail);
	
	void modifyDesignRelationSetting(@Param("designRelationSettings") List<DesignRelationSetting> designRelationSetting, @Param("designInformationId") Long designInformationId);
	
	int deleteRelationSetting(Long designInformationId);
	
	int deleteDesignInformationDetail(Long designInformationId);
	
	int deleteDesignInformation(Long designInformationId);
	
	
}
