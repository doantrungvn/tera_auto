package org.terasoluna.qp.domain.service.autocomplete;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemAutocompleteInput;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;

@Service
public class AutocompleteDesignShareServiceImpl implements AutocompleteDesignShareService{

	@Inject
	private ScreenItemRepository screenItemRepository;
	
	@Inject
	private ProblemListRepository problemListRepository ;
	
	@Inject
	private TableDesignDetailRepository tableDesignDetailRepository;
	
	@Inject
	private DomainDesignRepository domainDesignRepository;
	
	@Inject
	private AutocompleteDesignRepository autocompleteRepository;
	
	@Inject
	private SqlDesignInputRepository sqlDesignInputRepository;
	
	@Inject
	private ScreenDesignRepository screenDesignRepository;
	
	@Inject
	private TableDesignRepository tableDesignRepository;
	
	@Override
	public ImpactChangeOfAutocompleteDesign detectListAffectedWhenModify(Long autocompleteId, CommonModel common,Boolean isRunBatch) {
		ImpactChangeOfAutocompleteDesign impact = new ImpactChangeOfAutocompleteDesign();
		List<ScreenItem> screenItemsImpacted = executeImpactModifyOnScreenDesign(autocompleteId, common, isRunBatch);
		if(CollectionUtils.isEmpty(screenItemsImpacted)){
			return impact;
		}
		List<ScreenDesign> screenDesignImpacted = getScreenDesignsImpactedByScreenItemsImpacted(screenItemsImpacted, common);
		if(CollectionUtils.isNotEmpty(screenDesignImpacted))
			impact.setScreenDesignsImpacted(screenDesignImpacted);
		return impact;
	}

	@Override
	public ImpactChangeOfAutocompleteDesign detectListAffectedWhenDelete(AutocompleteDesign autocomplete, CommonModel common,Boolean isRunBatch) {
		ImpactChangeOfAutocompleteDesign impact = new ImpactChangeOfAutocompleteDesign();
		List<ProblemList> problemLists = new ArrayList<ProblemList>();
		ScreenDesignsProblems screenDesignProblems = executeImpactDeleteOnScreenDesign(autocomplete, common, isRunBatch);
		TableDesignsProblems tableDesignsProblems = executeImpactDeleteOnTableDesignDetail(autocomplete, common, isRunBatch);
		DomainDesignsProblems domainDesignsProblems = executeImpactDeleteOnDomainDesign(autocomplete, common, isRunBatch);
		boolean isNotImpact = !this.checkIsImpact(screenDesignProblems, tableDesignsProblems, domainDesignsProblems);
		if(isNotImpact){
			return impact;
		}
		if(CollectionUtils.isNotEmpty(screenDesignProblems.getScreendesigns())){
			impact.setScreenDesignsImpacted(screenDesignProblems.getScreendesigns());
		}
		if(CollectionUtils.isNotEmpty(tableDesignsProblems.getTableDesigns())){
			impact.setTableDesignsImpacted(tableDesignsProblems.getTableDesigns());
		}
		if(CollectionUtils.isNotEmpty(domainDesignsProblems.getDomainDesigns())){
			impact.setDomainDesignsImpacted(domainDesignsProblems.getDomainDesigns());
		}
		if(CollectionUtils.isNotEmpty(screenDesignProblems.getProblems())){
			problemLists.addAll(screenDesignProblems.getProblems());
		}
		if(CollectionUtils.isNotEmpty(tableDesignsProblems.getProblems())){
			problemLists.addAll(tableDesignsProblems.getProblems());
		}
		if(CollectionUtils.isNotEmpty(domainDesignsProblems.getProblems())){
			problemLists.addAll(domainDesignsProblems.getProblems());
		}
		if(autocomplete.getAutocompleteId() != null){
			this.deleteListProblem(autocomplete, isRunBatch);
		}
		if(CollectionUtils.isNotEmpty(problemLists)){
			this.saveListProblem(autocomplete, problemLists, isRunBatch);
		}
		return impact;
	}
	
	private boolean checkIsImpact(ScreenDesignsProblems screenDesignsProblems, TableDesignsProblems tableDesignsProblems, DomainDesignsProblems domainDesignsProblems){
		if(CollectionUtils.isNotEmpty(screenDesignsProblems.getProblems())){
			return true;
		}
		if(CollectionUtils.isNotEmpty(tableDesignsProblems.getProblems())){
			return true;
		}
		if(CollectionUtils.isNotEmpty(domainDesignsProblems.getProblems())){
			return true;
		}
		return false;
	}
	
