package org.terasoluna.qp.domain.service.screendesign;

public class Event {
	private String eventtype;
	private String effectareatype;
	private String effectarea;
	private String blogicid;
	private String blogicname;
	
	private Bean[] inputbeans;
	private Bean[] outputbeans;
	public String getEventtype() {
		return eventtype;
	}
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	public String getEffectareatype() {
		if (this.effectareatype == null || this.effectareatype.equals("null") || this.effectareatype.equals("undefined")) {
			return "";
		}
		return effectareatype;
	}
	public void setEffectareatype(String effectareatype) {
		this.effectareatype = effectareatype;
	}
	public String getEffectarea() {
		if (this.effectarea == null || this.effectarea.equals("null") || this.effectarea.equals("undefined")) {
			return "";
		}
		return effectarea;
	}
	public void setEffectarea(String effectarea) {
		this.effectarea = effectarea;
	}
	public String getBlogicid() {
		if (this.blogicid == null || this.blogicid.equals("null") || this.blogicid.equals("undefined")) {
			return "";
		}
		return blogicid;
	}
	public void setBlogicid(String blogicid) {
		this.blogicid = blogicid;
	}
	public String getBlogicname() {
		if (this.blogicname == null || this.blogicname.equals("null") || this.blogicname.equals("undefined")) {
			return "";
		}
		return blogicname;
	}
	public void setBlogicname(String blogicname) {
		this.blogicname = blogicname;
	}
	public Bean[] getInputbeans() {
		return inputbeans;
	}
	public void setInputbeans(Bean[] inputbeans) {
		this.inputbeans = inputbeans;
	}
	public Bean[] getOutputbeans() {
		return outputbeans;
	}
	public void setOutputbeans(Bean[] outputbeans) {
		this.outputbeans = outputbeans;
	}
	
	
}
