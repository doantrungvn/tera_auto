package org.terasoluna.qp.app.autocomplete;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;

public class AutocompleteForm implements Serializable {

	private static final long serialVersionUID = -9170873584006168492L;
	
	private String autocompleteId;
	
	@NotEmpty(message = AutocompleteMessageConst.SC_AUTOCOMPLETE_0005)
	@QpNameSize(message = AutocompleteMessageConst.SC_AUTOCOMPLETE_0005)
	@QpNamePattern(message=AutocompleteMessageConst.SC_AUTOCOMPLETE_0005)
	private String autocompleteName;
	
	@NotEmpty(message = AutocompleteMessageConst.SC_AUTOCOMPLETE_0006)
	@QpCodeSize(message=AutocompleteMessageConst.SC_AUTOCOMPLETE_0006)
	@QpCodePattern(message = AutocompleteMessageConst.SC_AUTOCOMPLETE_0006)
	private String autocompleteCode;
	
	@QpRemarkSize(message=CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private String projectId;
	private String projectIdAutocomplete;
	private String moduleId;
	private String moduleIdAutocomplete;
	private String moduleName;
	private String matchingType="1";
	private String minLength="0";
	private Boolean omitOverlap;
	private String[] matchingTypes;
	private Timestamp updatedDate;
	private String displayColumnIdAutocomplete;
	private String displayColumnId;
	private String displayFunction;
	private String displaySqlResultId;
	private String displayTableId;
	private String displayTableIdAutocomplete;
	private boolean displayColumnEnabled;
	private String submitColumnIdAutocomplete;
	private String submitColumnId;
	private String submitFunction;
	private String submitSqlResultId;
	private String submitTableId;
	private String submitTableIdAutocomplete;
	private boolean submitColumnEnabled;
	private String arg01;
	private String arg02;
	private String arg03;
	private String arg04;
	private String arg05;
	private String arg06;
	private String arg07;
	private String arg08;
	private String arg09;
	private String arg10;
	private String arg11;
	private String arg12;
	private String arg13;
	private String arg14;
	private String arg15;
	private String arg16;
	private String arg17;
	private String arg18;
	private String arg19;
	private String arg20;
	private String output01ColumnIdAutocomplete;
	private String output01ColumnId;
	private String output01Function;
	private String output01SqlResultId;
	private String output01TableId;
	private String output01TableIdAutocomplete;
	private boolean output01ColumnEnabled;
	private String output02ColumnIdAutocomplete;
	private String output02ColumnId;
	private String output02Function;
	private String output02SqlResultId;
	private String output02TableId;
	private String output02TableIdAutocomplete;
	private boolean output02ColumnEnabled;
	private String output03ColumnIdAutocomplete;
	private String output03ColumnId;
	private String output03Function;
	private String output03SqlResultId;
	private String output03TableId;
	private String output03TableIdAutocomplete;
	private boolean output03ColumnEnabled;
	private String output04ColumnIdAutocomplete;
	private String output04ColumnId;
	private String output04Function;
	private String output04SqlResultId;
	private String output04TableId;
	private String output04TableIdAutocomplete;
	private boolean output04ColumnEnabled;
	private String output05ColumnIdAutocomplete;
	private String output05ColumnId;
	private String output05Function;
	private String output05SqlResultId;
	private String output05TableId;
	private String output05TableIdAutocomplete;
	private boolean output05ColumnEnabled;
	private String output06ColumnIdAutocomplete;
	private String output06ColumnId;
	private String output06Function;
	private String output06SqlResultId;
	private String output06TableId;
	private String output06TableIdAutocomplete;
	private boolean output06ColumnEnabled;
	private String output07ColumnIdAutocomplete;
	private String output07ColumnId;
	private String output07Function;
	private String output07SqlResultId;
	private String output07TableId;
	private String output07TableIdAutocomplete;
	private boolean output07ColumnEnabled;
	private String output08ColumnIdAutocomplete;
	private String output08ColumnId;
	private String output08Function;
	private String output08SqlResultId;
	private String output08TableId;
	private String output08TableIdAutocomplete;
	private boolean output08ColumnEnabled;
	private String output09ColumnIdAutocomplete;
	private String output09ColumnId;
	private String output09Function;
	private String output09SqlResultId;
	private String output09TableId;
	private String output09TableIdAutocomplete;
	private boolean output09ColumnEnabled;
	private String output10ColumnIdAutocomplete;
	private String output10ColumnId;
	private String output10Function;
	private String output10SqlResultId;
	private String output10TableId;
	private String output10TableIdAutocomplete;
	private boolean output10ColumnEnabled;
	private String output11ColumnIdAutocomplete;
	private String output11ColumnId;
	private String output11Function;
	private String output11SqlResultId;
	private String output11TableId;
	private String output11TableIdAutocomplete;
	private boolean output11ColumnEnabled;
	private String output12ColumnIdAutocomplete;
	private String output12ColumnId;
	private String output12Function;
	private String output12SqlResultId;
	private String output12TableId;
	private String output12TableIdAutocomplete;
	private boolean output12ColumnEnabled;
	private String output13ColumnIdAutocomplete;
	private String output13ColumnId;
	private String output13Function;
	private String output13SqlResultId;
	private String output13TableId;
	private String output13TableIdAutocomplete;
	private boolean output13ColumnEnabled;
	private String output14ColumnIdAutocomplete;
	private String output14ColumnId;
	private String output14Function;
	private String output14SqlResultId;
	private String output14TableId;
	private String output14TableIdAutocomplete;
	private boolean output14ColumnEnabled;
	private String output15ColumnIdAutocomplete;
	private String output15ColumnId;
	private String output15Function;
	private String output15SqlResultId;
	private String output15TableId;
	private String output15TableIdAutocomplete;
	private boolean output15ColumnEnabled;
	private String output16ColumnIdAutocomplete;
	private String output16ColumnId;
	private String output16Function;
	private String output16SqlResultId;
	private String output16TableId;
	private String output16TableIdAutocomplete;
	private boolean output16ColumnEnabled;
	private String output17ColumnIdAutocomplete;
	private String output17ColumnId;
	private String output17Function;
	private String output17SqlResultId;
	private String output17TableId;
	private String output17TableIdAutocomplete;
	private boolean output17ColumnEnabled;
	private String output18ColumnIdAutocomplete;
	private String output18ColumnId;
	private String output18Function;
	private String output18SqlResultId;
	private String output18TableId;
	private String output18TableIdAutocomplete;
	private boolean output18ColumnEnabled;
	private String output19ColumnIdAutocomplete;
	private String output19ColumnId;
	private String output19Function;
	private String output19SqlResultId;
	private String output19TableId;
	private String output19TableIdAutocomplete;
	private boolean output19ColumnEnabled;
	private String output20ColumnIdAutocomplete;
	private String output20ColumnId;
	private String output20Function;
	private String output20SqlResultId;
	private String output20TableId;
	private String output20TableIdAutocomplete;
	private boolean output20ColumnEnabled;
	private String designStatus;
	private String moduleStatus;
	
	public String getAutocompleteId() {
		return autocompleteId;
	}
	public void setAutocompleteId(String autocompleteId) {
		this.autocompleteId = autocompleteId;
	}
	public String getAutocompleteName() {
		return autocompleteName;
	}
	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
	}
	public String getAutocompleteCode() {
		return autocompleteCode;
	}
	public void setAutocompleteCode(String autocompleteCode) {
		this.autocompleteCode = autocompleteCode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getMatchingType() {
		return matchingType;
	}
	public void setMatchingType(String matchingType) {
		this.matchingType = matchingType;
	}
	public String getMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public Boolean getOmitOverlap() {
		return omitOverlap;
	}
	public void setOmitOverlap(Boolean omitOverlap) {
		this.omitOverlap = omitOverlap;
	}
	public String[] getMatchingTypes() {
		return matchingTypes;
	}
	public void setMatchingTypes(String[] matchingTypes) {
		this.matchingTypes = matchingTypes;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getDisplayColumnIdAutocomplete() {
		return displayColumnIdAutocomplete;
	}
	public void setDisplayColumnIdAutocomplete(String displayColumnIdAutocomplete) {
		this.displayColumnIdAutocomplete = displayColumnIdAutocomplete;
	}
	public String getDisplayColumnId() {
		return displayColumnId;
	}
	public void setDisplayColumnId(String displayColumnId) {
		this.displayColumnId = displayColumnId;
	}
	public String getDisplayFunction() {
		return displayFunction;
	}
	public void setDisplayFunction(String displayFunction) {
		this.displayFunction = displayFunction;
	}
	public String getDisplaySqlResultId() {
		return displaySqlResultId;
	}
	public void setDisplaySqlResultId(String displaySqlResultId) {
		this.displaySqlResultId = displaySqlResultId;
	}
	public String getDisplayTableId() {
		return displayTableId;
	}
	public void setDisplayTableId(String displayTableId) {
		this.displayTableId = displayTableId;
	}
	public String getDisplayTableIdAutocomplete() {
		return displayTableIdAutocomplete;
	}
	public void setDisplayTableIdAutocomplete(String displayTableIdAutocomplete) {
		this.displayTableIdAutocomplete = displayTableIdAutocomplete;
	}
	public boolean isDisplayColumnEnabled() {
		return displayColumnEnabled;
	}
	public void setDisplayColumnEnabled(boolean displayColumnEnabled) {
		this.displayColumnEnabled = displayColumnEnabled;
	}
	public String getSubmitColumnIdAutocomplete() {
		return submitColumnIdAutocomplete;
	}
	public void setSubmitColumnIdAutocomplete(String submitColumnIdAutocomplete) {
		this.submitColumnIdAutocomplete = submitColumnIdAutocomplete;
	}
	public String getSubmitColumnId() {
		return submitColumnId;
	}
	public void setSubmitColumnId(String submitColumnId) {
		this.submitColumnId = submitColumnId;
	}
	public String getSubmitFunction() {
		return submitFunction;
	}
	public void setSubmitFunction(String submitFunction) {
		this.submitFunction = submitFunction;
	}
	public String getSubmitSqlResultId() {
		return submitSqlResultId;
	}
	public void setSubmitSqlResultId(String submitSqlResultId) {
		this.submitSqlResultId = submitSqlResultId;
	}
	public String getSubmitTableId() {
		return submitTableId;
	}
	public void setSubmitTableId(String submitTableId) {
		this.submitTableId = submitTableId;
	}
	public String getSubmitTableIdAutocomplete() {
		return submitTableIdAutocomplete;
	}
	public void setSubmitTableIdAutocomplete(String submitTableIdAutocomplete) {
		this.submitTableIdAutocomplete = submitTableIdAutocomplete;
	}
	public boolean isSubmitColumnEnabled() {
		return submitColumnEnabled;
	}
	public void setSubmitColumnEnabled(boolean submitColumnEnabled) {
		this.submitColumnEnabled = submitColumnEnabled;
	}
	public String getArg01() {
		return arg01;
	}
	public void setArg01(String arg01) {
		this.arg01 = arg01;
	}
	public String getArg02() {
		return arg02;
	}
	public void setArg02(String arg02) {
		this.arg02 = arg02;
	}
	public String getArg03() {
		return arg03;
	}
	public void setArg03(String arg03) {
		this.arg03 = arg03;
	}
	public String getArg04() {
		return arg04;
	}
	public void setArg04(String arg04) {
		this.arg04 = arg04;
	}
	public String getArg05() {
		return arg05;
	}
	public void setArg05(String arg05) {
		this.arg05 = arg05;
	}
	public String getArg06() {
		return arg06;
	}
	public void setArg06(String arg06) {
		this.arg06 = arg06;
	}
	public String getArg07() {
		return arg07;
	}
	public void setArg07(String arg07) {
		this.arg07 = arg07;
	}
	public String getArg08() {
		return arg08;
	}
	public void setArg08(String arg08) {
		this.arg08 = arg08;
	}
	public String getArg09() {
		return arg09;
	}
	public void setArg09(String arg09) {
		this.arg09 = arg09;
	}
	public String getArg10() {
		return arg10;
	}
	public void setArg10(String arg10) {
		this.arg10 = arg10;
	}
	public String getArg11() {
		return arg11;
	}
	public void setArg11(String arg11) {
		this.arg11 = arg11;
	}
	public String getArg12() {
		return arg12;
	}
	public void setArg12(String arg12) {
		this.arg12 = arg12;
	}
	public String getArg13() {
		return arg13;
	}
	public void setArg13(String arg13) {
		this.arg13 = arg13;
	}
	public String getArg14() {
		return arg14;
	}
	public void setArg14(String arg14) {
		this.arg14 = arg14;
	}
	public String getArg15() {
		return arg15;
	}
	public void setArg15(String arg15) {
		this.arg15 = arg15;
	}
	public String getArg16() {
		return arg16;
	}
	public void setArg16(String arg16) {
		this.arg16 = arg16;
	}
	public String getArg17() {
		return arg17;
	}
	public void setArg17(String arg17) {
		this.arg17 = arg17;
	}
	public String getArg18() {
		return arg18;
	}
	public void setArg18(String arg18) {
		this.arg18 = arg18;
	}
	public String getArg19() {
		return arg19;
	}
	public void setArg19(String arg19) {
		this.arg19 = arg19;
	}
	public String getArg20() {
		return arg20;
	}
	public void setArg20(String arg20) {
		this.arg20 = arg20;
	}
	public String getOutput01ColumnIdAutocomplete() {
		return output01ColumnIdAutocomplete;
	}
	public void setOutput01ColumnIdAutocomplete(String output01ColumnIdAutocomplete) {
		this.output01ColumnIdAutocomplete = output01ColumnIdAutocomplete;
	}
	public String getOutput01ColumnId() {
		return output01ColumnId;
	}
	public void setOutput01ColumnId(String output01ColumnId) {
		this.output01ColumnId = output01ColumnId;
	}
	public String getOutput01Function() {
		return output01Function;
	}
	public void setOutput01Function(String output01Function) {
		this.output01Function = output01Function;
	}
	public String getOutput01SqlResultId() {
		return output01SqlResultId;
	}
	public void setOutput01SqlResultId(String output01SqlResultId) {
		this.output01SqlResultId = output01SqlResultId;
	}
	public String getOutput01TableId() {
		return output01TableId;
	}
	public void setOutput01TableId(String output01TableId) {
		this.output01TableId = output01TableId;
	}
	public String getOutput01TableIdAutocomplete() {
		return output01TableIdAutocomplete;
	}
	public void setOutput01TableIdAutocomplete(String output01TableIdAutocomplete) {
		this.output01TableIdAutocomplete = output01TableIdAutocomplete;
	}
	public boolean isOutput01ColumnEnabled() {
		return output01ColumnEnabled;
	}
	public void setOutput01ColumnEnabled(boolean output01ColumnEnabled) {
		this.output01ColumnEnabled = output01ColumnEnabled;
	}
	public String getOutput02ColumnIdAutocomplete() {
		return output02ColumnIdAutocomplete;
	}
	public void setOutput02ColumnIdAutocomplete(String output02ColumnIdAutocomplete) {
		this.output02ColumnIdAutocomplete = output02ColumnIdAutocomplete;
	}
	public String getOutput02ColumnId() {
		return output02ColumnId;
	}
	public void setOutput02ColumnId(String output02ColumnId) {
		this.output02ColumnId = output02ColumnId;
	}
	public String getOutput02Function() {
		return output02Function;
	}
	public void setOutput02Function(String output02Function) {
		this.output02Function = output02Function;
	}
	public String getOutput02SqlResultId() {
		return output02SqlResultId;
	}
	public void setOutput02SqlResultId(String output02SqlResultId) {
		this.output02SqlResultId = output02SqlResultId;
	}
	public String getOutput02TableId() {
		return output02TableId;
	}
	public void setOutput02TableId(String output02TableId) {
		this.output02TableId = output02TableId;
	}
	public String getOutput02TableIdAutocomplete() {
		return output02TableIdAutocomplete;
	}
	public void setOutput02TableIdAutocomplete(String output02TableIdAutocomplete) {
		this.output02TableIdAutocomplete = output02TableIdAutocomplete;
	}
	public boolean isOutput02ColumnEnabled() {
		return output02ColumnEnabled;
	}
	public void setOutput02ColumnEnabled(boolean output02ColumnEnabled) {
		this.output02ColumnEnabled = output02ColumnEnabled;
	}
	public String getOutput03ColumnIdAutocomplete() {
		return output03ColumnIdAutocomplete;
	}
	public void setOutput03ColumnIdAutocomplete(String output03ColumnIdAutocomplete) {
		this.output03ColumnIdAutocomplete = output03ColumnIdAutocomplete;
	}
	public String getOutput03ColumnId() {
		return output03ColumnId;
	}
	public void setOutput03ColumnId(String output03ColumnId) {
		this.output03ColumnId = output03ColumnId;
	}
	public String getOutput03Function() {
		return output03Function;
	}
	public void setOutput03Function(String output03Function) {
		this.output03Function = output03Function;
	}
	public String getOutput03SqlResultId() {
		return output03SqlResultId;
	}
	public void setOutput03SqlResultId(String output03SqlResultId) {
		this.output03SqlResultId = output03SqlResultId;
	}
	public String getOutput03TableId() {
		return output03TableId;
	}
	public void setOutput03TableId(String output03TableId) {
		this.output03TableId = output03TableId;
	}
	public String getOutput03TableIdAutocomplete() {
		return output03TableIdAutocomplete;
	}
	public void setOutput03TableIdAutocomplete(String output03TableIdAutocomplete) {
		this.output03TableIdAutocomplete = output03TableIdAutocomplete;
	}
	public boolean isOutput03ColumnEnabled() {
		return output03ColumnEnabled;
	}
	public void setOutput03ColumnEnabled(boolean output03ColumnEnabled) {
		this.output03ColumnEnabled = output03ColumnEnabled;
	}
	public String getOutput04ColumnIdAutocomplete() {
		return output04ColumnIdAutocomplete;
	}
	public void setOutput04ColumnIdAutocomplete(String output04ColumnIdAutocomplete) {
		this.output04ColumnIdAutocomplete = output04ColumnIdAutocomplete;
	}
	public String getOutput04ColumnId() {
		return output04ColumnId;
	}
	public void setOutput04ColumnId(String output04ColumnId) {
		this.output04ColumnId = output04ColumnId;
	}
	public String getOutput04Function() {
		return output04Function;
	}
	public void setOutput04Function(String output04Function) {
		this.output04Function = output04Function;
	}
	public String getOutput04SqlResultId() {
		return output04SqlResultId;
	}
	public void setOutput04SqlResultId(String output04SqlResultId) {
		this.output04SqlResultId = output04SqlResultId;
	}
	public String getOutput04TableId() {
		return output04TableId;
	}
	public void setOutput04TableId(String output04TableId) {
		this.output04TableId = output04TableId;
	}
	public String getOutput04TableIdAutocomplete() {
		return output04TableIdAutocomplete;
	}
	public void setOutput04TableIdAutocomplete(String output04TableIdAutocomplete) {
		this.output04TableIdAutocomplete = output04TableIdAutocomplete;
	}
	public boolean isOutput04ColumnEnabled() {
		return output04ColumnEnabled;
	}
	public void setOutput04ColumnEnabled(boolean output04ColumnEnabled) {
		this.output04ColumnEnabled = output04ColumnEnabled;
	}
	public String getOutput05ColumnIdAutocomplete() {
		return output05ColumnIdAutocomplete;
	}
	public void setOutput05ColumnIdAutocomplete(String output05ColumnIdAutocomplete) {
		this.output05ColumnIdAutocomplete = output05ColumnIdAutocomplete;
	}
	public String getOutput05ColumnId() {
		return output05ColumnId;
	}
	public void setOutput05ColumnId(String output05ColumnId) {
		this.output05ColumnId = output05ColumnId;
	}
	public String getOutput05Function() {
		return output05Function;
	}
	public void setOutput05Function(String output05Function) {
		this.output05Function = output05Function;
	}
	public String getOutput05SqlResultId() {
		return output05SqlResultId;
	}
	public void setOutput05SqlResultId(String output05SqlResultId) {
		this.output05SqlResultId = output05SqlResultId;
	}
	public String getOutput05TableId() {
		return output05TableId;
	}
	public void setOutput05TableId(String output05TableId) {
		this.output05TableId = output05TableId;
	}
	public String getOutput05TableIdAutocomplete() {
		return output05TableIdAutocomplete;
	}
	public void setOutput05TableIdAutocomplete(String output05TableIdAutocomplete) {
		this.output05TableIdAutocomplete = output05TableIdAutocomplete;
	}
	public boolean isOutput05ColumnEnabled() {
		return output05ColumnEnabled;
	}
	public void setOutput05ColumnEnabled(boolean output05ColumnEnabled) {
		this.output05ColumnEnabled = output05ColumnEnabled;
	}
	public String getOutput06ColumnIdAutocomplete() {
		return output06ColumnIdAutocomplete;
	}
	public void setOutput06ColumnIdAutocomplete(String output06ColumnIdAutocomplete) {
		this.output06ColumnIdAutocomplete = output06ColumnIdAutocomplete;
	}
	public String getOutput06ColumnId() {
		return output06ColumnId;
	}
	public void setOutput06ColumnId(String output06ColumnId) {
		this.output06ColumnId = output06ColumnId;
	}
	public String getOutput06Function() {
		return output06Function;
	}
	public void setOutput06Function(String output06Function) {
		this.output06Function = output06Function;
	}
	public String getOutput06SqlResultId() {
		return output06SqlResultId;
	}
	public void setOutput06SqlResultId(String output06SqlResultId) {
		this.output06SqlResultId = output06SqlResultId;
	}
	public String getOutput06TableId() {
		return output06TableId;
	}
	public void setOutput06TableId(String output06TableId) {
		this.output06TableId = output06TableId;
	}
	public String getOutput06TableIdAutocomplete() {
		return output06TableIdAutocomplete;
	}
	public void setOutput06TableIdAutocomplete(String output06TableIdAutocomplete) {
		this.output06TableIdAutocomplete = output06TableIdAutocomplete;
	}
	public boolean isOutput06ColumnEnabled() {
		return output06ColumnEnabled;
	}
	public void setOutput06ColumnEnabled(boolean output06ColumnEnabled) {
		this.output06ColumnEnabled = output06ColumnEnabled;
	}
	public String getOutput07ColumnIdAutocomplete() {
		return output07ColumnIdAutocomplete;
	}
	public void setOutput07ColumnIdAutocomplete(String output07ColumnIdAutocomplete) {
		this.output07ColumnIdAutocomplete = output07ColumnIdAutocomplete;
	}
	public String getOutput07ColumnId() {
		return output07ColumnId;
	}
	public void setOutput07ColumnId(String output07ColumnId) {
		this.output07ColumnId = output07ColumnId;
	}
	public String getOutput07Function() {
		return output07Function;
	}
	public void setOutput07Function(String output07Function) {
		this.output07Function = output07Function;
	}
	public String getOutput07SqlResultId() {
		return output07SqlResultId;
	}
	public void setOutput07SqlResultId(String output07SqlResultId) {
		this.output07SqlResultId = output07SqlResultId;
	}
	public String getOutput07TableId() {
		return output07TableId;
	}
	public void setOutput07TableId(String output07TableId) {
		this.output07TableId = output07TableId;
	}
	public String getOutput07TableIdAutocomplete() {
		return output07TableIdAutocomplete;
	}
	public void setOutput07TableIdAutocomplete(String output07TableIdAutocomplete) {
		this.output07TableIdAutocomplete = output07TableIdAutocomplete;
	}
	public boolean isOutput07ColumnEnabled() {
		return output07ColumnEnabled;
	}
	public void setOutput07ColumnEnabled(boolean output07ColumnEnabled) {
		this.output07ColumnEnabled = output07ColumnEnabled;
	}
	public String getOutput08ColumnIdAutocomplete() {
		return output08ColumnIdAutocomplete;
	}
	public void setOutput08ColumnIdAutocomplete(String output08ColumnIdAutocomplete) {
		this.output08ColumnIdAutocomplete = output08ColumnIdAutocomplete;
	}
	public String getOutput08ColumnId() {
		return output08ColumnId;
	}
	public void setOutput08ColumnId(String output08ColumnId) {
		this.output08ColumnId = output08ColumnId;
	}
	public String getOutput08Function() {
		return output08Function;
	}
	public void setOutput08Function(String output08Function) {
		this.output08Function = output08Function;
	}
	public String getOutput08SqlResultId() {
		return output08SqlResultId;
	}
	public void setOutput08SqlResultId(String output08SqlResultId) {
		this.output08SqlResultId = output08SqlResultId;
	}
	public String getOutput08TableId() {
		return output08TableId;
	}
	public void setOutput08TableId(String output08TableId) {
		this.output08TableId = output08TableId;
	}
	public String getOutput08TableIdAutocomplete() {
		return output08TableIdAutocomplete;
	}
	public void setOutput08TableIdAutocomplete(String output08TableIdAutocomplete) {
		this.output08TableIdAutocomplete = output08TableIdAutocomplete;
	}
	public boolean isOutput08ColumnEnabled() {
		return output08ColumnEnabled;
	}
	public void setOutput08ColumnEnabled(boolean output08ColumnEnabled) {
		this.output08ColumnEnabled = output08ColumnEnabled;
	}
	public String getOutput09ColumnIdAutocomplete() {
		return output09ColumnIdAutocomplete;
	}
	public void setOutput09ColumnIdAutocomplete(String output09ColumnIdAutocomplete) {
		this.output09ColumnIdAutocomplete = output09ColumnIdAutocomplete;
	}
	public String getOutput09ColumnId() {
		return output09ColumnId;
	}
	public void setOutput09ColumnId(String output09ColumnId) {
		this.output09ColumnId = output09ColumnId;
	}
	public String getOutput09Function() {
		return output09Function;
	}
	public void setOutput09Function(String output09Function) {
		this.output09Function = output09Function;
	}
	public String getOutput09SqlResultId() {
		return output09SqlResultId;
	}
	public void setOutput09SqlResultId(String output09SqlResultId) {
		this.output09SqlResultId = output09SqlResultId;
	}
	public String getOutput09TableId() {
		return output09TableId;
	}
	public void setOutput09TableId(String output09TableId) {
		this.output09TableId = output09TableId;
	}
	public String getOutput09TableIdAutocomplete() {
		return output09TableIdAutocomplete;
	}
	public void setOutput09TableIdAutocomplete(String output09TableIdAutocomplete) {
		this.output09TableIdAutocomplete = output09TableIdAutocomplete;
	}
	public boolean isOutput09ColumnEnabled() {
		return output09ColumnEnabled;
	}
	public void setOutput09ColumnEnabled(boolean output09ColumnEnabled) {
		this.output09ColumnEnabled = output09ColumnEnabled;
	}
	public String getOutput10ColumnIdAutocomplete() {
		return output10ColumnIdAutocomplete;
	}
	public void setOutput10ColumnIdAutocomplete(String output10ColumnIdAutocomplete) {
		this.output10ColumnIdAutocomplete = output10ColumnIdAutocomplete;
	}
	public String getOutput10ColumnId() {
		return output10ColumnId;
	}
	public void setOutput10ColumnId(String output10ColumnId) {
		this.output10ColumnId = output10ColumnId;
	}
	public String getOutput10Function() {
		return output10Function;
	}
	public void setOutput10Function(String output10Function) {
		this.output10Function = output10Function;
	}
	public String getOutput10SqlResultId() {
		return output10SqlResultId;
	}
	public void setOutput10SqlResultId(String output10SqlResultId) {
		this.output10SqlResultId = output10SqlResultId;
	}
	public String getOutput10TableId() {
		return output10TableId;
	}
	public void setOutput10TableId(String output10TableId) {
		this.output10TableId = output10TableId;
	}
	public String getOutput10TableIdAutocomplete() {
		return output10TableIdAutocomplete;
	}
	public void setOutput10TableIdAutocomplete(String output10TableIdAutocomplete) {
		this.output10TableIdAutocomplete = output10TableIdAutocomplete;
	}
	public boolean isOutput10ColumnEnabled() {
		return output10ColumnEnabled;
	}
	public void setOutput10ColumnEnabled(boolean output10ColumnEnabled) {
		this.output10ColumnEnabled = output10ColumnEnabled;
	}
	public String getOutput11ColumnIdAutocomplete() {
		return output11ColumnIdAutocomplete;
	}
	public void setOutput11ColumnIdAutocomplete(String output11ColumnIdAutocomplete) {
		this.output11ColumnIdAutocomplete = output11ColumnIdAutocomplete;
	}
	public String getOutput11ColumnId() {
		return output11ColumnId;
	}
	public void setOutput11ColumnId(String output11ColumnId) {
		this.output11ColumnId = output11ColumnId;
	}
	public String getOutput11Function() {
		return output11Function;
	}
	public void setOutput11Function(String output11Function) {
		this.output11Function = output11Function;
	}
	public String getOutput11SqlResultId() {
		return output11SqlResultId;
	}
	public void setOutput11SqlResultId(String output11SqlResultId) {
		this.output11SqlResultId = output11SqlResultId;
	}
	public String getOutput11TableId() {
		return output11TableId;
	}
	public void setOutput11TableId(String output11TableId) {
		this.output11TableId = output11TableId;
	}
	public String getOutput11TableIdAutocomplete() {
		return output11TableIdAutocomplete;
	}
	public void setOutput11TableIdAutocomplete(String output11TableIdAutocomplete) {
		this.output11TableIdAutocomplete = output11TableIdAutocomplete;
	}
	public boolean isOutput11ColumnEnabled() {
		return output11ColumnEnabled;
	}
	public void setOutput11ColumnEnabled(boolean output11ColumnEnabled) {
		this.output11ColumnEnabled = output11ColumnEnabled;
	}
	public String getOutput12ColumnIdAutocomplete() {
		return output12ColumnIdAutocomplete;
	}
	public void setOutput12ColumnIdAutocomplete(String output12ColumnIdAutocomplete) {
		this.output12ColumnIdAutocomplete = output12ColumnIdAutocomplete;
	}
	public String getOutput12ColumnId() {
		return output12ColumnId;
	}
	public void setOutput12ColumnId(String output12ColumnId) {
		this.output12ColumnId = output12ColumnId;
	}
	public String getOutput12Function() {
		return output12Function;
	}
	public void setOutput12Function(String output12Function) {
		this.output12Function = output12Function;
	}
	public String getOutput12SqlResultId() {
		return output12SqlResultId;
	}
	public void setOutput12SqlResultId(String output12SqlResultId) {
		this.output12SqlResultId = output12SqlResultId;
	}
	public String getOutput12TableId() {
		return output12TableId;
	}
	public void setOutput12TableId(String output12TableId) {
		this.output12TableId = output12TableId;
	}
	public String getOutput12TableIdAutocomplete() {
		return output12TableIdAutocomplete;
	}
	public void setOutput12TableIdAutocomplete(String output12TableIdAutocomplete) {
		this.output12TableIdAutocomplete = output12TableIdAutocomplete;
	}
	public boolean isOutput12ColumnEnabled() {
		return output12ColumnEnabled;
	}
	public void setOutput12ColumnEnabled(boolean output12ColumnEnabled) {
		this.output12ColumnEnabled = output12ColumnEnabled;
	}
	public String getOutput13ColumnIdAutocomplete() {
		return output13ColumnIdAutocomplete;
	}
	public void setOutput13ColumnIdAutocomplete(String output13ColumnIdAutocomplete) {
		this.output13ColumnIdAutocomplete = output13ColumnIdAutocomplete;
	}
	public String getOutput13ColumnId() {
		return output13ColumnId;
	}
	public void setOutput13ColumnId(String output13ColumnId) {
		this.output13ColumnId = output13ColumnId;
	}
	public String getOutput13Function() {
		return output13Function;
	}
	public void setOutput13Function(String output13Function) {
		this.output13Function = output13Function;
	}
	public String getOutput13SqlResultId() {
		return output13SqlResultId;
	}
	public void setOutput13SqlResultId(String output13SqlResultId) {
		this.output13SqlResultId = output13SqlResultId;
	}
	public String getOutput13TableId() {
		return output13TableId;
	}
	public void setOutput13TableId(String output13TableId) {
		this.output13TableId = output13TableId;
	}
	public String getOutput13TableIdAutocomplete() {
		return output13TableIdAutocomplete;
	}
	public void setOutput13TableIdAutocomplete(String output13TableIdAutocomplete) {
		this.output13TableIdAutocomplete = output13TableIdAutocomplete;
	}
	public boolean isOutput13ColumnEnabled() {
		return output13ColumnEnabled;
	}
	public void setOutput13ColumnEnabled(boolean output13ColumnEnabled) {
		this.output13ColumnEnabled = output13ColumnEnabled;
	}
	public String getOutput14ColumnIdAutocomplete() {
		return output14ColumnIdAutocomplete;
	}
	public void setOutput14ColumnIdAutocomplete(String output14ColumnIdAutocomplete) {
		this.output14ColumnIdAutocomplete = output14ColumnIdAutocomplete;
	}
	public String getOutput14ColumnId() {
		return output14ColumnId;
	}
	public void setOutput14ColumnId(String output14ColumnId) {
		this.output14ColumnId = output14ColumnId;
	}
	public String getOutput14Function() {
		return output14Function;
	}
	public void setOutput14Function(String output14Function) {
		this.output14Function = output14Function;
	}
	public String getOutput14SqlResultId() {
		return output14SqlResultId;
	}
	public void setOutput14SqlResultId(String output14SqlResultId) {
		this.output14SqlResultId = output14SqlResultId;
	}
	public String getOutput14TableId() {
		return output14TableId;
	}
	public void setOutput14TableId(String output14TableId) {
		this.output14TableId = output14TableId;
	}
	public String getOutput14TableIdAutocomplete() {
		return output14TableIdAutocomplete;
	}
	public void setOutput14TableIdAutocomplete(String output14TableIdAutocomplete) {
		this.output14TableIdAutocomplete = output14TableIdAutocomplete;
	}
	public boolean isOutput14ColumnEnabled() {
		return output14ColumnEnabled;
	}
	public void setOutput14ColumnEnabled(boolean output14ColumnEnabled) {
		this.output14ColumnEnabled = output14ColumnEnabled;
	}
	public String getOutput15ColumnIdAutocomplete() {
		return output15ColumnIdAutocomplete;
	}
	public void setOutput15ColumnIdAutocomplete(String output15ColumnIdAutocomplete) {
		this.output15ColumnIdAutocomplete = output15ColumnIdAutocomplete;
	}
	public String getOutput15ColumnId() {
		return output15ColumnId;
	}
	public void setOutput15ColumnId(String output15ColumnId) {
		this.output15ColumnId = output15ColumnId;
	}
	public String getOutput15Function() {
		return output15Function;
	}
	public void setOutput15Function(String output15Function) {
		this.output15Function = output15Function;
	}
	public String getOutput15SqlResultId() {
		return output15SqlResultId;
	}
	public void setOutput15SqlResultId(String output15SqlResultId) {
		this.output15SqlResultId = output15SqlResultId;
	}
	public String getOutput15TableId() {
		return output15TableId;
	}
	public void setOutput15TableId(String output15TableId) {
		this.output15TableId = output15TableId;
	}
	public String getOutput15TableIdAutocomplete() {
		return output15TableIdAutocomplete;
	}
	public void setOutput15TableIdAutocomplete(String output15TableIdAutocomplete) {
		this.output15TableIdAutocomplete = output15TableIdAutocomplete;
	}
	public boolean isOutput15ColumnEnabled() {
		return output15ColumnEnabled;
	}
	public void setOutput15ColumnEnabled(boolean output15ColumnEnabled) {
		this.output15ColumnEnabled = output15ColumnEnabled;
	}
	public String getOutput16ColumnIdAutocomplete() {
		return output16ColumnIdAutocomplete;
	}
	public void setOutput16ColumnIdAutocomplete(String output16ColumnIdAutocomplete) {
		this.output16ColumnIdAutocomplete = output16ColumnIdAutocomplete;
	}
	public String getOutput16ColumnId() {
		return output16ColumnId;
	}
	public void setOutput16ColumnId(String output16ColumnId) {
		this.output16ColumnId = output16ColumnId;
	}
	public String getOutput16Function() {
		return output16Function;
	}
	public void setOutput16Function(String output16Function) {
		this.output16Function = output16Function;
	}
	public String getOutput16SqlResultId() {
		return output16SqlResultId;
	}
	public void setOutput16SqlResultId(String output16SqlResultId) {
		this.output16SqlResultId = output16SqlResultId;
	}
	public String getOutput16TableId() {
		return output16TableId;
	}
	public void setOutput16TableId(String output16TableId) {
		this.output16TableId = output16TableId;
	}
	public String getOutput16TableIdAutocomplete() {
		return output16TableIdAutocomplete;
	}
	public void setOutput16TableIdAutocomplete(String output16TableIdAutocomplete) {
		this.output16TableIdAutocomplete = output16TableIdAutocomplete;
	}
	public boolean isOutput16ColumnEnabled() {
		return output16ColumnEnabled;
	}
	public void setOutput16ColumnEnabled(boolean output16ColumnEnabled) {
		this.output16ColumnEnabled = output16ColumnEnabled;
	}
	public String getOutput17ColumnIdAutocomplete() {
		return output17ColumnIdAutocomplete;
	}
	public void setOutput17ColumnIdAutocomplete(String output17ColumnIdAutocomplete) {
		this.output17ColumnIdAutocomplete = output17ColumnIdAutocomplete;
	}
	public String getOutput17ColumnId() {
		return output17ColumnId;
	}
	public void setOutput17ColumnId(String output17ColumnId) {
		this.output17ColumnId = output17ColumnId;
	}
	public String getOutput17Function() {
		return output17Function;
	}
	public void setOutput17Function(String output17Function) {
		this.output17Function = output17Function;
	}
	public String getOutput17SqlResultId() {
		return output17SqlResultId;
	}
	public void setOutput17SqlResultId(String output17SqlResultId) {
		this.output17SqlResultId = output17SqlResultId;
	}
	public String getOutput17TableId() {
		return output17TableId;
	}
	public void setOutput17TableId(String output17TableId) {
		this.output17TableId = output17TableId;
	}
	public String getOutput17TableIdAutocomplete() {
		return output17TableIdAutocomplete;
	}
	public void setOutput17TableIdAutocomplete(String output17TableIdAutocomplete) {
		this.output17TableIdAutocomplete = output17TableIdAutocomplete;
	}
	public boolean isOutput17ColumnEnabled() {
		return output17ColumnEnabled;
	}
	public void setOutput17ColumnEnabled(boolean output17ColumnEnabled) {
		this.output17ColumnEnabled = output17ColumnEnabled;
	}
	public String getOutput18ColumnIdAutocomplete() {
		return output18ColumnIdAutocomplete;
	}
	public void setOutput18ColumnIdAutocomplete(String output18ColumnIdAutocomplete) {
		this.output18ColumnIdAutocomplete = output18ColumnIdAutocomplete;
	}
	public String getOutput18ColumnId() {
		return output18ColumnId;
	}
	public void setOutput18ColumnId(String output18ColumnId) {
		this.output18ColumnId = output18ColumnId;
	}
	public String getOutput18Function() {
		return output18Function;
	}
	public void setOutput18Function(String output18Function) {
		this.output18Function = output18Function;
	}
	public String getOutput18SqlResultId() {
		return output18SqlResultId;
	}
	public void setOutput18SqlResultId(String output18SqlResultId) {
		this.output18SqlResultId = output18SqlResultId;
	}
	public String getOutput18TableId() {
		return output18TableId;
	}
	public void setOutput18TableId(String output18TableId) {
		this.output18TableId = output18TableId;
	}
	public String getOutput18TableIdAutocomplete() {
		return output18TableIdAutocomplete;
	}
	public void setOutput18TableIdAutocomplete(String output18TableIdAutocomplete) {
		this.output18TableIdAutocomplete = output18TableIdAutocomplete;
	}
	public boolean isOutput18ColumnEnabled() {
		return output18ColumnEnabled;
	}
	public void setOutput18ColumnEnabled(boolean output18ColumnEnabled) {
		this.output18ColumnEnabled = output18ColumnEnabled;
	}
	public String getOutput19ColumnIdAutocomplete() {
		return output19ColumnIdAutocomplete;
	}
	public void setOutput19ColumnIdAutocomplete(String output19ColumnIdAutocomplete) {
		this.output19ColumnIdAutocomplete = output19ColumnIdAutocomplete;
	}
	public String getOutput19ColumnId() {
		return output19ColumnId;
	}
	public void setOutput19ColumnId(String output19ColumnId) {
		this.output19ColumnId = output19ColumnId;
	}
	public String getOutput19Function() {
		return output19Function;
	}
	public void setOutput19Function(String output19Function) {
		this.output19Function = output19Function;
	}
	public String getOutput19SqlResultId() {
		return output19SqlResultId;
	}
	public void setOutput19SqlResultId(String output19SqlResultId) {
		this.output19SqlResultId = output19SqlResultId;
	}
	public String getOutput19TableId() {
		return output19TableId;
	}
	public void setOutput19TableId(String output19TableId) {
		this.output19TableId = output19TableId;
	}
	public String getOutput19TableIdAutocomplete() {
		return output19TableIdAutocomplete;
	}
	public void setOutput19TableIdAutocomplete(String output19TableIdAutocomplete) {
		this.output19TableIdAutocomplete = output19TableIdAutocomplete;
	}
	public boolean isOutput19ColumnEnabled() {
		return output19ColumnEnabled;
	}
	public void setOutput19ColumnEnabled(boolean output19ColumnEnabled) {
		this.output19ColumnEnabled = output19ColumnEnabled;
	}
	public String getOutput20ColumnIdAutocomplete() {
		return output20ColumnIdAutocomplete;
	}
	public void setOutput20ColumnIdAutocomplete(String output20ColumnIdAutocomplete) {
		this.output20ColumnIdAutocomplete = output20ColumnIdAutocomplete;
	}
	public String getOutput20ColumnId() {
		return output20ColumnId;
	}
	public void setOutput20ColumnId(String output20ColumnId) {
		this.output20ColumnId = output20ColumnId;
	}
	public String getOutput20Function() {
		return output20Function;
	}
	public void setOutput20Function(String output20Function) {
		this.output20Function = output20Function;
	}
	public String getOutput20SqlResultId() {
		return output20SqlResultId;
	}
	public void setOutput20SqlResultId(String output20SqlResultId) {
		this.output20SqlResultId = output20SqlResultId;
	}
	public String getOutput20TableId() {
		return output20TableId;
	}
	public void setOutput20TableId(String output20TableId) {
		this.output20TableId = output20TableId;
	}
	public String getOutput20TableIdAutocomplete() {
		return output20TableIdAutocomplete;
	}
	public void setOutput20TableIdAutocomplete(String output20TableIdAutocomplete) {
		this.output20TableIdAutocomplete = output20TableIdAutocomplete;
	}
	public boolean isOutput20ColumnEnabled() {
		return output20ColumnEnabled;
	}
	public void setOutput20ColumnEnabled(boolean output20ColumnEnabled) {
		this.output20ColumnEnabled = output20ColumnEnabled;
	}
	public String getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
}
