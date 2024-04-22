package org.terasoluna.qp.domain.service.autocomplete;

import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;

public class AutocompleteDesignCompound extends SqlDesignCompound {
	
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	private AutocompleteDesign autocomplete;
	
	private Boolean actionDelete;

	public AutocompleteDesign getAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(AutocompleteDesign autocomplete) {
		this.autocomplete = autocomplete;
	}
	
	@Override
	public void setSqlDesign(SqlDesign sqlDesign) {
		this.autocomplete.setSqlDesign(sqlDesign);
		super.setSqlDesign(sqlDesign);
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

}
