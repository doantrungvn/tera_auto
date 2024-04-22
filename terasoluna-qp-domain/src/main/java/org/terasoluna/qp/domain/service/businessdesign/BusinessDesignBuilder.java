package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.AdvanceComponent;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DownloadFileComponent;
import org.terasoluna.qp.domain.model.EmailComponent;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExportFileComponent;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.FileOperationComponent;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.ImportFileComponent;
import org.terasoluna.qp.domain.model.LogComponent;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NestedLogicComponent;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.TransactionComponent;
import org.terasoluna.qp.domain.model.UtilityComponent;

public class BusinessDesignBuilder {

	private Boolean isOnlyView = false;
	private List<SequenceLogic> lstSequenceLogics = new ArrayList<SequenceLogic>();
	private List<FeedbackComponent> lstFeedbackComponent = new ArrayList<FeedbackComponent>();
	private List<IfComponent> lstIfComponents = new ArrayList<IfComponent>();
	private List<NavigatorComponent> lstNavComponent = new ArrayList<NavigatorComponent>();
	private List<CommonComponent> lstCommonComponent = new ArrayList<CommonComponent>();
	private List<LoopComponent> lstLoopComponent = new ArrayList<LoopComponent>();
	private List<DecisionComponent> lstDecisionComponent = new ArrayList<DecisionComponent>();
	private List<AssignComponent> lstAssignComponent = new ArrayList<AssignComponent>();
	private List<BusinessCheckComponent> lstBusinessCheckComponents = new ArrayList<BusinessCheckComponent>();
	private List<ExecutionComponent> lstExecutionComponents = new ArrayList<ExecutionComponent>();
	private List<AdvanceComponent> lstAdvanceComponents = new ArrayList<AdvanceComponent>();
	private List<FileOperationComponent> lstFileOperationComponents = new ArrayList<FileOperationComponent>();
	private List<ImportFileComponent> lstImportFileComponents = new ArrayList<ImportFileComponent>();
	private List<ExportFileComponent> lstExportFileComponents = new ArrayList<ExportFileComponent>();
	private List<TransactionComponent> lstTransactionComponents = new ArrayList<TransactionComponent>();
	private List<LogComponent> lstLogComponents = new ArrayList<LogComponent>();
	private List<UtilityComponent> lstUtilityComponents = new ArrayList<UtilityComponent>();
	private List<NestedLogicComponent> lstNestedLogicComponents = new ArrayList<NestedLogicComponent>();
	private List<SequenceConnector> lstSequenceConnectors = new ArrayList<SequenceConnector>();
	private List<EmailComponent> lstEmailComponents = new ArrayList<EmailComponent>();
	private List<DownloadFileComponent> lstDownloadFileComponents = new ArrayList<DownloadFileComponent>();
	private List<ExceptionComponent> lstExceptionComponent = new ArrayList<ExceptionComponent>();

	public BusinessDesignBuilder(Boolean isOnlyView, List<SequenceLogic> lstSequenceLogics, List<FeedbackComponent> lstFeedbackComponent, List<IfComponent> lstIfComponents, List<NavigatorComponent> lstNavComponent, List<CommonComponent> lstCommonComponent, List<LoopComponent> lstLoopComponent, List<DecisionComponent> lstDecisionComponent, List<AssignComponent> lstAssignComponent, List<BusinessCheckComponent> lstBusinessCheckComponents, List<ExecutionComponent> lstExecutionComponents, List<AdvanceComponent> lstAdvanceComponents, List<FileOperationComponent> lstFileOperationComponents, List<ImportFileComponent> lstImportFileComponents, List<ExportFileComponent> lstExportFileComponents, List<TransactionComponent> lstTransactionComponents, List<LogComponent> lstLogComponents, List<UtilityComponent> lstUtilityComponents, List<NestedLogicComponent> lstNestedLogicComponents, List<SequenceConnector> lstSequenceConnectors, List<EmailComponent> lstEmailComponents, List<DownloadFileComponent> lstDownloadFileComponents, List<ExceptionComponent> lstExceptionComponent) {
		this.isOnlyView = isOnlyView;
		this.lstSequenceLogics = lstSequenceLogics;
		this.lstFeedbackComponent = lstFeedbackComponent;
		this.lstIfComponents = lstIfComponents;
		this.lstNavComponent = lstNavComponent;
		this.lstCommonComponent = lstCommonComponent;
		this.lstLoopComponent = lstLoopComponent;
		this.lstDecisionComponent = lstDecisionComponent;
		this.lstAssignComponent = lstAssignComponent;
		this.lstBusinessCheckComponents = lstBusinessCheckComponents;
		this.lstExecutionComponents = lstExecutionComponents;
		this.lstAdvanceComponents = lstAdvanceComponents;
		this.lstFileOperationComponents = lstFileOperationComponents;
		this.lstImportFileComponents = lstImportFileComponents;
		this.lstExportFileComponents = lstExportFileComponents;
		this.lstTransactionComponents = lstTransactionComponents;
		this.lstLogComponents = lstLogComponents;
		this.lstUtilityComponents = lstUtilityComponents;
		this.lstNestedLogicComponents = lstNestedLogicComponents;
		this.lstSequenceConnectors = lstSequenceConnectors;
		this.lstEmailComponents = lstEmailComponents;
		this.lstDownloadFileComponents = lstDownloadFileComponents;
		this.setLstExceptionComponent(lstExceptionComponent);
	}

