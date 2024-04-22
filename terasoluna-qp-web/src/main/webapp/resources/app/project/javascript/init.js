$(document).ready(function() {
	$('.element').draggable({
		revert: true,
		containment: '.design',
		stack: '.design',
		zIndex: 999999,
		scroll: false,
	});
	
	$('.text-design').droppable({
		accept: '.element',
		drop: function(event, ui) {
			acceptElement(event, ui, $(this));
		},
		activeClass: "state-droppable",
	});
	
	$("input[name$='projectCode']").change(function(){
		var dbName =  $("input[name$='dbName']");
		if(dbName.val() == ''){
			dbName.val($(this).val());
		}
	})
});