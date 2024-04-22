<style>
/*************************************************************
    change Theme
*************************************************************/
.qp-root {
	<c:if test="${CL_THEME_COLOR.get('common-screen-size')} !=null and sessionScope.THEME_INFOR.get('common-screen-size') == null}">
        width: ${CL_THEME_COLOR.get('common-screen-size')-20}px ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-screen-size')}">
        width: ${sessionScope.THEME_INFOR.get("common-screen-size")-20}px ;
    </c:if>
}
</style>

<script type="text/javascript">
		$.qp.initalFancybox = function(className, href) {
			if (href == null) {
				$("a." + className).fancybox({
					'centerOnScroll' : true,
					'enableEscapeButton' : false,
					'width' : '100%',
					'height' : '100%',
					'autoSize' : false,
					'autoScale' : true,
					'autoDimensions' : true,
					'hideOnOverlayClick' : false,
					'transitionIn' : 'none',
					'transitionOut' : 'none',
					'type' : 'iframe'
				});
			}
		};
	$(document).ready(function(){
		$.qp.initalFancybox("qp-link-popup", null);		
		$.qp.initalFancybox("qp-button-popup", null);
	});
</script>