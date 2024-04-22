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
    
	$("#srcgenElements").find("div:not(.strike)").draggable( {
	      containment: '#allDragDropContent',
	      stack: '#allDragDropContent table',
	      helper: 'clone',
	      revert: "invalid",
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  }, 
	      zIndex: 99999999,
	} ).disableSelection();
	
	
	$(".srcgenElementsTable").find("div").draggable( {
	      containment: '#allDragDropContent',
	      stack: '.drap-drop-area',
	      helper: 'clone',
	      revert: "invalid",
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  }, 
	      zIndex: 99999999, 
	} ).disableSelection();
	
	$("#sectionCustomArea").draggable( {
	      containment: '#allDragDropContent',
	      stack: '.drap-drop-area',
	      helper: 'clone',
	      revert: "invalid",
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  }, 
	      zIndex: 99999999, 
	} ).disableSelection();
	
	$("#srcgenHeaderLinkArea").droppable({
		accept: "#divHtmlLink",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLink(event, ui, $(this));
		}
	});
	
	$(".drap-drop-area").droppable({
		accept: ".srcgenElementsTable div[elementtype!=4],#sectionCustomArea",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertArea(event, ui, $(this));
		}
	});
	
	$("#srcgenAction").find("div").draggable( {
	      containment: '#allDragDropContent',
	      stack: '#allDragDropContent table',
	      helper: 'clone',
	      revert: "invalid",
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  }, 
	      zIndex: 99999999,
	} ).disableSelection();
	
	//
	$("#divCustomItem").draggable( {
	      containment: '#allDragDropContent',
	      stack: '#allDragDropContent table',
	      helper: 'clone',
	      revert: "invalid",
	      stop: function(event, ui) {
	    	  	$(this).css("z-index","auto");
	    	  }, 
	      zIndex: 99999999,
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
		handle: '.sortable',
	});
	
	$(".form-area-content").sortable({
        connectWith: '.form-area-content',
        handle: '.srcgenTableSort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth() - 4);
			return ui;
		},
		items: '.areaContent:not(.area-tab .areaContent), .area-tab',
		deactivate: function( event, ui ) {
			var temp = $(this).closest('.form-area-content').find("#srcgenAreaTemplate").clone();
			$(this).closest('.form-area-content').find("#srcgenAreaTemplate").remove();
			$(this).closest('.form-area-content').append(temp);
			
			$(".drap-drop-area").droppable({
				accept: ".srcgenElementsTable div[elementtype!=4]",
				activeClass: "state-droppable",
				drop: function(event, ui) {
					insertArea(event, ui, $(this));
				}
			});
		}
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
/*	$(".dropComponent").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});*/
	
	$(window).scroll(function(){
		$(".form-area-content").find(".table tbody td:not(.srcgenControl):onScreen").droppable({
			disabled: false,
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponent(event, ui, $(this));
			}
		});
		
		$(".form-area-content").find(".table td:not(.srcgenControl):not(:onScreen)").droppable({
			disabled: true,
			accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertComponent(event, ui, $(this));
			}
		});
		
		$(".dropLabel:onScreen").droppable({
			disabled: false,
			accept: "#divLabel",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertLable(event, ui, $(this));
			}
		});
		
		$(".dropLabel:not(:onScreen)").droppable({
			disabled: true,
			accept: "#divLabel",
			activeClass: "state-droppable",
			drop: function(event, ui) {
				insertLable(event, ui, $(this));
			}
		});
    });
	
	$(".enableGroupTd:onScreen").droppable({
		accept: "#srcgenElements div, #srcgenAction div, #newDragElementTd div, #divCustomItem",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertComponent(event, ui, $(this));
		}
	});
	
	$(".dropLabel:onScreen").droppable({
		accept: "#divLabel",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertLable(event, ui, $(this));
		}
	});
	
	$(".action-area").droppable({
		accept: "#divHtmlButton, #divLabel, #divDynamicLabel, #divHtmlLink, #divHtmlLinkDynamic, #newDragElementTd div[datatype=22], #newDragElementTd div[datatype=21]",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertActionElement(event, ui, $(this));				
		}
	});
	
	$(".section-area").droppable({
		accept: "#srcgenControlDiv tr[class!='srcgenElementsTable'] div[id!='sectionCustomArea'], #newDragElementTd div",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertAnyElement(event, ui, $(this));				
		}
	});	
	
	//check row, col span
	$(".form-area-content").find("table.qp-table-form").each(function(i){
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
	$(".form-area-content").find("table.qp-table-list-none-action").each(function(i){
		checkColRowSpan($(this));
		checkSwapListTable($(this));
	});

	$('#hideComponentTool').scrollToFixed();
	$('#showComponentTool').scrollToFixed();
	$('#srcgenControl').scrollToFixed({marginTop: 15});
	
	$("#srcgenFormTemplate").droppable({
		accept: "#form-element",
		activeClass: "state-droppable",
		drop: function(event, ui) {
			insertForm(event, ui, $(this));
		}
	});
	
	initBlurEventElement();
	//initTab();
	
	$("#loadingScreen").hide();
	$("#dragDropContent").css("visibility", "");
	$("#design-action").show();
	$('.qp-input-pickcolor').colorpicker();
	
	$('.modal').keydown(function(event){    
	    if(event.keyCode==13){
	       //$(this).find(".modal-footer .qp-button-client:last").trigger('click');
	    }
	});
	
	$("#dragDropContent").sortable({
        handle: '.form-sort',
        update: function(e, ui) {
        	refreshFormIndex();
		},
		helper: function(e, ui) {
			ui.width(ui.outerWidth());
			return ui;
		},
		items: '.form-layout',
	});
});
