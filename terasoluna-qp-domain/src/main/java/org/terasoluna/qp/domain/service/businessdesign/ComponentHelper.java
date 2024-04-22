package org.terasoluna.qp.domain.service.businessdesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.AdvanceComponent;
import org.terasoluna.qp.domain.model.AdvanceInputValue;
import org.terasoluna.qp.domain.model.AdvanceOutputValue;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;
import org.terasoluna.qp.domain.model.BDParameterIndex;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.ColumnFileFormat;
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.CommonInputValue;
import org.terasoluna.qp.domain.model.CommonOutputValue;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DownloadFileComponent;
import org.terasoluna.qp.domain.model.EmailComponent;
import org.terasoluna.qp.domain.model.EmailContent;
import org.terasoluna.qp.domain.model.EmailRecipient;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExceptionDetail;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.ExportAssignValue;
import org.terasoluna.qp.domain.model.ExportFileComponent;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.FileFormat;
import org.terasoluna.qp.domain.model.FileOperationComponent;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.IfConditionDetail;
import org.terasoluna.qp.domain.model.ImportAssignValue;
import org.terasoluna.qp.domain.model.ImportFileComponent;
import org.terasoluna.qp.domain.model.LogComponent;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.MergeFileDetail;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.UtilityComponent;

public class ComponentHelper {
	public static void mappingDetailOfDownload(Boolean isGencode, List<DownloadFileComponent> lstDownloadComponents, List<FormulaDetail> lstFormulaDetails, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstDownloadComponents)) {
			for (DownloadFileComponent objComponent : lstDownloadComponents) {
				if (!isGencode && objComponent.getParameterId() != null && objComponent.getParameterId().length() > 0) {
					objComponent.setParameterId(objComponent.getParameterScope() + objComponent.getParameterId());
					objComponent.setParameterIdAutocomplete(mNameParameter.getOrDefault(objComponent.getParameterId(), ""));
				}

				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();

				if (BusinessDesignConst.DownloadFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getFileNameType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getFileNameFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setFileNameFormulaDetails(lstFormulaDetailTemps);
				}
			}
		}
	}

	public static void mappingDetailOfEmail(Boolean isGencode, List<EmailComponent> lstEmailComponents, List<EmailRecipient> lstEmailRecipients, List<EmailContent> lstEmailContents, List<FormulaDetail> lstFormulaDetails) {
		if (CollectionUtils.isNotEmpty(lstEmailRecipients)) {
			for (EmailRecipient obj : lstEmailRecipients) {
				if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(obj.getRecipientType())) {
					List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(obj.getRecipientFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					obj.setRecipientFormulaDetails(lstFormulaDetailTemps);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstEmailComponents)) {
			for (EmailComponent objComponent : lstEmailComponents) {
				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();

				if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSubjectType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getSubjectFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setSubjectFormulaDetails(lstFormulaDetailTemps);
				}

				List<EmailRecipient> lstEmailRecipientTemps = new ArrayList<EmailRecipient>();
				if (CollectionUtils.isNotEmpty(lstEmailRecipients)) {
					for (EmailRecipient objDetail : lstEmailRecipients) {
						if (objDetail.getEmailComponentId().equals(objComponent.getEmailComponentId())) {
							lstEmailRecipientTemps.add(objDetail);
						}
					}
				}
				objComponent.setEmailRecipients(lstEmailRecipientTemps);

				if (CollectionUtils.isNotEmpty(lstEmailContents)) {
					for (EmailContent objDetail : lstEmailContents) {
						if (objDetail.getEmailComponentId().equals(objComponent.getEmailComponentId())) {
							objComponent.setEmailContent(objDetail);
						}
					}
				}
			}
		}
	}

	public static void mappingDetailOfUtility(Boolean isGencode, List<UtilityComponent> lstUtilityComponents, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstUtilityComponents)) {
			for (UtilityComponent objComponent : lstUtilityComponents) {
				// set parameter
				if (!isGencode && objComponent.getTargetId() != null && objComponent.getTargetId().length() > 0) {
					objComponent.setTargetId(objComponent.getTargetScope() + objComponent.getTargetId());
					objComponent.setTargetIdAutocomplete(mNameParameter.getOrDefault(objComponent.getTargetId(), ""));
				}

				if (!isGencode && objComponent.getParameterId() != null && objComponent.getParameterId().length() > 0) {
					objComponent.setParameterId(objComponent.getParameterScope() + objComponent.getParameterId());
					objComponent.setParameterIdAutocomplete(mNameParameter.getOrDefault(objComponent.getParameterId(), ""));
				}

				if (!isGencode && objComponent.getIndexId() != null && objComponent.getIndexId().length() > 0) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(objComponent.getIndexScope())) {
						objComponent.setIndexIdAutocomplete(objComponent.getIndexId());
					} else {
						if (!isGencode) {
							objComponent.setIndexId(objComponent.getIndexScope() + objComponent.getIndexId());
							objComponent.setIndexIdAutocomplete(mNameParameter.getOrDefault(objComponent.getIndexId(), ""));
						}
					}
				}

				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_UTILITY_CONTENT.equals(index.getTableType()) && objComponent.getUtilityComponentId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objComponent.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objComponent.setLstParameterIndex(lstParameterIndex);

				List<BDParameterIndex> lstIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_UTILITY_INDEX.equals(index.getTableType()) && objComponent.getUtilityComponentId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objComponent.getIndexScope() + index.getParameterId());
							}
							lstIndex.add(index);
						}
					}
				}
				objComponent.setLstIndex(lstIndex);
			}
		}
	}

	public static void mappingDetailOfLog(Boolean isGencode, List<LogComponent> lstLogComponents, List<FormulaDetail> lstFormulaDetails) {
		if (CollectionUtils.isNotEmpty(lstLogComponents)) {
			for (LogComponent objComponent : lstLogComponents) {
				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();

				if (BusinessDesignConst.LogComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getMessageType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getMessageFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setMessageFormulaDetails(lstFormulaDetailTemps);
				}
			}
		}
	}

	public static void mappingDetailOfExportFile(Boolean isGencode, List<ExportFileComponent> lstExportFileComponents, List<ExportAssignValue> lstExportAssignValues, List<FileFormat> lstFileFormats, List<ColumnFileFormat> lstColumnFileFormats, List<FormulaDetail> lstFormulaDetails, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstExportAssignValues)) {
			for (ExportAssignValue objDetail : lstExportAssignValues) {
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				if (CollectionUtils.isNotEmpty(lstColumnFileFormats)) {
					for (ColumnFileFormat objColumnFileFormat : lstColumnFileFormats) {
						if (objDetail.getExportAssignValueId().equals(objColumnFileFormat.getExportAssignValueId())) {
							objDetail.setColumnFileFormat(objColumnFileFormat);
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstExportFileComponents)) {
			for (ExportFileComponent objComponent : lstExportFileComponents) {
				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
				if (BusinessDesignConst.ExportFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getDestinationPathFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setDestinationPathFormulaDetails(lstFormulaDetailTemps);
				}

				List<ExportAssignValue> lstExportAssignValueTemps = new ArrayList<ExportAssignValue>();
				if (CollectionUtils.isNotEmpty(lstExportAssignValues)) {
					for (ExportAssignValue objDetail : lstExportAssignValues) {
						if (objDetail.getExportFileComponentId().equals(objComponent.getExportFileComponentId())) {
							lstExportAssignValueTemps.add(objDetail);
						}
					}
				}
				objComponent.setLstExportAssignValues(lstExportAssignValueTemps);

				if (!isGencode && objComponent.getParameterId() != null && objComponent.getParameterId().length() > 0) {
					objComponent.setParameterId(objComponent.getParameterScope() + objComponent.getParameterId());
					objComponent.setParameterIdAutocomplete(mNameParameter.getOrDefault(objComponent.getParameterId(), ""));
				}

				if (CollectionUtils.isNotEmpty(lstFileFormats)) {
					for (FileFormat objDetail : lstFileFormats) {
						if (objDetail.getFileId().equals(objComponent.getExportFileComponentId()) && BusinessDesignConst.ExportFileComponent.TYPE_EXPORT.equals(objDetail.getFileType())) {
							objComponent.setFileFormat(objDetail);
						}
					}
				}
			}
		}
	}

	public static void mappingDetailOfImportFile(Boolean isGencode, List<ImportFileComponent> lstImportFileComponents, List<ImportAssignValue> lstImportAssignValues, List<FileFormat> lstFileFormats, List<FormulaDetail> lstFormulaDetails, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstImportAssignValues)) {
			for (ImportAssignValue objDetail : lstImportAssignValues) {
				if (!isGencode && objDetail.getTargetId() != null && objDetail.getTargetId().length() > 0) {
					objDetail.setTargetId(objDetail.getTargetScope() + objDetail.getTargetId());
					objDetail.setTargetIdAutocomplete(mNameParameter.getOrDefault(objDetail.getTargetId(), ""));
				}
			}
		}

		if (CollectionUtils.isNotEmpty(lstImportFileComponents)) {
			for (ImportFileComponent objComponent : lstImportFileComponents) {
				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
				if (BusinessDesignConst.ImportFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getSourcePathFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setSourcePathFormulaDetails(lstFormulaDetailTemps);
				}

				List<ImportAssignValue> lstImportAssignValueTemps = new ArrayList<ImportAssignValue>();
				if (CollectionUtils.isNotEmpty(lstImportAssignValues)) {
					for (ImportAssignValue objDetail : lstImportAssignValues) {
						if (objDetail.getImportFileComponentId().equals(objComponent.getImportFileComponentId())) {
							lstImportAssignValueTemps.add(objDetail);
						}
					}
				}
				objComponent.setLstImportAssignValues(lstImportAssignValueTemps);

				if (!isGencode && objComponent.getTargetId() != null && objComponent.getTargetId().length() > 0) {
					objComponent.setTargetId(objComponent.getTargetScope() + objComponent.getTargetId());
					objComponent.setTargetIdAutocomplete(mNameParameter.getOrDefault(objComponent.getTargetId(), ""));
				}

				if (CollectionUtils.isNotEmpty(lstFileFormats)) {
					for (FileFormat objDetail : lstFileFormats) {
						if (objDetail.getFileId().equals(objComponent.getImportFileComponentId()) && BusinessDesignConst.ImportFileComponent.TYPE_IMPORT.equals(objDetail.getFileType())) {
							objComponent.setFileFormat(objDetail);
						}
					}
				}
			}
		}
	}

	public static void mappingDetailOfFileOperation(Boolean isGencode, List<FileOperationComponent> lstFileOperationComponents, List<MergeFileDetail> lstMergeFileDetails, List<FormulaDetail> lstFormulaDetails) {
		if (CollectionUtils.isNotEmpty(lstMergeFileDetails)) {
			for (MergeFileDetail objMerge : lstMergeFileDetails) {
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objMerge.getSourcePathType())) {
					List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objMerge.getSourcePathFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objMerge.setSourcePathFormulaDetails(lstFormulaDetailTemps);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstFileOperationComponents)) {
			for (FileOperationComponent objComponent : lstFileOperationComponents) {
				List<FormulaDetail> lstFormulaDetailTemps = new ArrayList<FormulaDetail>();

				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getSourcePathFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setSourcePathFormulaDetails(lstFormulaDetailTemps);
				}

				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getDestinationPathFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setDestinationPathFormulaDetails(lstFormulaDetailTemps);
				}

				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getNewFilenameType())) {
					lstFormulaDetailTemps = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(objComponent.getNewFilenameFormulaId())) {
								lstFormulaDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setNewFilenameFormulaDetails(lstFormulaDetailTemps);
				}

				List<MergeFileDetail> lstMergeFileDetailTemps = new ArrayList<MergeFileDetail>();
				if (BusinessDesignConst.FileOperationComponent.TYPE_MERGE.equals(objComponent.getType())) {
					if (CollectionUtils.isNotEmpty(lstMergeFileDetails)) {
						for (MergeFileDetail objDetail : lstMergeFileDetails) {
							if (objDetail.getFileOperationComponentId().equals(objComponent.getFileOperationComponentId())) {
								lstMergeFileDetailTemps.add(objDetail);
							}
						}
					}
					objComponent.setLstMergeFileDetails(lstMergeFileDetailTemps);
				}
			}
		}
	}

	public static void mappingDetailOfAdvance(Boolean isGencode, List<AdvanceComponent> lstAdvanceComponents, List<AdvanceInputValue> lstAdvanceInputValues, List<AdvanceOutputValue> lstAdvanceOutputValues, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstAdvanceInputValues)) {
			for (AdvanceInputValue objInput : lstAdvanceInputValues) {
				// set parameter
				if (!isGencode && objInput.getParameterId() != null && objInput.getParameterId().length() > 0) {
					objInput.setParameterId(objInput.getParameterScope() + objInput.getParameterId());
					objInput.setParameterIdAutocomplete(mNameParameter.getOrDefault(objInput.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_ADVANCE_INPUT_VALUE.equals(index.getTableType()) && index.getTableId().equals(objInput.getAdvanceInputValueId())) {
							if (!isGencode) {
								index.setParameterId(objInput.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objInput.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstAdvanceOutputValues)) {
			for (AdvanceOutputValue objOutput : lstAdvanceOutputValues) {
				// set parameter
				if (!isGencode && objOutput.getTargetId() != null && objOutput.getTargetId().length() > 0) {
					objOutput.setTargetId(objOutput.getTargetScope() + objOutput.getTargetId());
					objOutput.setTargetIdAutocomplete(mNameParameter.getOrDefault(objOutput.getTargetId(), ""));
				}
				List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_ADVANCE_OUTPUT_VALUE.equals(index.getTableType()) && index.getTableId().equals(objOutput.getAdvanceOutputValueId())) {
							if (!isGencode) {
								index.setParameterId(objOutput.getTargetScope() + index.getParameterId());
							}
							lstTargetIndex.add(index);
						}
					}
				}
				objOutput.setLstTargetIndex(lstTargetIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstAdvanceComponents)) {
			for (AdvanceComponent objComponent : lstAdvanceComponents) {
				List<AdvanceInputValue> lstInputValueTemp = new ArrayList<AdvanceInputValue>();
				List<AdvanceOutputValue> lstOutputValueTemp = new ArrayList<AdvanceOutputValue>();
				if (CollectionUtils.isNotEmpty(lstAdvanceInputValues)) {
					for (AdvanceInputValue objInput : lstAdvanceInputValues) {
						// set parameter
						if (objInput.getAdvanceComponentId().equals(objComponent.getAdvanceComponentId())) {
							lstInputValueTemp.add(objInput);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(lstAdvanceOutputValues)) {
					for (AdvanceOutputValue objOutput : lstAdvanceOutputValues) {
						// set parameter
						if (objOutput.getAdvanceComponentId().equals(objComponent.getAdvanceComponentId())) {
							lstOutputValueTemp.add(objOutput);
						}
					}
				}
				objComponent.setParameterInputBeans(lstInputValueTemp);
				objComponent.setParameterOutputBeans(lstOutputValueTemp);
			}
		}
	}

	public static void mappingDetailOfExecution(Boolean isGencode, List<ExecutionComponent> lstExecutionComponents, List<ExecutionInputValue> lstExecutionInputValues, List<ExecutionOutputValue> lstExecutionOutputValues, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstExecutionInputValues)) {
			for (ExecutionInputValue objDetail : lstExecutionInputValues) {
				// set parameter
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_EXECUTION_INPUT_VALUE.equals(index.getTableType()) && index.getTableId().equals(objDetail.getExecutionInputValueId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstExecutionOutputValues)) {
			for (ExecutionOutputValue objDetail : lstExecutionOutputValues) {
				// set parameter
				if (!isGencode && objDetail.getTargetId() != null && objDetail.getTargetId().length() > 0) {
					objDetail.setTargetId(objDetail.getTargetScope() + objDetail.getTargetId());
					objDetail.setTargetIdAutocomplete(mNameParameter.getOrDefault(objDetail.getTargetId(), ""));
				}
				List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_EXECUTION_OUTPUT_VALUE.equals(index.getTableType()) && index.getTableId().equals(objDetail.getExecutionOutputValueId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getTargetScope() + index.getParameterId());
							}
							lstTargetIndex.add(index);
						}
					}
				}
				objDetail.setLstTargetIndex(lstTargetIndex);
			}
		}
		// map detail of execution
		if (CollectionUtils.isNotEmpty(lstExecutionComponents)) {
			for (ExecutionComponent objComponent : lstExecutionComponents) {
				List<ExecutionInputValue> lstInputValueTemp = new ArrayList<ExecutionInputValue>();
				List<ExecutionOutputValue> lstOutputValueTemp = new ArrayList<ExecutionOutputValue>();
				if (CollectionUtils.isNotEmpty(lstExecutionInputValues)) {
					for (ExecutionInputValue objDetail : lstExecutionInputValues) {
						// impact status
						if (objDetail.getExecutionComponentId() == null) {
							if (objComponent.getSqlDesignId() != null && objComponent.getSqlDesignId().equals(objDetail.getSqlDesignIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstInputValueTemp.add(objDetail);
							}
						} else if (objDetail.getExecutionComponentId().equals(objComponent.getExecutionComponentId())) {
							if (objDetail.getSqlDesignInputIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getSqlDesignInputCode().equals(objDetail.getSqlDesignInputCodeRefer()) && objDetail.getSqlDesignInputName().equals(objDetail.getSqlDesignInputNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer()) && objDetail.getArrayFlg().equals(objDetail.getArrayFlg())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstInputValueTemp.add(objDetail);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(lstExecutionOutputValues)) {
					for (ExecutionOutputValue objDetail : lstExecutionOutputValues) {
						// impact status
						if (objDetail.getExecutionComponentId() == null) {
							if (objComponent.getSqlDesignId() != null && objComponent.getSqlDesignId().equals(objDetail.getSqlDesignIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstOutputValueTemp.add(objDetail);
							}
						} else if (objDetail.getExecutionComponentId().equals(objComponent.getExecutionComponentId())) {
							if (objDetail.getSqlDesignOutputIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (FunctionCommon.equals(objDetail.getSqlDesignOutputCode(), objDetail.getSqlDesignOutputCodeRefer()) &&
									FunctionCommon.equals(objDetail.getSqlDesignOutputName(), objDetail.getSqlDesignOutputNameRefer()) &&
									FunctionCommon.equals(objDetail.getArrayFlg(), objDetail.getArrayFlg())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstOutputValueTemp.add(objDetail);
						}
					}
				}
				objComponent.setParameterInputBeans(lstInputValueTemp);
				objComponent.setParameterOutputBeans(lstOutputValueTemp);
			}
		}
	}

	public static void mappingDetailOfBusinessCheck(Boolean isGencode, List<BusinessCheckComponent> lstBusinessCheckComponents, List<BusinessCheckDetail> lstBusinessCheckDetails, List<BusinessDetailContent> lstBusinessDetailContents, List<MessageParameter> lstMessageParameters, List<FormulaDetail> lstFormulaDetails, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstBusinessDetailContents)) {
			for (BusinessDetailContent objContent : lstBusinessDetailContents) {
				if (!isGencode && StringUtils.isNotBlank(objContent.getParameterId())) {
					objContent.setParameterId(objContent.getParameterScope() + objContent.getParameterId());
					objContent.setParameterIdAutocomplete(mNameParameter.getOrDefault(objContent.getParameterId(), ""));
				}

				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_BUSINESS_CHECK_CONTENT.equals(index.getTableType()) && index.getTableId().equals(objContent.getBusinessDetailContentId())) {
							if (!isGencode) {
								index.setParameterId(objContent.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objContent.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstBusinessCheckDetails)) {
			for (BusinessCheckDetail objDetail : lstBusinessCheckDetails) {
				List<MessageParameter> lstMessageParameterTemps = new ArrayList<MessageParameter>();
				if (CollectionUtils.isNotEmpty(lstMessageParameters)) {
					for (MessageParameter objMessage : lstMessageParameters) {
						if (BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK.equals(objMessage.getTargetType()) && objMessage.getTargetId().equals(objDetail.getBusinessCheckDetailId())) {
							lstMessageParameterTemps.add(objMessage);
						}
					}
				}
				objDetail.setParameters(lstMessageParameterTemps);

				// map formula for case : formula
				if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_FORMULA.equals(objDetail.getBusinessCheckType())) {
					List<FormulaDetail> lstFormulaDetailOfIf = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail objFormulaDetail : lstFormulaDetails) {
							if (objFormulaDetail.getFormulaDefinitionId().equals(objDetail.getFormulaDefinitionId())) {
								lstFormulaDetailOfIf.add(objFormulaDetail);
							}
						}
					}
					objDetail.setFormulaDefinitionDetails(lstFormulaDetailOfIf);
				} else if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE.equals(objDetail.getBusinessCheckType()) || BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED.equals(objDetail.getBusinessCheckType())) {
					List<BusinessDetailContent> lstBDetailContentTemps = new ArrayList<BusinessDetailContent>();
					if (CollectionUtils.isNotEmpty(lstBusinessDetailContents)) {
						for (BusinessDetailContent objContent : lstBusinessDetailContents) {
							if (objContent.getBusinessCheckDetailId().equals(objDetail.getBusinessCheckDetailId())) {
								lstBDetailContentTemps.add(objContent);
							}
						}
					}
					objDetail.setContents(lstBDetailContentTemps);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstBusinessCheckComponents)) {
			for (BusinessCheckComponent objComponent : lstBusinessCheckComponents) {
				List<BusinessCheckDetail> lstBusinessCheckDetailTemps = new ArrayList<BusinessCheckDetail>();
				if (CollectionUtils.isNotEmpty(lstBusinessCheckDetails)) {
					for (BusinessCheckDetail objDetail : lstBusinessCheckDetails) {
						if (objDetail.getBusinessCheckComponentId().equals(objComponent.getBusinessCheckComponentId())) {
							lstBusinessCheckDetailTemps.add(objDetail);
						}
					}
				}
				objComponent.setBusinessCheckDetails(lstBusinessCheckDetailTemps);
			}
		}
	}

	public static void mappingDetailOfAssign(Boolean isGencode, List<AssignComponent> lstAssignComponent, List<AssignDetail> lstAssignDetails, List<FormulaDetail> lstFormulaDetails, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstAssignDetails)) {
			for (AssignDetail objDetail : lstAssignDetails) {
				if (!isGencode && objDetail.getTargetId() != null && objDetail.getTargetId().length() > 0) {
					objDetail.setTargetId(objDetail.getTargetScope() + objDetail.getTargetId());
					objDetail.setTargetIdAutocomplete(mNameParameter.getOrDefault(objDetail.getTargetId(), ""));
				}

				if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_PARAMETER.equals(objDetail.getAssignType())) {
					if (objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
						if (!isGencode) {
							objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
							objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
						}
						objDetail.setFormulaDefinitionContent(null);
						objDetail.setFormulaDefinitionId(null);
					}
				} else if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA.equals(objDetail.getAssignType())) {
					List<FormulaDetail> lstFormulaDetailsOfAssign = new ArrayList<FormulaDetail>();
					if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
						for (FormulaDetail formulaDetail : lstFormulaDetails) {
							if (objDetail.getFormulaDefinitionId().equals(formulaDetail.getFormulaDefinitionId())) {
								lstFormulaDetailsOfAssign.add(formulaDetail);
							}
						}
					}
					objDetail.setFormulaDefinitionDetails(lstFormulaDetailsOfAssign);
				}

				List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (objDetail.getAssignDetailId().equals(index.getTableId())) {
							if (BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_TARGET.equals(index.getTableType())) {
								if (!isGencode) {
									index.setParameterId(objDetail.getTargetScope() + index.getParameterId());
								}
								lstTargetIndex.add(index);
							}
							if (BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_PARAMETER.equals(index.getTableType())) {
								if (!isGencode) {
									index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
								}
								lstParameterIndex.add(index);
							}
						}
					}
				}
				objDetail.setLstTargetIndex(lstTargetIndex);
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstAssignComponent)) {
			for (AssignComponent objComponent : lstAssignComponent) {
				List<AssignDetail> lstAssignDetailTemp = new ArrayList<AssignDetail>();
				for (AssignDetail objDetail : lstAssignDetails) {
					if (objDetail.getAssignComponentId().equals(objComponent.getAssignComponentId())) {
						lstAssignDetailTemp.add(objDetail);
					}
				}
				objComponent.setDetails(lstAssignDetailTemp);
			}
		}
	}

	public static void mappingDetailOfDecision(Boolean isGencode, List<DecisionComponent> lstDecisionComponent, List<DecisionInputValue> lstDecisionInputValues, List<DecisionOutputValue> lstDecisionOutputValues, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstDecisionInputValues)) {
			for (DecisionInputValue objDetail : lstDecisionInputValues) {
				// set parameter
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				for (BDParameterIndex index : lstBdParameterIndexs) {
					if (BusinessDesignConst.ParameterIndex.TABLE_DECISION_INPUT_VALUE.equals(index.getTableType()) && objDetail.getDecisionInputValueId().equals(index.getTableId())) {
						if (!isGencode) {
							index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
						}
						lstParameterIndex.add(index);
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstDecisionOutputValues)) {
			for (DecisionOutputValue objDetail : lstDecisionOutputValues) {
				// set parameter
				if (!isGencode && objDetail.getTargetId() != null && objDetail.getTargetId().length() > 0) {
					objDetail.setTargetId(objDetail.getTargetScope() + objDetail.getTargetId());
					objDetail.setTargetIdAutocomplete(mNameParameter.getOrDefault(objDetail.getTargetId(), ""));
				}
				List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();
				for (BDParameterIndex index : lstBdParameterIndexs) {
					if (BusinessDesignConst.ParameterIndex.TABLE_DECISION_OUTPUT_VALUE.equals(index.getTableType()) && objDetail.getDecisionOutputValueId().equals(index.getTableId())) {
						if (!isGencode) {
							index.setParameterId(objDetail.getTargetScope() + index.getParameterId());
						}
						lstTargetIndex.add(index);
					}
				}
				objDetail.setLstTargetIndex(lstTargetIndex);
			}
		}
		// map detail of decision
		if (CollectionUtils.isNotEmpty(lstDecisionComponent)) {
			for (DecisionComponent objComponent : lstDecisionComponent) {
				List<DecisionInputValue> lstInputValueTemp = new ArrayList<DecisionInputValue>();
				List<DecisionOutputValue> lstOutputValueTemp = new ArrayList<DecisionOutputValue>();
				if (CollectionUtils.isNotEmpty(lstDecisionInputValues)) {
					for (DecisionInputValue objDetail : lstDecisionInputValues) {
						// impact status
						if (objDetail.getDecisionComponentId() == null) {
							if (objComponent.getDecisionTableId() != null && objComponent.getDecisionTableId().equals(objDetail.getDecisionTableIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstInputValueTemp.add(objDetail);
							}
						} else if (objDetail.getDecisionComponentId().equals(objComponent.getDecisionComponentId())) {
							if (objDetail.getDecisionInputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getDecisionInputBeanCode().equals(objDetail.getDecisionInputBeanCodeRefer()) && objDetail.getDecisionInputBeanName().equals(objDetail.getDecisionInputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstInputValueTemp.add(objDetail);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(lstDecisionOutputValues)) {
					for (DecisionOutputValue objDetail : lstDecisionOutputValues) {
						// impact status
						if (objDetail.getDecisionComponentId() == null) {
							if (objComponent.getDecisionTableId() != null && objComponent.getDecisionTableId().equals(objDetail.getDecisionTableIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstOutputValueTemp.add(objDetail);
							}
						} else if (objDetail.getDecisionComponentId().equals(objComponent.getDecisionComponentId())) {
							if (objDetail.getDecisionOutputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getDecisionOutputBeanCode().equals(objDetail.getDecisionOutputBeanCodeRefer()) && objDetail.getDecisionOutputBeanName().equals(objDetail.getDecisionOutputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstOutputValueTemp.add(objDetail);
						}
					}
				}
				objComponent.setParameterInputBeans(lstInputValueTemp);
				objComponent.setParameterOutputBeans(lstOutputValueTemp);
			}
		}
	}

	public static void mappingDetailOfLoop(Boolean isGencode, List<LoopComponent> lstLoopComponent, List<FormulaDetail> lstFormulaDetails,List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstLoopComponent)) {
			for (LoopComponent objComponent : lstLoopComponent) {
				if (!isGencode && objComponent.getParameterId() != null && objComponent.getParameterId().length() > 0) {
					objComponent.setParameterId(objComponent.getParameterScope() + objComponent.getParameterId());
					objComponent.setParameterIdAutocomplete(mNameParameter.getOrDefault(objComponent.getParameterId(), ""));
				}
				List<FormulaDetail> lstFormulaDetailOfLoop = new ArrayList<FormulaDetail>();
				if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
					for (FormulaDetail objDetail : lstFormulaDetails) {
						if (objDetail.getFormulaDefinitionId().equals(objComponent.getFormulaDefinitionId())) {
							lstFormulaDetailOfLoop.add(objDetail);
						}
					}
				}
				objComponent.setFormulaDefinitionDetails(lstFormulaDetailOfLoop);
				
				if (!isGencode && objComponent.getFromValue() != null && objComponent.getFromValue().length() > 0) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(objComponent.getFromScope())) {
						objComponent.setFromValueAutocomplete(objComponent.getFromValue());
					} else {
						if (!isGencode) {
							objComponent.setFromValue(objComponent.getFromScope() + objComponent.getFromValue());
							objComponent.setFromValueAutocomplete(mNameParameter.getOrDefault(objComponent.getFromValue(), ""));
						}
					}
				}
				
				if (!isGencode && objComponent.getToValue() != null && objComponent.getToValue().length() > 0) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(objComponent.getToScope())) {
						objComponent.setToValueAutocomplete(objComponent.getToValue());
					} else {
						if (!isGencode) {
							objComponent.setToValue(objComponent.getToScope() + objComponent.getToValue());
							objComponent.setToValueAutocomplete(mNameParameter.getOrDefault(objComponent.getToValue(), ""));
						}
					}
				}
				
				List<BDParameterIndex> lstFrom = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_LOOP_FROM.equals(index.getTableType()) && objComponent.getLoopComponentId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objComponent.getFromScope() + index.getParameterId());
							}
							lstFrom.add(index);
						}
					}
				}
				objComponent.setLstFromIndex(lstFrom);
				
				List<BDParameterIndex> lstTo = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_LOOP_TO.equals(index.getTableType()) && objComponent.getLoopComponentId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objComponent.getToScope() + index.getParameterId());
							}
							lstTo.add(index);
						}
					}
				}
				objComponent.setLstToIndex(lstTo);

			}
		}
	}

	public static void mappingDetailOfCommon(Boolean isGencode, List<CommonComponent> lstCommonComponent, List<CommonInputValue> lstCommonInputValues, List<CommonOutputValue> lstCommonOutputValues, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstCommonInputValues)) {
			for (CommonInputValue objDetail : lstCommonInputValues) {
				// set parameter
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_COMMON_INPUT_VALUE.equals(index.getTableType()) && objDetail.getCommonInputValueId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstCommonOutputValues)) {
			for (CommonOutputValue objDetail : lstCommonOutputValues) {
				// set parameter
				if (!isGencode && objDetail.getTargetId() != null && objDetail.getTargetId().length() > 0) {
					objDetail.setTargetId(objDetail.getTargetScope() + objDetail.getTargetId());
					objDetail.setTargetIdAutocomplete(mNameParameter.getOrDefault(objDetail.getTargetId(), ""));
				}
				List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_COMMON_OUTPUT_VALUE.equals(index.getTableType()) && objDetail.getCommonOutputValueId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getTargetScope() + index.getParameterId());
							}
							lstTargetIndex.add(index);
						}
					}
				}
				objDetail.setLstTargetIndex(lstTargetIndex);
			}
		}
		if (CollectionUtils.isNotEmpty(lstCommonComponent)) {
			for (CommonComponent objComponent : lstCommonComponent) {
				List<CommonInputValue> lstInputValueTemp = new ArrayList<CommonInputValue>();
				List<CommonOutputValue> lstOutputValueTemp = new ArrayList<CommonOutputValue>();
				if (CollectionUtils.isNotEmpty(lstCommonInputValues)) {
					for (CommonInputValue objDetail : lstCommonInputValues) {
						// impact status
						if (objDetail.getCommonComponentId() == null) {
							if (objComponent.getBusinessLogicId() != null && objComponent.getBusinessLogicId().equals(objDetail.getBusinessLogicIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstInputValueTemp.add(objDetail);
							}
						} else if (objDetail.getCommonComponentId().equals(objComponent.getCommonComponentId())) {
							if (objDetail.getInputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getInputBeanCode().equals(objDetail.getInputBeanCodeRefer()) && objDetail.getInputBeanName().equals(objDetail.getInputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer()) && objDetail.getArrayFlg().equals(objDetail.getArrayFlgRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstInputValueTemp.add(objDetail);
						}
					}
				}
				if (CollectionUtils.isNotEmpty(lstCommonOutputValues)) {
					for (CommonOutputValue objDetail : lstCommonOutputValues) {
						// impact status
						if (objDetail.getCommonComponentId() == null) {
							if (objComponent.getBusinessLogicId() != null && objComponent.getBusinessLogicId().equals(objDetail.getBusinessLogicIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								lstOutputValueTemp.add(objDetail);
							}
						} else if (objDetail.getCommonComponentId().equals(objComponent.getCommonComponentId())) {
							if (objDetail.getOutputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getOutputBeanCode().equals(objDetail.getOutputBeanCodeRefer()) && objDetail.getOutputBeanName().equals(objDetail.getOutputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer()) && objDetail.getArrayFlg().equals(objDetail.getArrayFlgRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstOutputValueTemp.add(objDetail);
						}
					}
				}
				objComponent.setParameterInputBeans(lstInputValueTemp);
				objComponent.setParameterOutputBeans(lstOutputValueTemp);
			}
		}
	}

	public static void mappingDetailOfNavigator(Boolean isGencode, List<NavigatorComponent> lstNavComponent, List<NavigatorDetail> lstNavDetails, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstNavDetails)) {
			for (NavigatorDetail objDetail : lstNavDetails) {
				// set parameter
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (FunctionCommon.equals(BusinessDesignConst.ParameterIndex.TABLE_NAVIGATOR_DETAIL_PARAMETER, index.getTableType()) && FunctionCommon.equals(objDetail.getNavigatorDetailId(), index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		// map detail of navigator
		if (CollectionUtils.isNotEmpty(lstNavComponent)) {
			for (NavigatorComponent objComponent : lstNavComponent) {
				List<NavigatorDetail> lstDetails = new ArrayList<NavigatorDetail>();
				if (CollectionUtils.isNotEmpty(lstNavDetails) && BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC.equals(objComponent.getNavigatorToType())) {
					for (NavigatorDetail objDetail : lstNavDetails) {
						// impact status
						if (objDetail.getNavigatorComponentId() == null) {
							if (objComponent.getNavigatorToId() != null && objComponent.getNavigatorToId().equals(objDetail.getNavigatorToIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								objDetail.setInputBeanId(objDetail.getInputBeanIdRefer());
								objDetail.setInputBeanCode(objDetail.getInputBeanCodeRefer());
								objDetail.setInputBeanName(objDetail.getInputBeanNameRefer());
								objDetail.setArrayFlg(objDetail.getArrayFlgRefer());
								objDetail.setDataType(objDetail.getDataTypeRefer());
								lstDetails.add(objDetail);
							}
						} else if (objDetail.getNavigatorComponentId().equals(objComponent.getNavigatorComponentId())) {
							if (objDetail.getInputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getInputBeanCode().equals(objDetail.getInputBeanCodeRefer()) && objDetail.getInputBeanName().equals(objDetail.getInputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstDetails.add(objDetail);
						}
					}
				}
				objComponent.setParameterInputBeans(lstDetails);
			}
		}
	}

	public static void mappingConditionOfIf(Boolean isGencode, List<IfComponent> lstIfComponents, List<IfConditionDetail> lstIfConditionDetails, List<FormulaDetail> lstFormulaDetails) {
		if (CollectionUtils.isNotEmpty(lstIfConditionDetails)) {
			for (IfConditionDetail objConditionDetail : lstIfConditionDetails) {
				List<FormulaDetail> lstFormulaDetailOfIf = new ArrayList<FormulaDetail>();
				if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
					for (FormulaDetail objDetail : lstFormulaDetails) {
						if (objDetail.getFormulaDefinitionId().equals(objConditionDetail.getFormulaDefinitionId())) {
							lstFormulaDetailOfIf.add(objDetail);
						}
					}
				}
				objConditionDetail.setFormulaDefinitionDetails(lstFormulaDetailOfIf);
			}
		}
		// Map condition to if component.
		if (CollectionUtils.isNotEmpty(lstIfComponents)) {
			for (IfComponent objComponent : lstIfComponents) {
				List<IfConditionDetail> lstConditionDetailTemps = new ArrayList<IfConditionDetail>();
				if (CollectionUtils.isNotEmpty(lstIfConditionDetails)) {
					for (IfConditionDetail objConditionDetail : lstIfConditionDetails) {
						if (objConditionDetail.getIfComponentId().equals(objComponent.getIfComponentId())) {
							lstConditionDetailTemps.add(objConditionDetail);
						}
					}
				}
				objComponent.setIfConditionDetails(lstConditionDetailTemps);
			}
		}
	}

	public static void mappingMessageOfFeedback(Boolean isGencode, List<FeedbackComponent> lstFeedbackComponent, List<MessageParameter> lstMessageParameters) {
		if (CollectionUtils.isNotEmpty(lstFeedbackComponent)) {
			for (FeedbackComponent objFeedback : lstFeedbackComponent) {
				List<MessageParameter> lstMessageParameterTemps = new ArrayList<MessageParameter>();
				if (CollectionUtils.isNotEmpty(lstMessageParameters)) {
					for (MessageParameter objMessage : lstMessageParameters) {
						if (BusinessDesignConst.MessageParameter.TARGET_TYPE_FEEDBACK.equals(objMessage.getTargetType()) && objMessage.getTargetId().equals(objFeedback.getFeedbackComponentId())) {
							lstMessageParameterTemps.add(objMessage);
						}
					}
				}
				objFeedback.setMessageParameter(lstMessageParameterTemps);
			}
		}
	}

	public static void mappingMessageParameter(Boolean isGencode, List<MessageParameter> lstMessageParameters, Map<String, String> mNameParameter, List<BDParameterIndex> lstBdParameterIndexs) {
		if (CollectionUtils.isNotEmpty(lstMessageParameters)) {
			for (MessageParameter objMessage : lstMessageParameters) {
				if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VARIABLE.equals(objMessage.getParameterType())) {
					// set parameter
					if (StringUtils.isNotBlank(objMessage.getParameterCode())) {
						if (!isGencode) {
							objMessage.setParameterCode(objMessage.getParameterScope() + objMessage.getParameterCode());
							if (mNameParameter != null) {
								objMessage.setParameterCodeAutocomplete(mNameParameter.getOrDefault(objMessage.getParameterCode(), ""));
							} else {
								objMessage.setParameterCodeAutocomplete("");
							}
						}
							
						List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
						if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
							for (BDParameterIndex index : lstBdParameterIndexs) {
								if (BusinessDesignConst.ParameterIndex.TABLE_MESSAGE_PARAMETER.equals(index.getTableType()) && objMessage.getMessageParameterId().equals(index.getTableId())) {
									if (!isGencode) {
										index.setParameterId(objMessage.getParameterScope() + index.getParameterId());
									}
									lstParameterIndex.add(index);
								}
							}
						}
						objMessage.setLstParameterIndex(lstParameterIndex);
					}
				}
			}
		}
	}

	public static void mappingFormulaDetailOfFormulaDefinition(Boolean isGencode, List<FormulaDetail> lstFormulaDetails, List<FormulaMethodInput> lstFormulaMethodInputs, List<FormulaMethodOutput> lstFormulaMethodOutputs, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstFormulaMethodInputs)) {
			for (FormulaMethodInput objInput : lstFormulaMethodInputs) {
				if (BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_PARAMETER.equals(objInput.getParameterType())) {
					// set parameter
					if (StringUtils.isNotBlank(objInput.getParameterId())) {
						if (!isGencode) {
							objInput.setParameterId(objInput.getParameterScope() + objInput.getParameterId());
							if (mNameParameter != null) {
								objInput.setParameterIdAutocomplete(mNameParameter.getOrDefault(objInput.getParameterId(), ""));
							} else {
								objInput.setParameterIdAutocomplete("");
							}
						}
							
						List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
						if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
							for (BDParameterIndex index : lstBdParameterIndexs) {
								if (BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL.equals(index.getTableType()) && objInput.getFormulaMethodInputId().equals(index.getTableId())) {
									if (!isGencode) {
										index.setParameterId(objInput.getParameterScope() + index.getParameterId());
									}
									lstParameterIndex.add(index);
								}
							}
						}
						objInput.setLstParameterIndex(lstParameterIndex);
					}
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(lstFormulaDetails)) {
			for (FormulaDetail objDetail : lstFormulaDetails) {
				List<FormulaMethodInput> lstInputTemps = new ArrayList<FormulaMethodInput>();
				if (CollectionUtils.isNotEmpty(lstFormulaMethodInputs)) {
					for (FormulaMethodInput objInput : lstFormulaMethodInputs) {
						if (objInput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())) {
							lstInputTemps.add(objInput);
						}
					}
				}
				objDetail.setFormulaMethodInputs(lstInputTemps);

				List<FormulaMethodOutput> lstOutputTemps = new ArrayList<FormulaMethodOutput>();
				if (CollectionUtils.isNotEmpty(lstFormulaMethodOutputs)) {
					for (FormulaMethodOutput objOutput : lstFormulaMethodOutputs) {
						if (objOutput.getFormulaDetailId().equals(objDetail.getFormulaDetailId())) {
							lstOutputTemps.add(objOutput);
						}
					}
				}
				objDetail.setFormulaMethodOutputs(lstOutputTemps);
				// mapping index
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (objDetail.getFormulaDetailId().equals(index.getTableId())) {
							ComponentHelper.unParseParameterIndexOfFormula(isGencode, index, objDetail.getType().intValue(), lstParameterIndex);
						}
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
				// mapping id of parameter
				if (objDetail.getType() != null) {
					if (!isGencode) {
						Integer scope;
						switch (objDetail.getType().intValue()) {
							case BusinessDesignConst.FormulaBuilder.TYPE_IN_BUSINESSLOGIC:
								scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN;
								objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
								break;
							case BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC:
								scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OBJECT_DEFINITION;
								objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
								break;
							case BusinessDesignConst.FormulaBuilder.TYPE_OU_BUSINESSLOGIC:
								scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OUTPUT_BEAN;
								objDetail.setParameterId(scope.toString() + objDetail.getParameterId());
								break;
							default:
								break;
						}
					}
				}
			}
		}
	}

	public static void mappingDetailOfParameterIndex(Boolean isGencode, List<BDParameterIndex> lstBdParameterIndexs) {
		if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
			for (BDParameterIndex index : lstBdParameterIndexs) {
				// mapping index
				if (index.getParameterIndexType() != null) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(index.getParameterIndexType())) {
						index.setParameterIndexIdAutocomplete(index.getParameterIndexId());
					} else {
						if (!isGencode) {
							index.setParameterIndexId(index.getParameterIndexType() + index.getParameterIndexId());
						}
					}
				}
			}
		}
	}

	private static void unParseParameterIndexOfFormula(Boolean isGencode, BDParameterIndex index, int type, List<BDParameterIndex> lstParameterIndex) {
		if (BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL_PARAMETER.equals(index.getTableType())) {
			if (!isGencode) {
				Integer scope;
				switch (type) {
					case BusinessDesignConst.FormulaBuilder.TYPE_IN_BUSINESSLOGIC:
						scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN;
						index.setParameterId(scope.toString() + index.getParameterId());
						break;
					case BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC:
						scope = BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OBJECT_DEFINITION;
						index.setParameterId(scope.toString() + index.getParameterId());
						break;
					default:
						break;
				}
			}
			lstParameterIndex.add(index);
		}
	}

	public static void mappingDetailOfException(Boolean isGencode, List<ExceptionComponent> lstExceptionComponent, List<ExceptionDetail> lstExceptionDetails, List<BDParameterIndex> lstBdParameterIndexs, Map<String, String> mNameParameter) {
		if (CollectionUtils.isNotEmpty(lstExceptionDetails)) {
			for (ExceptionDetail objDetail : lstExceptionDetails) {
				// set parameter
				if (!isGencode && objDetail.getParameterId() != null && objDetail.getParameterId().length() > 0) {
					objDetail.setParameterId(objDetail.getParameterScope() + objDetail.getParameterId());
					objDetail.setParameterIdAutocomplete(mNameParameter.getOrDefault(objDetail.getParameterId(), ""));
				}
				List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
				if (CollectionUtils.isNotEmpty(lstBdParameterIndexs)) {
					for (BDParameterIndex index : lstBdParameterIndexs) {
						if (BusinessDesignConst.ParameterIndex.TABLE_EXCEPTION_DETAIL_PARAMETER.equals(index.getTableType()) && objDetail.getExceptionDetailId().equals(index.getTableId())) {
							if (!isGencode) {
								index.setParameterId(objDetail.getParameterScope() + index.getParameterId());
							}
							lstParameterIndex.add(index);
						}
					}
				}
				objDetail.setLstParameterIndex(lstParameterIndex);
			}
		}
		// map detail of navigator
		if (CollectionUtils.isNotEmpty(lstExceptionComponent)) {
			for (ExceptionComponent objComponent : lstExceptionComponent) {
				List<ExceptionDetail> lstDetails = new ArrayList<ExceptionDetail>();
				if (CollectionUtils.isNotEmpty(lstExceptionDetails) && BusinessDesignConst.ExceptionComponent.EXCEPTION_TO_TYPE_BLOGIC.equals(objComponent.getExceptionToType())) {
					for (ExceptionDetail objDetail : lstExceptionDetails) {
						// impact status
						if (objDetail.getExceptionComponentId() == null) {
							if (objComponent.getExceptionToId() != null && objComponent.getExceptionToId().equals(objDetail.getExceptionToIdRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.ADDED);
								objDetail.setInputBeanId(objDetail.getInputBeanIdRefer());
								objDetail.setInputBeanCode(objDetail.getInputBeanCodeRefer());
								objDetail.setInputBeanName(objDetail.getInputBeanNameRefer());
								objDetail.setArrayFlg(objDetail.getArrayFlgRefer());
								objDetail.setDataType(objDetail.getDataTypeRefer());
								lstDetails.add(objDetail);
							}
						} else if (objDetail.getExceptionComponentId().equals(objComponent.getExceptionComponentId())) {
							if (objDetail.getInputBeanIdRefer() == null) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.DELETED);
							} else if (objDetail.getInputBeanCode().equals(objDetail.getInputBeanCodeRefer()) && objDetail.getInputBeanName().equals(objDetail.getInputBeanNameRefer()) && objDetail.getDataType().equals(objDetail.getDataTypeRefer())) {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.NONE);
							} else {
								objDetail.setImpactStatus(BusinessDesignConst.ImpactStatus.MODIFIED);
							}
							lstDetails.add(objDetail);
						}
					}
				}
				objComponent.setParameterInputBeans(lstDetails);
			}
		}
	}
}
