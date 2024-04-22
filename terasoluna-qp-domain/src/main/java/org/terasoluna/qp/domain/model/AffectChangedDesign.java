/**
 * 
 */
package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author datld
 *
 */
public class AffectChangedDesign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<BusinessLogic> listBusinessLogics;
	
	List<SqlDesign> listSqlDesigns;
	
	List<TableDesignForeignKey> listTableDesignForeignKeyAffect;

	public List<BusinessLogic> getListBusinessLogics() {
		return listBusinessLogics;
	}

	public void setListBusinessLogics(List<BusinessLogic> listBusinessLogics) {
		this.listBusinessLogics = listBusinessLogics;
	}

	public List<SqlDesign> getListSqlDesigns() {
		return listSqlDesigns;
	}

	public void setListSqlDesigns(List<SqlDesign> listSqlDesigns) {
		this.listSqlDesigns = listSqlDesigns;
	}

	public List<TableDesignForeignKey> getListTableDesignForeignKeyAffect() {
		return listTableDesignForeignKeyAffect;
	}

	public void setListTableDesignForeignKeyAffect(
			List<TableDesignForeignKey> listTableDesignForeignKeyAffect) {
		this.listTableDesignForeignKeyAffect = listTableDesignForeignKeyAffect;
	}
}
