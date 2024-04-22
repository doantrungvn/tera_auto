<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="header-name">
		<qp:message code="Database initialization guideline" />
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		<div class="panel panel-default qp-div-information">
			<div class="panel-heading">
				<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
				<span class="pq-heading-text">
					<c:if test="${sessionScope.CURRENT_PROJECT.dbType eq 1 }">
						Postgres
					</c:if>
					<c:if test="${sessionScope.CURRENT_PROJECT.dbType eq 2 }">
						Oracle
					</c:if>
				</span>
			</div>
			<div class="panel-body">
				<c:if test="${sessionScope.CURRENT_PROJECT.dbType eq 1 }">
					<jsp:include page="guideline_postgres.jsp"></jsp:include>
				</c:if>
				<c:if test="${sessionScope.CURRENT_PROJECT.dbType eq 2 }">
					<jsp:include page="guideline_oracle.jsp"></jsp:include>
				</c:if>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>