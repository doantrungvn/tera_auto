package org.terasoluna.qp.domain.repository.accountproject;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.AccountProject;

@Repository
public interface AccountProjectRepository {
	boolean deleteAccountProject(Long accountId);

	int registerAccountProject(@Param("accountId") Long accountId,@Param("projectItems") List<AccountProject> projectItems);
	
	List<AccountProject> findAll();
}
