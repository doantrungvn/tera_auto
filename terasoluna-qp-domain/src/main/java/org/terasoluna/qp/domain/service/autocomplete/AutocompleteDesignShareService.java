package org.terasoluna.qp.domain.service.autocomplete;

import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;

public interface AutocompleteDesignShareService {
	
	public ImpactChangeOfAutocompleteDesign detectListAffectedWhenModify(Long autocompleteId,CommonModel common,Boolean isRunBatch);
	
	public ImpactChangeOfAutocompleteDesign detectListAffectedWhenDelete(AutocompleteDesign autocomplete,CommonModel common,Boolean isRunBatch);
	
	public void detectListAffectedWhenDeleteOfBatch(Long autocompleteId, String autocompleteCode, CommonModel common);
	
	public void detectListAffectedWhenModifyOfBatch(Long autocompleteId,CommonModel common);

}
