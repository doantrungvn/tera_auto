package org.terasoluna.qp.domain.repository.menudesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MenuDesignItem;

public interface MenuDesignRepository {
	Long getSequencesMenuDesignItem(@Param("size") Integer size);
	
	MenuDesign getMenuDesignInformation(@Param("menuDesign") MenuDesign menuDesign);
	
	MenuDesign findMenuDesignByProjectId(@Param("projectId") Long projectId);
	
	int checkExists(@Param("projectId") Long projectId);
	
	List<MenuDesignItem> findMenuDesignItemByProjectId(@Param("menuDesign") MenuDesign menuDesign);
	
	boolean deleteMenuDesignItem(@Param("projectId") Long projectId, @Param("menuId") Long menuId);
	
	boolean deleteMenuDesign(@Param("projectId") Long projectId, @Param("menuId") Long menuId);
	
	boolean updateMenuDesign(MenuDesign menuDesign);

	int createMenuDesign(MenuDesign menuDesign);
	
	int createMenuDesignItem(@Param("menuDesignItems") List<MenuDesignItem> menuDesignItems);

	List<MenuDesignItem> getMenuDesignItemForPreview(@Param("menuDesign") MenuDesign menuDesign);
	
	List<MenuDesignItem> getMenuDesignItemForGenerateJSP(@Param("menuDesign") MenuDesign menuDesign);

	MenuDesign getMenuDesignInformationForJSP(@Param("menuDesign") MenuDesign menuDesign);
	
	MenuDesign getNavigationInformationForJSP(@Param("projectId") Long projectId, @Param("screenId") Long screenId, @Param("headerMenuAction") Long headerMenuAction);
	
}
