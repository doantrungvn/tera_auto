package org.terasoluna.qp.app.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;

public class DateTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String value;
	protected String type;
	protected String pattern;
	protected Object timeZone;
	protected String dateStyle;
	protected String timeStyle;

	private String var;
	private int scope;

	public DateTag() {
		init();
	}

	private void init() {
		this.type = (this.dateStyle = this.timeStyle = null);
		this.pattern = (this.var = null);
		this.value = null;
		this.timeZone = null;
		this.dateStyle = null;
		this.scope = 1;
	}

	// 'type' attribute
	public void setType(String type) throws JspTagException {
		this.type = type;
	}

	// 'type' attribute
	public void setVar(String var) {
		this.var = var;
	}

	// 'scope' attribute
	public void setScope(String scope) {
		this.scope = Util.getScope(scope);
	}

	// 'value' attribute
	public void setValue(String value) {
		this.value = value;
	}

	// 'pattern' attribute
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	// 'timeZone' attribute
	public void setTimeZone(Object timeZone) throws JspTagException {
		this.timeZone = timeZone;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String formatted = null;
			if (this.value.equals(null) || this.value.length() == 0) {
				return 6;
			}

			if (type.equals("date")) {
				formatted = DateUtils.formatDate(this.value, SessionUtils.getCurrentAccountProfile().getDateFormat());
			} else if (type.equals("datetime")) {
				formatted = DateUtils.formatDateTime(this.value, SessionUtils.getCurrentAccountProfile().getDateTimeFormat());
			} else if (type.equals("time")) {
				formatted = DateUtils.formatTime(this.value, SessionUtils.getCurrentAccountProfile().getTimeFormat());
			}

			if (this.value == null) {
				if (this.var != null) {
					this.pageContext.removeAttribute(this.var, this.scope);
				}
				return 6;
			}

			if (this.var != null) {
				this.pageContext.setAttribute(this.var, formatted, this.scope);
			} else {
				try {
					this.pageContext.getOut().print(formatted);
				} catch (IOException ioe) {
					throw new JspTagException(ioe.toString(), ioe);
				}
			}
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			try {
				this.pageContext.getOut().print(this.value);
			} catch (IOException ioe) {
				throw new JspTagException(ioe.toString(), ioe);
			}
		}
		return 6;
	}

	public void release() {
		init();
	}

}
