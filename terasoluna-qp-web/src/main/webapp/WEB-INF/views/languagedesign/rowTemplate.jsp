<script id="languageDesignForm-template" type="text/template">
	<tr class="ar-dataRow">
	<td><span class="ar-groupIndex"></span></td>
	<td>
		<select name="languageDesignForms[0].languageCode" class="form-control qp-input-select" onchange="typeChangeLanguage(this)" >
			<option value=""><qp:message code="sc.sys.0030"></qp:message></option>
			<c:forEach var="item" items="${CL_LANGUAGE_CODE }">
	             <option value="${item.key}"><qp:message code="${CL_LANGUAGE_CODE.get(item.key)}"/></option>
			</c:forEach>
		</select>
		<input type="hidden" name="languageDesignForms[0].languageName" />
		<input type="hidden" name="languageDesignForms[0].languageId"/>
		<input type="hidden" name="languageDesignForms[0].itemSeqNo" class="ar-groupIndex"/>
	</td>
	<td style="border-right: none; padding-top:8px;"></td>
	<td>
		<input type="radio" name="defaultLanguage" class="radio qp-input-radio ar-groupIndex"/>
	</td>
	<td>
		<a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" onclick="$.qp.ar.removeRow({link:this,isReserved:true});" href="javascript:void(0)"></a>
	</td>
	</tr>
</script>