	public Boolean getIsOnlyView() {
		return isOnlyView;
	}

	public void setIsOnlyView(Boolean isOnlyView) {
		this.isOnlyView = isOnlyView;
	}

	public List<SequenceLogic> getLstSequenceLogics() {
		return lstSequenceLogics;
	}

	public void setLstSequenceLogics(List<SequenceLogic> lstSequenceLogics) {
		this.lstSequenceLogics = lstSequenceLogics;
	}

	public List<FeedbackComponent> getLstFeedbackComponent() {
		return lstFeedbackComponent;
	}

	public void setLstFeedbackComponent(List<FeedbackComponent> lstFeedbackComponent) {
		this.lstFeedbackComponent = lstFeedbackComponent;
	}

	public List<IfComponent> getLstIfComponents() {
		return lstIfComponents;
	}

	public void setLstIfComponents(List<IfComponent> lstIfComponents) {
		this.lstIfComponents = lstIfComponents;
	}

	public List<NavigatorComponent> getLstNavComponent() {
		return lstNavComponent;
	}

	public void setLstNavComponent(List<NavigatorComponent> lstNavComponent) {
		this.lstNavComponent = lstNavComponent;
	}

	public List<CommonComponent> getLstCommonComponent() {
		return lstCommonComponent;
	}

	public void setLstCommonComponent(List<CommonComponent> lstCommonComponent) {
		this.lstCommonComponent = lstCommonComponent;
	}

	public List<LoopComponent> getLstLoopComponent() {
		return lstLoopComponent;
	}

	public void setLstLoopComponent(List<LoopComponent> lstLoopComponent) {
		this.lstLoopComponent = lstLoopComponent;
	}

	public List<DecisionComponent> getLstDecisionComponent() {
		return lstDecisionComponent;
	}

	public void setLstDecisionComponent(List<DecisionComponent> lstDecisionComponent) {
		this.lstDecisionComponent = lstDecisionComponent;
	}

	public List<AssignComponent> getLstAssignComponent() {
		return lstAssignComponent;
	}

	public void setLstAssignComponent(List<AssignComponent> lstAssignComponent) {
		this.lstAssignComponent = lstAssignComponent;
	}

	public List<BusinessCheckComponent> getLstBusinessCheckComponents() {
		return lstBusinessCheckComponents;
	}

	public void setLstBusinessCheckComponents(List<BusinessCheckComponent> lstBusinessCheckComponents) {
		this.lstBusinessCheckComponents = lstBusinessCheckComponents;
	}

	public List<ExecutionComponent> getLstExecutionComponents() {
		return lstExecutionComponents;
	}

	public void setLstExecutionComponents(List<ExecutionComponent> lstExecutionComponents) {
		this.lstExecutionComponents = lstExecutionComponents;
	}

	public List<AdvanceComponent> getLstAdvanceComponents() {
		return lstAdvanceComponents;
	}

	public void setLstAdvanceComponents(List<AdvanceComponent> lstAdvanceComponents) {
		this.lstAdvanceComponents = lstAdvanceComponents;
	}

	public List<FileOperationComponent> getLstFileOperationComponents() {
		return lstFileOperationComponents;
	}

	public void setLstFileOperationComponents(List<FileOperationComponent> lstFileOperationComponents) {
		this.lstFileOperationComponents = lstFileOperationComponents;
	}

	public List<ImportFileComponent> getLstImportFileComponents() {
		return lstImportFileComponents;
	}

	public void setLstImportFileComponents(List<ImportFileComponent> lstImportFileComponents) {
		this.lstImportFileComponents = lstImportFileComponents;
	}

	public List<ExportFileComponent> getLstExportFileComponents() {
		return lstExportFileComponents;
	}

	public void setLstExportFileComponents(List<ExportFileComponent> lstExportFileComponents) {
		this.lstExportFileComponents = lstExportFileComponents;
	}

	public List<TransactionComponent> getLstTransactionComponents() {
		return lstTransactionComponents;
	}

