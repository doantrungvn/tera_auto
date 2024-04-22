package org.terasoluna.qp.domain.service.importmanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.importmanagement.ImportManagementRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;

@Service
public class ImportManagementServiceImpl implements ImportManagementService {
	@Inject
	ImportManagementRepository importManagementRepository;
	
	@Inject
    ProjectRepository projectRepository;
	
	@Inject
	ImportMessageDesignService importMessageDesignService;
	
	@Inject
	ImportDomainDesignService importDomainDesignService;
	
	@Inject
	ImportCodelistDesignService importCodelistDesignService;
	
	@Inject
	ImportTableDesignService importTableDesignService;

	@Override
	public Map<String, Object> processImport(ImportManagement importManagement, CommonModel commonModel) {

		//import data 
		Map<String, Object> returnMap = selectImportService(importManagement, commonModel);
		
		if(importManagement.getDocumentType() != 4){
			//write log
			ImportUtils.writeImportResult(importManagement, (List<String[]>) returnMap.get(ImportManagementConst.ERR_WHEN_IMPORT));
		}
		
		return returnMap;
	}	
	
	
	private Map<String, Object> selectImportService(ImportManagement importManagement, CommonModel commonModel){
	  //Fix current project 20160613
		Map<String, Object> returnData = new HashMap<String, Object>();
		switch (importManagement.getDocumentType()) {
		case 1://code list design
			returnData = importCodelistDesignService.importData(importManagement, commonModel.getWorkingProjectId(), commonModel.getCreatedBy());
			break;
		case 2://domain design
			returnData = importDomainDesignService.importData(importManagement, commonModel.getWorkingProjectId(), commonModel.getCreatedBy());
			break;
		case 3://message design
			returnData = importMessageDesignService.importData(importManagement, commonModel.getWorkingProjectId(), commonModel.getCreatedBy());
			break;
		case 4://table design
		    Project project = projectRepository.findById(commonModel.getWorkingProjectId(), commonModel.getCreatedBy());
			returnData = importTableDesignService.importData(importManagement, project, commonModel.getCreatedBy(), commonModel);
			break;
		default:			
			break;
		}		
		return returnData;
	}	
}
