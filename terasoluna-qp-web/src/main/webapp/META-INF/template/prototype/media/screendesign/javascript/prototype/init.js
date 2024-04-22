$(document).ready(function () {
	
	$('ul.tabs-list li').click(function(){
		var tab_id = $(this).attr('data-tab');
		var div = $(this).closest("div");
		$(div).find('ul.tabs-list li').removeClass('tab-current');
		$(div).find('.tab-content').removeClass('tab-current');

		$(this).addClass('tab-current');
		$("#"+tab_id).addClass('tab-current');
	});
	
    var sdComponents = $("#srcgenControlElement");
    var posComponents = sdComponents.position(); 
    var sdLink = $("#showComponentTool");
    
	$("#srcgenElements").find("div").draggable( {
	      containment: '#allDragDropContent',
	      stack: '#allDragDropContent table',
	      revert: function(event, ui) {$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};return true;},
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	} ).disableSelection();
	
	
	$(".srcgenElementsTable").find("div").draggable( {
	      containment: '#allDragDropContent',
	      stack: '.drap-drop-area',
	      revert: function(event, ui) {$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};return true;},
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	} ).disableSelection();
	
	$("#srcgenHeaderLinkArea").droppable({
		accept: "#divHtmlLink",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLink(event, ui, $(this));
		}
	});
	
	$(".drap-drop-area").droppable({
		accept: ".srcgenElementsTable div[elementtype!=4]",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertArea(event, ui, $(this));
		}
	});
	
	$("#srcgenAction").find("div").draggable( {
	      containment: '#allDragDropContent',
	      stack: '#allDragDropContent table',
	      revert: function(event, ui) {$(this).data("uiDraggable").originalPosition = {top : 0,left : 0};return true;},
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  } 
	} ).disableSelection();
	
	$("#srcgenActionArea").find("div").droppable({
		accept: "#divHtmlButton",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertButton(event, ui, $(this));
		}
	});
	
	reloadDroppableSpan();
	
	$('.form-area-content').find("table[id^=srcgenTableId]").find("tbody").sortable({
		helper: function(e, ui) {
			ui.children().each(function() {
				$(this).width($(this).width());
			});
			return ui;
		},
		update: function(e, ui) {
			reIndexTable(e.target.closest("table"));
		},
		cursor: 'move',
		items: 'tr:not(:first,[class=disableSort])',
		handle: '.sortable'
	});
	
	$(".form-area-content").sortable({
        connectWith: '.form-area-content',
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.areaContent, .area-tab',
	});
	
	$(".area-tab").sortable({
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.areaContent',
	});
	
	//init drap drop when display data
	$(".dropComponent").droppable({
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(".enableGroupTd").droppable({
		accept: "#srcgenElements div[id!='divLabel'], #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(".dropLabel").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	
	$(".action-area").droppable({
		accept: "#divHtmlButton, #divLabel, #divHtmlLink",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertActionElement(event, ui, $(this));				
		}
	});
	
	$(".section-area").droppable({
		accept: "#srcgenControlDiv tr[class!='srcgenElementsTable'] div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertAnyElement(event, ui, $(this));				
		}
	});	
	
	//check row, col span
	$(".form-area-content").find("table[class*=qp-table-form]").each(function(i){
		checkColRowSpan($(this));
		checkSwap($(this));
		
		$(this).find("tr:first").find("td[class=srcgenControl]").find(".glyphicon-screenshot").droppable({
			accept: "#" + $(this).attr("id") + " tr td[class=srcgenControl] .glyphicon-screenshot",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				swapColumn(event, ui, $(this));
			}
		});
	});
	
	//list table
	$(".form-area-content").find("table[class*=qp-table-list-none-action]").each(function(i){
		checkColRowSpan($(this));
		checkSwapListTable($(this));
	});

	$('#srcgenControl').affix({
	    offset: {
	        top: $('#srcgenControl').offset().top
	    }
	});
	
	$("#srcgenFormTemplate").droppable({
		accept: "#form-element",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertForm(event, ui, $(this));
		}
	});
	initBlurEventElement();
	initTab();
	
	$("#loadingScreen").hide();
	$("#dragDropContent").css("visibility", "");
	$("#design-action").show();
});
