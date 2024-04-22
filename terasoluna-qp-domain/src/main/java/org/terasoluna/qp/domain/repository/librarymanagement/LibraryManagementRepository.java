package org.terasoluna.qp.domain.repository.librarymanagement;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.LibraryManagement;

public interface LibraryManagementRepository {

	Long countBySearchCriteria(@Param("criteria") LibraryManagementSearchCriteria criteria);

	List<LibraryManagement> findPageBySearchCriteria(@Param("criteria") LibraryManagementSearchCriteria criteria, @Param("pageable") Pageable pageable);
	
	LibraryManagement findLibrary(@Param("libraryId") Long libraryId);
	
	void registerLibrary(LibraryManagement library);
	
	boolean modifyLibrary(LibraryManagement library);
	
	int deleteLibrary(@Param("libraryId") Long libraryId);
	
	Long countLibraryByGroup(LibraryManagement library);
	
	List<LibraryManagement> findAllLibrary(@Param("projectId") Long projectId);

	List<LibraryManagement> findAllLibraryWithFileContent(@Param("projectId")Long projectId);
}