	@Override
	public void detectListAffectedWhenDeleteOfBatch(Long autocompleteId,
			String autoCompleteCode, CommonModel common) {
		AutocompleteDesign autocomplete = new AutocompleteDesign();
		autocomplete.setAutocompleteId(autocompleteId);
		autocomplete.setAutocompleteName(autoCompleteCode);
		this.detectListAffectedWhenDelete(autocomplete, common, true);
	}

	@Override
	public void detectListAffectedWhenModifyOfBatch(Long autocompleteId, CommonModel common) {
		this.detectListAffectedWhenModify(autocompleteId, common, true);
	}
	
	
	private List<ScreenDesign> getScreenDesignsImpactedByScreenItemsImpacted(List<ScreenItem> screenItemsImpacted, CommonModel common) {
		List<Long> screenDesigIdsImpacted =  initScreenDesignIds(screenItemsImpacted.get(0));
		for(ScreenItem screenItem : screenItemsImpacted){
			Long screenId = screenItem.getScreenId();
			if(screenDesigIdsImpacted.contains(screenId)){
				continue;
			}
			screenDesigIdsImpacted.add(screenId);
		}
		List<ScreenDesign> screenDesignsImpacted = this.screenDesignRepository.getAllScreenByScreenIds(screenDesigIdsImpacted, common.getWorkingLanguageId());
		return screenDesignsImpacted;
	}

	private List<Long> initScreenDesignIds(ScreenItem screenItem){
		Long screenDesignId = screenItem.getScreenId();
		List<Long> screenDesigIdsImpacted = new ArrayList<Long>();
		screenDesigIdsImpacted.add(screenDesignId);
		return screenDesigIdsImpacted;
	}
	
	private List<ScreenItem> executeImpactModifyOnScreenDesign(Long autocompleteId, CommonModel common, boolean isRunBatch){
		List<ScreenItem> screenItemsImpact = this.screenItemRepository.getListInputOfItemByAutocompleteId(autocompleteId);
		AutocompleteDesign autompleteSaved = this.autocompleteRepository.findOneById(autocompleteId);
		if(CollectionUtils.isNotEmpty(screenItemsImpact)){
			boolean check = checkAndSaveProblemScreenImpactWhenModifyAutocomplete(screenItemsImpact, autompleteSaved, common, isRunBatch);
			if(!check){
				screenItemsImpact.clear();
			}
		}
		return screenItemsImpact;
	}
	
	private DomainDesignsProblems executeImpactDeleteOnDomainDesign(AutocompleteDesign autocomplete, CommonModel common, boolean isRunBatch){
		 List<DomainDesign> domainDesignsImpacted = this.domainDesignRepository.getListDomainDesignByAutocompleteId(autocomplete.getAutocompleteId());
		 DomainDesignsProblems domainDesignsProblems = new DomainDesignsProblems(); 
		 if(CollectionUtils.isEmpty(domainDesignsImpacted)){
			 return domainDesignsProblems;
		 }
		 List<ProblemList> prbls = saveProblemDomainDesignImpactWhenDeleteAutocomplete(domainDesignsImpacted, autocomplete, common, isRunBatch);
		 domainDesignsProblems.setDomainDesigns(domainDesignsImpacted);
		 domainDesignsProblems.setProblems(prbls);
		 return domainDesignsProblems;
	} 
	
	private TableDesignsProblems executeImpactDeleteOnTableDesignDetail(AutocompleteDesign autocomplete, CommonModel common, boolean isRunBatch){
		 List<TableDesignDetails> tableDesignDetailsImpacted = this.tableDesignDetailRepository.getListTableDesignDetailByAutoCompleteId(autocomplete.getAutocompleteId());
		 List<TableDesign> tableDesignsImpated = new ArrayList<TableDesign>();
		 TableDesignsProblems tableDesignsProblems = new TableDesignsProblems();
		 if(CollectionUtils.isEmpty(tableDesignDetailsImpacted)){
			 return tableDesignsProblems;
		 }
		 List<ProblemList> lstpr = saveProblemTableDesignImpactWhenDeleteAutocomplete(tableDesignDetailsImpacted, autocomplete, common, isRunBatch);
		 tableDesignsImpated = this.getListTableDesignByTableDetails(tableDesignDetailsImpacted, common);
		 tableDesignsProblems.setProblems(lstpr);
		 tableDesignsProblems.setTableDesigns(tableDesignsImpated);
		 return tableDesignsProblems;
	}
	
