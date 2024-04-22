package org.terasoluna.qp.domain.service.librarymanagement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LibraryManagement;
import org.terasoluna.qp.domain.repository.librarymanagement.LibraryManagementSearchCriteria;

@Service
public interface LibraryManagementService{
	
	Page<LibraryManagement> findPageByCriteria(LibraryManagementSearchCriteria criteria, Pageable pageable);
	
	LibraryManagement findLibraryManagement(Long libraryId);
	
	void registerLibrary(LibraryManagement library, CommonModel common);
	
	void modifyLibrary(LibraryManagement library, CommonModel common);
	
	LibraryManagement deleteLibrary(LibraryManagement library, CommonModel common);
}
