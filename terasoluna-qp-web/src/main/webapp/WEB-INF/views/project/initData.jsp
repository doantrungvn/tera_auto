<tiles:putAttribute name="additionalHeading">
	<script type="text/javascript">
		$(document).ready(function() {
			var CL_POSTGRESQL_CONFIG = {};
			<c:forEach items="${CL_POSTGRESQL_CONFIG}" var="item"> 
				CL_POSTGRESQL_CONFIG['${item.key}'] = '${item.value}';
			</c:forEach>
			var CL_ORACLE_CONFIG = {};
			<c:forEach items="${CL_ORACLE_CONFIG}" var="item"> 
				CL_ORACLE_CONFIG['${item.key}'] = '${item.value}';
			</c:forEach>

			$("#dbType").change(function(){
				if($(this).val() == 1){
					$("#dbHostName").val(CL_POSTGRESQL_CONFIG['dbHostName']);
					$("#dbPort").val(CL_POSTGRESQL_CONFIG['dbPort']);
					$("#dbUser").val(CL_POSTGRESQL_CONFIG['dbUser']);
					$("#dbPassword").val(CL_POSTGRESQL_CONFIG['dbPassword']);
					$("#dbDriver").val(CL_POSTGRESQL_CONFIG['dbDriver']);
				} else if($(this).val() == 2){
					$("#dbHostName").val(CL_ORACLE_CONFIG['dbHostName']);
					$("#dbPort").val(CL_ORACLE_CONFIG['dbPort']);
					$("#dbUser").val(CL_ORACLE_CONFIG['dbUser']);
					$("#dbPassword").val(CL_ORACLE_CONFIG['dbPassword']);
					$("#dbDriver").val(CL_ORACLE_CONFIG['dbDriver']);
				}
			});
		});
	</script>
</tiles:putAttribute>