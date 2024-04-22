<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="additionalHeading">
	</tiles:putAttribute>
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.account" /></span></li>
         <li><span><qp:message code="sc.accountproject.0001"/></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="header-link">
		<qp:authorization permission="accountSearch">
			<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
			<a href="${pageContext.request.contextPath}/account/search"><qp:message code="sc.account.0008" /></a>
		</qp:authorization>
        
        <script type="text/javascript">
        	$(document).ready(function () {
                
        		var $checkBoxChecked = $("input[scope='node']:checked");
    			var $checkBoxAll = $('input[scope="node"]');
    			if($checkBoxChecked.length == $checkBoxAll.length) {
    				$('input[scope="root"]').prop('checked', true);
    			} else {
    				$('input[scope="root"]').prop('checked', false);
    			}
        	});
        	
            function changeChecked(obj) {
                var $table = $(obj).closest('table');
                
        		switch ($(obj).attr('scope')) {
        		case "root":
        			$table.find('input[scope="node"]').prop('checked', $(obj).is(':checked'));
        			break;
        	
        		default:
        			var $checkBoxChecked = $table.find('input[scope="node"]:checked');
        			var $checkBoxAll = $table.find('input[scope="node"]');
        			if($checkBoxChecked.length == $checkBoxAll.length && $(obj).is(':checked')) {
        				$table.find('input[scope="root"]').prop('checked', $(obj).is(':checked'));
        			} else {
        				$table.find('input[scope="root"]').prop('checked', false);
        			}
        			break;
        		}
            }
            
        </script>
        
	</tiles:putAttribute>
	
	<tiles:putAttribute name="body">
	 <form:form method="post" action="${pageContext.request.contextPath}/accountproject/modify" modelAttribute="accountProjectListWrapper">
		<input type="hidden" value="${notExistFlg}"/>
		<c:if test="${notExistFlg ne 1}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span> <span class="pq-heading-text"><qp:message code="sc.accountrolepermission.0010" /></span>
					</div>
					<div class="panel-body">
						<table class="table table-bordered qp-table-form">
							<colgroup>
								<col width="20%" />
								<col width="30%" />
								<col width="20%" />
								<col width="30%" />
							</colgroup>
							<tr>
								<th><qp:message code="sc.accountrolepermission.0005" /></th>
								<td>${accountForm.username}</td>
								<th><qp:message code="sc.accountrolepermission.0006" /></th>
								<td>${accountForm.accountNonLocked}</td>
							</tr>
							<%--
								<tr>
									<th><qp:message code="sc.accountrolepermission.0007" /></th>
									<td>${accountForm.accountNonExpired}</td>
									<th><qp:message code="sc.accountrolepermission.0008" /></th>
									<td>${accountForm.credentialsNonExpired}</td>
								</tr>
							 --%>
						</table>
					</div>
				</div>
				<br />
			
				<div class="panel panel-default qp-div-select">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text"><qp:message code="sc.project.0010" /></span>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="tblAccountProject" class="table table-bordered qp-table-list-none-action">
								<colgroup>
									<col width="30px"/>
									<col width="40%" />
									<col width="30%" />
									<col width="30%" />
								</colgroup>
								<thead>
									<tr>
										<th><input type="checkbox" class="checkbox qp-input-checkbox" onchange="changeChecked(this)" scope="root"></input></th>
										<th><qp:message code="sc.project.0005" /></th>
										<th><qp:message code="sc.project.0006" /></th>
										<th><qp:message code="sc.sys.0055" /></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="project" items="${accountProjectListWrapper.accProjectList}" varStatus="rowStatus">
									<tr>
										<td> 
											<form:checkbox path="accProjectList[${rowStatus.index}].selected" class="checkbox qp-input-checkbox" scope="node" onchange="changeChecked(this)"/> 
										</td>
										<td>
										    <qp:authorization permission="projectView" isDisplay="true" displayValue="${project.projectName}">
                                                <c:choose>
                                                    <c:when test="${project.owerProject}">                                                 
	                                                   <a class="qp-link-popup" href="${pageContext.request.contextPath}/project/view?projectId=${f:h(project.projectId)}"><qp:formatText value="${project.projectName}" /></a>
                                                    </c:when>  
                                                    <c:otherwise>
                                                        <qp:formatText value="${project.projectName}" />
                                                    </c:otherwise>
                                                </c:choose>
	                                       </qp:authorization>
		                                   </td>
										<td><qp:formatText value="${project.projectCode}"/></td>
										<td><qp:message code="${CL_DESIGN_STATUS.get(project.status.toString())}"/></td>
										<form:hidden path="accProjectList[${rowStatus.index}].accountId" value="${project.accountId}" />
										<form:hidden path="accProjectList[${rowStatus.index}].projectId" value="${project.projectId}" />
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
	
				<div class="qp-div-action">
					<form:hidden path="accountId" value="${accountForm.accountId}" />
					<form:hidden path="updatedDate" value="${accountForm.updatedDate}" />
					<button type="button" class="btn qp-button qp-dialog-confirm">
						<qp:message code="sc.sys.0031" />
					</button>
				</div>
			
		</c:if>	
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>