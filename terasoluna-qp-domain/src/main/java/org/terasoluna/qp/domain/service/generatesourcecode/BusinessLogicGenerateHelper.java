package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.IfConditionDetail;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogicExt;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

public class BusinessLogicGenerateHelper {
	private static final int END_IF = 19;
	private List<SequenceLogicExt> lstSequenceLogics = new ArrayList<SequenceLogicExt>();
	private List<SequenceConnector> lstSequenceConnectors = new ArrayList<SequenceConnector>();
	
	public List<SequenceLogicExt> getLstSequenceLogics() {
		return lstSequenceLogics;
	}

	public void setLstSequenceLogics(List<SequenceLogicExt> lstSequenceLogics) {
		this.lstSequenceLogics = lstSequenceLogics;
	}

	public List<SequenceConnector> getLstSequenceConnectors() {
		return lstSequenceConnectors;
	}

	public void setLstSequenceConnectors(List<SequenceConnector> lstSequenceConnectors) {
		this.lstSequenceConnectors = lstSequenceConnectors;
	}

	/**
	 * Cast data type from source to destination
	 * 
	 * @param target
	 * @param source
	 * @param value
	 * @return
	 */
	public static String getContentByCastDataType(int target, int source, String value) {
		
		switch (target) {

			case GenerateSourceCodeConst.DataType.OBJECT:
					if (GenerateSourceCodeConst.DataType.OBJECT != source) value = "null"; 
					break;
				
			case GenerateSourceCodeConst.DataType.BYTE:
					if (GenerateSourceCodeConst.DataType.BYTE != source) value = "null"; 
					break;
				
			case GenerateSourceCodeConst.DataType.SHORT:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE: value = "Short.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.SHORT: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.INTEGER:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT: value = "Integer.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.INTEGER: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.LONG:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT:
						case GenerateSourceCodeConst.DataType.INTEGER: value = "Long.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.TIMESTAMP:
						case GenerateSourceCodeConst.DataType.DATETIME:
						case GenerateSourceCodeConst.DataType.TIME:
						case GenerateSourceCodeConst.DataType.DATE: value = value+".getTime()"; break;
						case GenerateSourceCodeConst.DataType.LONG: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.FLOAT:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT:
						case GenerateSourceCodeConst.DataType.INTEGER:
						case GenerateSourceCodeConst.DataType.LONG: value = "Float.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.FLOAT: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.DOUBLE:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT:
						case GenerateSourceCodeConst.DataType.INTEGER:
						case GenerateSourceCodeConst.DataType.LONG:
						case GenerateSourceCodeConst.DataType.FLOAT: value = "Double.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.DOUBLE: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.CHARACTER:
				if (GenerateSourceCodeConst.DataType.CHARACTER != source) value = "null"; 
				break;
				
			case GenerateSourceCodeConst.DataType.BOOLEAN:
				if (GenerateSourceCodeConst.DataType.BOOLEAN != source) value = "null"; 
				break;

			case GenerateSourceCodeConst.DataType.STRING:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT:
						case GenerateSourceCodeConst.DataType.INTEGER:
						case GenerateSourceCodeConst.DataType.LONG:
						case GenerateSourceCodeConst.DataType.FLOAT:
						case GenerateSourceCodeConst.DataType.DOUBLE:
						case GenerateSourceCodeConst.DataType.CHARACTER:
						case GenerateSourceCodeConst.DataType.BOOLEAN:
						case GenerateSourceCodeConst.DataType.BIGDECIMAL:
						case GenerateSourceCodeConst.DataType.TIMESTAMP:
						case GenerateSourceCodeConst.DataType.DATETIME:
						case GenerateSourceCodeConst.DataType.TIME:
						case GenerateSourceCodeConst.DataType.DATE: value = "String.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.STRING: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.BIGDECIMAL:
					switch (source) {
						case GenerateSourceCodeConst.DataType.BYTE:
						case GenerateSourceCodeConst.DataType.SHORT:
						case GenerateSourceCodeConst.DataType.INTEGER:
						case GenerateSourceCodeConst.DataType.LONG:
						case GenerateSourceCodeConst.DataType.FLOAT:
						case GenerateSourceCodeConst.DataType.DOUBLE: value = "java.math.BigDecimal.valueOf("+value+")"; break;
						case GenerateSourceCodeConst.DataType.BIGDECIMAL: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.TIMESTAMP:
			case GenerateSourceCodeConst.DataType.DATETIME:
					switch (source) {
						case GenerateSourceCodeConst.DataType.LONG: value = "new java.sql.Timestamp("+value+")"; break;
						case GenerateSourceCodeConst.DataType.TIME:
						case GenerateSourceCodeConst.DataType.DATE: value = "new java.sql.Timestamp("+value+".getTime())"; break;
						case GenerateSourceCodeConst.DataType.TIMESTAMP:
						case GenerateSourceCodeConst.DataType.DATETIME: break;
						default: value = "null"; break;
					}
				break;

			case GenerateSourceCodeConst.DataType.TIME:
					switch (source) {
						case GenerateSourceCodeConst.DataType.LONG: value = "new java.sql.Time("+value+")"; break;
						case GenerateSourceCodeConst.DataType.TIME: break;
						default: value = "null"; break;
					}
				break;

			case GenerateSourceCodeConst.DataType.ENTITY:
				if (GenerateSourceCodeConst.DataType.ENTITY != source) value = "null";
				break;
				
			case GenerateSourceCodeConst.DataType.DATE:
					switch (source) {
						case GenerateSourceCodeConst.DataType.LONG: value = "new java.sql.Date("+value+")"; break;
						case GenerateSourceCodeConst.DataType.TIME: value = "new java.sql.Date("+value+".getTime())"; break;
						case GenerateSourceCodeConst.DataType.DATE: break;
						default: value = "null"; break;
					}
				break;
				
			case GenerateSourceCodeConst.DataType.COMMON_OBJECT:
				if (GenerateSourceCodeConst.DataType.COMMON_OBJECT != source) value = "null"; 
				break;
			
			case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT:
				if (GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT != source) value = "null"; 
				break;
			
			default: value = StringUtils.EMPTY; break;
			}
		
		return value;
	}

	public List<SequenceLogicExt> buildTreeSourceCode() {
		List<SequenceLogicExt> lstSortLogics = new ArrayList<SequenceLogicExt>();
		if (CollectionUtils.isNotEmpty(this.getLstSequenceLogics()) && CollectionUtils.isNotEmpty(this.getLstSequenceConnectors())) {

			// find next node
			buidNextNode();

			for (SequenceLogicExt sequenceLogic : lstSequenceLogics) {
				switch (sequenceLogic.getComponentType()) {
					case BusinessDesignConst.COMPONENT_LOOP:
						buidForNode(sequenceLogic);
						break;
					case BusinessDesignConst.COMPONENT_IF:
						buidIFNode(sequenceLogic);
						break;
					default:
						break;
				}
			}
			for (SequenceLogicExt objLogic : this.getLstSequenceLogics()) {
				if (BusinessDesignConst.COMPONENT_START == objLogic.getComponentType()) {
					lstSortLogics.add(objLogic);
					break;
				}
			}
			// orderSequenceLogic(lstSortLogics);
			int maxCycle = 10;
			int count = 0;
			if (lstSortLogics.size() > 0) {
				SequenceLogicExt lastLogic = lstSortLogics.get(lstSortLogics.size() - 1);
				while (lastLogic != null && lastLogic.getNextSequenceLogic() != null) {
					SequenceLogicExt tempLogic = lastLogic.getNextSequenceLogic();
					//validate recursive 
					if(lstSortLogics.contains(tempLogic)){
						count ++ ;
					}
					if(count > maxCycle){
						break;
					}
					lstSortLogics.add(tempLogic);
					lastLogic = tempLogic;
					
				}
			} else {
				System.out.println(this.getLstSequenceLogics().size());
			}
		}
		// build tree for business logic
		return lstSortLogics;
	}

	private void buidNextNode() {
		String nextId = "";
		for (SequenceLogicExt objLogic : lstSequenceLogics) {
			SequenceLogicExt nextLogic = null;
			nextId = "";
			if (BusinessDesignConst.COMPONENT_IF == objLogic.getComponentType()) {
				nextId = objLogic.getRelatedSequenceLogicId();
			} else {
				for (SequenceConnector sequenceConnector : lstSequenceConnectors) {
					if (sequenceConnector.getConnectorSource().equals(objLogic.getSequenceLogicId()) && sequenceConnector.getConnectorType().equals("")) {
						nextId = sequenceConnector.getConnectorDest();
						break;
					}
				}
			}
			for (SequenceLogicExt objNextLogic : lstSequenceLogics) {
				if (objNextLogic.getSequenceLogicId().equals(nextId)) {
					nextLogic = objNextLogic;
					break;
				}
			}
			objLogic.setNextSequenceLogic(nextLogic);
		}
	}

	private void buidForNode(SequenceLogicExt sequenceLogic) {
		String nextId = "";
		SequenceLogicExt nextLogic = null;
		for (SequenceConnector sequenceConnector : lstSequenceConnectors) {
			if (sequenceConnector.getConnectorSource().equals(sequenceLogic.getSequenceLogicId()) && BusinessDesignConst.ConnectorType.CYCLE.equals(sequenceConnector.getConnectorType())) {
				nextId = sequenceConnector.getConnectorDest();
				break;
			}
		}
		for (SequenceLogicExt objNextLogic : lstSequenceLogics) {
			if (objNextLogic.getSequenceLogicId().equals(nextId)) {
				nextLogic = objNextLogic;
				break;
			}
		}
		sequenceLogic.getLstChildOfForLogics().add(nextLogic);
		while (nextLogic != null && nextLogic.getNextSequenceLogic() != null && !sequenceLogic.getSequenceLogicId().equals(nextLogic.getNextSequenceLogic().getSequenceLogicId())) {
			SequenceLogicExt temp = nextLogic.getNextSequenceLogic();
			nextLogic = temp;
			sequenceLogic.getLstChildOfForLogics().add(nextLogic);
		}
	}

	private void buidIFNode(SequenceLogicExt sequenceLogic) {
		IfComponent mappingComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), IfComponent.class);
		ArrayList<ArrayList<SequenceLogicExt>> lstNode = new ArrayList<ArrayList<SequenceLogicExt>>();
		if (mappingComponent.getIfConditionDetails() != null) {
			if (mappingComponent.getIfConditionDetails().size() == 1) {
				ArrayList<SequenceLogicExt> lstLogicOfBranch = new ArrayList<SequenceLogicExt>();
				lstNode.add(lstLogicOfBranch);
			}
			for (IfConditionDetail condition : mappingComponent.getIfConditionDetails()) {
				SequenceLogicExt nextLogic = null;
				String nextId = "";
				ArrayList<SequenceLogicExt> lstLogicOfBranch = new ArrayList<SequenceLogicExt>();
				String label = "";

				if (mappingComponent.getIfConditionDetails().size() == 1) {
					label = BusinessDesignConst.ConnectorType.FALSE;
				} else if (mappingComponent.getIfConditionDetails().size() == 2) {
					if (mappingComponent.getIfConditionDetails().indexOf(condition) == 0) {
						label = BusinessDesignConst.ConnectorType.TRUE;
					} else {
						label = BusinessDesignConst.ConnectorType.FALSE;
					}
				} else {
					label = (mappingComponent.getIfConditionDetails().indexOf(condition) + 1) + "." + condition.getCaption();
				}

				// get first node of branch.
				for (SequenceConnector sequenceConnector : lstSequenceConnectors) {
					if (sequenceConnector.getConnectorSource().equals(sequenceLogic.getSequenceLogicId()) && label.equals(sequenceConnector.getConnectorType())) {
						nextId = sequenceConnector.getConnectorDest();
						break;
					}
				}
				for (SequenceLogicExt objNextLogic : lstSequenceLogics) {
					if (objNextLogic.getSequenceLogicId().equals(nextId)) {
						nextLogic = objNextLogic;
						break;
					}
				}
				lstLogicOfBranch.add(nextLogic);
				// get next node of this branch.
				while (nextLogic != null && nextLogic.getNextSequenceLogic() != null && sequenceLogic.getRelatedSequenceLogicId() != null &&  !sequenceLogic.getRelatedSequenceLogicId().equals(nextLogic.getNextSequenceLogic().getSequenceLogicId())) {
					if (nextLogic.getNextSequenceLogic().getComponentType() == END_IF) {
						break;
					}
					
					SequenceLogicExt temp = nextLogic.getNextSequenceLogic();
					nextLogic = temp;
					lstLogicOfBranch.add(nextLogic);
				}
				lstNode.add(lstLogicOfBranch);
			}
		}
		sequenceLogic.setLstChildOfIfLogics(lstNode);
	}

}
