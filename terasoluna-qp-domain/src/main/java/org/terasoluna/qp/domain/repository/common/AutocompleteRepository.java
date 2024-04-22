
package org.terasoluna.qp.domain.repository.common;
import java.util.List;

import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.service.common.AutocompleteInput;

public interface AutocompleteRepository {
	List<Autocomplete> list(AutocompleteInput autocompleteInput);
}
