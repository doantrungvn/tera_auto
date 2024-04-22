package org.terasoluna.qp.domain.repository.sampletype;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.SampleType;

@Repository
public interface SampleTypeRepository {	
	List<SampleType> getSampleTypes();
}
