package org.terasoluna.qp.app.autocomplete.converter;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;
import org.terasoluna.qp.app.autocomplete.OutputForm;
import org.terasoluna.qp.domain.model.AutocompleteDesign;

public class AutocompleteOutputConverter extends DozerConverter<OutputForm, AutocompleteDesign>  {

	public AutocompleteOutputConverter() {
		super(OutputForm.class, AutocompleteDesign.class);
	}

	@Override
	public OutputForm convertFrom(AutocompleteDesign autocompleteDesign,OutputForm outputForm) {
		if(outputForm==null) {
			outputForm = new OutputForm();
		}
		outputForm.setOutput1Column(autocompleteDesign.getOutput01());
		outputForm.setOutput2Column(autocompleteDesign.getOutput02());
		outputForm.setOutput3Column(autocompleteDesign.getOutput03());
		outputForm.setOutput4Column(autocompleteDesign.getOutput04());
		outputForm.setOutput5Column(autocompleteDesign.getOutput05());
		outputForm.setOutput6Column(autocompleteDesign.getOutput06());
		outputForm.setOutput7Column(autocompleteDesign.getOutput07());
		outputForm.setOutput8Column(autocompleteDesign.getOutput08());
		outputForm.setOutput9Column(autocompleteDesign.getOutput09());
		outputForm.setOutput10Column(autocompleteDesign.getOutput10());
		outputForm.setOutput11Column(autocompleteDesign.getOutput11());
		outputForm.setOutput12Column(autocompleteDesign.getOutput12());
		outputForm.setOutput13Column(autocompleteDesign.getOutput13());
		outputForm.setOutput14Column(autocompleteDesign.getOutput14());
		outputForm.setOutput15Column(autocompleteDesign.getOutput15());
		outputForm.setOutput16Column(autocompleteDesign.getOutput16());
		outputForm.setOutput17Column(autocompleteDesign.getOutput17());
		outputForm.setOutput18Column(autocompleteDesign.getOutput18());
		outputForm.setOutput19Column(autocompleteDesign.getOutput19());
		outputForm.setOutput20Column(autocompleteDesign.getOutput20());
		
		this.setDisplayEnabled(outputForm, autocompleteDesign);
		outputForm.setSubmitColumn(autocompleteDesign.getSubmitColumn());
		return outputForm;
	}

	@Override
	public AutocompleteDesign convertTo(OutputForm outputForm,AutocompleteDesign autocompleteDesign) {
		if(outputForm!=null){
			if(autocompleteDesign==null){
				autocompleteDesign = new AutocompleteDesign();
			}
			autocompleteDesign.setOutput01(outputForm.getOutput1Column());
			autocompleteDesign.setOutput02(outputForm.getOutput2Column());
			autocompleteDesign.setOutput03(outputForm.getOutput3Column());
			autocompleteDesign.setOutput04(outputForm.getOutput4Column());
			autocompleteDesign.setOutput05(outputForm.getOutput5Column());
			autocompleteDesign.setOutput06(outputForm.getOutput6Column());
			autocompleteDesign.setOutput07(outputForm.getOutput7Column());
			autocompleteDesign.setOutput08(outputForm.getOutput8Column());
			autocompleteDesign.setOutput09(outputForm.getOutput9Column());
			autocompleteDesign.setOutput10(outputForm.getOutput10Column());
			autocompleteDesign.setOutput11(outputForm.getOutput11Column());
			autocompleteDesign.setOutput12(outputForm.getOutput12Column());
			autocompleteDesign.setOutput13(outputForm.getOutput13Column());
			autocompleteDesign.setOutput14(outputForm.getOutput14Column());
			autocompleteDesign.setOutput15(outputForm.getOutput15Column());
			autocompleteDesign.setOutput16(outputForm.getOutput16Column());
			autocompleteDesign.setOutput17(outputForm.getOutput17Column());
			autocompleteDesign.setOutput18(outputForm.getOutput18Column());
			autocompleteDesign.setOutput19(outputForm.getOutput19Column());
			autocompleteDesign.setOutput20(outputForm.getOutput20Column());
			
			autocompleteDesign.setDisplayColumnFlag(this.getDisplayFlag(outputForm));
			autocompleteDesign.setSubmitColumn(outputForm.getSubmitColumn());
		}
		return autocompleteDesign;
	}
	
	private final String separator = ",";
	
	private String getDisplayFlag(OutputForm autocompleteForm) {
		StringBuffer flag = new StringBuffer();
		flag.append(1);
		flag.append(separator);
		flag.append(1);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput1Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput2Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput3Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput4Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput5Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput6Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput7Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput8Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput9Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput10Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput11Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput12Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput13Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput14Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput15Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput16Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput17Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput18Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput19Display()?1:0);
		flag.append(separator);
		flag.append(autocompleteForm.getOutput20Display()?1:0);
		return flag.toString();
	}
	
	private void setDisplayEnabled(OutputForm outputForm,AutocompleteDesign autocompleteDesign) {
		String[] displayColumnFlags = StringUtils.split(autocompleteDesign.getDisplayColumnFlag(), separator);
		if(!(displayColumnFlags==null || displayColumnFlags.length!=22)){
			outputForm.setOutput1Display("1".equals(displayColumnFlags[2])?true:false);
			outputForm.setOutput2Display("1".equals(displayColumnFlags[3])?true:false);
			outputForm.setOutput3Display("1".equals(displayColumnFlags[4])?true:false);
			outputForm.setOutput4Display("1".equals(displayColumnFlags[5])?true:false);
			outputForm.setOutput5Display("1".equals(displayColumnFlags[6])?true:false);
			outputForm.setOutput6Display("1".equals(displayColumnFlags[7])?true:false);
			outputForm.setOutput7Display("1".equals(displayColumnFlags[8])?true:false);
			outputForm.setOutput8Display("1".equals(displayColumnFlags[9])?true:false);
			outputForm.setOutput9Display("1".equals(displayColumnFlags[10])?true:false);
			outputForm.setOutput10Display("1".equals(displayColumnFlags[11])?true:false);
			outputForm.setOutput11Display("1".equals(displayColumnFlags[12])?true:false);
			outputForm.setOutput12Display("1".equals(displayColumnFlags[13])?true:false);
			outputForm.setOutput13Display("1".equals(displayColumnFlags[14])?true:false);
			outputForm.setOutput14Display("1".equals(displayColumnFlags[15])?true:false);
			outputForm.setOutput15Display("1".equals(displayColumnFlags[16])?true:false);
			outputForm.setOutput16Display("1".equals(displayColumnFlags[17])?true:false);
			outputForm.setOutput17Display("1".equals(displayColumnFlags[18])?true:false);
			outputForm.setOutput18Display("1".equals(displayColumnFlags[19])?true:false);
			outputForm.setOutput19Display("1".equals(displayColumnFlags[20])?true:false);
			outputForm.setOutput20Display("1".equals(displayColumnFlags[21])?true:false);
		}
	}
}
