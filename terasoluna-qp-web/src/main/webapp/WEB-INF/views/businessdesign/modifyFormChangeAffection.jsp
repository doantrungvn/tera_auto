<tiles:insertDefinition name="layouts">
    
    <tiles:putAttribute name="breadcrumb">
         <li><span><qp:message code="tqp.businesslogicdesign"></qp:message></span></li>
         <li><span><qp:message code="sc.businesslogicdesign.0172" /></span></li>
    </tiles:putAttribute>
    
	<tiles:putAttribute name="body">
		<form:form method="post" modelAttribute="businessDesignForm" action="${pageContext.request.contextPath}/businessdesign/modify">
			<form:errors path="*" cssClass="alert qp-error" delimiter="<br/>" element="div" cssStyle="" />
			<c:if test="${not empty businessDesignForm.businessLogicId}">
				<div class="panel panel-default qp-div-information">
					<div class="panel-heading">
						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
						<span class="pq-heading-text">
							<qp:message code="sc.businesslogicdesign.0018" />
						</span>
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
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0005" /></th>
								<td><qp:formatText value="${businessDesignForm.businessLogicName}" /></td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0006" /></th>
								<td><qp:formatText value="${businessDesignForm.businessLogicCode}" /></td>
							</tr>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0019" /></th>
								<td><qp:message code="${CL_RETURN_TYPE.get(businessDesignForm.returnType.toString())}" /></td>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0020" /></th>
								<td><qp:message code="${CL_DESIGN_STATUS.get(businessDesignForm.designStatus.toString())}" /></td>
							</tr>
							<c:if test="${businessDesignForm.customizeFlg}">
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0021" /></th>
									<td id="valueContent"><qp:formatText value="${businessDesignForm.fileName}" /></td>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0022" /></th>
									<td><qp:formatText value="${businessDesignForm.packageName}" /></td>
								</tr>
							</c:if>
							<tr>
								<th class="qp-table-th-text style-header-table"><qp:message code="sc.sys.0028" /></th>
								<td colspan="3"><qp:formatText value="${businessDesignForm.remark}" /></td>
							</tr>
						</table>
					</div>
				</div>
				<div style="display:none">
					<input type="hidden" name="formJson" value="${f:h(formJson)}" />
				</div>
				<div class="qp-div-action">
					<qp:authorization permission="businesslogicModify">
						<button type="submit" class="btn qp-button" name="jsonBack">
							<qp:message code="sc.sys.0023" />
						</button>
						<button type="submit" class="btn qp-button qp-dialog-confirm">
							<qp:message code="sc.sys.0031"></qp:message>
						</button>
					</qp:authorization>
				</div>
                <c:if test="${!empty businessDesignForm.lstAffectedBlogicCommon}">
    				<div class="panel panel-default qp-div-select">
    					<div class="panel-heading">
    						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
    						<span class="pq-heading-text">
    							<qp:message code="sc.businesslogicdesign.0248" />
    						</span>
    					</div>
    					<div class="panel-body">
    						<table class="table table-bordered qp-table-list-none-action">
    							<colgroup>
    								<col>
    								<col>
    								<col width="30%">
    								<col width="30%">
    							</colgroup>
    							<thead>
    								<tr>
    									<th><qp:message code="sc.sys.0004" /></th>
    									<th><qp:message code="sc.businesslogicdesign.0005" /></th>
    									<th><qp:message code="sc.businesslogicdesign.0006" /></th>
    									<th><qp:message code="sc.module.0007" /></th>
    								</tr>
    							</thead>
    							<tbody>
    								<c:forEach var="businessLogic" items="${businessDesignForm.lstAffectedBlogicCommon}" varStatus="status">
    									<tr>
    										<td>${status.count}</td>
    										<td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
    										<td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
    										<td><qp:formatText value="${businessLogic.moduleIdAutocomplete}" /></td>
    									</tr>
    								</c:forEach>
    								<c:if test="${empty businessDesignForm.lstAffectedBlogicCommon}">
    									<tr>
    										<td colspan="4"><qp:message code="inf.sys.0013" /></td>
    									</tr>
    								</c:if>
    							</tbody>
    						</table>
    					</div>
    				</div>
                </c:if>
                <c:if test="${!empty businessDesignForm.lstAffectedBlogicNavigator}">
                    <div class="panel panel-default qp-div-select">
                        <div class="panel-heading">
                            <span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
                            <span class="pq-heading-text">
                                <qp:message code="sc.businesslogicdesign.0249" />
                            </span>
                        </div>
                        <div class="panel-body">
                            <table class="table table-bordered qp-table-list-none-action">
                                <colgroup>
                                    <col>
                                    <col>
                                    <col width="30%">
                                    <col width="30%">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><qp:message code="sc.sys.0004" /></th>
                                        <th><qp:message code="sc.businesslogicdesign.0005" /></th>
                                        <th><qp:message code="sc.businesslogicdesign.0006" /></th>
                                        <th><qp:message code="sc.module.0007" /></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="businessLogic" items="${businessDesignForm.lstAffectedBlogicNavigator}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td><qp:formatText value="${businessLogic.businessLogicName}" /></td>
                                            <td><qp:formatText value="${businessLogic.businessLogicCode}" /></td>
                                            <td><qp:formatText value="${businessLogic.moduleIdAutocomplete}" /></td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty businessDesignForm.lstAffectedBlogicNavigator}">
                                        <tr>
                                            <td colspan="4"><qp:message code="inf.sys.0013" /></td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                
                <br />
                
                <c:if test="${!empty businessDesignForm.lstAffectedScreenItems}">
    				<div class="panel panel-default qp-div-select">
    					<div class="panel-heading">
    						<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
    						<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0250" /></span>
    					</div>
    					<div class="panel-body">
    						<table class="table table-bordered qp-table-list-none-action">
                                <colgroup>
                                    <col/>
                                    <col/>
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                    <col width="20%" />
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><qp:message code="sc.sys.0004"></qp:message></th>
                                        <th><qp:message code="sc.screendesign.0194"></qp:message></th>
                                        <th><qp:message code="sc.screendesign.0093"></qp:message></th>
                                        <th><qp:message code="sc.screendesign.0271"></qp:message></th>
                                        <th><qp:message code="sc.businesslogicdesign.0047" /></th>
                                        <th><qp:message code="sc.module.0007"></qp:message></th>
                                    </tr>
                                </thead>
                                <c:forEach var="screenItem" items="${businessDesignForm.lstAffectedScreenItems}" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td><qp:formatText value="${screenItem.messageDesign.messageString}"/></td>
                                        <td><qp:formatText value="${screenItem.itemCode}"/></td>
                                        <td><qp:message code="${CL_QP_ITEMTYPE.get(screenItem.logicalDataType.toString())}" /></td>
                                        <td><qp:formatText value="${screenItem.messageScreen.messageString}"/></td>
                                       	<td><qp:formatText value="${screenItem.moduleName}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
    					</div>
    				</div>
                </c:if>
			</c:if>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>