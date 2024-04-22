package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.FileFormat;
import org.terasoluna.qp.domain.model.ImportAssignValue;
import org.terasoluna.qp.domain.model.ImportFileComponent;

public interface ImportFileComponentRepository {
	Long getSequencesImportFileComponent(@Param("size") Integer size);

	int registerImportFileComponent(@Param("importFileComponentItems") List<ImportFileComponent> ImportFileComponentItems);

	List<ImportFileComponent> findImportFileComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ImportAssignValue> findImportAssignValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<FileFormat> findFileFormatByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ImportFileComponent> findAllImportFileComponentByModule(@Param("moduleId") Long moduleId);

	List<ImportAssignValue> findAllImportAssignValueByModule(@Param("moduleId") Long moduleId);

	List<FileFormat> findAllFileFormatByModule(@Param("moduleId") Long moduleId);

	List<ImportFileComponent> findAllImportFileComponentByModuleCommon(@Param("projectId") Long projectId);

	List<ImportAssignValue> findAllImportAssignValueByModuleCommon(@Param("projectId") Long projectId);

	List<FileFormat> findAllFileFormatByModuleCommon(@Param("projectId") Long projectId);

}
