package org.terasoluna.qp.domain.repository.sqldesign;

import org.terasoluna.qp.domain.model.SqlDesignParameters;

public interface SqlDesignParametersRepository {
	
	SqlDesignParameters findById(Long sqlDesignParametersId);

	void create(SqlDesignParameters sqlDesignParameters);

	void update(SqlDesignParameters sqlDesignParameters);
	
	void delete(SqlDesignParameters sqlDesignParameters);
}
