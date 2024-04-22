package org.terasoluna.qp.domain.repository.tabledesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;

 public interface UserDefineCodelistRepository {
	
	void createUserDefineCodelist(UserDefineCodelist userDefineCodelist);
	void createUserDefineCodelistDetails(@Param("userDefineCodelistDetails") List<UserDefineCodelistDetails> userDefineCodelistDetails);
	void deleteUserDefineCodelist(Long codelistId);
	void multiDeleteUserDefineCodelist(@Param("listOfId") List<Long> listOfId);
	
	List<UserDefineCodelistDetails> getByTableDesign(Long tableDesignId);
	List<UserDefineCodelistDetails> getAllByCodeList(Long codelistId);
	List<UserDefineCodelistDetails> getAllByDomainDesign(DomainDesign domainDesign);
	List<UserDefineCodelistDetails> getUserDefineCodeListDetailsByListIds(@Param("listOfId") List<Long> listOfId);
	List<UserDefineCodelistDetails> getAllUserDefineCodeListDetails();
	List<UserDefineCodelistDetails> getTableCodelistByProject(Long projectId);
	List<UserDefineCodelistDetails> getScreenUserDefineCodelistByProject(Long projectId);
}
