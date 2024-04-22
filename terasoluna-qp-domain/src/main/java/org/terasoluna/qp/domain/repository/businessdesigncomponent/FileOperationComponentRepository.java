package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.FileOperationComponent;
import org.terasoluna.qp.domain.model.MergeFileDetail;

public interface FileOperationComponentRepository {
	Long getSequencesFileOperationComponent(@Param("size") Integer size);

	int registerFileOperationComponent(@Param("fileOperationComponentItems") List<FileOperationComponent> FileOperationComponentItems);

	List<FileOperationComponent> findFileOperationComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<MergeFileDetail> findMergeFileDetailByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<FileOperationComponent> findAllFileOperationComponentByModule(@Param("moduleId") Long moduleId);

	List<MergeFileDetail> findAllMergeFileDetailByModule(@Param("moduleId") Long moduleId);

	List<FileOperationComponent> findAllFileOperationComponentByModuleCommon(@Param("projectId") Long projectId);

	List<MergeFileDetail> findAllMergeFileDetailByModuleCommon(@Param("projectId") Long projectId);
}
