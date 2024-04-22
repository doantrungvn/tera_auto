package org.terasoluna.qp.app.screendesign;

import java.io.Serializable;
import java.util.List;

public class ScreenDesignStatusForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long screenId;
	
	private List<ScreenItemStatusForm> listOfScreenItemStatusForm;

	public List<ScreenItemStatusForm> getListOfScreenItemStatusForm() {
		return listOfScreenItemStatusForm;
	}

	public void setListOfScreenItemStatusForm(
			List<ScreenItemStatusForm> listOfScreenItemStatusForm) {
		this.listOfScreenItemStatusForm = listOfScreenItemStatusForm;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

}