	private List<TableDesign> getListTableDesignByTableDetails(List<TableDesignDetails> tableDesignDetailsImpacted, CommonModel common){
		List<Long> tableDetailIds = initTableDetailIds(tableDesignDetailsImpacted.get(0));
		for(TableDesignDetails tabledetail : tableDesignDetailsImpacted){
			Long tableDesignId = tabledetail.getTableDesignId();
			if(tableDetailIds.contains(tableDesignId)){
				continue;
			}
			tableDetailIds.add(tableDesignId);
		}
		List<TableDesign> tableDesignImpated =  this.tableDesignRepository.getTableDesignByTableId(tableDetailIds);
		return tableDesignImpated;
	}
	
	private List<Long> initTableDetailIds(TableDesignDetails tableDetails) {
		List<Long> tableDetailIds = new ArrayList<Long>();
		tableDetailIds.add(tableDetails.getTableDesignId());
		return tableDetailIds;
	}


	private ScreenDesignsProblems executeImpactDeleteOnScreenDesign(AutocompleteDesign autocomplete, CommonModel common, boolean isRunBatch){
		List<ScreenItem> screenItemsImpact = this.screenItemRepository.getListScreenItemByAutocompleteId(autocomplete.getAutocompleteId());
		ScreenDesignsProblems screenDesignsProblems = new ScreenDesignsProblems();
		if(CollectionUtils.isEmpty(screenItemsImpact)){
			return screenDesignsProblems;
		}
		List<ProblemList> lstProblems = saveProblemScreenImpactWhenDeleteAutocomplete(screenItemsImpact, autocomplete, common, isRunBatch);
		List<ScreenDesign> screenDesigns = getScreenDesignsImpactedByScreenItemsImpacted(screenItemsImpact, common);
		screenDesignsProblems.setProblems(lstProblems);
		screenDesignsProblems.setScreendesigns(screenDesigns);
		return screenDesignsProblems;
	}
	
	private boolean checkAndSaveProblemScreenImpactWhenModifyAutocomplete(List<ScreenItem> screenItems, AutocompleteDesign autocomplete,CommonModel common, boolean isRunBatch){
		this.deleteListProblem(autocomplete,isRunBatch);
		List<SqlDesignInput> sqlInputsAutocomplete = Arrays.asList(this.sqlDesignInputRepository.findAllBySqlDesignId(autocomplete.getSqlDesign().getSqlDesignId()));
		if(CollectionUtils.isEmpty(sqlInputsAutocomplete)){
			return false;
		}
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		List<ScreenItemInputSql> screentItemsImpacted = getInputsOfAutocompleteNotAddToAutocompleteScreenItem(sqlInputsAutocomplete, screenItems);
		if(CollectionUtils.isEmpty(screentItemsImpacted)){
			return false;
		}
		for(ScreenItemInputSql screenItemInput : screentItemsImpacted){
			for(SqlDesignInput sqlInput : screenItemInput.getSqlInputs()){
				ProblemList problemList = getProblemListOfScreenItemModify(screenItemInput,sqlInput, autocomplete, common);
				lstProblemLists.add(problemList);
			}
		}
		this.saveListProblem(autocomplete, lstProblemLists,isRunBatch);
		return true;
	}


	private List<ScreenItemInputSql> getInputsOfAutocompleteNotAddToAutocompleteScreenItem(List<SqlDesignInput> inputAutocompletes,List<ScreenItem> screenItems){
		List<ScreenItemInputSql> screentItemsImpacted = new ArrayList<ScreenItemInputSql>();
		for(ScreenItem screenItem :  screenItems){
			List<SqlDesignInput> inputNoAdded = checkAndGetInputsSqlNoAddedInScreenItems(inputAutocompletes, screenItem.getScreenItemAutocompleteInputs());
			if(CollectionUtils.isNotEmpty(inputNoAdded)){
				ScreenItemInputSql itemInput = new ScreenItemInputSql(screenItem, inputNoAdded);
				screentItemsImpacted.add(itemInput);
			}
		}
		return screentItemsImpacted;
	}
	
	private List<SqlDesignInput> checkAndGetInputsSqlNoAddedInScreenItems(List<SqlDesignInput> inputAutocompletes, List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs){
		List<SqlDesignInput> sqlDesignInputs = new ArrayList<>();
		int i = 0;
		for(SqlDesignInput sqlDesignInput : inputAutocompletes ){
			i++;
			if(i == 1)
				continue;
			SqlDesignInput sqlInputNoAdded = checkAndGetInputSqlNotAddedScreenItemInput(sqlDesignInput, screenItemAutocompleteInputs);
			if(sqlInputNoAdded != null){
				sqlDesignInputs.add(sqlInputNoAdded);
			}
		}
		return sqlDesignInputs;
	}
	
