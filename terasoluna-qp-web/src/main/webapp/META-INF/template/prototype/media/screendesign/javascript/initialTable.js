$.qp.screenDesign = (function($$module){
	$$module.initData = function($table){
		
		$table.find("tbody tr").find("td:first").each(function(){
			var $parentDiv = $(this).find("div:first");
			var $childDiv = $parentDiv.find("div:first");
			var level = $childDiv.html();
			var count = (level.match(/\./g) || []).length;
			
			var content = '<div style="float:left; width: 56px;height: 100%; border-right-style: solid; border-right-width: 1px; border-right-color: rgb(221, 221, 221); text-align: center;">&nbsp;</div>';
			
			for (var i = 0; i < count; i++) {
				$childDiv.before(content);
			}
		});
	};
	return $$module;
})(jQuery.namespace('$.qp.screenDesign'));