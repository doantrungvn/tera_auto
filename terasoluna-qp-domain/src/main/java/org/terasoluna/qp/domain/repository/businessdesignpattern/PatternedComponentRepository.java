package org.terasoluna.qp.domain.repository.businessdesignpattern;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.PatternedComponent;
import org.terasoluna.qp.domain.model.PatternedDetail;
import org.terasoluna.qp.domain.model.PatternedDetailConnector;

public interface PatternedComponentRepository {

	List<PatternedComponent> findPatternedComponentByProject(@Param("projectId") Long projectId);

	List<PatternedDetail> findPatternedDetailByProject(@Param("projectId") Long projectId);

	List<PatternedDetailConnector> findPatternedDetailConnectorByProject(@Param("projectId") Long projectId);

}
