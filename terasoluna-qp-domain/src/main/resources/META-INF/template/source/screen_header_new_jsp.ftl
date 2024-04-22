<#macro compress_single_line>
	<#local captured><#nested></#local>
	${ captured?replace("^\\s*[\\n\\r]+", "", "rm") }
</#macro>
<@compress_single_line>
<#if screenDesignForm.templateType == 1>
	<tiles:insertDefinition name="layouts">
<#else>
	<tiles:insertDefinition name="layouts-popup">
</#if>
	<#if screenDesignForm.templateType?has_content && screenDesignForm.templateType == 1>
		<tiles:putAttribute name="breadcrumb">
	         <li><span><qp:message code="${projectCode}${'.'}${moduleCode}"/></span></li>
	         <li><span><qp:message code="${screenCode}" /></span></li>
	    </tiles:putAttribute>
    <#else>
    	<tiles:putAttribute name="header-name">
	         <span><qp:message code="${screenCode}" /></span>
	    </tiles:putAttribute>
    </#if>
    
    <#if screenDesignForm.headerLinkItems?size gt 0>
		<tiles:putAttribute name="header-link">
			<#list screenDesignForm.areaNonGroup as screenArea>
				<#list screenDesignForm.headerLinkItems as headerArea>
					<#if screenArea.screenAreaId == headerArea.screenAreaId &&  screenArea.areaType == -1>
						${headerArea.element}&nbsp;
					</#if>
				</#list>
			</#list>
		</tiles:putAttribute>
	</#if>
		
    <tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext}/resources/app/${moduleCode?uncap_first}/${processJS}"></script>
		<script type="text/javascript" src="${pageContext}/resources/app/${moduleCode?uncap_first}/${initJS}"></script>
		<script>
			function beforePostNotConfirm(obj) {
				var url = $(obj).attr('button-href');
				var form = $(obj).closest("form").attr("action",url);
				$.qp.undoFormatNumericForm(form);
				form.submit();
			}
			function beforePostHaveConfirm(obj) {
				var url = $(obj).attr('button-href');
				$(obj).closest("form").attr("action",url);
			}
			
			function beforeGetNotConfirm(obj) {
				var url = $(obj).attr('button-href');
				window.location.href = url;
			}
			function beforeGetHaveConfirm(obj) {
				var url = $(obj).attr('button-href');
			}
		</script>
		
		<style>
			.qp-button-type {
				${projectStyle['commonButtonBgColor']}
				${projectStyle['commonButtonTextColor']}
			}
			
			.qp-button-type:ACTIVE {
				${projectStyle['commonButtonBgActiveColor']}
			}
			
			.qp-button-type-warning {
				${projectStyle['commonButtonDeleteBgColor']}
				${projectStyle['commonButtonDeleteTextColor']}
			}
			
			.qp-button-type-warning:ACTIVE {
				${projectStyle['commonButtonDeleteBgActiveColor']}
			}
			
			.qp-button-type-client {
				${projectStyle['clientButtonDeleteBgColor']}
				${projectStyle['clientButtonDeleteTextColor']}
			}
			
			.qp-button-type-client:ACTIVE {
				${projectStyle['clientButtonDeleteBgActiveColor']}
			}
		</style>
		
		
    </tiles:putAttribute>
    
    <tiles:putAttribute name="body">
</@compress_single_line>