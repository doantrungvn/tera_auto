package org.terasoluna.qp.domain.model;

import java.util.ArrayList;
import java.util.List;

public class SequenceLogicExt  extends SequenceLogic{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SequenceLogicExt> lstChildLogics = new ArrayList<SequenceLogicExt>();
	
	private List<SequenceLogicExt> lstChildOfForLogics = new ArrayList<SequenceLogicExt>();
	
	private ArrayList<ArrayList<SequenceLogicExt>> lstChildOfIfLogics = new ArrayList<ArrayList<SequenceLogicExt>>();
	
	private SequenceLogicExt nextSequenceLogic;

	public List<SequenceLogicExt> getLstChildLogics() {
		return lstChildLogics;
	}

	public void setLstChildLogics(List<SequenceLogicExt> lstChildLogics) {
		this.lstChildLogics = lstChildLogics;
	}

	public List<SequenceLogicExt> getLstChildOfForLogics() {
		return lstChildOfForLogics;
	}

	public void setLstChildOfForLogics(List<SequenceLogicExt> lstChildOfForLogics) {
		this.lstChildOfForLogics = lstChildOfForLogics;
	}

	public ArrayList<ArrayList<SequenceLogicExt>> getLstChildOfIfLogics() {
		return lstChildOfIfLogics;
	}

	public void setLstChildOfIfLogics(ArrayList<ArrayList<SequenceLogicExt>> lstChildOfIfLogics) {
		this.lstChildOfIfLogics = lstChildOfIfLogics;
	}

	public SequenceLogicExt getNextSequenceLogic() {
		return nextSequenceLogic;
	}

	public void setNextSequenceLogic(SequenceLogicExt nextSequenceLogic) {
		this.nextSequenceLogic = nextSequenceLogic;
	}
	
	public SequenceLogicExt(SequenceLogic logic) {
		// TODO Auto-generated constructor stub
		this.setActionPath(logic.getActionPath());
		this.setBusinessLogicId(logic.getBusinessLogicId());
		this.setComponentId(logic.getComponentId());
		this.setComponentType(logic.getComponentType());
		this.setCssClass(logic.getCssClass());
		this.setGroupFlg(logic.getGroupFlg());
		this.setImagePath(logic.getImagePath());
		this.setParentSequenceLogicId(logic.getParentSequenceLogicId());
		this.setPrefixLabel(logic.getPrefixLabel());
		this.setRelatedSequenceLogicId(logic.getRelatedSequenceLogicId());
		this.setRemark(logic.getRemark());
		this.setSequenceLogicId(logic.getSequenceLogicId());
		this.setSequenceLogicName(logic.getSequenceLogicName());
		this.setSequenceNo(logic.getSequenceNo());
		this.setStrData(logic.getStrData());
		this.setTempSequenceId(logic.getTempSequenceId());
		this.setxCoordinates(logic.getxCoordinates());
		this.setyCoordinates(logic.getyCoordinates());
	}
}
