<style type="text/css">
.label-menu-corner {
	font-size: 10px;
	line-height: 12px;
	position: absolute;
	left: 24px;
	top: 8px;
}

.dropdown-alerts {
	min-width: 250px;
}
</style>
<div class="com-header-info-panel" style="height: 47px;">
			<div class="com-header-logo-panel" style="width: 30%; float: left; padding-top: 12px; height: 100%;">
				<div style="float: left; ">
					<a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/resources/media/images/logo_header_01.png" style="border: 0px; height: 25px;" /> </a>
				</div>
			</div>
			<div class="com-header-logo-panel" style="width: 70%; float: left; padding-top: 12px; height: 50%;">
				<div type="0" style="float: left;  width: 38%;">&nbsp;</div>
				<div type="1" style="float: right;  width: 38%; text-align: right;">&nbsp;</div>
			</div>
			<div class="com-header-button-panel" style="width: 70%; float: left; height: 50%;">
				<div type="3" style="width: 100%; float: right; text-align: right;">
					<c:if test="${not empty sessionScope.CURRENT_PROJECT}">
						<qp:message code="sc.tqp.0017" />:
						<qp:authorization permission="projectView" isDisplay="true" displayValue="${sessionScope.CURRENT_PROJECT.projectName}">
							<a class="qp-link-popup" href="${pageContext.request.contextPath}/project/view?projectId=${f:h(sessionScope.CURRENT_PROJECT.projectId)}"><qp:formatText value="${sessionScope.CURRENT_PROJECT.projectName}" /></a>
						</qp:authorization>
						(<a href="${pageContext.request.contextPath}/home"><qp:message code="sc.tqp.0018" /></a>)
					</c:if>
					<c:if test="${empty sessionScope.CURRENT_PROJECT && not empty sessionScope.ACCOUNT_PROFILE.currentProjectId}">
						<a href="${pageContext.request.contextPath}/home"><qp:message code="sc.homepage.0005" /></a>
					</c:if>
				</div>
			</div>
		</div>
