$(document).ready(
	function() {
		
		$(".nav-tabs").find("a:not(.dropdown-toggle)").each(function() {
			$(this).bind("click", function(e) {
				// prevent the Default behavior
				e.preventDefault();

				//if click tab selected then nothing
				if ($("#subjectAreaId").val() == $(this).attr("id")) {
					return;
				}
				//if link to subject area
				if ($(this).attr("id") == 0){
					window.open($(this).attr('url'), '_blank');
					return;
				}
				
				var isConfirm = true;
				if ($("#isChangeFlg").val() == 1 ) {
					isConfirm = $.qp.confirm(fcomMsgSource['inf.sys.0032']);
				}

				if (isConfirm) {
					$("#subjectAreaId").val($(this).attr("id"));
					var url = CONTEXT_PATH + "/graphicdatabasedesign/search?mode=1&subjectAreaId=" + $(this).attr("id");
					/*$("#formSave").attr('action', url);
					$("#formSave").attr('method', "GET");
					$("#formSave").submit();*/
					window.location.href = url + "&r=" + Math.random();
				}
				
			})
		});

		$(".nav-tabs").find("a .display-tab").addClass("li-word-wrap");
		/*	var url = CONTEXT_PATH + "/graphicdatabasedesign/search"
		$("#formSave").attr('action', url);*/

		$('#table').on('keydown', 'input', function(event) { 
			if (event.which == 13) {
				event.preventDefault();
			}
		});
		
		$('#keys').on('keydown', 'input', function(event) { 
			if (event.which == 13) {
				event.preventDefault();
			}
		});

		$('.out-focus-lower').on('change', function() {
			var lowerVal = $(this).val().toLowerCase();
			$(this).val(lowerVal);
		});
		
		if ($("#mode").val() == 3 ) {
			$("#isChangeFlg").val("1")
		}
});

function showData() {
	$('.nav-pills').show();
	$('.nav-tabs').tabdrop({text: dbMsgSource["sc.databasedesign.0099"]});

	$('#databaseDesign').show();
	$('#controls').show();
	$('#io').show();
	$('#keys').show();
	$('#table').show();
	$('#areaKey').show();
}