	private SqlDesignInput checkAndGetInputSqlNotAddedScreenItemInput(SqlDesignInput sqlDesignInput, List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs){
		if(screenItemAutocompleteInputs.size() == 0)
			return sqlDesignInput;
		if(screenItemAutocompleteInputs.size() == 1)
			return sqlDesignInput;
		int i = 0;
		for(ScreenItemAutocompleteInput input : screenItemAutocompleteInputs){
			i++;
			if(i == 1)
				continue;
			if(sqlDesignInput.getSqlDesignId().equals(input.getInputId())){
				return null;
			}
		}
		
		return sqlDesignInput;
	}
	
	private List<ProblemList> saveProblemDomainDesignImpactWhenDeleteAutocomplete (List<DomainDesign> domainDesignsImpacted, AutocompleteDesign autocomplete,CommonModel common,boolean isRunBatch){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		for(DomainDesign domainDesign : domainDesignsImpacted){
			ProblemList problemList = getProblemListByDomainDesignItem(domainDesign, autocomplete, common);
			lstProblemLists.add(problemList);
		}
		return lstProblemLists;
	}
	
	private List<ProblemList> saveProblemTableDesignImpactWhenDeleteAutocomplete(List<TableDesignDetails> tableDesignDetailsImpacted, AutocompleteDesign autocomplete,CommonModel common, boolean isRunBatch){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		for(TableDesignDetails tableDetail : tableDesignDetailsImpacted){
			ProblemList problemList = getProblemListByTableDetailItem(tableDetail, autocomplete, common);
			lstProblemLists.add(problemList);
		}
		return lstProblemLists;
	}
	
	private List<ProblemList> saveProblemScreenImpactWhenDeleteAutocomplete(List<ScreenItem> screenItemsImpact, AutocompleteDesign autocomplete,CommonModel common, boolean isRunBatch){
		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
		for(ScreenItem screenItem : screenItemsImpact){
			ProblemList problemList = getProblemListByScreenItem(screenItem, autocomplete, common);
			lstProblemLists.add(problemList);
		}
		return lstProblemLists;
	}
	
