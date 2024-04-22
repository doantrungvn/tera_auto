package org.terasoluna.qp.domain.repository.messagedesign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.Language;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;

@Repository
public interface MessageDesignRepository {
	
	Long countByCriteria(@Param("criteria") MessageDesignCriteria criteria);
	
	List<MessageDesign> findPageByCriteria(@Param("criteria") MessageDesignCriteria criteria, @Param("pageable") Pageable pageable);
	
	List<MessageDesign> findAll();

	MessageDesign findById(@Param("messageDesignId") Long messageDesignId);

	List<MessageDesign> findAllByCode(MessageDesign messageDesign);
	
	void register(MessageDesign messageDesign);
	
	void registerMultiple(@Param("messageDesigns") List<MessageDesign> messageDesigns);
	
	int modify(MessageDesign messageDesign);
	
	int modifyByMessageCode(Map<String, Object> sqlMap);
	
	int delete(MessageDesign messageDesign);
	
	int countByModuleId(@Param("module") Module module);
	
	HashMap<String, Long> countReferenceByMessageDesignId(MessageDesign messageDesign);
	
	List<MessageDesign> findAllByLanguage(@Param("criteria") Language language);
	
	int modifyMultipleMessage(@Param("messageDesigns") List<MessageDesign> messageDesigns);
	
	//DungNN - get mesage design by List message code and language code
	List<MessageDesign> findByLanguageCodeAndMessageCode(@Param("criteria") MessageDesignCriteria criteria, @Param("messageCodes") List<String> listOfMesssageCode);
	
	Long messageDesignGetSequences(@Param("size") Integer size);
	
	void updateByTranslate(@Param("messageDesigns") List<MessageDesign> messageDesigns);
	
	/**get message for translate*/
	List<MessageDesign> findToTranslate(@Param("languageDesign") LanguageDesign language, @Param("projectId") Long projectId);
	
	/**DungNN*/
	List<MessageDesign> getDefaultMessageFromQPSystem();

	void deleteMsgLst(@Param("messageDesigns")  List<MessageDesign> messageDesign, @Param("moduleId") Long moduleId);
	
	//QuangVD - get mesage design by List message code and language code and project id
	List<MessageDesign> findByMessageCodeOfProject(@Param("languageId") Long languageId, @Param("messageCodes") List<String> listOfMesssageCode,@Param("projectId") Long projectId);
		
	List<BusinessDesign> getListOfBDesignAffected(MessageDesign messageDesign);
	
	MenuDesign getMenuDesignInformation(MessageDesign messageDesign);
	
	List<HashMap<Long, String>> findRelatedScreenDesignById(MessageDesign messageDesign);
	
	List<MessageDesign> getAllMessageDesignByProjectId(@Param("projectId") Long projectId);
	
	int checkMessageCodeExist(@Param("messageCode") String messageCode, @Param("projectId") Long projectId);
	
	int checkMessageStringExist(MessageDesign messageDesign);
	
	List<MessageDesign> getMessageDesignByModuleId(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId);
	
	List<MessageDesign> getMessageDesignByMessageCoe(@Param("messageDesigns") List<MessageDesign> messageDesigns, @Param("projectId") Long projectId,@Param("languageId") Long languageId );
	
	List<MessageDesign> getMessageDesignModuleByModuleId(@Param("moduleId") Long moduleId, @Param("languageId") Long languageId);
	
	List<MessageDesign> getMessageDesignRegisted(@Param("projectId") Long projectId, @Param("moduleId") Long moduleId, @Param("languageId") Long languageId);
}
