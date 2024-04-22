function p( name )
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}

(function( $ ) {
		$.apply = function(place, data){
			$.each(data, function(key, value){
				$(place).find("#" + key).val(value);
			});
		}
		$.calc = {
			sumOf: function(result, unit){
				if(!unit) unit = result;
				
				var res = 0;
				for(var i = 0; i < $(unit).length; i++){
					var value = 
						($.trim($(unit).eq(i).val()) == "") ? 0 : parseFloat($(unit).eq(i).val(), 10);

					res += value;
				}

				if(unit == result)
					return res;
					
				$(result).html(res);
			},
			round05: function(figure){
				var res = figure - Math.floor(figure);

				var resu = 0;
				if(res > 0.5) {
					resu = 1;
				} else {
					resu = .5;
				}
				return Math.floor(figure) + res;
			},
			multiplyOf: function(result, unit){
				if(!unit) unit = result;
				var res = 0;
				var bo = false;
				for(var i = 0; i < $(unit).length; i++){
					if($.trim($(unit).eq(i).val()) != ""){
						if(!bo)						
							res = parseFloat($(unit).eq(i).val(), 10);
						else
							res *= parseFloat($(unit).eq(i).val(), 10);
						bo = true;
					}
				}
				if(unit == result)
					return res;
				
				$(result).html(res);
			}
		};
		
		$.widget( "ui.combobox", {
			options: {
				minLength: 3,
				removeOnFalse: false,
				showArrow: false
			},
			_create: function() {
				var randomnumber = "id" + (Math.random() + "").substring(2);
				var self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "";
				var input = this.input = $( "<input id='" + randomnumber + "'>" )
					.insertAfter( select )
					.val( value )
					.change({id: "#" + randomnumber, options: this.options}, function(event){
						var object = $(event.data.id);
						var str = object.val();
						if(str.length >= event.data.options.minLength){
							$.ajax({
								url: "http://ws.geonames.org/searchJSON",
								dataType: "jsonp",
								randomid: event.data.id,
								data: {
									featureClass: "P",
									style: "full",
									maxRows: 12,
									name_startsWith: object.val()
								},
								success: function( data ) {
									var ress = $.map( data.geonames, function( item ) {
										return {
											label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
											value: item.name
										}
									});
									$(this.randomid)
										.autocomplete("option", "source", ress)
										.autocomplete("search", $(this.randomid).val());
									
								}
							});
						}
					})
					.autocomplete({
						delay: 0,
						source: [],
						select: function( event, ui ) {
							
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});								
							}
						}
					})
					.addClass( "ui-widget autocomplete fl" )
					.width( select.width() )
					.removeClass( "ui-autocomplete-input" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};
			if(this.options.showArrow){
				this.button = $( "<span class='ui-state-default fl' style='display: inline-block; margin-right: 5px'><span class='ui-icon ui-icon-triangle-1-s'></span></span>" )	
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.css( "position" , "relative" )
					.insertAfter( input )
					.position({
						my: "right center",
						at: "right center",
						of: input
					})
					.removeClass( "ui-corner-all" )
					.hover(function(){
						$(this).toggleClass('ui-state-active');
					})
					.click(function(randomnumber, event) {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", event.data );
						input.focus();
				});
			}
				$('<div class="cb"></div>').appendTo(this.button.parent());
			},

			destroy: function() {
				this.input.remove();
				this.button.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});
	})( jQuery );