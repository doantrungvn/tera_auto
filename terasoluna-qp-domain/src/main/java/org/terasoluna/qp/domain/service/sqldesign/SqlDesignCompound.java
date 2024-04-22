package org.terasoluna.qp.domain.service.sqldesign;

import java.io.Serializable;
import java.util.List;

import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignGroupBy;
import org.terasoluna.qp.domain.model.SqlDesignHaving;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOrder;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesign;

public class SqlDesignCompound implements Serializable {
	
	private static final long serialVersionUID = -6329307952517965105L;

	private SqlDesign sqlDesign;
	
	private SqlDesignResult[] sqlDesignResults;
	
	private SqlDesignCondition[] sqlDesignConditions;
	
	private SqlDesignTable[] sqlDesignTables;
	
	private SqlDesignTable sqlDesignTable;
	
	private SqlDesignGroupBy[] sqlDesignGroupBys;
	
	private SqlDesignHaving[] sqlDesignHavings;
	
	private SqlDesignOrder[] sqlDesignOrders;
	
	private SqlDesignInput[] sqlDesignInputs;
	
	private SqlDesignOutput[] sqlDesignOutputs;
	
	private SqlDesignValue[] sqlDesignValues;
	
	private List<BusinessDesign> affectedBusinessDesigns;
	
	private List<ScreenDesign> affectedScreenDesigns;
	 
	private List<DomainDesign> affectedDomainDesigns;
	
	private List<TableDesign> affectedTableDesigns;
	
	private String dialect;
	
	public SqlDesign getSqlDesign() {
		return sqlDesign;
	}

	public void setSqlDesign(SqlDesign sqlDesign) {
		this.sqlDesign = sqlDesign;
	}

	public SqlDesignResult[] getSqlDesignResults() {
		return sqlDesignResults;
	}

	public void setSqlDesignResults(SqlDesignResult[] sqlDesignResults) {
		this.sqlDesignResults = sqlDesignResults;
	}

	public SqlDesignCondition[] getSqlDesignConditions() {
		return sqlDesignConditions;
	}

	public void setSqlDesignConditions(SqlDesignCondition[] sqlDesignConditions) {
		this.sqlDesignConditions = sqlDesignConditions;
	}

	public SqlDesignTable[] getSqlDesignTables() {
		return sqlDesignTables;
	}

	public void setSqlDesignTables(SqlDesignTable[] sqlDesignTable) {
		this.sqlDesignTables = sqlDesignTable;
	}

	public SqlDesignGroupBy[] getSqlDesignGroupBys() {
		return sqlDesignGroupBys;
	}

	public void setSqlDesignGroupBys(SqlDesignGroupBy[] sqlDesignGroupBy) {
		this.sqlDesignGroupBys = sqlDesignGroupBy;
	}

	public SqlDesignHaving[] getSqlDesignHavings() {
		return sqlDesignHavings;
	}

	public void setSqlDesignHavings(SqlDesignHaving[] sqlDesignHaving) {
		this.sqlDesignHavings = sqlDesignHaving;
	}

	public SqlDesignOrder[] getSqlDesignOrders() {
		return sqlDesignOrders;
	}

	public void setSqlDesignOrders(SqlDesignOrder[] sqlDesignOrder) {
		this.sqlDesignOrders = sqlDesignOrder;
	}

	public SqlDesignInput[] getSqlDesignInputs() {
		return sqlDesignInputs;
	}

	public void setSqlDesignInputs(SqlDesignInput[] sqlDesignInput) {
		this.sqlDesignInputs = sqlDesignInput;
	}

	public SqlDesignOutput[] getSqlDesignOutputs() {
		return sqlDesignOutputs;
	}

	public void setSqlDesignOutputs(SqlDesignOutput[] sqlDesignOutput) {
		this.sqlDesignOutputs = sqlDesignOutput;
	}

	public SqlDesignTable getSqlDesignTable() {
		return sqlDesignTable;
	}

	public void setSqlDesignTable(SqlDesignTable sqlDesignTable) {
		this.sqlDesignTable = sqlDesignTable;
	}

	public SqlDesignValue[] getSqlDesignValues() {
		return sqlDesignValues;
	}

	public void setSqlDesignValues(SqlDesignValue[] sqlDesignValues) {
		this.sqlDesignValues = sqlDesignValues;
	}

	public List<BusinessDesign> getAffectedBusinessDesigns() {
		return affectedBusinessDesigns;
	}

	public void setAffectedBusinessDesigns(List<BusinessDesign> affectedBusinessDesigns) {
		this.affectedBusinessDesigns = affectedBusinessDesigns;
	}

	public List<ScreenDesign> getAffectedScreenDesigns() {
		return affectedScreenDesigns;
	}

	public void setAffectedScreenDesigns(List<ScreenDesign> affectedScreenDesigns) {
		this.affectedScreenDesigns = affectedScreenDesigns;
	}

	public List<DomainDesign> getAffectedDomainDesigns() {
		return affectedDomainDesigns;
	}

	public void setAffectedDomainDesigns(List<DomainDesign> affectedDomainDesigns) {
		this.affectedDomainDesigns = affectedDomainDesigns;
	}

	public List<TableDesign> getAffectedTableDesigns() {
		return affectedTableDesigns;
	}

	public void setAffectedTableDesigns(List<TableDesign> affectedTableDesigns) {
		this.affectedTableDesigns = affectedTableDesigns;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
}
