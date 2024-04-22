package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementRepository;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component(value="SessionManagementGenerateHandler")
public class SessionManagementGenerateHandler extends GenerationHandler {
	
	@Inject
	SystemService systemService;

    @Inject
    SessionManagementRepository sessionManagementRepository;

	private static final String TEMPLATE_SESSION_MANAGEMENT = "session_management.ftl";

    private List<SessionManagement> sessionManagementList;
    
	private StringBuilder pathPackage;
	
	private void init(GenerateSourceCode generateSourceCode) {
		// Setting package folder source
		String[] split = null;
		if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
			split = generateSourceCode.getProject().getPackageName().split("\\.");
		}
		if (split != null && split.length > 0) {
			pathPackage = new StringBuilder();
			for (String str : split) {
				pathPackage.append(str).append(File.separator);
			}
		}
	}
	
	@Override
	public void handle(GenerateSourceCode generateSourceCode,  CommonModel common) {
		init(generateSourceCode);	
		generateSessionManagement(generateSourceCode, common);
	}

	private void generateSessionManagement(GenerateSourceCode generateSourceCode, CommonModel common){
		OutputStreamWriter out = null;
		
	    try {
            Long projectId = common.getWorkingProjectId();
            sessionManagementList = sessionManagementRepository.findAllOfProject(projectId);
            
//            if (CollectionUtils.isNotEmpty(sessionManagementList)) {
                String outputDir = "";
                
        		outputDir = createFileOutputFolder("", generateSourceCode.getSourcePathWeb(), "");
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("package", generateSourceCode.getProject().getPackageName());
                data.put("sessionManagementList", sessionManagementList);
                
                this.process(data, TEMPLATE_SESSION_MANAGEMENT, outputDir
                                + WordUtils.capitalize("CustomizeSessionUtils")
                                + GenerateSourceCodeConst.JAVA_EXTEND);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
	}
    
    private String createFileOutputFolder(String branchFolderPath, String pathRoot, String moduleType) {
        StringBuilder pathOutput = new StringBuilder();
        
        pathOutput.append("src").append(File.separator)
                  .append("main").append(File.separator)
                  .append("java").append(File.separator)
                  .append("org").append(File.separator)
                  .append("terasoluna").append(File.separator)
                  .append("qp").append(File.separator)
                  .append("app").append(File.separator)
                  .append("common").append(File.separator)
                  .append("ultils");

        return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
    }

}
