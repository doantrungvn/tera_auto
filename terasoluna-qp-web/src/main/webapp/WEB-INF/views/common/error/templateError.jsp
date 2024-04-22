<script type="text/javascript">
CONTEXT_PATH = "<%=request.getContextPath()%>";	
</script>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1 id = "header"></h1>
                <div class="error-details">
			        <div class="error">
			            <c:if test="${!empty exceptionCode}">
			            	[${f:h(exceptionCode)}]
			            </c:if>
			            <span id="detail"></span>
			        </div>
                </div>
                <div class="error-actions">
                    <a href="javascript:void(0)" class="btn btn-primary btn-lg" onclick="takeMeHome();"><span class="glyphicon glyphicon-home"></span>
                         <spring:message code="sc.sys.js.0032" /></a>
                </div>
            </div>
        </div>
    </div>
</div>
