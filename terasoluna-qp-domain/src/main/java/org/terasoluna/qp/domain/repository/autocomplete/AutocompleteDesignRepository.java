package org.terasoluna.qp.domain.repository.autocomplete;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.ConsistencyValidationModel;
import org.terasoluna.qp.domain.model.Module;

public interface AutocompleteDesignRepository {
	
	/**
	 * Count autocomplete by criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return the long
	 */
	Long countByCriteria(@Param("criteria")AutocompleteSearchCriteria searchCriteria);
	
	/**
	 * Search autocomplete by criteria. 
	 *
	 * @param searchCriteria the search criteria
	 * @param pageable the pageable
	 * @return the list
	 */
	List<AutocompleteDesign> findByCriteria(@Param("criteria")AutocompleteSearchCriteria searchCriteria,@Param("pageable")Pageable pageable);

	/**
	 * Register new autocomplete.
	 *
	 * @param autocomplete the autocomplete
	 */
	void register(AutocompleteDesign autocomplete);

	/**
	 * Delete autocomplete.
	 *
	 * @param autocompleteDesign the autocomplete design
	 * @return true, if successful
	 */
	boolean delete(AutocompleteDesign autocompleteDesign);

	/**
	 * Find autocomplete by id.
	 *
	 * @param autocompleteId the autocomplete id
	 * @return the autocomplete design
	 */
	AutocompleteDesign findOneById(Long autocompleteId);
	
	/**
	 * Modify autocomplete.
	 *
	 * @param autocompleteDesign the autocomplete design
	 * @return true, if successful
	 */
	boolean modify(AutocompleteDesign autocompleteDesign);
	
	boolean isNameExist(AutocompleteDesign autocomplete);

	Integer getExistNameCode(AutocompleteDesign autocomplete);
	
	List<ConsistencyValidationModel> getReferenceById(@Param("autocompleteId") Long autocompleteId,
														@Param("languageId") Long languageId);
	
	boolean isProjectChangeable(AutocompleteDesign autocomplete);
	// Counts for check foreign key to module table
	int countByModuleId(@Param("module") Module module);

	/**
	 * Delete sql design's children.
	 *
	 * @param sqlDesignId the sql design id
	 */
	void deleteSqlDesignChildren(Long sqlDesignId);
	
	AutocompleteDesign findAutocompleteBySqlDesignId(Long sqlDesignId);
	
	boolean modifyDesignStatus(@Param("autocompleteDesign") AutocompleteDesign autocompleteDesign);
	
	List<Long> getAllAutocompleteDesignId(@Param("projectId") Long projectId);
	
	Long[] checkAutocompleteDesignExists(@Param("listOfId") List<Long> listOfId);
	
	AutocompleteDesign getAutocompleteDesignByScreenItem(@Param("autocompleteId") Long autocompleteId);
	
	List<AutocompleteDesign> getAllAutocompleteDesignByProjectId(@Param("projectId") Long projectId);

	List<AutocompleteDesign> getAllAutocompleteDesignByModuleId(@Param("moduleId")Long moduleId,@Param("projectId") Long projectId);
}
