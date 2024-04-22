package org.terasoluna.qp.domain.service.sample;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.qp.domain.model.Sample;
import org.terasoluna.qp.domain.repository.sample.SampleCriteria;

public interface SampleService {
	Page<Sample> findPageByCriteria(SampleCriteria sampleCriteria, Pageable pageable);
	void addSample(Sample sample);	
	void deleteSample(Integer columnId);
	void updateSample(Sample sample);
	Sample findOne(int columnId);
}
