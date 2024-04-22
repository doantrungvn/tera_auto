package org.terasoluna.qp.domain.service.sampletype;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.domain.model.SampleType;
import org.terasoluna.qp.domain.repository.sampletype.SampleTypeRepository;

@Service
@Transactional
public class SampleTypeServiceImpl implements SampleTypeService {

	@Inject
	SampleTypeRepository sampleTypeRepository;
	@Override
	public List<SampleType> getSampleTypes() {
		// TODO Auto-generated method stub
		return sampleTypeRepository.getSampleTypes();
	}

}
