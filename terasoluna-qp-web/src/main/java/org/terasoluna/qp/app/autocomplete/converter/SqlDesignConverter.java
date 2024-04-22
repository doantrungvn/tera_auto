package org.terasoluna.qp.app.autocomplete.converter;

import org.dozer.DozerConverter;
import org.terasoluna.qp.app.autocomplete.AutocompleteForm;
import org.terasoluna.qp.domain.model.SqlDesign;

public class SqlDesignConverter extends DozerConverter<AutocompleteForm, SqlDesign>  {

	public SqlDesignConverter() {
		super(AutocompleteForm.class, SqlDesign.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AutocompleteForm convertFrom(SqlDesign sqlDesign, AutocompleteForm autocompleteForm) {
		if(sqlDesign!=null) {
			if(autocompleteForm==null) {
				autocompleteForm = new AutocompleteForm();
			}
			if(sqlDesign.getOmitOverlap()==1) {
				autocompleteForm.setOmitOverlap(true);
			} else autocompleteForm.setOmitOverlap(false);
			
		}
		return autocompleteForm;
	}

	@Override
	public SqlDesign convertTo(AutocompleteForm autocompleteForm, SqlDesign sqlDesign) {
		if(autocompleteForm!=null) {
			if(sqlDesign==null) {
				sqlDesign = new SqlDesign();
			}
			if(!(autocompleteForm.getOmitOverlap()==null || !autocompleteForm.getOmitOverlap())) {
				sqlDesign.setOmitOverlap(1);
			} else sqlDesign.setOmitOverlap(0);
			
		}
		return sqlDesign;
	}
}