<!-- Start Menu panel -->
<div class="navbar navbar-default navbar-horizontal">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home"><qp:message code="tqp.tqp" /></a>
		</div>
	
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="navbar-menu">
			<ul class="nav navbar-nav">
				<qp:authorizations permissions="projectSearch,businesstypeSearch,moduleSearch,functiondesignSearch">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><qp:message code="sc.tqp.0011"/> </a>
						<ul class="dropdown-menu">
							<qp:authorization permission="projectSearch">
								<li><a href="${pageContext.request.contextPath}/project/search?init"><qp:message code="tqp.project" /></a></li>
							</qp:authorization>
							<c:if test="${not empty sessionScope.CURRENT_PROJECT}">
								<qp:authorization permission="businesstypeSearch">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/businesstype/search?init"><qp:message code="tqp.businesstype" /></a></li>
								</qp:authorization>
								<qp:authorization permission="moduleSearch">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/module/search?init"><qp:message code="tqp.module" /></a></li>
								</qp:authorization>
								<qp:authorization permission="functiondesignSearch">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/functiondesign/search?init"><qp:message code="tqp.functiondesign" /></a></li>
								</qp:authorization>
							</c:if>
						</ul>
					</li>
				</qp:authorizations>
				<c:if test="${not empty sessionScope.CURRENT_PROJECT}">
					<qp:authorizations permissions="domaindesignSearch,subjectareaSearch,tabledesignSearch,graphicdatabasedesignSearch,viewdesignSearch,autocompleteSearch,sqldesignSearch,screendesignTransition,screendesignSearch,decisiontableSearch,businesslogicSearch,commonobjectdefinitionSearch,externalobjectdefinitionSearch">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><qp:message code="sc.tqp.0012" /> </a>
							<ul class="dropdown-menu" role="menu">
								<qp:authorization permission="domaindesignSearch">
									<li><a href="${pageContext.request.contextPath}/domaindesign/search?init"><qp:message code="tqp.domaindesign" /></a></li>
								</qp:authorization>
								
								<qp:authorizations permissions="subjectareaSearch,tabledesignSearch,graphicdatabasedesignSearch,viewdesignSearch,importschema">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/importschema/importschema?init"><qp:message code="tqp.importschema" /></a></li>
									
									<qp:authorization permission="subjectareaSearch">
										<li><a href="${pageContext.request.contextPath}/subjectarea/search?init"><qp:message code="tqp.subareadesign" /></a></li>
									</qp:authorization>
									<qp:authorization permission="tabledesignSearch">
										<li><a href="${pageContext.request.contextPath}/tabledesign/search?init"><qp:message code="tqp.tabledesign" /></a></li>
									</qp:authorization>
									<qp:authorization permission="graphicdatabasedesignSearch">
										<li><a href="${pageContext.request.contextPath}/graphicdatabasedesign/search?init"><qp:message code="tqp.graphicdatabasedeisgn" /></a></li>
									</qp:authorization>
									<qp:authorization permission="viewdesignSearch">
										<li><a href="${pageContext.request.contextPath}/viewdesign/search?init"><qp:message code="tqp.viewdesign" /></a></li>
									</qp:authorization>
								</qp:authorizations>
								
								<qp:authorizations permissions="autocompleteSearch,sqldesignSearch">
									<li class="divider"></li>
									<qp:authorization permission="autocompleteSearch">
										<li><a href="${pageContext.request.contextPath}/autocomplete/search?init"><qp:message code="tqp.autocomplete" /></a></li>
									</qp:authorization>
									<qp:authorization permission="sqldesignSearch">
										<li><a href="${pageContext.request.contextPath}/sqldesign/search?init"><qp:message code="tqp.sqldesign" /></a></li>
									</qp:authorization>
									<%-- <qp:authorization permission="domaindatatypeSearch">
										<li><a href="${pageContext.request.contextPath}/domaindatatype/search?init"><qp:message code="tqp.domaindatatype" /></a></li>
										<li class="divider"></li>
									</qp:authorization> --%>
									
								</qp:authorizations>
								
								<qp:authorizations permissions="screendesignTransition,screendesignSearch">
									<li class="divider"></li>
									<qp:authorization permission="screendesignTransition">
										<li><a href="${pageContext.request.contextPath}/screendesign/transition?init"><qp:message code="tqp.screentransition" /></a></li>
									</qp:authorization>
									<qp:authorization permission="screendesignSearch">
										<li><a href="${pageContext.request.contextPath}/screendesign/search?init"><qp:message code="tqp.screenlist" /></a></li>
									</qp:authorization>
								</qp:authorizations>
								
								<qp:authorizations permissions="decisiontableSearch,businesslogicSearch,commonobjectdefinitionSearch,externalobjectdefinitionSearch,sessionmanagementSearch">
									<li class="divider"></li>
									<qp:authorization permission="externalobjectdefinitionSearch">
										<li><a href="${pageContext.request.contextPath}/externalobjectdefinition/search?init"><qp:message code="tqp.externalobjectdefinition" /></a></li>
									</qp:authorization>
									<qp:authorization permission="commonobjectdefinitionSearch">
										<li><a href="${pageContext.request.contextPath}/commonobjectdefinition/search?init"><qp:message code="tqp.commonobjectdefinition" /></a></li>
									</qp:authorization>
                                    <qp:authorization permission="sessionmanagementSearch">
									    <li><a href="${pageContext.request.contextPath}/sessionmanagement/search?init"><qp:message code="tqp.sessionmanagement" /></a></li>
                                    </qp:authorization>
									<qp:authorization permission="decisiontableSearch">
										<li class="divider"></li>
										<li><a href="${pageContext.request.contextPath}/decisiontable/search?init"><qp:message code="tqp.decisiontable" /></a></li>
									</qp:authorization>
									<qp:authorization permission="businesslogicSearch">
										<li class="divider"></li>
										<li><a href="${pageContext.request.contextPath}/businessdesign/search?init"><qp:message code="tqp.businesslogicdesign" /></a></li>
									</qp:authorization>
									
								</qp:authorizations>
							</ul>
						</li>
					</qp:authorizations>
					<!-- End upper design -->
					<qp:authorizations permissions="languagedesignSearch,messagedesignSearch,codelistSearch,functionmasterSearch, designinformationSearch, librarymanagementSearch, loggingmanagementModify,licensedesignsearch">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><qp:message code="sc.tqp.0013" /></a>
						<ul class="dropdown-menu" role="menu">
							<qp:authorizations permissions="languagedesignSearch,messagedesignSearch">
								<qp:authorization permission="languagedesignSearch">
									<li><a href="${pageContext.request.contextPath}/languagedesign/search?init"><qp:message code="sc.languagedesign.0009"/></a></li>
								</qp:authorization>
								<qp:authorization permission="messagedesignSearch">
									<li><a href="${pageContext.request.contextPath}/messagedesign/search?init"><qp:message code="tqp.messagedesign" /></a></li>
								</qp:authorization>
							</qp:authorizations>

							<qp:authorization permission="codelistSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/codelist/search?init"><qp:message code="tqp.codelist" /></a></li>
							</qp:authorization>
							<qp:authorization permission="functionmasterSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/functionmaster/search?init"><qp:message code="tqp.functionmaster" /></a></li>
							</qp:authorization>
							<qp:authorization permission="menudesignModify">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/menudesign/modify?init"><qp:message code="tqp.menudesign" /></a></li>
							</qp:authorization>
							
							<%--<li class="divider"></li>
							<li><a href="${pageContext.request.contextPath}/schedule/search?init">Schedule</a></li> --%>
							<qp:authorization permission="librarymanagementSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/librarymanagement/search?init"><qp:message code="sc.librarymanagement.0001" /></a></li>
							</qp:authorization>
							<qp:authorization permission="loggingmanagementModify">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/loggingmanagement/modify?init"><qp:message code="sc.loggingmanagement.0001" /></a></li>
							</qp:authorization>

							<qp:authorization permission="designinformationSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/designinformation/search?init"><qp:message code="tqp.designinformation" /></a></li>
							</qp:authorization>
							
							<qp:authorization permission="licensedesignSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/licensedesign/search?init"><qp:message code="tqp.licensedesign" /></a></li>
							</qp:authorization>
						</ul>
					</li>
					</qp:authorizations>
					<qp:authorizations permissions="generationGenerateddl,generationGeneratehtml,generationGeneratedocument,generationGeneratesourcecode,generationGeneratemanagement,importmanagement">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><qp:message code="tqp.generation" />
								
							</a>
							<ul class="dropdown-menu" role="menu">
								<qp:authorization permission="generationGenerateddl">
									<li><a href="${pageContext.request.contextPath}/generation/generateddl"><qp:message code="tqp.generateddl" /></a></li>
								</qp:authorization>
								<qp:authorization permission="generationGeneratehtml">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/screendesign/generateHTML?init"><qp:message code="tqp.generatehtml" /></a></li>
								</qp:authorization>
	<%-- 							<qp:authorization permission="generationGeneratescreen">
									<li><a href="${pageContext.request.contextPath}/generation/generatescreen"><qp:message code="tqp.generatescreen" /></a></li>
								</qp:authorization> --%>
								<!-- <li class="divider"></li> -->
								<%-- <qp:authorization permission="generationGeneratedb"> --%>
									<%-- <li><a href="${pageContext.request.contextPath}/generatedb/search?init">Generate DB from screen</a></li> --%>
								<%-- </qp:authorization> --%>
								<%-- <li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/generateblogic/search?init">Generate Blogic</a></li> --%>
								<qp:authorization permission="generationGeneratedocument">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/generatedocument/generatedocument?init"><qp:message code="sc.generatedocument.0001" /></a></li>
								</qp:authorization>
								<qp:authorization permission="generationGeneratesourcecode">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/generatesourcecode/generatesourcecode?init"><qp:message code="sc.generatesourcecode.0000" /></a></li>
								</qp:authorization>
								<qp:authorization permission="generationGeneratemanagement">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/generatemanagement/generatemanagement?init"><qp:message code="sc.generation.0021" /></a></li>
								</qp:authorization>
								<qp:authorization permission="importmanagement">
									<li class="divider"></li>
									<li><a href="${pageContext.request.contextPath}/importmanagement/importmanagement?init"><qp:message code="tqp.importmanagement" /></a></li>
								</qp:authorization>
								<!-- <li class="divider"></li> -->
							</ul>
						</li>
					</qp:authorizations>
				</c:if>
				<qp:authorizations permissions="accountSearch,roleSearch,languageSearch,messageSearch,accountruledefinitionSearch,webservicetokenmanagementSearch,licensemanagementSearch">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><qp:message code="sc.tqp.0014" /></a>
						<ul class="dropdown-menu" role="menu">
							<qp:authorization permission="accountruledefinitionSearch">
								<li><a href="${pageContext.request.contextPath}/accountruledefinition/search?init"><qp:message code="tqp.accountruledefinition" /></a></li>
								<li class="divider"></li>
							</qp:authorization>
							<qp:authorization permission="accountSearch">
								<li><a href="${pageContext.request.contextPath}/account/search?init"><qp:message code="tqp.account" /></a></li>
								<li class="divider"></li>
							</qp:authorization>
							<qp:authorization permission="roleSearch">
								<li><a href="${pageContext.request.contextPath}/role/search?init"><qp:message code="tqp.rolemanagement" /></a></li>
								<li class="divider"></li>
							</qp:authorization>
							<qp:authorization permission="languageSearch">
								<li><a href="${pageContext.request.contextPath}/language/search?init"><qp:message code="tqp.language" /></a></li>
							</qp:authorization>
							<qp:authorization permission="messageSearch">
								<li><a href="${pageContext.request.contextPath}/message/search?init"><qp:message code="tqp.message" /></a></li>
							</qp:authorization>
							<qp:authorization permission="webservicetokenmanagementSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/webservicetokenmanagement/search?init"><qp:message code="tqp.webservicetokenmanagement" /></a></li>
							</qp:authorization>
							<qp:authorization permission="licensemanagementSearch">
								<li class="divider"></li>
								<li><a href="${pageContext.request.contextPath}/licensemanagement/search?init"><qp:message code="tqp.licensemanagement" /></a></li>
							</qp:authorization>
						</ul>
					</li>
				</qp:authorizations>
			<%-- <li class="dropdown"><a href="${pageContext.request.contextPath}/sample/search?init"><qp:message code="sc.tqp.0016" /></a></li> --%>
			</ul>	  
			<ul class="nav navbar-nav navbar-side">
				 <li class="dropdown" id="navTopProblem">
					<a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="min-width:17px; font-size: 16px; padding-left: 16px; padding-right: 16px;">
						<i class="glyphicon glyphicon-bell" ></i>
						<span class="label label-danger label-menu-corner" style="display: none;"></span>
					</a>
				</li>
				<li class="dropdown">
					<a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="font-size: 16px;">
						<span class="glyphicon glyphicon-globe"></span> 
					</a>
					<ul class="dropdown-menu dropdown-nav-left" role="menu">
					<c:forEach items="${CL_LANGUAGE_LIST}" var="item">
						<li>
							<a onclick="$.qp.changeSystemLanguage('${item.key}')" href="javascript:"><img src="${pageContext.request.contextPath}/resources/media/images/${item.key}.png" style="border: 0px;" />&nbsp;&nbsp;<qp:message code="${CL_LANGUAGE_CODE.get(item.key) }"/></a>
						</li>
					</c:forEach>
					</ul>
				</li>
				<li class="dropdown">
					<a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="font-size: 16px;">
						<span class="glyphicon glyphicon-user"></span>
					</a>
					<ul class="dropdown-menu dropdown-user dropdown-nav-left">
						<%-- <qp:authorization permission="accountprofileView"> --%>
							<li>
								<a class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifyUserSetting">
									<span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;<qp:message code="sc.tqp.0009" />
								</a>
							</li>
						<%-- </qp:authorization> --%>
						<qp:authorization permission="accountprofileModifysystemsetting">
							<li>
								<a class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifySystemSetting">
									<span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;<qp:message code="sc.tqp.0010" />
								</a>
							</li>
						</qp:authorization>
						
						<qp:authorization permission="accountprofileModifytheme">
							<li>
								<a class="qp-link-popup" href="${pageContext.request.contextPath}/accountprofile/modifyTheme">
									<span class="glyphicon glyphicon-text-color"></span>&nbsp;&nbsp;<qp:message code="sc.tqp.0015" />
								</a>
							</li>
						</qp:authorization>
						<li class="divider"></li>
						<li>
							<a href="javascript:document.getElementById('logOutForm').submit();">
								<span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;<qp:message code="sc.tqp.0002" />
							</a> 
							<form:form method="POST" action="${pageContext.request.contextPath}/logout" id="logOutForm"></form:form>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	<!-- /.navbar-collapse -->
</div>