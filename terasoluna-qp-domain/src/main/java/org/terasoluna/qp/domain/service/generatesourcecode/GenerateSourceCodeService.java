package org.terasoluna.qp.domain.service.generatesourcecode;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.Project;

@Service
public interface GenerateSourceCodeService{

	GenerateSourceCode processGenerateSourceCode(GenerateSourceCode generateSourceCode, CommonModel common);

	String processGenerateAllSourceCode(Project project, String exportPath, String sourceExportPath, String filePattern, String tempFolder, CommonModel common);
	
	GenerateSourceCode processGenerateWarFile(Project project, String tempFolder, String sourceExportPath, CommonModel commonModel);
	
}
