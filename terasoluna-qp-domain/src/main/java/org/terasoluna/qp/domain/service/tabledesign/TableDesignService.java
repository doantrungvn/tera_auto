package org.terasoluna.qp.domain.service.tabledesign;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignCriteria;

public interface TableDesignService{
	
	Page<TableDesign> findPageByCriteria(@Param("criteria") TableDesignCriteria criteria,@Param("pageable") Pageable pageable);

	TableDesign findOneById(Long tableListId);

	void createTable(TableDesign tableDesign, CommonModel commonModel, Project project, AccountProfile account);
	
	void modifyTable(TableDesign listTableDesign, CommonModel commonModel, Project project, boolean isExport);
	
	void modifyTableCommon(TableDesign tableDesign, CommonModel commonModel);
	
	void deleteTable(TableDesign tableDesign, Long accountId, Long projectId, boolean hasProblem);
	
	void updateItem(TableDesign tableDesign, CommonModel commonModel);
	
	Map<Integer, String> getOptionItemType(TableDesignDetails tableDesignDetails,List<ValidationRule> listValidationRule);
	
	TableDesign loadTableDesign(Long tableId, CommonModel commonModel);
	
	TableDesign getTableDesignForScreenItemSetting(Long tableId);
	
	//void calculatorItemType(TableDesignDetails designDetails, List<ValidationRule> validationRules);
	
	void modifyDesignStatus(TableDesign tableDesign, Long accountId, Long projectId);
	
	void loadDataWhenHasErr(TableDesign tableDesign, Long projectId);
	
	void loadListAffected(List<TableDesign> tableDesign, int fromResourceType, Long projectId);
	
	TableDesign getTableDesignForView(Long tableId, CommonModel commonModel);

	void impactChangeDesignSqlDesignByDeleteTable(List<TableDesign> tableDesign, Long accountId, Long projectId);
	
	void impactChangeDesignBusinessLogicByDeleteTable(List<TableDesign> tableDesign, Long projectId);
	
	void processAutoSynchDataReferBusinessLogicByDeleteColumn(List<TableDesign> tableDesign, Long accountId, Long projectId);
	
	void processAutoSynchDataReferBusinessLogicByAddingColumn (List<TableDesign> tableDesignForModify);
	
	void processAutoSynchDataReferBusinessLogicByModifyTableDesign (List<TableDesign> tableDesign);
	
	void updateForeignKeyWhenDeleteColumns(List<Long> listColumnId);
	
	void insertKey(TableDesign tableDesign, Long projectId);
	
	SqlDesignOutput[] getSqlDesignOutputsForAdvanceSetting(Long sqlDesignId);
	
	TableDesignDetails findOneTableDesignDetail(Long columnId);
}
