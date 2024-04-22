package org.terasoluna.qp.app.viewdesign;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.ViewDesignMessageConst;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm;

public class ViewDesignForm extends SqlDesignForm{
	
	private static final long serialVersionUID = 1051264456447197327L;
	@NotEmpty(message = ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_NAME,groups=ViewDesignValidationGroup.class)
	@QpNameSize(message =  ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_NAME,groups=ViewDesignValidationGroup.class)
	@QpNamePattern(message= ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_NAME,groups=ViewDesignValidationGroup.class)
	private String sqlDesignName;
	@NotEmpty(message =  ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE,groups=ViewDesignValidationGroup.class)
	@QpCodeSize(message=ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE,groups=ViewDesignValidationGroup.class)
	@QpCodePattern(message = ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE,groups=ViewDesignValidationGroup.class)
	private String sqlDesignCode;
	
	public String getSqlDesignName() {
		return sqlDesignName;
	}
	public void setSqlDesignName(String sqlDesignName) {
		this.sqlDesignName = sqlDesignName;
	}
	public String getSqlDesignCode() {
		return sqlDesignCode;
	}
	public void setSqlDesignCode(String sqlDesignCode) {
		this.sqlDesignCode = sqlDesignCode;
	}
}
