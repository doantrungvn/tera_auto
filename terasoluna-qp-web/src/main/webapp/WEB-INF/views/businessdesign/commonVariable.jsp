<script type="text/javascript">
	// Array store all connections
	var allInstanceConnect = new Array();
	var arrInputBean = new Array();
	var arrOutputBean = new Array();
	var arrObjectDefinition = new Array();
	var arrComponent = new Array();
	var returnType = '${businessDesignForm.returnType}';
	var blogicType = '${businessDesignForm.blogicType}';
	var patternType = '${businessDesignForm.patternType}';
	var screenId = '${businessDesignForm.screenId}';
	var moduleIdOfBD = '${businessDesignForm.moduleId}';
	var moduleIdAutocompleteOfBD = '${businessDesignForm.moduleIdAutocomplete}';
	var moduleCode = '${businessDesignForm.moduleCode}';
	var moduleType = '${businessDesignForm.moduleType}';
	var businessLogicId = '${businessDesignForm.businessLogicId}';
	var requestMethod = '${businessDesignForm.requestMethod}';
	// Iterator
	<c:forEach items="${businessDesignForm.lstSequenceConnectors}" var="item">
		  var instanceConnect;
	      instanceConnect = {
	              source : '${item.connectorSource}',
	              target : '${item.connectorDest}',
	              label  : '${item.connectorType}'
	      };
	      // Adding array
	      allInstanceConnect.push(instanceConnect);
	</c:forEach>
	var CL_BD_DATATYPE = {}//CL_QP_Datatype
	<c:forEach items="${CL_BD_DATATYPE}" var="item">
	    CL_BD_DATATYPE['${item.key}'] = '${item.value}';
	</c:forEach>
	<c:forEach items="${businessDesignForm.lstSequenceLogics}" var="item">
		arrComponent.push({id:'${item.sequenceLogicId}',componentType:parseInt('${item.componentType}')})
	</c:forEach>
		
	var CL_QP_DATATYPE_NOT_ENTITY = {};
	<c:forEach items="${CL_BD_DATATYPE_NOT_ENTITY}" var="item">
	    CL_QP_DATATYPE_NOT_ENTITY['${item.key}'] = '${item.value}';
	</c:forEach>
	
	var CL_QP_OPERATORTYPE = {};
	<c:forEach items="${CL_QP_OPERATORTYPE}" var="item">
	    CL_QP_OPERATORTYPE['${item.key}'] = '${item.value}';
	</c:forEach>
	
	var CL_SUPPORT_OPTION_VALUE_FLAG = {};
	<c:forEach items="${CL_SUPPORT_OPTION_VALUE_FLAG}" var="item">
	    CL_SUPPORT_OPTION_VALUE_FLAG['${item.key}'] = '${item.value}';
	</c:forEach>
          
	var MAX_NESTED_OBJECT = '${CL_SYSTEM_SETTING["maxNestedObject"]}';
	var webservicePattern = "${f:h(sessionScope.CURRENT_PROJECT.webservicePattern)}";
	
	var CL_EX_DATATYPE_NOT_COMMON_EXTERNAL_ENTITY = {};
	<c:forEach items="${CL_EX_DATATYPE_NOT_COMMON_EXTERNAL_ENTITY}" var="item">
		CL_EX_DATATYPE_NOT_COMMON_EXTERNAL_ENTITY['${item.key}'] = '${item.value}';
	</c:forEach>
</script>