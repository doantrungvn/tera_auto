package org.terasoluna.qp.domain.service.subjectarea;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SubjectArea;
import org.terasoluna.qp.domain.repository.subjectarea.SubjectAreaSearchCriteria;

@Service
public interface SubjectAreaService{
	
	Page<SubjectArea> findPageByCriteria(SubjectAreaSearchCriteria criteria, Pageable pageable);
	
	SubjectArea register(SubjectArea subjectArea, CommonModel common) throws BusinessException;
	
	List<SubjectArea> getAllByProjectId(long projectId);

	SubjectArea findOneById(Long areaId,boolean checkDesignStatus, CommonModel common) throws BusinessException;

	void modify(SubjectArea area, CommonModel common) throws BusinessException;

	void delete(SubjectArea subjectArea, CommonModel common) throws BusinessException;

	void assignSetting(SubjectArea area, String  processName);

}