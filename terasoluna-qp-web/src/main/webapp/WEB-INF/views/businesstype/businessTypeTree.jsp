<div class="btn-group qp-input-customselect">
	<form:input type="text" path="businessTypeName" cssClass="form-control dropdown-toggle" data-toggle="dropdown" maxlength="200" />
	<form:hidden path="businessTypeId" />
	<span class="caret"></span>
	<ul class="dropdown-menu" id="businessType"></ul>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businesstype/builddata.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/businesstype/menutree.js"></script>
	<script type="text/javascript">var menuData = [<c:forEach var="bt" items="${businessTypes}" varStatus="status">{"businessTypeId": "${bt.businessTypeId}","businessTypeName": "${bt.businessTypeName}","businessTypeCode": "${bt.businessTypeCode}","parentBusinessTypeId": "${bt.parentBusinessTypeId}","level": "${bt.level}","path": "${bt.path}"}<c:if test="${!status.last}">,</c:if></c:forEach>];</script>
	<script type="text/javascript">
		$(document).ready(function(){
			var businessType = new menuTree(buildData(menuData));
			businessType.show('businessType');
			
			// reset businessTypeId in case of businessTypeName is empty
			$("input[id='businessTypeName']").change(function(){
				var inputText = $(this);
				var nextHidden =  inputText.next("input[type=hidden]");
				
				// reset parentBusinessTypeId before populating data
				nextHidden.val("");
				
				// in case of parentBusinessTypeName is empty, parentBusinessTypeId must be reseted
				if (!inputText.val()) {
					nextHidden.val("");
				} 
				
				// otherwise, reset parentBusinessTypeId 
				if (!nextHidden.val()) {
					inputText.val ("");
				}
			});
		});
	</script>
</div>