$(function() {
	/* sort */
	$('#tbl_list_post').find("tbody").sortable({
		// placeholder: "ui-sortable-placeholder"
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(event, ui) {
			$("[name=arrIndex]").each(function(i) {
				if (i < $("[name=arrIndex]").length) {
					$('[name=arrIndex]:eq('+i+')').val(i + 1);
					$('span[id=stt]:eq('+i+')').html(i + 1);
				}
			});
		},
		items: 'tr',
		cursor: 'move',
		handle: '.sortable'
	});
	
	var defaultSubject = "";
	var defaultSubjectFlg = "";
	var span = $('<span class="glyphicon glyphicon-ok"></span>');
	
	for(i = 0 ; i<$('#tbl_list_post').find('tr').length;i++){
		defaultSubjectFlg = $('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)');
		if(defaultSubjectFlg.find('span').val() != null){
			defaultSubject = $('#tbl_list_post').find('tr:eq('+i+')').find("input[name$='.areaId']").val();
		}
	}

	/* Event process */
	$("button.qp-dialog-confirm").click(function( event ) {
		
		$("#tbl_list_post").find("td").each(function() {
			var itemPos = $(this).find("span").text();
			$(this).find(".itemPos").val(itemPos);
		});
		
		var scope = $("#tbl_list_post").find("tr.trChange");
		var itemSeqNo = $(scope).find("input:hidden[name ='arrIndex']").val();
		$('input:hidden[name=itemSeqNo]').val(itemSeqNo);

		// set area name and area code
		var areaName = $(scope).find("td:eq(1)").text();
		var areaCode = $(scope).find("td:eq(2)").text();
		var areaDefaultFlg = $(scope).find("td:eq(3)").text(); 
		$(scope).find("td:eq(0)").find("input:hidden[name$='.areaName']").val(areaName);
		$(scope).find("td:eq(0)").find("input:hidden[name$='.areaCode']").val(areaCode);
		$(scope).find("td:eq(0)").find("input:hidden[name$='.defaultFlg']").val(areaDefaultFlg);
		
	});
	
	var areaNmCl = $('#tbl_list_post').find('.trChange').find("td:eq(1)");
	var areaCdCl = $('#tbl_list_post').find('.trChange').find("td:eq(2)");
	var defaultFlgCl = $('#tbl_list_post').find('.trChange').find("td:eq(3)");
	
	$('input[name=areaName]').change(function(){
		$(areaNmCl).text($(this).val());
		$(areaCdCl).text($('input[name=areaCode]').val());
	});
	
	$('input[name=areaCode]').change(function(){
		$(areaCdCl).text($(this).val());
	});
	
	// Event when reload
	var areaNm = $('input[name=areaName]').val();
	var areaCd = $('input[name=areaCode]').val();
	
	if(areaNm != ''){
		$(areaNmCl).text(areaNm);
	}
	
	if(areaCd != ''){
		$(areaCdCl).text(areaCd);
	}
	
	if($('#defaultFlg1').is(':checked')){
		if(positionAreaId !=  defaultSubject){
			defaultFlgCl.append(span);
			for(i = 0 ; i<$('#tbl_list_post').find('tr').length;i++){
				defaultSubjectFlg = $('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)');
				if($('#tbl_list_post').find('tr:eq('+i+')').find("input[name$='.areaId']").val() == defaultSubject){
					$('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)').find('span').remove();
				}
			}
		}
	}
	
	$('#defaultFlg1').click(function () {
        if ($(this).is(':checked')) {
        	if(positionAreaId ==  defaultSubject){
        		defaultFlgCl.append(span);
        	}else{
        		defaultFlgCl.append(span);
        		for(i = 0 ; i<$('#tbl_list_post').find('tr').length;i++){
    				defaultSubjectFlg = $('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)');
    				if($('#tbl_list_post').find('tr:eq('+i+')').find("input[name$='.areaId']").val() == defaultSubject){
    					$('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)').find('span').remove();
    				}
    			}
        	}
        }
    });
	$('#defaultFlg2').click(function () {
        if ($(this).is(':checked')) {
        	if(positionAreaId ==  defaultSubject){
        		$('#tbl_list_post').find('tr').find('td:eq(3)').find('span').remove();
        	}else{
        		$(defaultFlgCl).find('span').remove();
        		for(i = 0 ; i<$('#tbl_list_post').find('tr').length;i++){
    				defaultSubjectFlg = $('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)');
    				if($('#tbl_list_post').find('tr:eq('+i+')').find("input[name$='.areaId']").val() == defaultSubject){
    					$('#tbl_list_post').find('tr:eq('+i+')').find('td:eq(3)').append(span);
    				}
    			}
        	}
        }
    });
});

function drawByOnChangeAutocomplete(obj) {
	$("#subjectAreaForm").attr("method","get");
	$("#subjectAreaForm").submit();
}

 // Set table code when autocomplete select
function changeTableCodeLabel(obj) {

	var scope = $(obj.target).closest('td');
	var tableDesignId = $(obj.target).val();
	
	if(tableDesignId == ''){
		$(scope).next().find('span').text('');
	} else {
		var tableCd = obj.item.output01;
		$(scope).next().find('span').text(tableCd);
	}
}
