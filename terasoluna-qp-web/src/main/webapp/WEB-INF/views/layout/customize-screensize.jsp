<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="fcomThemeScreenSize" value="1100"></c:set>
	<c:if test="${CL_THEME_COLOR.get('common-screen-size') !=null and sessionScope.THEME_INFOR.get('common-screen-size') == null}">
		<c:set var="fcomThemeScreenSize" value="${CL_THEME_COLOR.get('common-screen-size')}"></c:set>
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-screen-size')}">
    	<c:set var="fcomThemeScreenSize" value="${sessionScope.THEME_INFOR.get('common-screen-size')}"></c:set>
    </c:if>
	<script type="text/javascript">
		$.qp.initalFancybox = function(className, href) {
			if (href == null) {
				$("a." + className).fancybox({
					'centerOnScroll' : true,
					'enableEscapeButton' : false,
					onStart : function() {
						$("body").css("overflow", "hidden");
					},
					onClosed : function() {
						$("body").css("overflow", "auto");
					},
					afterClose : function(){
						fcomFancyBoxClose();
			        },
					'width' : ${fcomThemeScreenSize},
					'height' : '100%',
					'autoSize' : false,
					'autoScale' : true,
					'autoDimensions' : true,
					'hideOnOverlayClick' : false,
					'transitionIn' : 'none',
					'transitionOut' : 'none',
					'type' : 'iframe',
					'beforeLoad': function(){
						//error when running in IE11 browser.
// 						var state = {"html":window.top.document.documentElement.innerHTML};
						var state = {"html":""};
						window.history.replaceState(state,"",window.top.location.href);
					},
					'afterLoad':function(event,arg01,arg02){
						if($.qp.isHasPageErrors($(this.content).contents()[0].documentElement)) {
							var iframeHtml = $($("iframe").contents()[0].documentElement).html();
							$("iframe").parents(".fancybox-overlay:first").remove();
							var state = {"html":iframeHtml};
							window.history.pushState(state,"",window.location.href);
							$.qp.updateDocument(window.top.document,{"html":state.html})
							return false;
						}
					},
					helpers: { 
						overlay : { closeClick: false } 
					}
				});
			}
			if (href != null) {
				$("a." + className).fancybox({
					'href' : href,
					'centerOnScroll' : true,
					'enableEscapeButton' : false,
					onStart : function() {
						$("body").css("overflow", "hidden");
					},
					onClosed : function() {
						$("body").css("overflow", "auto");
					},
					afterClose : function(){
						fcomFancyBoxClose();
					},
					'width' : ${fcomThemeScreenSize},
					'height' : '100%',
					'autoSize' : false,
					'autoScale' : true,
					'autoDimensions' : true,
					'hideOnOverlayClick' : false,
					'transitionIn' : 'none',
					'transitionOut' : 'none',
					'type' : 'iframe',
					helpers: { 
						overlay : { closeClick: false } 
					}
				});
			}
		};
	$(document).ready(function(){
		$.qp.initalFancybox("qp-link-popup", null);
		$.qp.initalFancybox("qp-button-popup", null);
	});
	function fcomFancyBoxClose()
	{
		var isReload = $('#fcomReloadFancyBox').val();
		if(isReload == 'true')
		{
			var parentWindow = window.parent.document;
			parentWindow.location.reload();
		}
	}
</script>