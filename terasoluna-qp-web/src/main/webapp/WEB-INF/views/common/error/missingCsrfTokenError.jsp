<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>CSRF Error!</title>
<link type="text/css" href="${pageContext.request.contextPath}/resources/media/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
body {
	background-image:
		url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAxMC8yOS8xMiKqq3kAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzVxteM2AAABHklEQVRIib2Vyw6EIAxFW5idr///Qx9sfG3pLEyJ3tAwi5EmBqRo7vHawiEEERHS6x7MTMxMVv6+z3tPMUYSkfTM/R0fEaG2bbMv+Gc4nZzn+dN4HAcREa3r+hi3bcuu68jLskhVIlW073tWaYlQ9+F9IpqmSfq+fwskhdO/AwmUTJXrOuaRQNeRkOd5lq7rXmS5InmERKoER/QMvUAPlZDHcZRhGN4CSeGY+aHMqgcks5RrHv/eeh455x5KrMq2yHQdibDO6ncG/KZWL7M8xDyS1/MIO0NJqdULLS81X6/X6aR0nqBSJcPeZnlZrzN477NKURn2Nus8sjzmEII0TfMiyxUuxphVWjpJkbx0btUnshRihVv70Bv8ItXq6Asoi/ZiCbU6YgAAAABJRU5ErkJggg==);
}

.error-template {
	padding: 40px 15px;
	text-align: center;
}

.error-actions {
	margin-top: 15px;
	margin-bottom: 15px;
}

.error-actions .btn {
	margin-right: 10px;
}
</style>
<script type="text/javascript">
var messageString = '<spring:message code="err.sys.0057"/>';
CONTEXT_PATH = "<%=request.getContextPath()%>";	
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath}/resources/app/common/javascript/errorPage.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>CSRF Error! Missing CSRF Token!</h1>
					<div class="error-details">
						<div class="error">
							<c:if test="${!empty exceptionCode}">[${f:h(exceptionCode)}]</c:if>
							<spring:message code="err.sys.0056" />
						</div>
						<t:messagesPanel />
					</div>
					<div class="error-actions">
						<a href="${pageContext.request.contextPath}" class="btn btn-primary btn-lg" onclick="takeMeHome();"><span class="glyphicon glyphicon-home"></span><spring:message code="sc.sys.js.0032" /></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>