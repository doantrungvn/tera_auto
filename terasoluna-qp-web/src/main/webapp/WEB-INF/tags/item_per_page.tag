<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://nttdata.com/tags/qp" prefix="qp"%>
<%@ attribute name="form" required="true"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="overwriteMessage" required="false"%>
<script type="text/javascript">
	var actionPath = '${action}';
	var form = "#"+'${form}';
	var href = CONTEXT_PATH + "/PageSize/setPageSize?r="+Math.random();
	function calSavePageSize() {
		if($(form).length >0){
			var size = $( "#fcomPageSizeSelect" ).val();
			var param =  {
					action : actionPath,
					size : size
				};
			$.ajax({
				method : "GET",
				url : href,
				data : {data:JSON.stringify(param)}
			}).done(function(msg) {
				if(msg.status == '1'){
					$(form).submit();
				}
				else{
					// Error when save information of pagesize
					alert("");
				}
			});
		}else{
			// This form is not exist.
			alert("");
		}
		
	}
</script>
<c:set var="messageCd">sc.sys.0003</c:set>
<c:if test="${ not empty overwriteMessage }">
	<c:set var="messageCd">${overwriteMessage}</c:set>	 
</c:if> 
<c:choose>
	<c:when test="${page != null }">
		<c:set var="fcomSize" value="${page.size == null ? 10 : page.size}"></c:set>
		<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text">
			<qp:message code="${messageCd}"/>
			&nbsp;
			<span class="badge">&nbsp;${page.totalElements}&nbsp;</span>
		</span>
		<div class="form-inline qp-item-per-page">
			<label>
				<select id="fcomPageSizeSelect" style="margin-top: -3px;" name="size" aria-controls="dataTables-example" class="form-control qp-input-select" onchange="calSavePageSize()" >
					<c:forEach var="entry" items="${CL_PAGESIZE}">
						<c:if test="${entry.key == fcomSize}">
		    				<option value="${entry.key}" selected="selected">${entry.value}</option>
		    			</c:if>
		    			<c:if test="${entry.key != fcomSize}">
		    				<option value="${entry.key}">${entry.value}</option>
		    			</c:if>
		  			</c:forEach>
				</select>
			</label>
		</div>
	</c:when>
	<c:otherwise>
		<span class="glyphicon qp-heading-icon" style="width: 24px;" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text">
			<qp:message code="${messageCd}"/>
			&nbsp;
			<span class="badge">&nbsp;0&nbsp;</span>
		</span>
	</c:otherwise>
</c:choose>
