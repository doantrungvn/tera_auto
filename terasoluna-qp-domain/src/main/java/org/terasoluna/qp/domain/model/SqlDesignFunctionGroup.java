package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class SqlDesignFunctionGroup implements Serializable {
	
	private static final long serialVersionUID = 9143514127564718931L;
	private String groupCode;
	private List<SqlDesignFunction> sqlDesignFunctions;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public List<SqlDesignFunction> getSqlDesignFunctions() {
		return sqlDesignFunctions;
	}
	public void setSqlDesignFunctions(List<SqlDesignFunction> sqlDesignFunctions) {
		this.sqlDesignFunctions = sqlDesignFunctions;
	}
}
