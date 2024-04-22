<div class="panel-group" id="accordion" style="height: 100%">
	<div class="panel panel-default">
		<div class="panel-heading qp-bdesign-collapse-component" style="background-color: #3191D2;">
			<a data-toggle="collapse" data-parent="#accordion" href="#collapse-single-com"><qp:message code="sc.businesslogicdesign.0175" /></a>
		</div>
		<div id="collapse-single-com" class="panel-collapse collapse in">
			<jsp:include page="componentList/singleComponentList.jsp" />
		</div>
	</div>
	<c:if test="${businessDesignForm.blogicType == 0 or businessDesignForm.blogicType == 2}">
		<div class="panel panel-default">
			<div class="panel-heading qp-bdesign-collapse-component" style="background-color: #3191D2;">
				<a data-toggle="collapse" data-parent="#accordion" href="#collapse-patterned-comp"><qp:message code="sc.businesslogicdesign.0177" /></a>
			</div>
			<div id="collapse-patterned-comp" class="panel-collapse collapse">
				<jsp:include page="componentList/patternedComponentList.jsp" />
			</div>
		</div>
	</c:if>
	<c:if test="${businessDesignForm.blogicType == 3}">
		<div class="panel panel-default">
			<div class="panel-heading qp-bdesign-collapse-component" style="background-color: #3191D2;">
				<a data-toggle="collapse" data-parent="#accordion" href="#collapse-batch-comp"><qp:message code="sc.businesslogicdesign.0176" /></a>
			</div>
			<div id="collapse-batch-comp" class="panel-collapse collapse">
				<jsp:include page="componentList/batchComponentList.jsp" />
			</div>
		</div>
	</c:if>
    <div class="panel panel-default">
        <div class="panel-heading qp-bdesign-collapse-component" style="background-color: #3191D2;">
            <a data-toggle="collapse" data-parent="#accordion" href="#collapse-other-com"><qp:message code="sc.businesslogicdesign.0232" /></a>
        </div>
        <div id="collapse-other-com" class="panel-collapse collapse">
            <jsp:include page="componentList/otherComponentList.jsp" />
        </div>
    </div>
</div>
