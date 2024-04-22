package org.terasoluna.qp.domain.repository.impactchange;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.OutputBean;

public interface ImpactSessionManagementRepository {

	List<InputBean> findInputbeanBySessionId(@Param("sessionManagementId") Long sessionManagementId);

	List<OutputBean> findOutputbeanBySessionId(@Param("sessionManagementId") Long sessionManagementId);
}
