package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.LanguageDesign;

public interface GraphicDatabaseDesignService {

	GraphicDbDesign modifyTableByGraphicDesign(GraphicDbDesign graphicDbDesign, boolean hasProblem, List<BusinessDesign> lstBlogic, List<Long> lstSreenIds, 
			boolean generateDefaultDB, Long accountId, LanguageDesign languageDesign, Integer checkProject);

	GraphicDbDesign loadGraphicDesign(Long projectId, Long areaId);

	GraphicDbDesign loadAffectChangedDesign(GraphicDbDesign graphicDbDesign, Long projectId);

	GraphicDbDesign displayConfirmGenerateDbAndBlogic(Long accountId, Long projectId, Long moduleId, Long subjectAreaId, List<Long> listScreenIds, LanguageDesign languageDesign);

	void generateBlogicFromScreen(Long moduleId, List<Long> lstScreenIds, LanguageDesign languageDesign, Long accountId, Long projectId);

	SqlDesignXml converFromXMLToObject(GraphicDbDesign graphicDbDesign);

}
