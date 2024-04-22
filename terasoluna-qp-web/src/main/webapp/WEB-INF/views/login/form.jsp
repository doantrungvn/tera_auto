<tiles:insertDefinition name="layouts-login">
	<tiles:putAttribute name="body">
		<div class="qp-panel-form-login">
			<div class="panel panel-default login-panel ">
				<div class="panel-heading">
					<b>Login</b>
				</div>
				<div class="panel-body qp-panel-body-login">
					<form:form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
						<c:if test="${param.error == true}">
<%-- 							<t:messagesPanel messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" /> --%>
							<div class="alert">
								<ul>
									<li>${param.message }</li>
								</ul>
							</div>
						</c:if>
						<fieldset>
							<div class="form-group input-group">
								<input class="form-control qp-form-control" id="username" name="j_username" placeholder="Username" autofocus>
								<span class="input-group-addon"><i class="glyphicon glyphicon-user" style=""></i></span>
							</div>
							<div class="form-group input-group">
								<input class="form-control qp-form-control" placeholder="Password" type="password" id="password" name="j_password" value="">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
							</div>
							<div>
								<p class="help-block">User ID and Password are case sensitive</p>
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<div class="qp-div-action">
								<input id="loginBtn" type="submit" value="Login" class="btn btn-success " onclick="preventDoubleSubmission(this)">
								<input type="hidden" name="redirectTo" value="${f:h(param.redirectTo)}" />
							</div>
						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			var parentWindow = window.parent.document;
			$(document).ready(function() {
				if ($(parentWindow).find('iframe').length == 1) {
					$(parentWindow).find('.fancybox-overlay').hide();
					parentWindow.location.reload();
				}
			});
			function preventDoubleSubmission(thiz){
				$(thiz).val($.qp.unEscapseHTML(fcomMsgSource["sc.sys.0056"]));
				$(thiz).attr('disabled','disabled');
				$(thiz).closest("form").submit();
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>