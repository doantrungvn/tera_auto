package org.terasoluna.qp.domain.service.importmanagement;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportManagement;

@Service
public interface ImportManagementService {
	Map<String,Object> processImport(ImportManagement importManagement, CommonModel commonModel);
}
