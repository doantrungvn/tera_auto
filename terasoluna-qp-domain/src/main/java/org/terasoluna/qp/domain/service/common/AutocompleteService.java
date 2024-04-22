package org.terasoluna.qp.domain.service.common;

import java.util.List;

import org.terasoluna.qp.domain.model.Autocomplete;

public interface AutocompleteService {
	List<Autocomplete> list(AutocompleteInput autocompleteInput, String sourceType);
	
}
