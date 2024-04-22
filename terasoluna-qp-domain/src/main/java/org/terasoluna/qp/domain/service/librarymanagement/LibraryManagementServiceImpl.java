package org.terasoluna.qp.domain.service.librarymanagement;

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
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LibraryManagementMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LibraryManagement;
import org.terasoluna.qp.domain.repository.librarymanagement.LibraryManagementRepository;
import org.terasoluna.qp.domain.repository.librarymanagement.LibraryManagementSearchCriteria;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Service
@Transactional
public class LibraryManagementServiceImpl implements LibraryManagementService {

	@Inject
	LibraryManagementRepository libraryManagementRepository;

	@Inject
	ProjectService projectService;

	@Override
	public Page<LibraryManagement> findPageByCriteria(LibraryManagementSearchCriteria criteria, Pageable pageable) {
		List<LibraryManagement> libraryLst;
		long totalCount = libraryManagementRepository.countBySearchCriteria(criteria);
		if (0 < totalCount) {
			libraryLst = libraryManagementRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			libraryLst = Collections.emptyList();
		}
		Page<LibraryManagement> page = new PageImpl<LibraryManagement>(libraryLst, pageable, totalCount);
		return page;
	}

	@Override
	public LibraryManagement findLibraryManagement(Long libraryId) {
		LibraryManagement libraryManagement = libraryManagementRepository.findLibrary(libraryId);
		if (libraryManagement == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0013)));
		}
		return libraryManagement;
	}

	@Override
	public void registerLibrary(LibraryManagement library, CommonModel common) {
		// projectService.initData(getWorkingProjectId(), getAccountId());
		// projectService.validateProject(library.getProjectId(), true);
		projectService.validateProject(common.getCreatedBy(), common.getWorkingProjectId());

		ResultMessages resultMessages = ResultMessages.error();
		Long count = libraryManagementRepository.countLibraryByGroup(library);
		if (count > 0) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0006));
		}

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			// register librar
			library.setCreatedDate(FunctionCommon.getCurrentTime());
			library.setUpdatedDate(FunctionCommon.getCurrentTime());

			if (library.getOptionalFlg() == null) {
				library.setOptionalFlg(DbDomainConst.YesNoFlg.NO);
			}

			libraryManagementRepository.registerLibrary(library);
		}
	}

	@Override
	public void modifyLibrary(LibraryManagement library, CommonModel common) {
		LibraryManagement oldlibrary = findLibraryManagement(library.getLibraryId());
		//projectService.initData(getWorkingProjectId(), getAccountId());
		//projectService.validateProject(oldlibrary.getProjectId(), true);
		common.setDesignStatus(true);
		common.setProjectId(oldlibrary.getProjectId());
		projectService.validateProject(common);

		ResultMessages resultMessages = ResultMessages.error();
		Long count = libraryManagementRepository.countLibraryByGroup(library);
		if (count > 0) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0006));
		}

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			library.setSystemDate(FunctionCommon.getCurrentTime());

			if (library.getOptionalFlg() == null) {
				library.setOptionalFlg(DbDomainConst.YesNoFlg.NO);
			}
			if (!libraryManagementRepository.modifyLibrary(library)) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
			}
		}
	}

	@Override
	public LibraryManagement deleteLibrary(LibraryManagement library, CommonModel common) {

		LibraryManagement oldlibrary = findLibraryManagement(library.getLibraryId());
		//projectService.initData(getWorkingProjectId(), getAccountId());
		common.setDesignStatus(true);
		common.setProjectId(oldlibrary.getProjectId());
		projectService.validateProject(common);

		int count = libraryManagementRepository.deleteLibrary(library.getLibraryId());
		if (0 >= count) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0013)));
		} else {
			library = null;
		}
		return library;
	}

}
