<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">	
        Scheduler Manager
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/javascript/businesstype/businesstype.js"></script>
        <c:if test="${sessionScope.CURRENT_PROJECT.status eq 1 }">
	        <qp:authorization permission="businesstypeRegister">
	        	<span class="qp-link-header-icon glyphicon glyphicon-plus"></span>
	            <a href="${pageContext.request.contextPath}/schedule/register">Register task</a>
	        </qp:authorization>
        </c:if>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" role="form" >
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-search-condition">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
                    <span class="qp-heading-text"><qp:message code="sc.sys.0002" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="80%" />
						</colgroup>
						<tr>
							<th>Title</th>
							<td valign="middle"><input type="text" class="form-control" /></td>
						</tr>
					</table>
				</div>
			</div>
            <!-- Style for button submit -->
            <div class="qp-div-action">
                
                    <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
                
            </div>

		</form:form>

			<div class="panel panel-default qp-div-search-result">
				<div class="panel-heading">
                       <qp:itemPerPage form="screenDesignSearchForm" action="/screendesign/search"/>
                   </div>
				<div class="panel-body">
					<div class="table-responsive">
							<table class="table table-bordered qp-table-list">
								<colgroup>
									<col />
									<col width="20%" />
									<col width="20%" />
									<col width="20%" />
									<col width="30%" />
									<col width="7%"/>
								</colgroup>
								<thead>
									<tr>
										<th></th>
										<th>Title</th>
										<th>Date create</th>
										<th>Next run time</th>
										<th>Trigger</th>
										<th></th>
									</tr>
									<tr>
										<td style="text-align: center;"><a href="#"><span class="glyphicon glyphicon-play"></span></a></td>
										<td><a href="#">Recur service for customer</a></td>
										<td>01/01/2015 01:00:00</td>
										<td>01/02/2015 01:00:00</td>
										<td>At 01:00 Every webnesday of every week</td> 
										<td style="text-align: center;"><a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action"  href="${pageContext.request.contextPath}/schedule/register" style="margin: auto"></a>
											<a class="btn qp-button glyphicon glyphicon-remove qp-link-button qp-link-action" href="#" style="margin: auto"></a>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;"><a href="#"><span class="glyphicon glyphicon-stop" style="color: red;"></span></a></td>
										<td><a href="#">Backup database</a></td>
										<td>02/01/2015 01:00:00</td>
										<td>02/02/2015 01:00:00</td>
										<td>At 01:00 Every webnesday of every week</td> 
										<td style="text-align: center;"><a class="btn qp-button glyphicon glyphicon-pencil qp-link-button qp-link-action"  href="${pageContext.request.contextPath}/schedule/register" style="margin: auto"></a>
											<a class="btn qp-button glyphicon glyphicon-remove qp-link-button qp-link-action" href="#" style="margin: auto"></a>
										</td>
									</tr>
								</thead>
								
							</table>
						
					</div>
				</div>
			</div>
			
	</tiles:putAttribute>
</tiles:insertDefinition>
