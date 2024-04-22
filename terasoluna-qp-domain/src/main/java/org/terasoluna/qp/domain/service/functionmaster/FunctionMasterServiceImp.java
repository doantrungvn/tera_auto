package org.terasoluna.qp.domain.service.functionmaster;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonObjectAttribute;
import org.terasoluna.qp.domain.model.CommonObjectDefinition;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.UploadFile;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.functioninput.FunctionInputRepository;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterCriteria;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.functionoutput.FunctionOutputRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.uploadfile.UploadFileRepository;
import org.terasoluna.qp.domain.service.commonobjectdefinition.CommonObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.externalobjectdefinition.ExternalObjectDefinitionSharedService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class FunctionMasterServiceImp implements FunctionMasterService {

	@Inject
	FunctionMasterRepository functionMasterRepository;

	@Inject
	FunctionInputRepository functionInputRepository;

	@Inject
	FunctionOutputRepository functionOutputRepository;

	@Inject
	UploadFileRepository uploadFileRepository;

	@Inject
	ProblemListRepository problemListRepository;

	@Inject
	ProjectService projectService;

	@Inject
	CommonObjectDefinitionSharedService commonObjectDefinitionSharedService;

	@Inject
	ExternalObjectDefinitionSharedService externalObjectDefinitionSharedService;

	@Inject
	BusinessDesignRepository businessDesignRepository;

	@Inject
	DecisionTableRepository decisionTableRepository;

	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;

	@Inject
	ImpactChangeRepository impactChangeRepository;

	@Inject
	@Named(value = "CL_BD_DATATYPE_NOT_ENTITY")
	SimpleMapCodeList simpleMapCodeList;

	private static final String INPUT_OBJECT = "in";

	private static final String OUTPUT_OBJECT = "ou";

	private Integer itemSeqNo = 0;

	/**
	 * Finds all function master information with search criteria
	 * 
	 * @param criteria
	 *            FunctionMasterCriteria
	 * @return List of all function master
	 */
	@Override
	public Page<FunctionMaster> searchFunctionMaster(FunctionMasterCriteria criteria, Pageable pageable) {
		long totalCount = functionMasterRepository.countBySearchCriteria(criteria);

		List<FunctionMaster> functionMaster;
		if (0 < totalCount) {
			functionMaster = functionMasterRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			functionMaster = Collections.emptyList();
		}
		Page<FunctionMaster> page = new PageImpl<FunctionMaster>(functionMaster, pageable, totalCount);
		return page;
	}

	/**
	 * Register function master information
	 * 
	 * @param functionMaster
	 *            FunctionMaster information
	 * @throws IOException
	 */
	@Override
	public void registerFunctionMaster(FunctionMaster functionMaster, CommonModel common) {
		// validate project
		projectService.validateProject(common);

		Long totalCount = functionMasterRepository.countNameCodeExisted(functionMaster);
		ResultMessages resultMessages = ResultMessages.error();

		// validate duplicate name and code
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			Timestamp currentTime = FunctionCommon.getCurrentTime();
			// register upload file if have choose file
			Long uploadFileId = null;
			if (functionMaster.getFile() != null && functionMaster.getFile().length > 0) {
				UploadFile uploadFile = new UploadFile();
				uploadFile.setFileName(functionMaster.getFileName());
				uploadFile.setContent(functionMaster.getFile());
				uploadFile.setCreatedDate(currentTime);
				uploadFile.setUpdatedDate(currentTime);
				if (uploadFileRepository.register(uploadFile) <= 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
				}
				uploadFileId = uploadFile.getUploadFileId();
			}

			// register function master
			functionMaster.setFunctionMasterType(DbDomainConst.FunctionMasterType.CUSTOMIZE);
			functionMaster.setUploadFileId(uploadFileId);
			functionMaster.setProjectId(common.getWorkingProjectId());
			functionMaster.setCreatedDate(currentTime);
			functionMaster.setUpdatedDate(currentTime);
			if (functionMasterRepository.register(functionMaster) <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
			}
			Long functionMasterId = functionMaster.getFunctionMasterId();

			// register function method
			List<FunctionMethod> functionMethods = functionMaster.getFunctionMethod();
			if (FunctionCommon.isNotEmpty(functionMethods)) {
				this.registerFunctionMethod(functionMasterId, functionMethods);
			}
		}
	}

	/**
	 * find function master
	 * 
	 * @param functionMasterId
	 * @return FunctionMaster
	 */
	@Override
	public FunctionMaster findOneFuntionMasterById(Long functionMasterId) {
		FunctionMaster functionMaster = functionMasterRepository.findOneFuntionMasterById(functionMasterId);
		if (functionMaster == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
		}
		// get function input
		List<FunctionMethodInput> lstInputs = functionInputRepository.getFunctionInput(functionMasterId);
		for (FunctionMethodInput ip : lstInputs) {
			if (ip.getArrayFlg() == DbDomainConst.FunctionMasterArrayFlag.IS_ARRAY) {
				ip.setArrayFlgDisplay(true);
			} else {
				ip.setArrayFlgDisplay(false);
			}
		}
		// get function output
		List<FunctionMethodOutput> lstOutputs = functionOutputRepository.getFunctionOutput(functionMasterId);
		for (FunctionMethodOutput output : lstOutputs) {
			if (output.getArrayFlg() == DbDomainConst.FunctionMasterArrayFlag.IS_ARRAY) {
				output.setArrayFlgDisplay(true);
			} else {
				output.setArrayFlgDisplay(false);
			}
		}
		functionMaster.setFunctionInputForm(lstInputs);
		functionMaster.setLstFunctionOutput(lstOutputs);
		return functionMaster;
	}

	/**
	 * process modify function master
	 * 
	 * @param functionMaster
	 */
	@Override
	public void modifyFunctionMaster(FunctionMaster functionMaster, CommonModel common) {
		ResultMessages resultMessages = ResultMessages.error();
		// validate existence
		FunctionMaster oldFunctionMaster = this.loadFunctionMaster(functionMaster.getFunctionMasterId());
		if (oldFunctionMaster == null) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005));
		}
		// validate project
		common.setProjectId(oldFunctionMaster.getProjectId());
		projectService.validateProject(common);

		// validate duplicate name or code
		Long totalCount = functionMasterRepository.countNameCodeExisted(functionMaster);
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006));
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			Timestamp currentTime = FunctionCommon.getCurrentTime();
			Long uploadFileId = functionMaster.getUploadFileId();
			// if have change file in form
			if (functionMaster.getFlagChangeFile() != null && functionMaster.getFlagChangeFile()) {
				if (functionMaster.getFile() != null && functionMaster.getFile().length > 0) {
					// First : modify/insert upload file
					if (uploadFileId != null) {
						// modify upload file
						UploadFile uploadFile = uploadFileRepository.findOne(uploadFileId);
						if (uploadFile == null) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
						}
						uploadFile.setFileName(functionMaster.getFileName());
						uploadFile.setContent(functionMaster.getFile());
						uploadFile.setUpdatedBy(common.getUpdatedBy());
						uploadFile.setSysDatetime(currentTime);
						if (uploadFileRepository.modify(uploadFile) <= 0) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
						}
						uploadFileId = uploadFile.getUploadFileId();
					} else {
						// insert upload file
						UploadFile uploadFile = new UploadFile();
						uploadFile.setFileName(functionMaster.getFileName());
						uploadFile.setContent(functionMaster.getFile());
						uploadFile.setCreatedBy(common.getCreatedBy());
						uploadFile.setCreatedDate(currentTime);
						uploadFile.setUpdatedBy(common.getUpdatedBy());
						uploadFile.setUpdatedDate(currentTime);
						if (uploadFileRepository.register(uploadFile) <= 0) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
						}
						uploadFileId = uploadFile.getUploadFileId();
					}
					// After : modify function master
					functionMaster.setUploadFileId(uploadFileId);
					functionMaster.setUpdatedBy(common.getUpdatedBy());
					functionMaster.setSysDatetime(currentTime);
					if (functionMasterRepository.modify(functionMaster) <= 0) {
						throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
					}
				} else {
					// First : update function master because constraint
					functionMaster.setUploadFileId(null);
					functionMaster.setUpdatedBy(common.getUpdatedBy());
					functionMaster.setSysDatetime(currentTime);
					if (functionMasterRepository.modify(functionMaster) <= 0) {
						throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
					}
					// After : delete upload file if have data in database
					if (uploadFileId != null) {
						UploadFile uploadFile = uploadFileRepository.findOne(uploadFileId);
						if (uploadFile == null) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
						}
						if (uploadFileRepository.delete(uploadFileId) <= 0) {
							throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)));
						}
					}
				}
			} else {
				// only process modify function master
				functionMaster.setUpdatedBy(common.getUpdatedBy());
				functionMaster.setSysDatetime(currentTime);
				if (functionMasterRepository.modify(functionMaster) <= 0) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
				}
			}

			// Update function method
			FunctionMaster functionMasterBeforeUpdate = this.loadFunctionMaster(functionMaster.getFunctionMasterId());
			List<FunctionMethod> functionMethods = functionMaster.getFunctionMethod();
			List<FunctionMethod> functionMethodInsert = new ArrayList<FunctionMethod>();
			List<FunctionMethod> functionMethodUpdate = new ArrayList<FunctionMethod>();
			List<FunctionMethod> functionMethodsDelete = new ArrayList<FunctionMethod>();

			if (functionMethods != null && functionMethods.size() > 0) {

				// Find method add new.
				for (FunctionMethod functionMethod : functionMaster.getFunctionMethod()) {
					int index = functionMaster.getFunctionMethod().indexOf(functionMethod);
					functionMethod.setItemSeqNo(index);
					if (functionMethod.getFunctionMethodId() == null)
						functionMethodInsert.add(functionMethod);
				}

				boolean check = false;
				for (FunctionMethod before : functionMasterBeforeUpdate.getFunctionMethod()) {
					check = false;
					for (FunctionMethod after : functionMethods) {
						if (before.getFunctionMethodId().equals(after.getFunctionMethodId())) {
							functionMethodUpdate.add(after);
							check = true;
							break;
						}
					}
					if (!check) {
						functionMethodsDelete.add(before);
					}
				}
			} else {
				functionMethodsDelete.addAll(functionMasterBeforeUpdate.getFunctionMethod());
			}

			// insert batch job
			this.modifyAffected(functionMaster, functionMethodsDelete, functionMethodUpdate, common);

			// Insert new Function Method
			if (functionMethodInsert.size() > 0) {
				this.registerFunctionMethod(functionMaster.getFunctionMasterId(), functionMethodInsert);
			}

			// Delete Function method
			if (functionMethodsDelete.size() > 0) {
				functionMasterRepository.multiDeleteFunctionMethod(functionMethodsDelete);
			}

			// Update Function method
			if (functionMethodUpdate.size() > 0) {
				this.modifyFunctionMethod(functionMethodUpdate, functionMasterBeforeUpdate);
			}

		}
	}

	/**
	 * @param functionMethodUpdate
	 * @param functionMasterBeforeUpdate
	 */
	private void modifyFunctionMethod(List<FunctionMethod> functionMethodUpdate, FunctionMaster functionMasterBeforeUpdate) {

		functionMasterRepository.multiUpdateFunctionMethod(functionMethodUpdate);

		List<FunctionMethodInput> functionMethodInputInsert = new ArrayList<FunctionMethodInput>();
		List<FunctionMethodInput> functionMethodInputUpdate = new ArrayList<FunctionMethodInput>();
		List<FunctionMethodInput> functionMethodInputDelete = new ArrayList<FunctionMethodInput>();

		List<FunctionMethodOutput> functionMethodOutputInsert = new ArrayList<FunctionMethodOutput>();
		List<FunctionMethodOutput> functionMethodOutputUpdate = new ArrayList<FunctionMethodOutput>();
		List<FunctionMethodOutput> functionMethodOutputDelete = new ArrayList<FunctionMethodOutput>();

		for (FunctionMethod methodBefore : functionMasterBeforeUpdate.getFunctionMethod()) {

			for (FunctionMethod functionMethod : functionMethodUpdate) {
				if (functionMethod.getFunctionMethodId().equals(methodBefore.getFunctionMethodId())) {

					// Calculator FunctionMethodInput
					List<FunctionMethodInput> inputLogicBefore = methodBefore.getFunctionMethodInput();
					List<FunctionMethodInput> inputLogicAfter = functionMethod.getFunctionMethodInput();
					// Find input logic add new.
					for (FunctionMethodInput input : inputLogicAfter) {
						int index = inputLogicAfter.indexOf(input);
						input.setItemSeqNo(index);
						if (input.getMethodInputId() != null && input.getMethodInputId().contains(INPUT_OBJECT)) {
							input.setFunctionMethodId(functionMethod.getFunctionMethodId());
							functionMethodInputInsert.add(input);
						}
					}

					boolean check = false;
					for (FunctionMethodInput inputBefore : inputLogicBefore) {
						check = false;
						for (FunctionMethodInput inputAfter : inputLogicAfter) {
							if (DataTypeUtils.equals(inputBefore.getMethodInputId(), inputAfter.getMethodInputId())) {
								functionMethodInputUpdate.add(inputAfter);
								check = true;
								break;
							}
						}
						if (!check) {
							functionMethodInputDelete.add(inputBefore);
						}
					}

					// Calculator FunctionMethodOutput
					List<FunctionMethodOutput> outputLogicBefore = methodBefore.getFunctionMethodOutput();
					List<FunctionMethodOutput> outputLogicAfter = functionMethod.getFunctionMethodOutput();
					// Find input logic add new.
					for (FunctionMethodOutput output : outputLogicAfter) {
						int index = outputLogicAfter.indexOf(output);
						output.setItemSeqNo(index);
						if (output.getMethodOutputId() != null && output.getMethodOutputId().contains(OUTPUT_OBJECT)) {
							output.setFunctionMethodId(functionMethod.getFunctionMethodId());
							functionMethodOutputInsert.add(output);
						}
					}

					check = false;
					for (FunctionMethodOutput outputBefore : outputLogicBefore) {
						check = false;
						for (FunctionMethodOutput outputAfter : outputLogicAfter) {
							if (DataTypeUtils.equals(outputBefore.getMethodOutputId(), outputAfter.getMethodOutputId())) {
								functionMethodOutputUpdate.add(outputAfter);
								check = true;
								break;
							}
						}
						if (!check) {
							functionMethodOutputDelete.add(outputBefore);
						}
					}
				}
			}
		}

		// Insert Logic
		Map<String, Long> mapKeyInput = new HashMap<String, Long>();
		Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
		Long startSequenceMethodInput = 0L;
		Long startSequenceMethodOutput = 0L;
		Long sequenceFunctionMethodInput = functionMasterRepository.getSequencesFunctionMethodInput(functionMethodInputInsert.size() - 1);
		Long sequenceFunctionMethodOutput = functionMasterRepository.getSequencesFunctionMethodOutput(functionMethodOutputInsert.size() - 1);
		startSequenceMethodInput = sequenceFunctionMethodInput - functionMethodInputInsert.size() - 1;
		startSequenceMethodOutput = sequenceFunctionMethodOutput - functionMethodOutputInsert.size() - 1;

		if (functionMethodInputInsert.size() > 0) {
			for (FunctionMethodInput functionMethodInput : functionMethodInputInsert) {
				mapKeyInput.put(functionMethodInput.getMethodInputId(), startSequenceMethodInput);

				functionMethodInput.setMethodInputId(startSequenceMethodInput.toString());

				startSequenceMethodInput++;
				if ("".equals(functionMethodInput.getParentFunctionMethodInputId())) {
					functionMethodInput.setParentFunctionMethodInputId(null);
				}

				// map key of parent
				if (mapKeyInput.containsKey(functionMethodInput.getParentFunctionMethodInputId())) {
					functionMethodInput.setParentFunctionMethodInputId(mapKeyInput.get(functionMethodInput.getParentFunctionMethodInputId()).toString());
				}
			}

			functionMasterRepository.multiCreateFunctionInput(functionMethodInputInsert);
		}
		if (functionMethodOutputInsert.size() > 0) {
			for (FunctionMethodOutput functionMethodOutput : functionMethodOutputInsert) {
				mapKeyOutput.put(functionMethodOutput.getMethodOutputId(), startSequenceMethodOutput);

				functionMethodOutput.setMethodOutputId(startSequenceMethodOutput.toString());

				startSequenceMethodOutput++;
				if ("".equals(functionMethodOutput.getParentFunctionMethodOutputId())) {
					functionMethodOutput.setParentFunctionMethodOutputId(null);
				}

				// map key of parent
				if (mapKeyOutput.containsKey(functionMethodOutput.getParentFunctionMethodOutputId())) {
					functionMethodOutput.setParentFunctionMethodOutputId(mapKeyOutput.get(functionMethodOutput.getParentFunctionMethodOutputId()).toString());
				}
			}

			functionMasterRepository.multiCreateFunctionOutput(functionMethodOutputInsert);
		}

		// Delete Logic
		if (functionMethodInputDelete.size() > 0) {
			functionMasterRepository.multiDeleteMethodInput(functionMethodInputDelete);
		}
		if (functionMethodOutputDelete.size() > 0) {
			functionMasterRepository.multiDeleteMethodOutput(functionMethodOutputDelete);
		}

		// Update Logic
		if (functionMethodInputUpdate.size() > 0) {
			functionMasterRepository.multiUpdateMethodInput(functionMethodInputUpdate);
		}
		if (functionMethodOutputUpdate.size() > 0) {
			functionMasterRepository.multiUpdateMethodOutput(functionMethodOutputUpdate);
		}
	}

	/**
	 * process delete function master
	 */
	@Override
	public void delete(FunctionMaster functionMaster, CommonModel common) {
		FunctionMaster oldFunctionMaster = this.loadFunctionMaster(functionMaster.getFunctionMasterId());
		// check function master is common
		this.checkFunctionMasterCommon(oldFunctionMaster);
		// insert problem list
		deleteAffected(oldFunctionMaster, common);
		// delete function master
		functionMasterRepository.delete(functionMaster.getFunctionMasterId());
	}

	@Override
	public FunctionMaster loadFunctionMasterWithAffectDeleted(Long functionMasterId) {
		FunctionMaster functionMaster = this.loadFunctionMaster(functionMasterId);
		List<BusinessDesign> lstAffectedBlogic = functionMasterRepository.getBDesignEffectedDeleteFMaster(functionMaster.getFunctionMethod());
		List<DecisionTable> lstAffectedDecisionTable = functionMasterRepository.getDTableEffectedDeleteFMaster(functionMaster.getFunctionMethod());
		functionMaster.setListOfBusinessDesign(lstAffectedBlogic);
		functionMaster.setListOfDecisionTable(lstAffectedDecisionTable);
		return functionMaster;
	}

	/**
	 * Check Function Master is QP Common
	 */
	@Override
	public void checkFunctionMasterCommon(FunctionMaster functionMaster) {
		if (DbDomainConst.FunctionMasterType.QP_COMMON.equals(functionMaster.getFunctionMasterType())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0118, MessageUtils.getMessage(CommonMessageConst.TQP_FUNCTIONMASTER)));
		}
	}

	@Override
	public FunctionMaster loadFunctionMaster(Long functionMasterId) {

		FunctionMaster functionMasters = functionMasterRepository.findOneFuntionMasterById(functionMasterId);

		// Check exits function master
		if (functionMasters == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_FUNCTIONMASTER)));
		}
		UploadFile uploadFile = uploadFileRepository.findOne(functionMasters.getUploadFileId());
		List<FunctionMethod> functionMethods = functionMasterRepository.findFuntionMethodByFunctionMasterId(functionMasterId);
		List<FunctionMethodInput> functionMethodInputs = functionMasterRepository.findFunctionMethodInputByFunctionMasterId(functionMasterId);
		List<FunctionMethodOutput> functionMethodOutputs = functionMasterRepository.findFunctionMethodOutputByFunctionMasterId(functionMasterId);

		if (uploadFile != null) {
			functionMasters.setFile(uploadFile.getContent());
			functionMasters.setContent(uploadFile.getContent());
		}

		List<FunctionMethodInput> filtedMethodInput = null;
		List<FunctionMethodOutput> filtedMethodOutput = null;

		boolean inputIsNotNull = FunctionCommon.isNotEmpty(functionMethodInputs);
		boolean outputIsNotNull = FunctionCommon.isNotEmpty(functionMethodOutputs);

		if (FunctionCommon.isNotEmpty(functionMethods)) {
			for (FunctionMethod functionMethod : functionMethods) {
				Long functionMethodId = functionMethod.getFunctionMethodId();
				filtedMethodInput = new ArrayList<FunctionMethodInput>();
				filtedMethodOutput = new ArrayList<FunctionMethodOutput>();
				if (inputIsNotNull) {
					for (FunctionMethodInput functionMethodInput : functionMethodInputs) {
						if (functionMethodInput.getFunctionMethodId().equals(functionMethodId)) {
							filtedMethodInput.add(functionMethodInput);
						}
					}
				}
				if (outputIsNotNull) {
					for (FunctionMethodOutput functionMethodOutput : functionMethodOutputs) {
						if (functionMethodOutput.getFunctionMethodId().equals(functionMethodId)) {
							filtedMethodOutput.add(functionMethodOutput);
						}
					}
				}
				itemSeqNo = 0;
				calcTableIndexForMethodInput(filtedMethodInput, "");
				itemSeqNo = 0;
				calcTableIndexForMethodOutput(filtedMethodOutput, "");

				functionMethod.setFunctionMethodInput(filtedMethodInput);
				functionMethod.setFunctionMethodOutput(filtedMethodOutput);
			}

			itemSeqNo = 0;
			calcTableIndexForFunctionMethod(functionMethods, "");
		}
		functionMasters.setFunctionMethod(functionMethods);

		return functionMasters;
	}

	private void calcTableIndexForFunctionMethod(List<FunctionMethod> functionMethods, String groupPref) {
		if (!FunctionCommon.isEmpty(functionMethods)) {
			for (FunctionMethod functionMethod : functionMethods) {
				functionMethod.setItemSeqNo(itemSeqNo);
				itemSeqNo++;
				functionMethod.setTableIndex(itemSeqNo.toString());
			}
		}
	}

	private void calcTableIndexForMethodInput(List<FunctionMethodInput> filtedMethodInput, String groupPref) {
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if (!FunctionCommon.isEmpty(filtedMethodInput)) {
			for (FunctionMethodInput functionMethodInput : filtedMethodInput) {
				String currentGroup = groupPref;
				if (!FunctionCommon.isEmpty(functionMethodInput.getParentFunctionMethodInputId())) {
					currentGroup = mapTableIndex.get(functionMethodInput.getParentFunctionMethodInputId());
				} else {
					currentGroup = groupPref;
				}

				functionMethodInput.setGroupId(currentGroup);
				functionMethodInput.setItemSeqNo(itemSeqNo);
				itemSeqNo++;

				int maxIndex = mapSequence.getOrDefault(functionMethodInput.getGroupId(), 0);
				maxIndex++;
				String tableIndex;
				if (FunctionCommon.isEmpty(currentGroup)) {
					tableIndex = String.valueOf(maxIndex);
				} else {
					tableIndex = currentGroup + "." + maxIndex;
				}
				functionMethodInput.setTableIndex(tableIndex);

				mapTableIndex.put(functionMethodInput.getMethodInputId(), functionMethodInput.getTableIndex());
				mapSequence.put(functionMethodInput.getGroupId(), maxIndex);
			}
		}
	}

	private void calcTableIndexForMethodOutput(List<FunctionMethodOutput> filtedMethodOutput, String groupPref) {
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if (!FunctionCommon.isEmpty(filtedMethodOutput)) {
			for (FunctionMethodOutput functionMethodOutput : filtedMethodOutput) {
				String currentGroup = groupPref;
				if (!FunctionCommon.isEmpty(functionMethodOutput.getParentFunctionMethodOutputId())) {
					currentGroup = mapTableIndex.get(functionMethodOutput.getParentFunctionMethodOutputId());
				} else {
					currentGroup = groupPref;
				}

				functionMethodOutput.setGroupId(currentGroup);
				functionMethodOutput.setItemSeqNo(itemSeqNo);
				itemSeqNo++;

				int maxIndex = mapSequence.getOrDefault(functionMethodOutput.getGroupId(), 0);
				maxIndex++;
				String tableIndex;
				if (FunctionCommon.isEmpty(currentGroup)) {
					tableIndex = String.valueOf(maxIndex);
				} else {
					tableIndex = currentGroup + "." + maxIndex;
				}
				functionMethodOutput.setTableIndex(tableIndex);

				mapTableIndex.put(functionMethodOutput.getMethodOutputId(), functionMethodOutput.getTableIndex());
				mapSequence.put(functionMethodOutput.getGroupId(), maxIndex);
			}
		}
	}

	/**
	 * @param functionMasterId
	 * @param functionMethods
	 */
	private void registerFunctionMethod(Long functionMasterId, List<FunctionMethod> functionMethods) {
		Long startSequence = 0L;
		Long sequenceFunctionMethod = functionMasterRepository.getSequencesFunctionMethod(functionMethods.size() - 1);
		startSequence = sequenceFunctionMethod - (functionMethods.size() - 1);

		Integer numberOfMethodInput = 0;
		Integer numberOfMethodOutput = 0;

		for (FunctionMethod functionMethod : functionMethods) {
			if (functionMethod.getFunctionMethodInput() != null) {
				numberOfMethodInput += functionMethod.getFunctionMethodInput().size();
			}
			if (functionMethod.getFunctionMethodOutput() != null) {
				numberOfMethodOutput += functionMethod.getFunctionMethodOutput().size();
			}
		}

		Map<String, Long> mapKeyInput = new HashMap<String, Long>();
		Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
		Long startSequenceMethodInput = 0L;
		Long startSequenceMethodOutput = 0L;
		Long sequenceFunctionMethodInput = -1L;
		if (numberOfMethodInput.intValue() > 0) {
			sequenceFunctionMethodInput = functionMasterRepository.getSequencesFunctionMethodInput(numberOfMethodInput - 1);
		}
		Long sequenceFunctionMethodOutput = -1L;
		if (numberOfMethodOutput.intValue() > 0) {
			sequenceFunctionMethodOutput = functionMasterRepository.getSequencesFunctionMethodOutput(numberOfMethodOutput - 1);
		}
		startSequenceMethodInput = sequenceFunctionMethodInput - numberOfMethodInput - 1;
		startSequenceMethodOutput = sequenceFunctionMethodOutput - numberOfMethodOutput - 1;

		for (FunctionMethod functionMethod : functionMethods) {
			functionMethod.setFunctionMasterId(functionMasterId);
			functionMethod.setFunctionMethodId(startSequence);
			startSequence++;

			// map function method id for method input
			if (functionMethod.getFunctionMethodInput() != null) {
				for (FunctionMethodInput functionMethodInput : functionMethod.getFunctionMethodInput()) {
					mapKeyInput.put(functionMethodInput.getMethodInputId(), startSequenceMethodInput);

					int index = functionMethod.getFunctionMethodInput().indexOf(functionMethodInput);
					functionMethodInput.setFunctionMethodId(functionMethod.getFunctionMethodId());
					functionMethodInput.setMethodInputId(startSequenceMethodInput.toString());
					functionMethodInput.setItemSeqNo(index);

					startSequenceMethodInput++;
					if ("".equals(functionMethodInput.getParentFunctionMethodInputId())) {
						functionMethodInput.setParentFunctionMethodInputId(null);
					}

					// map key of parent
					if (mapKeyInput.containsKey(functionMethodInput.getParentFunctionMethodInputId())) {
						functionMethodInput.setParentFunctionMethodInputId(mapKeyInput.get(functionMethodInput.getParentFunctionMethodInputId()).toString());
					}
				}
			}
			// map function method id for method output
			if (functionMethod.getFunctionMethodOutput() != null) {
				for (FunctionMethodOutput functionMethodOutput : functionMethod.getFunctionMethodOutput()) {
					mapKeyOutput.put(functionMethodOutput.getMethodOutputId(), startSequenceMethodOutput);

					int index = functionMethod.getFunctionMethodOutput().indexOf(functionMethodOutput);
					functionMethodOutput.setFunctionMethodId(functionMethod.getFunctionMethodId());
					functionMethodOutput.setMethodOutputId(startSequenceMethodOutput.toString());
					functionMethodOutput.setItemSeqNo(index);

					startSequenceMethodOutput++;
					if ("".equals(functionMethodOutput.getParentFunctionMethodOutputId())) {
						functionMethodOutput.setParentFunctionMethodOutputId(null);
					}

					// map key of parent
					if (mapKeyOutput.containsKey(functionMethodOutput.getParentFunctionMethodOutputId())) {
						functionMethodOutput.setParentFunctionMethodOutputId(mapKeyOutput.get(functionMethodOutput.getParentFunctionMethodOutputId()).toString());
					}
				}
			}
		}
		functionMasterRepository.insertFunctionMethods(functionMethods);
	}

	@Override
	public List<FunctionMaster> loadAllFunctionMasterByProject(CommonModel common) {
		List<FunctionMaster> lstFunctionMasters = functionMasterRepository.findFunctionMasterByProjectId(common.getWorkingProjectId());

		if (lstFunctionMasters.size() > 0) {
			List<FunctionMethod> lstFunctionMethods = functionMasterRepository.findFuntionMethodByProjectId(common.getWorkingProjectId());
			List<FunctionMethodInput> lstFunctionMethodInputs = functionMasterRepository.findFunctionMethodInputByProjectId(common.getWorkingProjectId());
			List<FunctionMethodOutput> lstFunctionMethodOutputs = functionMasterRepository.findFunctionMethodOutputByProjectId(common.getWorkingProjectId());

			List<FunctionMethodInput> filtedMethodInput = null;
			List<FunctionMethodOutput> filtedMethodOutput = null;

			for (FunctionMethod functionMethod : lstFunctionMethods) {
				Long functionMethodId = functionMethod.getFunctionMethodId();
				filtedMethodInput = new ArrayList<FunctionMethodInput>();
				filtedMethodOutput = new ArrayList<FunctionMethodOutput>();

				for (FunctionMethodInput functionMethodInput : lstFunctionMethodInputs) {
					if (functionMethodInput.getFunctionMethodId().equals(functionMethodId)) {
						filtedMethodInput.add(functionMethodInput);
					}
				}

				for (FunctionMethodOutput functionMethodOutput : lstFunctionMethodOutputs) {
					if (functionMethodOutput.getFunctionMethodId().equals(functionMethodId)) {
						filtedMethodOutput.add(functionMethodOutput);
					}
				}

				functionMethod.setFunctionMethodInput(filtedMethodInput);
				functionMethod.setFunctionMethodOutput(filtedMethodOutput);
			}

			for (FunctionMaster functionMaster : lstFunctionMasters) {
				List<FunctionMethod> filtedFunctionMethod = new ArrayList<FunctionMethod>();
				for (FunctionMethod functionMethod : lstFunctionMethods) {
					if (functionMaster.getFunctionMasterId().equals(functionMethod.getFunctionMasterId())) {
						filtedFunctionMethod.add(functionMethod);
					}
				}
				functionMaster.setFunctionMethod(filtedFunctionMethod);
			}
		}
		return lstFunctionMasters;
	}

	@Override
	public List<FunctionMaster> loadAllFunctionMasterByProjectExcludeFuntionName(CommonModel common) {
		List<FunctionMaster> functionMasters = functionMasterRepository.findFunctionMasterByProjectId(common.getWorkingProjectId());
		return functionMasters;
	}

	@Override
	public List<FunctionMethod> findFuntionMethodByFunctionMasterId(Long functionMasterId) {
		if (functionMasterId == null) {
			throw new IllegalArgumentException("Id FunctionMaster is not empty !");
		}
		List<FunctionMethod> functionMethods = functionMasterRepository.findFuntionMethodByFunctionMasterId(functionMasterId);
		return functionMethods;
	}

	@Override
	public List<FunctionMethodInput> findFunctionMethodInputByFunctionMethodId(Long functionMethodId) {
		if (functionMethodId == null) {
			throw new IllegalArgumentException("functionMethodId is not empty !");
		}
		List<FunctionMethodInput> methodInputs = this.functionMasterRepository.findFunctionMethodInputByFunctionMethodId(functionMethodId);

		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		
		for (FunctionMethodInput in : methodInputs) {
			String currentGroup = "";
			if (in.getParentFunctionMethodInputId() != null) {
				currentGroup = mapTableIndex.get(in.getParentFunctionMethodInputId());
			}
			in.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
			maxIndex++;
			if (in.getParentFunctionMethodInputId() == null) {
				in.setTableIndex(String.valueOf(maxIndex));
			} else {
				in.setTableIndex(currentGroup + "." + maxIndex);
			}
			mapTableIndex.put(in.getMethodInputId(), in.getTableIndex());
			mapSequence.put(in.getGroupId(), maxIndex);
		}
		return methodInputs;
	}

	@Override
	public List<FunctionMethodOutput> findFunctionMethodOutputByFunctionMethodId(Long functionMethodId) {
		if (functionMethodId == null) {
			throw new IllegalArgumentException("functionMethodId is not empty !");
		}
		List<FunctionMethodOutput> methodOutputs = this.functionMasterRepository.findFunctionMethodOutputByFunctionMethodId(functionMethodId);
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		
		for (FunctionMethodOutput in : methodOutputs) {
			String currentGroup = "";
			if (in.getParentFunctionMethodOutputId() != null) {
				currentGroup = mapTableIndex.get(in.getParentFunctionMethodOutputId());
			}
			in.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
			maxIndex++;
			if (in.getParentFunctionMethodOutputId() == null) {
				in.setTableIndex(String.valueOf(maxIndex));
			} else {
				in.setTableIndex(currentGroup + "." + maxIndex);
			}
			mapTableIndex.put(in.getMethodOutputId(), in.getTableIndex());
			mapSequence.put(in.getGroupId(), maxIndex);
		}
		return methodOutputs;
	}

	@Override
	public List<FunctionMaster> findFunctionMasterDefault() {
		return functionMasterRepository.findFunctionMasterDefault();
	}

	@Override
	public List<FunctionMethod> findFuntionMethodDefault() {
		return functionMasterRepository.findFuntionMethodDefault();
	}

	@Override
	public List<FunctionMethodInput> findFunctionMethodInputDefault() {
		return functionMasterRepository.findFunctionMethodInputDefault();
	}

	@Override
	public List<FunctionMethodOutput> findFunctionMethodOutputDefault() {
		return functionMasterRepository.findFunctionMethodOutputDefault();
	}

	@Override
	public void registerFunctionMasterDefault(FunctionMaster functionMaster) {
		if (functionMasterRepository.register(functionMaster) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
		}
		Long functionMasterId = functionMaster.getFunctionMasterId();

		// register function method
		List<FunctionMethod> functionMethods = functionMaster.getFunctionMethod();
		if (FunctionCommon.isNotEmpty(functionMethods)) {
			this.registerFunctionMethod(functionMasterId, functionMethods);
		}
	}

	@Override
	public List<CommonObjectAttribute> findCommonObjectAttributeByCommonObject(Long commonObjectDefinitionId, CommonModel common) {
		List<CommonObjectAttribute> lstCommonObjectAttributes = new ArrayList<CommonObjectAttribute>();

		CommonObjectDefinition commonObjectDefinition = commonObjectDefinitionSharedService.getCommonObjectDefinition(commonObjectDefinitionId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(), null);

		for (CommonObjectAttribute objDetail : commonObjectDefinition.getCommonObjectAttributes()) {
			lstCommonObjectAttributes.add(objDetail);
		}
		return lstCommonObjectAttributes;
	}

	@Override
	public List<ExternalObjectAttribute> findExternalObjectAttributeByCommonObject(Long externalObjectDefinitionId, CommonModel common) {
		List<ExternalObjectAttribute> lstExternalObjectAttributes = new ArrayList<ExternalObjectAttribute>();

		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionSharedService.getExternalObjectDefinition(externalObjectDefinitionId, null, common.getWorkingProjectId(), common.getWorkingLanguageId(), 1);

		for (ExternalObjectAttribute objDetail : externalObjectDefinition.getExternalObjectAttributes()) {
			lstExternalObjectAttributes.add(objDetail);
		}

		return lstExternalObjectAttributes;
	}

	private void modifyAffected(FunctionMaster functionMaster, List<FunctionMethod> lstDeletedMethods, List<FunctionMethod> lstModifiedMethods, CommonModel common) {
		if (CollectionUtils.isNotEmpty(lstModifiedMethods)) {
			modifyMethodAffected(lstModifiedMethods, common);
		}
		if (CollectionUtils.isNotEmpty(lstDeletedMethods)) {
			deleteMethodAffected(lstDeletedMethods, functionMaster.getFunctionMasterCode(), common);
		}
	}

	private void deleteAffected(FunctionMaster functionMaster, CommonModel common) {
		if (CollectionUtils.isNotEmpty(functionMaster.getFunctionMethod())) {
			deleteMethodAffected(functionMaster.getFunctionMethod(), functionMaster.getFunctionMasterCode(), common);
		}
	}

	private void deleteMethodAffected(List<FunctionMethod> lstMethods, String functionMasterCode, CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		if (CollectionUtils.isNotEmpty(lstMethods)) {
			for (FunctionMethod method : lstMethods) {
				jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
				jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
				jobControl.setModuleId(null);
				jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
				jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.FUNCTION_METHOD));
				jobControl.setImpactId(String.valueOf(method.getFunctionMethodId()));
				jobControl.setCurAppStatus(GenerateAppStatus.INIT);
				jobControl.setAddDateTime(currentTime);
				jobControl.setUpdDateTime(currentTime);
				jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
				jobControl.setJobArgNm7(method.getFunctionMethodCode());
				jobControl.setJobArgNm8(functionMasterCode);
				// check the same business type job.
				Long count = impactChangeRepository.countImpactChangeByType(jobControl);
				if (count == 0)
					impactChangeRepository.registerImpactChange(jobControl);
				else
					impactChangeRepository.modifyImpactChange(jobControl);
			}
		}
	}

	private void modifyMethodAffected(List<FunctionMethod> lstMethods, CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		if (CollectionUtils.isNotEmpty(lstMethods)) {
			for (FunctionMethod method : lstMethods) {
				jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
				jobControl.setProjectId(String.valueOf(common.getWorkingProjectId()));
				jobControl.setModuleId(null);
				jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
				jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.FUNCTION_METHOD));
				jobControl.setImpactId(String.valueOf(method.getFunctionMethodId()));
				jobControl.setCurAppStatus(GenerateAppStatus.INIT);
				jobControl.setAddDateTime(currentTime);
				jobControl.setUpdDateTime(currentTime);
				jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
				// check the same business type job.
				Long count = impactChangeRepository.countImpactChangeByType(jobControl);
				if (count == 0)
					impactChangeRepository.registerImpactChange(jobControl);
				else
					impactChangeRepository.modifyImpactChange(jobControl);
			}
		}
	}
}
