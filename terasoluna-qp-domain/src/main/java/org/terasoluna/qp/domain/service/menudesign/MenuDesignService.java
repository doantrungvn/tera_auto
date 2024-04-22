/**
 * 
 */
package org.terasoluna.qp.domain.service.menudesign;

import java.util.List;

import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MenuDesignItem;

/**
 * @author datld
 *
 */
@Service
public interface MenuDesignService {
	
	MenuDesign getMenuDesignInformation(Long projectId, Long languageId);
	
	void registerMenuDesign(MenuDesign menuDesign, CommonModel common);
	
	void setLevelForMenuDesignItem(List<MenuDesignItem> menuDesignItems);
	
//	void deleteMenuDesign(MenuDesign menuDesign, Long projectId);
	
	MenuDesign getMenuDesignForPreview(Long projectId, Long languageId);
	
	MenuDesign getMenuDesignForGenerateJSP(Long projectId, Long languageId);
	
	MenuDesign getNavigationInformationForJSP(Long projectId, Long screenId, Long headerMenuAction);
	
	String buildMenu(MenuDesign menuDesign, int pmenuFor);
}
