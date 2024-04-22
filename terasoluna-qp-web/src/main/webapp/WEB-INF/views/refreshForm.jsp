<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript">
			var parentWindow = window.parent.document; 
			$(parentWindow).find('.fancybox-overlay').hide();
			parentWindow.location.reload();
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>