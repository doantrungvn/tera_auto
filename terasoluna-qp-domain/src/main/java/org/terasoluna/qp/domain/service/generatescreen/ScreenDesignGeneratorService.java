package org.terasoluna.qp.domain.service.generatescreen;

import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;

public interface ScreenDesignGeneratorService {
	void generateDefaultScreen(ScreenDesignDefault screenDesignDefault, boolean generateFromTransition);
	
	void generateDefaultBusinesslogic(ScreenDesignDefault screenDesignDefault);
	
	void generateSceenAndBlogic(Module generateScreen, CommonModel common);
}
