$(document).ready(function() {
	$('#header').html(messageString);
	$('#detail').html(messageString);
});


function takeMeHome() {
	var parentWindow = window.parent.document; 
	if($(parentWindow).find('iframe').length == 1) {
		$(parentWindow).find('.fancybox-overlay').hide();
	}
	window.location.href = CONTEXT_PATH;
}