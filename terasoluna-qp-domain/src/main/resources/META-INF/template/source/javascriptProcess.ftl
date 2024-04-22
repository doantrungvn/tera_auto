$(document).ready(function() {
        		<#list screenItemEvents as item>
	        		$('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find('[name="${item.itemCode}"]').change(function(){
	        			<#list item.screenItemEvents as event>
	        				<#if event.eventType == 1>
	        					<#assign query= ''>
								var url = '';
								var data = {};
								<#list event.screenItemEventMappings as mapping>
									<#if mapping.parentCodeInput?has_content>
										<#assign parentCodeInput = "${mapping.parentCodeInput}${'.'}" >
									<#else>
										<#assign parentCodeInput = "" />
									</#if>
									<#if mapping.inputBeanCode?has_content>
										<#assign inputBeanCode = "${mapping.inputBeanCode}" >
									<#else>
										<#assign inputBeanCode  = "" />
									</#if>
									<#assign elementNameInput = "${parentCodeInput}${inputBeanCode}">
									<#if elementNameInput?has_content>
										<#assign itemCode = (mapping.itemCode)!"">
										var ${elementNameInput} = '';
										<#if itemCode?string != "">
											<#assign itemCodeArr = itemCode?split('.')>
											
											<#if itemCodeArr?size == 3 >
												
												<#if itemCodeArr[0] != "" && itemCodeArr[1] != "" && itemCodeArr[2] != "" >
													${elementNameInput} = $('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val();
												</#if>
											</#if>
										</#if>
										
										<#assign query= "${query}${elementNameInput}=\"+${elementNameInput}+\"&">
									</#if>
								</#list>
								<#assign spaceParam= "$">
								<#if query?has_content>
									<#assign spaceParam= "">
								<#else>
									
								</#if>
								url = CONTEXT_PATH + "/${moduleCode?uncap_first}/${event.businessLogic.businessLogicCode}?${query}" +"${spaceParam}r="+Math.random();
								data = $.qp.getData(url);
								
								<#assign formCode = "" >
								<#assign areaCode = "" >
								<#assign elementNameOutput = "" >
								
								<#list event.screenItemEventMappings as mapping>
									<#if mapping.outBeanCode?has_content>
										<#assign outBeanCode = "${mapping.outBeanCode}" >
									<#else>
										<#assign outBeanCode  = "" />
									</#if>
									<#assign elementNameOutput = "${outBeanCode}">
									<#if elementNameOutput?has_content>
										<#assign itemCode = (mapping.itemCode)!"">
										<#if itemCode?string != "">
											<#assign itemCodeArr = itemCode?split('.')>
											<#if itemCodeArr?size == 3 >
												<#if itemCodeArr[0] != "" && itemCodeArr[1] != "" && itemCodeArr[2] != "" >
													<#assign formCode = itemCodeArr[0] >
													<#assign areaCode = itemCodeArr[1] >
												</#if>
											</#if>
										</#if>
									</#if>
								</#list>
								
								
							<#if parentOutputBeanCode?has_content>
								if ($.isArray(data.${parentOutputBeanCode})) {
									var trHidden = $('[name="${formCode}"]').find('[name="${areaCode}"]').find("table tbody tr:first");
									$('[name="${formCode}"]').find('[name="${areaCode}"]').find("tbody").find("tr:visible").remove();
									for (var i = 0; i < data.${parentOutputBeanCode}.length; i++) {
										trHidden.find("td:first").html(i+1);
										$('[name="${formCode}"]').find('[name="${areaCode}"]').find("table tbody").append(trHidden.prop('outerHTML'));
										$('[name="${formCode}"]').find('[name="${areaCode}"]').find("table tbody tr:last").attr("style", null);
							<#else>
								if ($.isArray(data)) {
									for (var i = 0; i < data.length; i++) {
							</#if>
											<#list event.screenItemEventMappings as mapping>
												<#if mapping.parentCodeOutput?has_content>
													<#assign parentCodeOutput = "${mapping.parentCodeOutput}${'.'}" >
												<#else>
													<#assign parentCodeOutput = "" />
												</#if>
												<#if mapping.outBeanCode?has_content>
													<#assign outBeanCode = "${mapping.outBeanCode}" >
												<#else>
													<#assign outBeanCode  = "" />
												</#if>
												<#assign elementNameOutput = "${outBeanCode}">
												
												<#if elementNameOutput?has_content>
													<#assign itemCode = (mapping.itemCode)!"">
													<#if itemCode?string != "">
														<#assign itemCodeArr = itemCode?split('.')>
														<#if itemCodeArr?size == 3 >
															<#if itemCodeArr[0] != "" && itemCodeArr[1] != "" && itemCodeArr[2] != "" >
																<#if parentOutputBeanCode?has_content>
																	$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find("table tbody tr:last").find("td[itemCode='${itemCodeArr[2]}']").html(data.${parentOutputBeanCode}[i]['${elementNameOutput}']);
																<#else>
																	$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find("table tbody tr:last").find("td[itemCode='${itemCodeArr[2]}']").html(data[i]['${elementNameOutput}']);
																</#if>
															</#if>
														</#if>
													</#if>
												</#if>
											</#list>
										}
										
										//Set value for <td>
										var sizeTbody = $('[name="${formCode}"]').find('[name="${areaCode}"]').find("table tbody").find("tr").size();
										<#if parentOutputBeanCode?has_content>
											for (var i = 0; i < data.${parentOutputBeanCode}.length; i++) {
										<#else>
											for (var i = 0; i < data.length; i++) {
										</#if>
											<#list event.screenItemEventMappings as mapping>
												<#if mapping.parentCodeOutput?has_content>
													<#assign parentCodeOutput = "${mapping.parentCodeOutput}${'.'}" >
												<#else>
													<#assign parentCodeOutput = "" />
												</#if>
												<#if mapping.outBeanCode?has_content>
													<#assign outBeanCode = "${mapping.outBeanCode}" >
												<#else>
													<#assign outBeanCode  = "" />
												</#if>
												<#assign elementNameOutput = "${outBeanCode}">
												
												<#if elementNameOutput?has_content>
													<#assign itemCode = (mapping.itemCode)!"">
													<#if itemCode?string != "">
														<#assign itemCodeArr = itemCode?split('.')>
														<#if itemCodeArr?size == 3 >
															<#if itemCodeArr[0] != "" && itemCodeArr[1] != "" && itemCodeArr[2] != "" >
																$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find("td[itemCode='${itemCodeArr[2]}']").find('[name$="${itemCodeArr[2]}"]').eq(i).val(data.output01[i]['${elementNameOutput}']);
															</#if>
														</#if>
													</#if>
												</#if>
											</#list>
										}
									
								} else {
									<#list event.screenItemEventMappings as mapping>
										<#assign parentCodeOutput = "" />
										<#assign outBeanCode  = "" />
										<#if mapping.parentCodeOutput?has_content>
											<#assign parentCodeOutput = "${mapping.parentCodeOutput}" >
										</#if>
										<#if mapping.outBeanCode?has_content>
											<#assign outBeanCode = "${mapping.outBeanCode}" >
										</#if>
										<#assign elementNameOutput = "${parentCodeOutput}${'.'}${outBeanCode}">
										
										<#if elementNameOutput?has_content>
											<#assign itemCode = (mapping.itemCode)!"">
											<#if itemCode?string != "">
												<#assign itemCodeArr = itemCode?split('.')>
												<#if itemCodeArr?size == 3 >
													<#if itemCodeArr[0] != "" && itemCodeArr[1] != "" && itemCodeArr[2] != "" >
														$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[1]}${'.'}${itemCodeArr[2]}"]').val(data["${parentCodeOutput}"]${'.'}${outBeanCode});
													</#if>
												</#if>
											</#if>
										</#if>
									</#list>
								}
								
	        				<#else>
	        					$('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find('[name="${item.itemCode}"]').closest('form').submit();
							</#if>
	        			</#list>
	        		});
				</#list>
				
				<#list autocompletes as item>
					<#assign i = 0>
					<#list item.screenItemAutocompleteInputs as input>
						<#if input.logicalDataTypeDepend?has_content>
							<#if input.logicalDataTypeDepend != 0>
								<#if input.screenItemCode?has_content>
									<#assign itemCodeArr = (input.screenItemCode)?split('.')>
									$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name${'$'}="${itemCodeArr[2]}"]').change(function() {
										<#assign index = (i+1)?number>
										<#assign length = index?length>
										<#assign new = 2 -length>
											
										$('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find('[name="${item.screenArea.areaCode}${'.'}${item.itemCode}Autocomplete"]').attr("arg0${index?left_pad(new, "0")}", $(this).val());
									});
									
								</#if>
							</#if>
						</#if>
					</#list>
				</#list>
				
        	});
        	
			<#list autocompletes as item>
				<#assign i = 0>
				<#list item.screenItemAutocompleteInputs as autoCompleteinput>
					<#assign index = (i+1)?number>
					<#if autoCompleteinput.screenItemCode?has_content>
						<#if autoCompleteinput.logicalDataTypeDepend?has_content>
							<#if autoCompleteinput.logicalDataTypeDepend == 0>
								<#if (autoCompleteinput.screenItemCodeDepend)?has_content && (autoCompleteinput.screenItemIdDepend)?has_content>
									<#assign nameFunction = "change" + autoCompleteinput.screenItemCodeDepend + (autoCompleteinput.screenAutocompleteInputId)?c >
										function ${nameFunction}(obj) {
											<#assign itemCodeArr = (autoCompleteinput.screenItemCode)?split('.')>
											
											<#assign length = index?length>
											<#assign new = 2 -length>
											<#assign input = "input0" + index?string>
											var ${input} = $("input[name$=${itemCodeArr[2]}]").val();
											$('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find('[name="${autoCompleteinput.screenItemCodeBeDepended}Autocomplete"]').val("");
											$('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find('[name="${autoCompleteinput.screenItemCodeBeDepended}Autocomplete"]').attr("arg0${index?left_pad(new, "0")}", ${input});
											$("${'#'}${autoCompleteinput.screenItemCodeBeDepended}AutocompleteId").data("ui-autocomplete")._trigger("change");
										}
										
										$(document).ready(function() {
											 $('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find("input[name$=${itemCodeArr[2]}Autocomplete]").attr("onchangeevent","${nameFunction}");
											 $.qp.initialAutocomplete($('[name="${item.screenForms.formCode}"]').find('[name="${item.screenArea.areaCode}"]').find("input[name$=${itemCodeArr[2]}Autocomplete]"));
										});
										
									<#assign i = i + 1>
								</#if>
							</#if>
						</#if>
					</#if>
				</#list>	
			</#list>
			
<#list screenAreaEvents as area>
	<#list area.screenAreaEvents as event>
		function eventArea${event.screenAreaEventId?replace(",", "")?replace(".", "")}(obj) {
			var strAlert = "";
			var isRequired = false;
			<#list event.ifRequire?split(',') as item>
				<#assign itemCodeArr = item?split('.')>
			if ($('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val() != undefined && $('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val() != null 
				&& $('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val().length > 0) {
				isRequired = true;	
			}
			</#list>
			if (isRequired) {
				<#list event.thenMustRequire?split(',') as item>
					<#assign itemCodeArr = item?split('.')>
				if ($('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val() != undefined && $('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val() != null 
					&& $('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').val().length == 0) {
					strAlert += "${itemCodeArr[2]} ${messageRequire}";
					strAlert += "\n";
					$('[name="${itemCodeArr[0]}"]').find('[name="${itemCodeArr[1]}"]').find('[name="${itemCodeArr[2]}"]').focus();
				}
				</#list>
			}
			if(strAlert.length > 0) {
				alert(strAlert);
				return false;
			}
		}
	</#list>
</#list>
			
			
function getObjectJson(object, name) {
  if (name in object) return object[name];
  for (key in object) {
    if ((typeof (object[key])) == 'object') {
      var t = findSomething(object[key], name);
      if (t) return t;
    }
  }
  return null;
}