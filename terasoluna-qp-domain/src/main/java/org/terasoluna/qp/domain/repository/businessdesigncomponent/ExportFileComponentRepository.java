package org.terasoluna.qp.domain.repository.businessdesigncomponent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.ColumnFileFormat;
import org.terasoluna.qp.domain.model.ExportAssignValue;
import org.terasoluna.qp.domain.model.ExportFileComponent;

public interface ExportFileComponentRepository {
	Long getSequencesExportFileComponent(@Param("size") Integer size);

	Long getSequencesExportAssignValue(@Param("size") Integer size);

	int registerExportFileComponent(@Param("exportFileComponentItems") List<ExportFileComponent> exportFileComponentItems);

	List<ExportFileComponent> findExportFileComponentByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ExportAssignValue> findExportAssignValueByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ColumnFileFormat> findColumnFileFormatByBusinessLogic(@Param("businessLogicId") Long businessLogicId);

	List<ExportFileComponent> findAllExportFileComponentByModule(@Param("moduleId") Long moduleId);

	List<ExportAssignValue> findAllExportAssignValueByModule(@Param("moduleId") Long moduleId);

	List<ColumnFileFormat> findAllColumnFileFormatByModule(@Param("moduleId") Long moduleId);

	List<ExportFileComponent> findAllExportFileComponentByModuleCommon(@Param("projectId") Long projectId);

	List<ExportAssignValue> findAllExportAssignValueByModuleCommon(@Param("projectId") Long projectId);

	List<ColumnFileFormat> findAllColumnFileFormatByModuleCommon(@Param("projectId") Long projectId);
}
