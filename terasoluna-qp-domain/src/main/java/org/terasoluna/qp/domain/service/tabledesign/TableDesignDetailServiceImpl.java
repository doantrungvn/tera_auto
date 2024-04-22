package org.terasoluna.qp.domain.service.tabledesign;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;

@Service
@Transactional
public class TableDesignDetailServiceImpl implements TableDesignDetailService {

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Override
	public TableDesignDetails findOne(long tableDesignDetailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableDesignDetails> findAllByTableDesign(long tableDesignId) {

		return tableDesignDetailRepository.findAllByTableDesign(tableDesignId);
	}
	
	@Override
	public List<TableDesignDetails> getAllInformationByTableDesign(long tableDesignId) {

		return tableDesignDetailRepository.getAllInformationByTableDesign(tableDesignId);
	}

	@Override
	public List<TableDesignDetails> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableDesignDetails> findAllKey(long tableDesignId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(TableDesign tableDesignDetail) {

		List<TableDesignDetails> tableDesignDetails = tableDesignDetail.getListTableDesignDetails();

		tableDesignDetailRepository.multiCreate(tableDesignDetails);
	}

	@Override
	public void update(TableDesignDetails tableDesignDetail) {
		tableDesignDetailRepository.update(tableDesignDetail);

	}
	
	@Override
	public void delete(long tableDesignDetailId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByTableDesignId(long tableDesignId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long countBySearchCriteria(TableDesignDetails tableDesignDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableDesignDetails> findBySearchCriteria(
			TableDesignDetails tableDesignDetail, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableDesignDetails> getAllByProjectAndSubArea(Long projectId, Long areaId) {
		// TODO Auto-generated method stub
		return tableDesignDetailRepository.getAllByProjectAndSubArea(projectId, areaId);
	}

	@Override
	public long checkConcurrenceWithSubjectArea(Long tableDesignId, Long areaId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TableDesignDetails> findByListId(List<Long> tddIds) {
		// TODO Auto-generated method stub
		return tableDesignDetailRepository.findByListId(tddIds);
	}

	@Override
	public List<TableDesignDetails> findByListTableDesignId(Long projectId, List<Long> listId) {
		// TODO Auto-generated method stub
		return tableDesignDetailRepository.findByListTableDesignId(projectId, listId);
	}
}
