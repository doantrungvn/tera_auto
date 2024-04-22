package org.terasoluna.qp.domain.service.generatedocument;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateDocument;
import org.terasoluna.qp.domain.model.Project;

@Service
public interface GenerateDocumentService {

	/**
	 * Process get data for export document
	 * 
	 * @param generateDocument
	 * 
	 * @return exportDocument
	 */
	GenerateDocument getDataForGenerateDocument(GenerateDocument generateDocument, CommonModel common);

	/**
	 * Process for export document
	 * 
	 * @param generateDocument
	 * 
	 * @return exportDocument
	 */
	GenerateDocument processGenerateDocumentExcelFile(GenerateDocument generateDocument, CommonModel common);
	
	/**
	 * Process for export all document
	 * 
	 * @param project, exportPath
	 * 
	 * @return file name
	 */
	String processGenerateAllDocement(Project project, String exportPath, String capturePath, CommonModel common);
}
