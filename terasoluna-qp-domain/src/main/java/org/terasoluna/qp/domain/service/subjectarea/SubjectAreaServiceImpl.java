package org.terasoluna.qp.domain.service.subjectarea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SubjectAreaMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.model.SubjectAreaKeyword;
import org.terasoluna.qp.domain.model.SubjectAreaTableDesign;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaKeywordRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaRepository;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaSearchCriteria;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaTableRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeUtil;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class SubjectAreaServiceImpl implements SubjectAreaService {

	@Inject
	SubjectAreaRepository areaRepository;

	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	SubjectAreaTableRepository subjectAreaTableRepository;;
	
	@Inject
	SubjectAreaKeywordRepository subjectAreaKeywordRepository;
	
	@Inject
	ProjectService projectService;
	
	@Override
	public List<SubjectArea> getAllByProjectId(long projectId) {
		
		return areaRepository.getAllByProjectId(projectId);
	}

	@Override
	public Page<SubjectArea> findPageByCriteria(SubjectAreaSearchCriteria criteria, Pageable pageable) {
		
		long totalCount = areaRepository.countBySearchCriteria(criteria);

		List<SubjectArea> articles;
		if (0 < totalCount) {
			articles = areaRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			articles = Collections.emptyList();
		}
		Page<SubjectArea> page = new PageImpl<SubjectArea>(articles, pageable, totalCount);
		
		return page;
	}
	

	@Override
	public SubjectArea findOneById(Long areaId,boolean checkDesignStatus, CommonModel common) throws BusinessException {
		// Get subject area information by id
		SubjectArea subjectArea = areaRepository.findOneById(areaId);
		if (subjectArea == null) {
			throw new BusinessException(ResultMessages.error()
					.add(CommonMessageConst.ERR_SYS_0037, 
							MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0017)));
		}
		projectService.validateProject(subjectArea.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), checkDesignStatus);
		return subjectArea;
	}
	
	@Override
	public SubjectArea register(SubjectArea subjectArea, CommonModel common) throws BusinessException {

		// Set common date for log
		subjectArea.setCreatedDate(FunctionCommon.getCurrentTime());
		subjectArea.setUpdatedDate(FunctionCommon.getCurrentTime());
		projectService.validateProject(subjectArea.getProjectId(), common.getCreatedBy(), common.getWorkingProjectId(), true);

		// Exist check
		existCheckAreaNameOrCode(subjectArea);
		areaRepository.insertSubAreaInfor(subjectArea);

		int size = subjectArea.getPositionLst() != null ? subjectArea.getPositionLst().size() : 0;
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				SubjectArea tempSub = subjectArea.getPositionLst().get(i);
				if (tempSub.getAreaId() == null) {
					tempSub.setAreaId(subjectArea.getAreaId());
					break;
				}
			}

			// Processing data of keywors
			processAllKeyword(subjectArea);

			// insert table on subject area table design
			registerSubAreaTbldesign(subjectArea);

			// Processing data of position
			processAllPosition(subjectArea);
		}
		return subjectArea;
	}

	@Override
	public void modify(SubjectArea subjectArea, CommonModel common) throws BusinessException {
		
		// Set common date for log
		subjectArea.setSysDatetime(FunctionCommon.getCurrentTime());
		
		// Check data had remove
		findOneById(subjectArea.getAreaId(),true, common);
		
		// Exist check
		existCheckAreaNameOrCode(subjectArea);
		
		// Check update subject area information
		boolean updated = areaRepository.updateAreaInforById(subjectArea);
		if (!updated) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}
		
		// Processing data of keywords
		processAllKeyword(subjectArea);
		
		// Delete table reference
		subjectAreaTableRepository.deleteSubAreaDesignTableByAreaId(subjectArea.getAreaId());
		
		// insert table on subject area table design
		registerSubAreaTbldesign(subjectArea);
		
		// Update all position
		processAllPosition(subjectArea);
		
	}

	@Override
	public void delete(SubjectArea subjectArea, CommonModel common) throws BusinessException {
		
		// Check data had remove
		SubjectArea subjectAreaDb = findOneById(subjectArea.getAreaId(),true, common);
		
		//check Concurrence
		if (DateUtils.compare(subjectAreaDb.getUpdatedDate(), subjectArea.getUpdatedDate()) == 1) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		projectService.validateProject(subjectAreaDb.getProjectId(), common.getUpdatedBy(), common.getWorkingProjectId(), true);
	
		// Delete sub subjectArea design table
		subjectAreaTableRepository.deleteSubAreaDesignTableByAreaId(subjectArea.getAreaId());
		// Delete subjectArea keyword table
		subjectAreaKeywordRepository.deleteKeyword(subjectArea.getAreaId());
		
		// Delete Sub subjectArea design			
		if (!areaRepository.deleteSubArea(subjectArea)) {
			throw new BusinessException(ResultMessages.error()
					.add(CommonMessageConst.ERR_SYS_0073, 
							MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0017)));
		}
	}

	private void existCheckAreaNameOrCode(SubjectArea subjectArea) {
		
		Integer existflag = areaRepository.getExitsSubAreaNameOrCode(subjectArea);
		ResultMessages resultMessages = ResultMessages.error(); 
		
		if(existflag != null) {
			switch(existflag){
			case 1:
				resultMessages.add(CommonMessageConst.ERR_SYS_0036, 
						MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004));
				break;
			case 2:
				resultMessages.add(CommonMessageConst.ERR_SYS_0036, 
						MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0005));
				break;
			case 3:
				resultMessages.add(CommonMessageConst.ERR_SYS_0036, 
						MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004));
				resultMessages.add(CommonMessageConst.ERR_SYS_0036, 
						MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0005));
				break;
			}
			
			if(resultMessages.isNotEmpty()){
				throw new BusinessException(resultMessages);
			}
		}
	}

	@Override
	public void assignSetting(SubjectArea subjectArea, String process) {
		
		List<TableDesign> tableLst = null;
		List<SubjectAreaKeyword> keywordLst = null;
		List<SubjectArea> positionLst = null;
		Long projectId = subjectArea.getProjectId();
		
		if(SubjectAreaConstant.PROCESS_REG.equals(process)){
			
			// Processing value for position
			List<SubjectArea> allPosition = new ArrayList<SubjectArea>();
			SubjectArea saElm = new SubjectArea();
			saElm.setItemSeqNo(1);
			allPosition.add(saElm);
			
			if(subjectArea.getProjectId() != null){
				positionLst = areaRepository.getAllPosLstByProjectId(projectId);
			}
			
			if(positionLst != null && positionLst.size() > 0){
				for(int i = 0; i < positionLst.size(); i++){
					positionLst.get(i).setItemSeqNo(positionLst.get(i).getItemSeqNo()+1);
					allPosition.add(positionLst.get(i));
				}
			}
			
			// Assign value position
			subjectArea.setItemSeqNo(1);
			subjectArea.setPositionLst(allPosition);
			
			// Processing value for table
			if(subjectArea.getTableLst() != null){
				subjectArea.getTableLst().clear();
			}
			
			// flag initial register
			List<TableDesign> tb = new ArrayList<TableDesign>();
			tb.add(new TableDesign());			
			subjectArea.setTableLst(tb);
			
		} else if(SubjectAreaConstant.PROCESS_REG_ERR.equals(process)){
			
			// Get all table when modify error
			if(subjectArea.getTableLst() != null && subjectArea.getTableLst().size() > 0){
				tableLst = getAllTable(subjectArea.getTableLst());
				
				if(tableLst.size() > 0) {
					// Assign value table
					subjectArea.setTableLst(tableLst);
				} else {
					subjectArea.getTableLst().clear();
				}
			}
			
			// Setting position list not include blank value in list
			positionLst = new ArrayList<SubjectArea>();
			for(SubjectArea sa : subjectArea.getPositionLst()){
				if(sa.getItemSeqNo() != null){
					positionLst.add(sa);
				}
			}
			
			// Sort ascending position list by sequenceNo
			Collections.sort(positionLst, new SubjectArea());
			// Assign value position
			subjectArea.setPositionLst(positionLst);
			
		} else if(SubjectAreaConstant.PROCESS_VIEW.equals(process)){
			// Get all table by subject area
			tableLst = tableDesignRepository.getTableDesignBySubjectAreaId(subjectArea.getAreaId());
			// Get all keywords by subject area
			keywordLst = subjectAreaKeywordRepository.getKeywordList(subjectArea.getAreaId());
			
			// Assign setting
			subjectArea.setTableLst(tableLst);
			subjectArea.setKeywordLst(keywordLst);
			
		} else if(SubjectAreaConstant.PROCESS_MODIFY.equals(process)) {
			// Get all position by project id
			positionLst = areaRepository.getAllPosLstByProjectId(projectId);
			// Get all table by subject area
			tableLst = tableDesignRepository.getTableDesignBySubjectAreaId(subjectArea.getAreaId());
			// Get all keywords by subject area
			keywordLst = subjectAreaKeywordRepository.getKeywordList(subjectArea.getAreaId());
			
			// Assign value
			subjectArea.setPositionLst(positionLst);
			subjectArea.setTableLst(tableLst);
			subjectArea.setKeywordLst(keywordLst);

		} else if(SubjectAreaConstant.PROCESS_MODIFY_ERR.equals(process)) {
			// Sort ascending position list by sequenceNo
			Collections.sort(subjectArea.getPositionLst(), new SubjectArea());
			
			// Get all table when modify error
			if(subjectArea.getTableLst() != null && subjectArea.getTableLst().size() > 0){
				tableLst = getAllTable(subjectArea.getTableLst());
				
				if(tableLst.size() > 0) {
					// Assign value table
					subjectArea.setTableLst(tableLst);
				} else {
					subjectArea.getTableLst().clear();
				}
			}
		}
	}

	private List<TableDesign> getAllTable(List<TableDesign> tableLst) {
		List<TableDesign> tableResult = new ArrayList<TableDesign>();
		
			for(int i = 0; i < tableLst.size(); i++) {
				TableDesign tb  = tableLst.get(i);
				if(tb.getTableDesignId() != null ){
					// Get table information by table id
					TableDesign element = tableDesignRepository.findOneById(tb.getTableDesignId());
					tableResult.add(element);
				} else {
					tableResult.add(tb);
				}
			}
		
		return tableResult;
	}

	private void processAllPosition(SubjectArea subjectArea) {
		boolean updated;
		
		if(subjectArea.getPositionLst() != null && subjectArea.getPositionLst().size() > 0) {
			// update all position
			updated = areaRepository.updatePosByAreaId(subjectArea.getPositionLst());
			// In the case of update error
			if (!updated) {
				throw new BusinessException(ResultMessages.error()
						.add(CommonMessageConst.ERR_SYS_0072, 
								MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0012)));
			}
		}
	}

	private void processAllKeyword(SubjectArea subjectArea) {
		
		// Process keyword information
		List<SubjectAreaKeyword> updateLst = new ArrayList<SubjectAreaKeyword>();
		List<SubjectAreaKeyword> insertLst = new ArrayList<SubjectAreaKeyword>();
		
		// Get list keyword for insert and update
		if(subjectArea.getKeywordLst() != null && subjectArea.getKeywordLst().size() > 0){
			for (SubjectAreaKeyword ak : subjectArea.getKeywordLst()) {
				if(null != ak.getKeywordId()) {
					updateLst.add(ak);
				} else {
					insertLst.add(ak);
				}
			}
			
			// List keyword for insert
			if(updateLst.size() > 0){
				// Delete keyword not in
				subjectAreaKeywordRepository.deleteKeyWordNotIn(subjectArea.getAreaId(), updateLst);
				// Update all keyword
				subjectAreaKeywordRepository.updateKeywordByAreaId(updateLst);
				
			} else {
				// In the case of all keyword had removed
				// Delete All keyword
				subjectAreaKeywordRepository.deleteKeyword(subjectArea.getAreaId());
			}
			
			// List keyword for update
			if(insertLst.size() > 0) {
				for(SubjectAreaKeyword ak : insertLst){
					subjectAreaKeywordRepository.insertKeyword(subjectArea.getAreaId(), ak);
				}
			}
		} else {
			// In the case of all keyword had removed
			// Delete All keyword
			subjectAreaKeywordRepository.deleteKeyword(subjectArea.getAreaId());
		}
	}

	private void registerSubAreaTbldesign(SubjectArea subjectArea) {
		
		if(subjectArea.getTableLst() != null && subjectArea.getTableLst().size() > 0) {
			List<SubjectAreaTableDesign> subAreaTableDesigns = new ArrayList<SubjectAreaTableDesign>();
			for(TableDesign tb : subjectArea.getTableLst()){
				if(!DomainDatatypeUtil.isEmpty(tb.getTableDesignId())) {
					SubjectAreaTableDesign subjectAreaTableDesign = new SubjectAreaTableDesign();
					subjectAreaTableDesign.setSubAreaId(subjectArea.getAreaId());
					subjectAreaTableDesign.setTableId(tb.getTableDesignId());
					subAreaTableDesigns.add(subjectAreaTableDesign);
				}
			}
			try {
				// Insert array
				subjectAreaTableRepository.insertArray(subAreaTableDesigns);
			} catch (Exception ex) {
				ex.printStackTrace();
				// if missing check fk then put out message
				if (ex.getMessage().contains("violates foreign key constraint")) {
					ResultMessages resultMessages = ResultMessages.error();
					throw new BusinessException(resultMessages.add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_TABLEDESIGN)));
				}
				throw ex;
			}
		}
		
	}

}
