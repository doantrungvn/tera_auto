<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
	<!-- Close this popup -->
		<script type="text/javascript">
			var parentWindow = window.parent.document; 
			function fcomCloseModifyComplete(){
				$(parentWindow).find('.fancybox-overlay').hide();
				parentWindow.location.reload();
			}
			
			$(document).ready(function() {
				$('<input>').attr({
					type : 'hidden',
					id : 'fcomReloadFancyBox',
					name : 'fcomReloadFancyBox',
					value : true
				}).appendTo($(parentWindow).find('body'));
			});
		</script>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-name">
		<qp:formatRemark value="${msgHeader}" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<div class="qp-div-action">
			<button type="button" class="btn qp-button" onclick="fcomCloseModifyComplete()"><qp:message code="sc.sys.0035" /></button>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>