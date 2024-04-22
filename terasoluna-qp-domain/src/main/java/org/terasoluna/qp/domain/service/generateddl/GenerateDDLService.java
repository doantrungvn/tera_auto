package org.terasoluna.qp.domain.service.generateddl;

import java.util.List;

import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.TableDesignDetails;

public interface GenerateDDLService {

	/**
	 * 
	 * @param project
	 * @param languageDesign
	 * @param generateMode
	 * @param listTableId
	 * @param isGenDrop
	 * @return
	 */
	StringBuilder generateSQL(Project project, LanguageDesign languageDesign, String generateMode, List<Long> listTableId, boolean isGenDrop, CommonModel commonModel);

	StringBuilder generateSQLForLogging(Integer dataType);

	void initData();

	String convertBaseTypeToDb(TableDesignDetails column);

}
