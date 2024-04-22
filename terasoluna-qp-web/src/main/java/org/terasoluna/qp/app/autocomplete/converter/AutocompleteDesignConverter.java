package org.terasoluna.qp.app.autocomplete.converter;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.autocomplete.AutocompleteForm;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AutocompleteDesign;

public class AutocompleteDesignConverter extends DozerConverter<AutocompleteForm, AutocompleteDesign>  {

	public AutocompleteDesignConverter() {
		super(AutocompleteForm.class, AutocompleteDesign.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public AutocompleteForm convertFrom(AutocompleteDesign autocompleteDesign,	AutocompleteForm autocompleteForm) {
		if(autocompleteDesign!=null){
			if(autocompleteForm==null) {
				autocompleteForm = new AutocompleteForm();
			}
			autocompleteForm.setAutocompleteId(autocompleteDesign.getAutocompleteId().toString());
			autocompleteForm.setAutocompleteName(autocompleteDesign.getAutocompleteName());
			autocompleteForm.setAutocompleteCode(autocompleteDesign.getAutocompleteCode());
			autocompleteForm.setDisplayColumnIdAutocomplete(autocompleteDesign.getDisplayColumn());
			autocompleteForm.setMatchingType(autocompleteDesign.getMatchingType().toString());
			autocompleteForm.setMinLength(autocompleteDesign.getMinLength().toString());
			autocompleteForm.setModuleId(autocompleteDesign.getModuleId()==null?StringUtils.EMPTY:autocompleteDesign.getModuleId().toString());
			autocompleteForm.setModuleIdAutocomplete(autocompleteDesign.getModuleName());
			autocompleteForm.setOutput01ColumnIdAutocomplete(autocompleteDesign.getOutput01());
			autocompleteForm.setOutput02ColumnIdAutocomplete(autocompleteDesign.getOutput02());
			autocompleteForm.setOutput03ColumnIdAutocomplete(autocompleteDesign.getOutput03());
			autocompleteForm.setOutput04ColumnIdAutocomplete(autocompleteDesign.getOutput04());
			autocompleteForm.setOutput05ColumnIdAutocomplete(autocompleteDesign.getOutput05());
			autocompleteForm.setOutput06ColumnIdAutocomplete(autocompleteDesign.getOutput06());
			autocompleteForm.setOutput07ColumnIdAutocomplete(autocompleteDesign.getOutput07());
			autocompleteForm.setOutput08ColumnIdAutocomplete(autocompleteDesign.getOutput08());
			autocompleteForm.setOutput09ColumnIdAutocomplete(autocompleteDesign.getOutput09());
			autocompleteForm.setOutput10ColumnIdAutocomplete(autocompleteDesign.getOutput10());
			autocompleteForm.setOutput11ColumnIdAutocomplete(autocompleteDesign.getOutput11());
			autocompleteForm.setOutput12ColumnIdAutocomplete(autocompleteDesign.getOutput12());
			autocompleteForm.setOutput13ColumnIdAutocomplete(autocompleteDesign.getOutput13());
			autocompleteForm.setOutput14ColumnIdAutocomplete(autocompleteDesign.getOutput14());
			autocompleteForm.setOutput15ColumnIdAutocomplete(autocompleteDesign.getOutput15());
			autocompleteForm.setOutput16ColumnIdAutocomplete(autocompleteDesign.getOutput16());
			autocompleteForm.setOutput17ColumnIdAutocomplete(autocompleteDesign.getOutput17());
			autocompleteForm.setOutput18ColumnIdAutocomplete(autocompleteDesign.getOutput18());
			autocompleteForm.setOutput19ColumnIdAutocomplete(autocompleteDesign.getOutput19());
			autocompleteForm.setOutput20ColumnIdAutocomplete(autocompleteDesign.getOutput20());
			autocompleteForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
//			autocompleteForm.setProjectIdAutocomplete(autocompleteDesign.getProjectName());
			autocompleteForm.setSubmitColumnId(autocompleteDesign.getSubmitColumn());
			autocompleteForm.setSubmitColumnIdAutocomplete(autocompleteDesign.getSubmitColumn());
			autocompleteForm.setUpdatedDate(autocompleteDesign.getUpdatedDate());
			autocompleteForm.setDesignStatus(String.valueOf(autocompleteDesign.getDesignStatus()));
			autocompleteForm.setRemark(autocompleteDesign.getRemark());
		}
		return autocompleteForm;
	}
	
	

	@Override
	public AutocompleteDesign convertTo(AutocompleteForm autocompleteForm, AutocompleteDesign autocompleteDesign) {
		if(autocompleteForm!=null){
			if(autocompleteDesign==null){
				autocompleteDesign = new AutocompleteDesign();
			}
			autocompleteDesign.setAutocompleteId(DataTypeUtils.convertTo(autocompleteForm.getAutocompleteId(),Long.class));
			autocompleteDesign.setAutocompleteName(autocompleteForm.getAutocompleteName());
			autocompleteDesign.setAutocompleteCode(autocompleteForm.getAutocompleteCode());
			autocompleteDesign.setDisplayColumn(autocompleteForm.getDisplayColumnIdAutocomplete());
			autocompleteDesign.setMatchingType(DataTypeUtils.convertTo(autocompleteForm.getMatchingType(),Integer.class));
			autocompleteDesign.setMinLength(DataTypeUtils.convertTo(autocompleteForm.getMinLength(),Integer.class));
			autocompleteDesign.setModuleId(StringUtils.isEmpty(autocompleteForm.getModuleId())?null:DataTypeUtils.convertTo(autocompleteForm.getModuleId(),Long.class));
			autocompleteDesign.setModuleName(autocompleteForm.getModuleIdAutocomplete());
			autocompleteDesign.setOutput01(autocompleteForm.getOutput01ColumnIdAutocomplete());
			autocompleteDesign.setOutput02(autocompleteForm.getOutput02ColumnIdAutocomplete());
			autocompleteDesign.setOutput03(autocompleteForm.getOutput03ColumnIdAutocomplete());
			autocompleteDesign.setOutput04(autocompleteForm.getOutput04ColumnIdAutocomplete());
			autocompleteDesign.setOutput05(autocompleteForm.getOutput05ColumnIdAutocomplete());
			autocompleteDesign.setOutput06(autocompleteForm.getOutput06ColumnIdAutocomplete());
			autocompleteDesign.setOutput07(autocompleteForm.getOutput07ColumnIdAutocomplete());
			autocompleteDesign.setOutput08(autocompleteForm.getOutput08ColumnIdAutocomplete());
			autocompleteDesign.setOutput09(autocompleteForm.getOutput09ColumnIdAutocomplete());
			autocompleteDesign.setOutput10(autocompleteForm.getOutput10ColumnIdAutocomplete());
			autocompleteDesign.setOutput11(autocompleteForm.getOutput11ColumnIdAutocomplete());
			autocompleteDesign.setOutput12(autocompleteForm.getOutput12ColumnIdAutocomplete());
			autocompleteDesign.setOutput13(autocompleteForm.getOutput13ColumnIdAutocomplete());
			autocompleteDesign.setOutput14(autocompleteForm.getOutput14ColumnIdAutocomplete());
			autocompleteDesign.setOutput15(autocompleteForm.getOutput15ColumnIdAutocomplete());
			autocompleteDesign.setOutput16(autocompleteForm.getOutput16ColumnIdAutocomplete());
			autocompleteDesign.setOutput17(autocompleteForm.getOutput17ColumnIdAutocomplete());
			autocompleteDesign.setOutput18(autocompleteForm.getOutput18ColumnIdAutocomplete());
			autocompleteDesign.setOutput19(autocompleteForm.getOutput19ColumnIdAutocomplete());
			autocompleteDesign.setOutput20(autocompleteForm.getOutput20ColumnIdAutocomplete());
			autocompleteDesign.setProjectId(SessionUtils.getCurrentProjectId());
//			autocompleteDesign.setProjectName(autocompleteForm.getProjectIdAutocomplete());
			autocompleteDesign.setSubmitColumn(autocompleteForm.getSubmitColumnId());
			autocompleteDesign.setUpdatedDate(autocompleteForm.getUpdatedDate());
			autocompleteDesign.setDesignStatus(DataTypeUtils.convertTo(autocompleteForm.getDesignStatus(), Integer.class));
			autocompleteDesign.setRemark(autocompleteForm.getRemark());
		}
		return autocompleteDesign;
	}

	
}
