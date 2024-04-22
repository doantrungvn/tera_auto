package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.UtilityComponent;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("UtilityComponentGenerateHandler")
public class UtilityComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private static final String NL_2_TAB = "\n\t\t";

	private static final String NL_3_TAB = "\n\t\t";

	/** Appends the specified element to the end of this list */
	private static final int TYPE_0 = 0;

	/** Inserts the specified element at the specified position in this list */
	private static final int TYPE_1 = 1;

	/** Appends all of the elements */
	private static final int TYPE_2 = 2;

	/** Inserts all of the elements at the specified position */
	private static final int TYPE_3 = 3;

	/** Removes all of the elements */
	private static final int TYPE_4 = 4;

	/** Removes the element at the specified position */
	private static final int TYPE_5 = 5;

	/** Removes the first occurrence of the specified element */
	private static final int TYPE_6 = 6;

	/** Removes from this list all of its elements */
	private static final int TYPE_7 = 7;

	private UtilityComponent currentComponent;

	private String getPrefix(Integer targetScope) {
		if (GenerateSourceCodeConst.TypeScope.INPUTBEAN == targetScope) {
			return "in";
		} else if (GenerateSourceCodeConst.TypeScope.OUTPUTBEAN == targetScope) {
			return "ou";
		} else {
			return "ob";
		}
	}

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {
		StringBuilder tmp = new StringBuilder();
		// Processing for component current
		if (this.currentComponent != null) {
			preGencode(builder, param);
			UtilityComponent currUtilityComp = currentComponent;

			if (currUtilityComp != null) {
				String prefixTarget = "";
				String prefixParameter = "";
				String prefixIndex = "";
				String target = "";
				String parameter = "";
				String index = "";

				if (currUtilityComp.getTargetScope() != null) {
					prefixTarget = getPrefix(currUtilityComp.getTargetScope());
					// Modify By HungHX
					//target = detailServiceImpHandler.getterAndSetterOfParameter(currUtilityComp.getTargetId(), currUtilityComp.getTargetScope(), prefixTarget, null, true, false);
					target = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, currUtilityComp.getTargetId(), currUtilityComp.getTargetScope(), prefixTarget, null);
				}

				if (currUtilityComp.getParameterScope() != null) {
					prefixParameter = getPrefix(currUtilityComp.getParameterScope());
					// Modify By HungHX
					//parameter = detailServiceImpHandler.getterAndSetterOfParameter(currUtilityComp.getParameterId(), currUtilityComp.getParameterScope(), prefixParameter, null, true, false);
					parameter = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, currUtilityComp.getParameterId(), currUtilityComp.getParameterScope(), prefixParameter, currUtilityComp.getLstParameterIndex());
				}

				if (currUtilityComp.getIndexScope() != null) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(currUtilityComp.getIndexScope())) {
						index = currUtilityComp.getIndexId();
					} else if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(currUtilityComp.getIndexScope())) {
						LoopComponent loopComponent = detailServiceImpHandler.getLoopComponentBySequence(detailServiceImpHandler.getAllLoopComponents(), currUtilityComp.getIndexId());
						index = loopComponent.getIndex();
					} else {
						prefixIndex = getPrefix(currUtilityComp.getIndexScope());
						index = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, currUtilityComp.getIndexId(), currUtilityComp.getIndexScope(), prefixIndex, currUtilityComp.getLstIndex());
					}
				}

				tmp.append(MessageFormat.format("if ({0} != null)", target));
				tmp.append(" {");
				tmp.append(NL_3_TAB);
				
				switch (currUtilityComp.getType()) {
					case TYPE_0:
						tmp.append(target);
						tmp.append(".add(");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_1:
						tmp.append(target);
						tmp.append(".add(");
						tmp.append(index);
						tmp.append(", ");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_2:
						tmp.append(target);
						tmp.append(".addAll(");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_3:
						tmp.append(target);
						tmp.append(".addAll(");
						tmp.append(index);
						tmp.append(", ");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_4:
						tmp.append(target);
						tmp.append(".clear();");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_5:
						tmp.append(target);
						tmp.append(".remove(");
						tmp.append(index);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_6:
						tmp.append(target);
						tmp.append(".remove(");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
					case TYPE_7:
						tmp.append(target);
						tmp.append(".removeAll(");
						tmp.append(parameter);
						tmp.append(");");
						tmp.append(NL_2_TAB);
						break;
				}

				tmp.append("}");
			}
		}
		builder.append(tmp);
		postGencode(builder, param);
	}

	public UtilityComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(UtilityComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start Utility node");
			builder.append(KEY_NL);
			
			if(this.currentComponent !=null) {
				builder.append("// Label:" + currentComponent.getLabel());
				builder.append(KEY_NL);
				
				String remark = currentComponent.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					if (org.springframework.util.StringUtils.countOccurrencesOf(remark, "\n") > 0) {
						remark = remark.replace("\n", KEY_NL);
						builder.append(BusinessDesignConst.MULTI_COMMENT_START).append(KEY_NL).append(remark).append(KEY_NL).append(BusinessDesignConst.MULTI_COMMENT_END).append(KEY_NL);
					} else {
						builder.append(BusinessDesignConst.SINGLE_COMMENT_START).append(SPACE).append(remark).append(KEY_NL);
					}
				}
			}
		}
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End Utility node");
			builder.append(KEY_NL);
		}
	}

}
