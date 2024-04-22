$( document ).ready(function() {
	sqlPatternChange($("select[name=sqlPatternSelect]"));
});
function sqlPatternChange(select){
	var value = $(select).val();
	var $SelectPanel = $("#selectForm").parents("div.panel:first");
	var $FromPanel = $("#fromForm").parents("div.panel:first");
	var $WherePanel = $("#whereForm").parents("div.panel:first");
	var $GroupPanel = $("#groupByForm").parents("div.panel:first");
	var $HavingPanel = $("#havingForm").parents("div.panel:first");
	var $OrderByPanel = $("#orderByForm").parents("div.panel:first");
	var $IntoPanel = $("#intoForm").parents("div.panel:first");
	var $ValuesPanel = $("#valueForm").parents("div.panel:first");
	
	switch(value) {
	case "0":
		$SelectPanel.show();
		$FromPanel.show();
		$WherePanel.show();
		$GroupPanel.show();
		$HavingPanel.show();
		$OrderByPanel.show();
		$IntoPanel.hide();
		$ValuesPanel.hide();
		break;
	case "1":
		$SelectPanel.hide();
		$ValuesPanel.show();
		$FromPanel .hide();
		$IntoPanel.show();
		$WherePanel.hide();
		$GroupPanel.hide();
		$HavingPanel.hide();
		$OrderByPanel.hide();
		break;
	case "2":
		$SelectPanel.hide();
		$ValuesPanel.show();
		$FromPanel.hide();
		$IntoPanel.show();
		$IntoPanel.find(".qp-heading-text").text("Table");
		$WherePanel.show();
		$GroupPanel.hide();
		$HavingPanel.hide();
		$OrderByPanel.hide();
		break;
	case "3":
		$SelectPanel.hide();
		$ValuesPanel.hide();
		$FromPanel.hide();
		$IntoPanel.show();
		$IntoPanel.find(".qp-heading-text").text("From");
		$WherePanel.show();
		$GroupByPanel.hide();
		$HavingPanel.hide();
		$OrderByPanel.hide();
		break;
	}
};