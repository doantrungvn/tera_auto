<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">	
	    <qp:message code="sc.screendesign.0253"/>
	</tiles:putAttribute>
	<tiles:putAttribute name="header-link">
		<%-- <a class="qp-link-popup" href="${pageContext.request.contextPath}/sample/createForm"></a> --%>
	</tiles:putAttribute>
	<tiles:putAttribute name="additionalHeading">

	</tiles:putAttribute>
	<tiles:putAttribute name="body">

		<script id="tbl-action-template" type="text/template">
			<tr>
				<td style="border: none;" width="70%"><select class="test combobox input-large form-control qp-input-autocomplete"></select></td>
				<td style="border: none;" width="28%"><qp:message code="sc.screendesign.0297" /></td>
				<td style="border: none;" class="com-output-fixlength" width="2%">
					<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title="" onclick="$.qp.removeRowJS('dynamic', this);" style="margin-top: 3px;" href="javascript:void(0)"></a>
				</td>
			</tr>
		</script>		

		<form:form method="post" modelAttribute="sampleForm"
			action="${pageContext.request.contextPath}/sample/delete">

			
			<div class="qp-div-action">
				<button type="button"
					class="btn qp-button-warning qp-dialog-confirm" name="mode"
					value="3"><qp:message code="sc.sys.0031"/></button>
			</div>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>