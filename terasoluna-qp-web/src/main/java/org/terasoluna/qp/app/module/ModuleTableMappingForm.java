package org.terasoluna.qp.app.module;

import java.io.Serializable;

public class ModuleTableMappingForm implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Long moduleId;
	
	private Long moduleTableMappingId;
	
	private String tblDesignId;
	
	private String tblDesignName;
	
	private String tblDesignCode;
	
	private String tblDesignIdAutocomplete;
	
	private String tableMappingType;

	private String areaPatternType;
	
	public String getAreaPatternType() {
		return areaPatternType;
	}

	public void setAreaPatternType(String areaPatternType) {
		this.areaPatternType = areaPatternType;
	}

	public Long getModuleTableMappingId() {
		return moduleTableMappingId;
	}

	public void setModuleTableMappingId(Long moduleTableMappingId) {
		this.moduleTableMappingId = moduleTableMappingId;
	}

	public String getTblDesignId() {
		return tblDesignId;
	}

	public void setTblDesignId(String tblDesignId) {
		this.tblDesignId = tblDesignId;
	}

	public String getTblDesignName() {
		return tblDesignName == null ? tblDesignIdAutocomplete : tblDesignName;
	}

	public void setTblDesignName(String tblDesignName) {
		this.tblDesignName = tblDesignName;
	}

	public String getTableMappingType() {
		return tableMappingType;
	}

	public void setTableMappingType(String tableMappingType) {
		this.tableMappingType = tableMappingType;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getTblDesignIdAutocomplete() {
		return tblDesignIdAutocomplete;
	}

	public void setTblDesignIdAutocomplete(
			String tblDesignIdAutocomplete) {
		this.tblDesignIdAutocomplete = tblDesignIdAutocomplete;
	}

	public String getTblDesignCode() {
	    return tblDesignCode;
    }

	public void setTblDesignCode(String tblDesignCode) {
	    this.tblDesignCode = tblDesignCode;
    }
}
