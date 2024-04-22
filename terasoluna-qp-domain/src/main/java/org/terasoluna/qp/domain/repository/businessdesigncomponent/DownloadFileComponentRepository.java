package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DownloadFileComponent;

public interface DownloadFileComponentRepository {
	Long getSequencesDownloadFileComponent(@Param("size") Integer size);

	int registerDownloadFileComponent(@Param("downloadFileComponentItems") List<DownloadFileComponent> downloadFileComponentItems);

	List<DownloadFileComponent> findDownloadFileComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<DownloadFileComponent> findAllDownloadFileComponentByModule(@Param("moduleId") Long moduleId);

	List<DownloadFileComponent> findAllDownloadFileComponentByModuleCommon(@Param("projectId") Long projectId);
		
}
