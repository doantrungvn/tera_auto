package org.terasoluna.qp.domain.service.autocomplete;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteSearchCriteria;

public interface AutocompleteDesignService {
	
	/**
	 * Find autocomplete by search criteria.
	 *
	 * @param criteria the criteria
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<AutocompleteDesign> findPageByCriteria(AutocompleteSearchCriteria criteria, Pageable pageable, CommonModel common);

	void registerAutocomleteDesign(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common);

	/**
	 * Delete autocomplete and related sql design
	 *
	 * @param autocompleteDesignCompound the autocomplete design compound
	 * @return true, if successful
	 */
	boolean delete(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common);

	/**
	 * Find autocomplete by id.
	 *
	 * @param autocompleteId the autocomplete id
	 * @return the autocomplete design
	 */
	AutocompleteDesign findOneById(Long autocompleteId);

	/**
	 * Find autocomplete compound by id.
	 *
	 * @param autocompleteId the autocomplete id
	 * @return the autocomplete design compound
	 */
	AutocompleteDesignCompound findCompoundById(Long autocompleteId);

	/**
	 * Modify autocomlete design.
	 *
	 * @param autocomplete the autocomplete
	 */
	void modifyAutocomleteDesign(AutocompleteDesignCompound autocomplete, CommonModel common);

	void modifyAdvancedAutocomleteDesign(AutocompleteDesignCompound autocomplete, CommonModel common);
	
	void registerAdvancedAutocomleteDesign(AutocompleteDesignCompound autocomplete, CommonModel common);

	TableDesignForeignKey findForeignKeyBetweenTwoTables(Long tableId,Long joinTableId);
	
	void modifyDesignStatus(AutocompleteDesignCompound autocompleteDesignCompound, CommonModel common);
	
	List<TableDesignDetails> getAllTableColumnByProject(Long projectId);

	void findAllDeletionAffection(AutocompleteDesignCompound sqlDesignCompound, CommonModel common);
}
