package org.terasoluna.qp.domain.repository.sqldesign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.terasoluna.qp.domain.model.SqlDesignOrder;

public interface SqlDesignOrderRepository {
	
	SqlDesignOrder findById(Long sqlDesignOrderId);

	void register(SqlDesignOrder sqlDesignOrder);
	
	void registerAll(@Param("sqlDesignOrders") SqlDesignOrder[] sqlDesignOrders);

	void modify(SqlDesignOrder sqlDesignOrder);
	
	void modifyAll(@Param("sqlDesignOrders") SqlDesignOrder[] sqlDesignOrders);
	
	void delete(SqlDesignOrder sqlDesignOrder);

	SqlDesignOrder[] findAllBySqlDesignId(Long sqlDesignId);
	
	Boolean deleteByExceptionalGroup(@Param("sqlDesignId") Long sqlDesignId,@Param("exceptionGroup")List<Long> exceptionGroup);
	
	Boolean deleteBySqlDesignId(@Param("sqlDesignId") Long sqlDesignId);
}