	public void setLstTransactionComponents(List<TransactionComponent> lstTransactionComponents) {
		this.lstTransactionComponents = lstTransactionComponents;
	}

	public List<LogComponent> getLstLogComponents() {
		return lstLogComponents;
	}

	public void setLstLogComponents(List<LogComponent> lstLogComponents) {
		this.lstLogComponents = lstLogComponents;
	}

	public List<UtilityComponent> getLstUtilityComponents() {
		return lstUtilityComponents;
	}

	public void setLstUtilityComponents(List<UtilityComponent> lstUtilityComponents) {
		this.lstUtilityComponents = lstUtilityComponents;
	}

	public List<NestedLogicComponent> getLstNestedLogicComponents() {
		return lstNestedLogicComponents;
	}

	public void setLstNestedLogicComponents(List<NestedLogicComponent> lstNestedLogicComponents) {
		this.lstNestedLogicComponents = lstNestedLogicComponents;
	}

	public List<SequenceConnector> getLstSequenceConnectors() {
		return lstSequenceConnectors;
	}

	public void setLstSequenceConnectors(List<SequenceConnector> lstSequenceConnectors) {
		this.lstSequenceConnectors = lstSequenceConnectors;
	}

	public void mappingComponentOfBlogic(BusinessDesign businessDesign) {
		String jsonData = "";
		List<SequenceLogic> lstParentLogics = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstParentConnectors = new ArrayList<SequenceConnector>();
		// map details of nested logic
		if (CollectionUtils.isNotEmpty(this.lstSequenceLogics)) {
			for (SequenceLogic objLogic : this.lstSequenceLogics) {
				if (businessDesign.getBusinessLogicId().equals(objLogic.getBusinessLogicId())) {

					BusinessDesignHelper.assignStyleComponent(objLogic, isOnlyView);
					switch (objLogic.getComponentType()) {
						case BusinessDesignConst.COMPONENT_FEEDBACK:
							if (CollectionUtils.isNotEmpty(this.lstFeedbackComponent)) {
								for (FeedbackComponent objComponent : this.lstFeedbackComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_IF:
							if (CollectionUtils.isNotEmpty(this.lstIfComponents)) {
								for (IfComponent objComponent : this.lstIfComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_NAVIGATOR:
							if (CollectionUtils.isNotEmpty(this.lstNavComponent)) {
								for (NavigatorComponent objComponent : this.lstNavComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_COMMON:
							if (CollectionUtils.isNotEmpty(this.lstCommonComponent)) {
								for (CommonComponent objComponent : this.lstCommonComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_LOOP:
							if (CollectionUtils.isNotEmpty(this.lstLoopComponent)) {
								for (LoopComponent objComponent : this.lstLoopComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_DECISION:
							if (CollectionUtils.isNotEmpty(this.lstDecisionComponent)) {
								for (DecisionComponent objComponent : this.lstDecisionComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_ASSIGN:
							if (CollectionUtils.isNotEmpty(this.lstAssignComponent)) {
								for (AssignComponent objComponent : this.lstAssignComponent) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_BUSINESSCHECK:
							if (CollectionUtils.isNotEmpty(this.lstBusinessCheckComponents)) {
								for (BusinessCheckComponent objComponent : this.lstBusinessCheckComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_EXECUTION:
							if (CollectionUtils.isNotEmpty(this.lstExecutionComponents)) {
								for (ExecutionComponent objComponent : this.lstExecutionComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_ADVANCE:
							if (CollectionUtils.isNotEmpty(this.lstAdvanceComponents)) {
								for (AdvanceComponent objComponent : this.lstAdvanceComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_FILEOPERATION:
							if (CollectionUtils.isNotEmpty(this.lstFileOperationComponents)) {
								for (FileOperationComponent objComponent : this.lstFileOperationComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_IMPORTFILE:
							if (CollectionUtils.isNotEmpty(this.lstImportFileComponents)) {
								for (ImportFileComponent objComponent : this.lstImportFileComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_EXPORTFILE:
							if (CollectionUtils.isNotEmpty(this.lstExportFileComponents)) {
								for (ExportFileComponent objComponent : this.lstExportFileComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_TRANSACTION:
							if (CollectionUtils.isNotEmpty(this.lstTransactionComponents)) {
								for (TransactionComponent objComponent : this.lstTransactionComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_LOG:
							if (CollectionUtils.isNotEmpty(this.lstLogComponents)) {
								for (LogComponent objComponent : this.lstLogComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_UTILITY:
							if (CollectionUtils.isNotEmpty(this.lstUtilityComponents)) {
								for (UtilityComponent objComponent : this.lstUtilityComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_EMAIL:
							if (CollectionUtils.isNotEmpty(this.lstEmailComponents)) {
								for (EmailComponent objComponent : this.lstEmailComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_DOWNLOAD_FILE:
							if (CollectionUtils.isNotEmpty(this.lstDownloadFileComponents)) {
								for (DownloadFileComponent objComponent : this.lstDownloadFileComponents) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						case BusinessDesignConst.COMPONENT_EXCEPTION:
							if (CollectionUtils.isNotEmpty(this.getLstExceptionComponent())) {
								for (ExceptionComponent objComponent : this.getLstExceptionComponent()) {
									if (objComponent.getSequenceLogicId().equals(Long.valueOf(objLogic.getSequenceLogicId()))) {
										jsonData = DataTypeUtils.toJson(objComponent);
										objLogic.setStrData(jsonData);
										break;
									}
								}
							}
							break;
						default:
							break;
					}
				}
			}
			// map details of nested logic
			Map<String, NestedLogicComponent> mapDataOfNestedLogic = new HashMap<String, NestedLogicComponent>();
			if (CollectionUtils.isNotEmpty(this.lstNestedLogicComponents)) {
				for (NestedLogicComponent nestedLogicComponent : this.lstNestedLogicComponents) {
					List<SequenceLogic> lstChildComponent = new ArrayList<SequenceLogic>();
					Map<String, String> mapChildComponent = new HashMap<String, String>();
					for (SequenceLogic objLogic : lstSequenceLogics) {
						if (nestedLogicComponent.getSequenceLogicId().equals(objLogic.getParentSequenceLogicId())) {
							if (objLogic.getComponentType().intValue() == BusinessDesignConst.COMPONENT_NESTEDLOGIC) {
								if (mapDataOfNestedLogic.containsKey(objLogic.getSequenceLogicId())) {
									jsonData = DataTypeUtils.toJson(mapDataOfNestedLogic.get(objLogic.getSequenceLogicId()));
									objLogic.setStrData(jsonData);
								}
							}
							lstChildComponent.add(objLogic);
							mapChildComponent.put(objLogic.getSequenceLogicId(), objLogic.getSequenceLogicId());
						}
					}
					List<SequenceConnector> lstChildConnector = new ArrayList<SequenceConnector>();
					for (SequenceConnector objConnector : lstSequenceConnectors) {
						if (mapChildComponent.containsKey(objConnector.getConnectorDest()) || mapChildComponent.containsKey(objConnector.getConnectorDest())) {
							objConnector.setIsParent(false);
							lstChildConnector.add(objConnector);
						}
					}
					nestedLogicComponent.setArrConnection(lstChildConnector);
					nestedLogicComponent.setArrComponent(lstChildComponent);
					mapDataOfNestedLogic.put(nestedLogicComponent.getSequenceLogicId(), nestedLogicComponent);
				}
			}
			// get parent component
			if (CollectionUtils.isNotEmpty(this.lstSequenceLogics)) {
				for (SequenceLogic objLogic : lstSequenceLogics) {
					if(businessDesign.getBusinessLogicId().equals(objLogic.getBusinessLogicId())) {
						if (Integer.valueOf(BusinessDesignConst.COMPONENT_NESTEDLOGIC).equals(objLogic.getComponentType().intValue())) {
							for (NestedLogicComponent objComponent : lstNestedLogicComponents) {
								if (objComponent.getSequenceLogicId().equals(objLogic.getSequenceLogicId())) {
									jsonData = DataTypeUtils.toJson(objComponent);
									objLogic.setStrData(jsonData);
									break;
								}
							}
						}
						if (!objLogic.getGroupFlg().booleanValue()) {
							lstParentLogics.add(objLogic);
						}
					}
				}
			}
			// get parent connector
			if (CollectionUtils.isNotEmpty(this.lstSequenceConnectors)) {
				for (SequenceConnector objConnector : lstSequenceConnectors) {
					if (objConnector.getIsParent()) {
						lstParentConnectors.add(objConnector);
					}
				}
			}

		}
		jsonData = DataTypeUtils.toJson(lstParentConnectors);
		businessDesign.setJsonConnector(jsonData);
		jsonData = DataTypeUtils.toJson(lstParentLogics);
		businessDesign.setJsonComponent(jsonData);
		businessDesign.setLstSequenceConnectors(lstParentConnectors);
		businessDesign.setLstSequenceLogics(lstParentLogics);
	}

	public List<ExceptionComponent> getLstExceptionComponent() {
		return lstExceptionComponent;
	}

	public void setLstExceptionComponent(List<ExceptionComponent> lstExceptionComponent) {
		this.lstExceptionComponent = lstExceptionComponent;
	}
}
