package org.terasoluna.qp.domain.service.designinformation;

import java.sql.Timestamp;
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
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DesignInformationMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.DesignInformation;
import org.terasoluna.qp.domain.model.DesignInformationDetail;
import org.terasoluna.qp.domain.model.DesignRelationSetting;
import org.terasoluna.qp.domain.repository.account.AccountRepository;
import org.terasoluna.qp.domain.repository.designinformation.DesignInformationCriteria;
import org.terasoluna.qp.domain.repository.designinformation.DesignInformationRepository;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class DesignInformationServiceImpl implements DesignInformationService {

	@Inject
	DesignInformationRepository designInformationRepository;

	@Inject
	AccountRepository accountRepository;

	@Inject
	ProjectService projectService;

	@Override
	public Page<DesignInformation> searchDesignInformation(DesignInformationCriteria criteria, Pageable pageable) {
		long totalCount = designInformationRepository.countBySearchCriteria(criteria);

		List<DesignInformation> designInformations;
		if (0 < totalCount) {
			designInformations = designInformationRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			designInformations = Collections.emptyList();
		}
		Page<DesignInformation> page = new PageImpl<DesignInformation>(designInformations, pageable, totalCount);
		return page;
	}

	@Override
	public void register(DesignInformation designInformation) {
		// Check duplicate on design information name
		if (designInformationRepository.findDesignInformationByName(designInformation) > 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0021)));
		}
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		DesignInformation designInformations = new DesignInformation();
		List<DesignInformationDetail> designInformationDetails = designInformation.getDesignInformationDetail();
		List<DesignRelationSetting> designRelationSettings = designInformation.getDesignRelationSetting();

		// Register design information
		designInformations.setDesignName(designInformation.getDesignName());
		designInformations.setRemark(designInformation.getRemark());
		//designInformations.setCreatedBy(accountId);
		designInformations.setCreatedDate(currentTime);
		designInformations.setUpdatedBy(designInformations.getCreatedBy());
		designInformations.setUpdatedDate(currentTime);
		designInformationRepository.registerDesignInformation(designInformations);

		// update sequence for item
		for (int i = 0; i < designInformationDetails.size(); i++) {
			designInformationDetails.get(i).setDesignInformationId(designInformations.getDesignInformationId());
		}
		designInformationRepository.registerDesignInformationDetail(designInformationDetails);

		// Register design relation setting
		for (int i = 0; i < designRelationSettings.size(); i++) {
			designRelationSettings.get(i).setDesignInformationId(designInformations.getDesignInformationId());
		}
		designInformationRepository.registerDesignRelationSetting(designRelationSettings);
	}

	@Override
	public void modify(DesignInformation designInformation) {
		// Check exist
		if (designInformationRepository.findDesignInformationById(designInformation.getDesignInformationId()) == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_DESIGNINFORMATION)));
		}

		Timestamp currentTime = FunctionCommon.getCurrentTime();

		// Modify design information
		// designInformation.setUpdatedBy(accountId);
		designInformation.setSysDatetime(currentTime);
		if (designInformationRepository.modifyDesignInformation(designInformation) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		// List After
		List<DesignInformationDetail> lstdesignInformationDetails = designInformation.getDesignInformationDetail();
		List<DesignRelationSetting> lstdesignRelationSettings = designInformation.getDesignRelationSetting();

		// Modify design information detail
		List<DesignInformationDetail> insertInformationDetail = new ArrayList<DesignInformationDetail>();
		List<DesignRelationSetting> insertRelationSetting = new ArrayList<DesignRelationSetting>();

		// Prepare data for design information detail insert list (identify will automatically get from sequence)
		for (DesignInformationDetail item : lstdesignInformationDetails) {
			if (item.getDesignInformationDetailId() == null) {
				item.setDesignInformationId(designInformation.getDesignInformationId());
				insertInformationDetail.add(item);
			}
		}

		// Prepare data for design relation setting (do not have sequence)
		for (DesignRelationSetting item : lstdesignRelationSettings) {
			if (item.getDesignInformationId() == null) {
				item.setDesignInformationId(designInformation.getDesignInformationId());
				insertRelationSetting.add(item);
			}
		}

		// Execute querry
		if (insertInformationDetail.size() > 0) {
			designInformationRepository.registerDesignInformationDetail(insertInformationDetail);
		}
		if (insertRelationSetting.size() > 0) {
			designInformationRepository.registerDesignRelationSetting(insertRelationSetting);
		}
	}

	public DesignInformation findDesignInformationById(DesignInformation designInformation) {
		// get design information from DB
		designInformation = designInformationRepository.findDesignInformationById(designInformation.getDesignInformationId());
		if (designInformation == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_DESIGNINFORMATION)));
		}
		// get design relation setting from DB
		List<DesignRelationSetting> designInformations = designInformationRepository.findDesignRelationSettingById(designInformation);
		designInformation.setDesignRelationSetting(designInformations);

		// get design information detail from DB
		List<DesignInformationDetail> designInformationDetail = designInformationRepository.findDesignInformationDetailById(designInformation);
		designInformation.setDesignInformationDetail(designInformationDetail);

		// get user name to display
		
		if (designInformation.getCreatedBy() != null) {
			Account accountCreatedBy = accountRepository.findOneByAccountId(designInformation.getCreatedBy());
			designInformation.setCreatedByName(accountCreatedBy.getUsername());
		}
		if (designInformation.getUpdatedBy() != null) {
			Account accountUpdatedBy = accountRepository.findOneByAccountId(designInformation.getUpdatedBy());
			designInformation.setUpdatedByName(accountUpdatedBy.getUsername());	
		}
	
		return designInformation;
	}

	@Override
	public DesignInformation deleteDesignInformation(DesignInformation designInformation) {
		DesignInformation designInformationExist = validateDesignInformation(designInformation.getDesignInformationId());
		int rowCount = 0;
		
		// Delete design relational
		rowCount = designInformationRepository.deleteRelationSetting(designInformationExist.getDesignInformationId());

		// Delete design information detail
		if (rowCount >= 0) {
			rowCount = designInformationRepository.deleteDesignInformationDetail(designInformationExist.getDesignInformationId());

			// Delete design information
			if (rowCount >= 0) {
				rowCount = designInformationRepository.deleteDesignInformation(designInformationExist.getDesignInformationId());
			}
		}

		// Check delete result
		if (rowCount <= 0) {
			designInformationExist.setResultMessages(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0004)));
		} else {
			designInformationExist = null;
		}

		return designInformationExist;
	}

	private DesignInformation validateDesignInformation(long designInformationId) {
		DesignInformation designInformation = designInformationRepository.findDesignInformationById(designInformationId);
		if (designInformation == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(DesignInformationMessageConst.SC_DESIGNINFORMATION_0004)));
		}
		return designInformation;
	}
}