	private void deleteListProblem(AutocompleteDesign autocomplete, boolean isRunBatch){
		if(!isRunBatch){
			return;
		}
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(autocomplete.getAutocompleteId());
		problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN, lstFroms);
	}
	
	private void saveListProblem(AutocompleteDesign autocomplete, List<ProblemList> lstProblemLists,  boolean isRunBatch){
		if(!isRunBatch){
			return;
		}
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(autocomplete.getAutocompleteId());
		problemListRepository.multiRegisterProblem(lstProblemLists);
	}
	
	/*private void deleteAndSaveListProblem(AutocompleteDesign autocomplete, List<ProblemList> lstProblemLists,  boolean isRunBatch){
		if(!isRunBatch){
			return;
		}
		List<Long> lstFroms = new ArrayList<Long>();
		lstFroms.add(autocomplete.getAutocompleteId());
		problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN, lstFroms);
		problemListRepository.multiRegisterProblem(lstProblemLists);
	}*/
	
	/**
	 * Get problems list for ScreenDesign when modify autocomplete
	 * @param screenItemInput
	 * @param sqlInput
	 * @param autocomplete
	 * @param common
	 * @return
	 */
	private ProblemList getProblemListOfScreenItemModify(ScreenItemInputSql screenItemInput,SqlDesignInput sqlInput, AutocompleteDesign autocomplete, CommonModel common){
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		ProblemList problemList = new ProblemList();
		problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0129,sqlInput.getSqlDesignInputCode(), autocomplete.getAutocompleteName(), screenItemInput.getScreenItem().getItemCode(), screenItemInput.getScreenItem().getScreenCode()));
		problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
		problemList.setResourceId(screenItemInput.getScreenItem().getScreenId());
		problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
		problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
		problemList.setModuleId(screenItemInput.getScreenItem().getModuleId());
		problemList.setProjectId(common.getProjectId());
		problemList.setCreatedBy(common.getCreatedBy());
		problemList.setFromResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN);
		problemList.setFromResourceId(autocomplete.getAutocompleteId());
		problemList.setCreatedDate(systemDate);
		return problemList;
	}
	
	/**
	 * get problem for domain when delete autocomplete
	 * @param domainDesign
	 * @param autocomplete
	 * @param common
	 * @return
	 */
	private ProblemList getProblemListByDomainDesignItem (DomainDesign domainDesign, AutocompleteDesign autocomplete, CommonModel common){
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		ProblemList problemList = new ProblemList();
		problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0128, autocomplete.getAutocompleteName(), domainDesign.getDomainCode()));
		problemList.setResourceType(DbDomainConst.ResourceType.DOMAIN_DESIGN);
		problemList.setResourceId(domainDesign.getDomainId());
		problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		problemList.setUrlId(DbDomainConst.ResourceURL.URL_DOMAIN_DESIGN);
		problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
		problemList.setProjectId(common.getProjectId());
		problemList.setCreatedBy(common.getCreatedBy());
		problemList.setFromResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN);
		problemList.setFromResourceId(autocomplete.getAutocompleteId());
		problemList.setCreatedDate(systemDate);
		return problemList;
	}
	
	/**
	 * get problems for table when delete when delete autocomplete
	 * @param tableDetail
	 * @param autocomplete
	 * @param common
	 * @return
	 */
	private ProblemList getProblemListByTableDetailItem(TableDesignDetails tableDetail, AutocompleteDesign autocomplete, CommonModel common){
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		ProblemList problemList = new ProblemList();
		problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0127, autocomplete.getAutocompleteName(), tableDetail.getCode(), tableDetail.getTableDesignCode()));
		problemList.setResourceType(DbDomainConst.ResourceType.TABLE_DESIGN);
		problemList.setResourceId(tableDetail.getTableDesignId());
		problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		problemList.setUrlId(DbDomainConst.ResourceURL.URL_TABLE_DESIGN);
		problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
		problemList.setProjectId(common.getProjectId());
		problemList.setCreatedBy(common.getCreatedBy());
		problemList.setFromResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN);
		problemList.setFromResourceId(autocomplete.getAutocompleteId());
		problemList.setCreatedDate(systemDate);
		return problemList;
	}
	
	/**
	 * get problems for ScreenDesign when autocomplete delete
	 * @param screenItem
	 * @param autocomplete
	 * @param common
	 * @return
	 */
	private ProblemList getProblemListByScreenItem(ScreenItem screenItem, AutocompleteDesign autocomplete, CommonModel common){
		Timestamp systemDate = FunctionCommon.getCurrentTime();
		ProblemList problemList = new ProblemList();
		problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0126, autocomplete.getAutocompleteName(), screenItem.getItemCode(), screenItem.getScreenCode()));
		problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
		problemList.setResourceId(screenItem.getScreenId());
		problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
		problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
		problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
		problemList.setModuleId(screenItem.getModuleId());
		problemList.setProjectId(common.getProjectId());
		problemList.setCreatedBy(common.getCreatedBy());
		problemList.setFromResourceType(DbDomainConst.FromResourceType.AUTOCOMPLETE_DESIGN);
		problemList.setFromResourceId(autocomplete.getAutocompleteId());
		problemList.setCreatedDate(systemDate);
		return problemList;
	}
	
	private static class ScreenItemInputSql{
		private ScreenItem screenItem;
		private List<SqlDesignInput> sqlInputs;
		
		public ScreenItemInputSql(ScreenItem screenItem, List<SqlDesignInput> sqlInputs){
			this.screenItem = screenItem;
			this.sqlInputs = sqlInputs;
		}
		
		public ScreenItem getScreenItem() {
			return screenItem;
		}

		public List<SqlDesignInput> getSqlInputs() {
			return sqlInputs;
		}
	}
	
	private static class ScreenDesignsProblems{
		private List<ScreenDesign> screendesigns;
		private List<ProblemList> problems;
		public List<ScreenDesign> getScreendesigns() {
			return screendesigns;
		}
		public void setScreendesigns(List<ScreenDesign> screendesigns) {
			this.screendesigns = screendesigns;
		}
		public List<ProblemList> getProblems() {
			return problems;
		}
		public void setProblems(List<ProblemList> problems) {
			this.problems = problems;
		}
	}
	
	private static class TableDesignsProblems{
		private List<TableDesign> tableDesigns;
		private List<ProblemList> problems;
		public List<TableDesign> getTableDesigns() {
			return tableDesigns;
		}
		public void setTableDesigns(List<TableDesign> tableDesigns) {
			this.tableDesigns = tableDesigns;
		}
		public List<ProblemList> getProblems() {
			return problems;
		}
		public void setProblems(List<ProblemList> problems) {
			this.problems = problems;
		}
	}
	
	private static class DomainDesignsProblems{
		private List<DomainDesign> domainDesigns;
		private List<ProblemList> problems;
		public List<DomainDesign> getDomainDesigns() {
			return domainDesigns;
		}
		public void setDomainDesigns(List<DomainDesign> domainDesigns) {
			this.domainDesigns = domainDesigns;
		}
		public List<ProblemList> getProblems() {
			return problems;
		}
		public void setProblems(List<ProblemList> problems) {
			this.problems = problems;
		}
	}

}
