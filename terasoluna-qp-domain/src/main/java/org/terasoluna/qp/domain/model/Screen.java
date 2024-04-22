package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class Screen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int screen_id;
	private int parent;
	private int module_id;
	private String screen_cd;
	private String screen_desc_cd;

	public int getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(int screen_id) {
		this.screen_id = screen_id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getModule_id() {
		return module_id;
	}

	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}

	public String getScreen_cd() {
		return screen_cd;
	}

	public void setScreen_cd(String screen_cd) {
		this.screen_cd = screen_cd;
	}

	public String getScreen_desc_cd() {
		return screen_desc_cd;
	}

	public void setScreen_desc_cd(String screen_desc_cd) {
		this.screen_desc_cd = screen_desc_cd;
	}

}
