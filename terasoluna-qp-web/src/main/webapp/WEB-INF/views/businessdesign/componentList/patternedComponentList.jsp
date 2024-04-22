<div style="padding: 2px 4px;">
	<c:if test="${not empty lstPatternedComponents}">
		<c:forEach var="component" items="${lstPatternedComponents}" varStatus="rowStatus">
			<div class="tool-class terminal bdesign-node bdesign-node-one" type="pattern" elementtype="" title="${component.name}">
				<img src="${pageContext.request.contextPath}/resources/media/images/businessdesign/${component.image}" class="tool-class-img" /> <span>${component.name}</span> 
				<input type="hidden" name="lstComponents" value='${component.jsonLstPatternedDetails}' /> 
				<input type="hidden" name="lstConnectors" value='${component.jsonLstPatternedDetailConnectors}' />
			</div>
		</c:forEach>
	</c:if>
</div>