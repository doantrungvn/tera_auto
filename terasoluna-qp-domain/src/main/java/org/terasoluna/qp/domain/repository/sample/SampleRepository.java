package org.terasoluna.qp.domain.repository.sample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.terasoluna.qp.domain.model.Sample;
import org.terasoluna.qp.domain.model.SampleChild;

@Repository
public interface SampleRepository {
	List<Sample> findPageByCriteria(@Param("criteria") SampleCriteria criteria, @Param("pageable") Pageable pageable);
	long countByCriteria(@Param("criteria") SampleCriteria criteria);
	int addSample(Sample sample);	
	void addSampleChilds(@Param("sampleChild") SampleChild[] sampleChild);
	Sample findOne(@Param("columnId") int columnId);
	SampleChild[] findChilds(@Param("parentId") int parentId);
	void updateSample(Sample sample);
	void updateSampleNonImage(Sample sample);
	void updateSampleChild(SampleChild sampleChild);
	void deleteSampleChildById(@Param("columnId") int columnId);
	void addSampleChild(@Param("sampleChild") SampleChild sampleChild);	
	void deleteSample(@Param("columnId") Integer columnId);
	void deleteSampleChildBySampleId(@Param("sampleId")Integer sampleId);
}
