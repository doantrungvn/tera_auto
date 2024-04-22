package org.terasoluna.qp.domain.service.importmanagement;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportManagement;
import org.terasoluna.qp.domain.model.Project;

@Service
public interface ImportTableDesignService {
	Map<String, Object> importData(ImportManagement importManagement, Project project, Long accountId, CommonModel commonModel); 
}
