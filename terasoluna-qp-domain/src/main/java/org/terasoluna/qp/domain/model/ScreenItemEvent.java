package org.terasoluna.qp.domain.model;

import java.util.List;

public class ScreenItemEvent {
	private Long screenItemEventId;
	
	private Integer itemEventSeqNo;
	
	private Long screenItemId;
	
	private Integer eventType = 2;
	
	private Integer effectAreaType;
	
	private String effectArea;
	
	private Long blogicId;
	
	private BusinessLogic businessLogic;

	private List<ScreenItemEventMapping> screenItemEventMappings;
	
	public ScreenItemEvent(Integer itemEventSeqNo, Integer  eventType, Integer effectAreaType, String effectArea, Long blogicId) {
		this.itemEventSeqNo = itemEventSeqNo;
		this.eventType = eventType;
		this.effectAreaType = effectAreaType;
		this.effectArea = effectArea;
		this.blogicId = blogicId;
	}
	
	public ScreenItemEvent() {
		
	}

	public Integer getItemEventSeqNo() {
		return itemEventSeqNo;
	}

	public void setItemEventSeqNo(Integer itemEventSeqNo) {
		this.itemEventSeqNo = itemEventSeqNo;
	}

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Integer getEffectAreaType() {
		return effectAreaType;
	}

	public void setEffectAreaType(Integer effectAreaType) {
		this.effectAreaType = effectAreaType;
	}

	public String getEffectArea() {
		return effectArea;
	}

	public void setEffectArea(String effectArea) {
		this.effectArea = effectArea;
	}

	public Long getBlogicId() {
		return blogicId;
	}

	public void setBlogicId(Long blogicId) {
		this.blogicId = blogicId;
	}

	public Long getScreenItemEventId() {
		return screenItemEventId;
	}

	public void setScreenItemEventId(Long screenItemEventId) {
		this.screenItemEventId = screenItemEventId;
	}

	public List<ScreenItemEventMapping> getScreenItemEventMappings() {
		return screenItemEventMappings;
	}

	public void setScreenItemEventMappings(List<ScreenItemEventMapping> screenItemEventMappings) {
		this.screenItemEventMappings = screenItemEventMappings;
	}

	public BusinessLogic getBusinessLogic() {
		return businessLogic;
	}

	public void setBusinessLogic(BusinessLogic businessLogic) {
		this.businessLogic = businessLogic;
	}
	
	
	
}
