package org.terasoluna.qp.domain.service.sample;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.qp.domain.model.Sample;
import org.terasoluna.qp.domain.model.SampleChild;
import org.terasoluna.qp.domain.repository.sample.SampleCriteria;
import org.terasoluna.qp.domain.repository.sample.SampleRepository;

@Service
@Transactional
public class SampleServiceImpl implements SampleService {

	@Inject
	SampleRepository sampleRepository;

	/**
	 * search sample by input criteria
	 */
	@Override
	public Page<Sample> findPageByCriteria(SampleCriteria sampleCriteria,
			Pageable pageable) {
		// TODO Auto-generated method stub
		long totalCount = sampleRepository.countByCriteria(sampleCriteria);

		List<Sample> samples;
		if (0 < totalCount) {
			samples = sampleRepository.findPageByCriteria(sampleCriteria,
					pageable);
		} else {
			samples = Collections.emptyList();
		}

		Page<Sample> page = new PageImpl<Sample>(samples, pageable, totalCount);

		return page;
	}

	/**
	 * Add a new sample
	 */
	@Override
	public void addSample(Sample sample) {
		// TODO Auto-generated method stub
		sampleRepository.addSample(sample);
		for (int i = 0; i < sample.getSampleChild().length; i++) {
			sample.getSampleChild()[i].setSample(sample);
		}
		sampleRepository.addSampleChilds(sample.getSampleChild());

	}

	/**
	 * Get sample by id
	 */
	@Override
	public Sample findOne(int columnId) {
		// TODO Auto-generated method stub

		Sample obj = sampleRepository.findOne(columnId);
		obj.setSampleChild(sampleRepository.findChilds(obj.getColumnId()));
		return obj;
	}

	/**
	 * Update a sample
	 */
	@Override	
	public void updateSample(Sample sample) {
		// TODO Auto-generated method stub
		// update sample
		if (sample.getColumnImage() != null
				&& sample.getColumnImage().length > 0) {
			sampleRepository.updateSample(sample);
		} else {
			sampleRepository.updateSampleNonImage(sample);
		}
		SampleChild[] childs = sampleRepository
				.findChilds(sample.getColumnId());

		if (sample.getSampleChild() != null) {
			// update and add child
			for (SampleChild sampleChild : sample.getSampleChild()) {
				boolean isExists = false;

				for (SampleChild item : childs) {
					if (item.getColumnId() == sampleChild.getColumnId()) {
						sampleRepository.updateSampleChild(sampleChild);
						isExists = true;
					}
				}
				if (!isExists || childs.length == 0) {
					sampleChild.setSample(sample);
					sampleRepository.addSampleChild(sampleChild);
				}
			}
		}
		// delete
		for (SampleChild item : childs) {
			boolean isExists = false;
			if (sample.getSampleChild() != null) {
				for (SampleChild itemNew : sample.getSampleChild()) {
					if (item.getColumnId() == itemNew.getColumnId()) {
						isExists = true;
					}
				}
			}
			if (!isExists) {
				sampleRepository.deleteSampleChildById(item.getColumnId());
			}
		}

	}

	/**
	 * Delete sample
	 */
	@Override
	public void deleteSample(Integer columnId) {
		//Delete child
		sampleRepository.deleteSampleChildBySampleId(columnId);
		//delete parent
		sampleRepository.deleteSample(columnId);
	}
}
