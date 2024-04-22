		<style>
/*************************************************************
    change Theme
*************************************************************/

/* body background color */
.qp-header-img {
	background-color: ${projectStyle ['common-background-color']};
	font-size: ${projectStyle ['common-font-size']};
	font-family: ${projectStyle ['common-font-family']};
}
/* menu bg*/
.navbar-default {
	background-image: linear-gradient(to bottom, #7895CF 0%, #7895CF 100%);
}
/* menu brand */
.navbar-default .navbar-brand {
	color: #ffffff;
	font-size: 18px;
}

.btn-disable {
    display:inline-block;
    pointer-events: none;       
}
.btn-disable_menu_item {
    display:inline-block;
    cursor: default;
}

/* menu text color*/
.navbar-default .navbar-nav>li>a {
	color: #ffffff;
}

/* header */
.qp-header-main-text {
	font-size: 18px;
	color: #333;
}

.qp-link-header {
	text-align: right;
	color: #0033CC;
}
/* panel */
.panel-default>.panel-heading {
	color: #ffffff;
	background-color: #7895CF;
	border-color: 1px #CBCFED;
	font-size: 12px;
	height: 28px;
}

.panel-body {
	padding: 10px;
	background: #ffffff;
}
/* panel table form */
.qp-table-form tr th {
	border: 1px solid #CBCFED;
	background-color: #eff5f9;
}

.qp-table-form tr th {
	text-align: right;
}
/* panel table list */
.qp-table-list thead tr th {
	border: 1px solid #CBCFED;
	background-color: #eff5f9;
}

.qp-table-list tbody tr td:first-child {
	text-align: center;
	width: 40px;
}

.qp-table-list tbody tr td:last-child {
	text-align: center;
	width: 40px;
}

.qp-table-list thead tr th {
	text-align: left;
}

/* badge */
.panel-default>.panel-heading .badge {
	color: #333;
	background-color: #ffffff;
}
/* footer */
.qp-footer {
	border-top: solid 1.5px #7895CF;
}
/* common */
.qp-button-warning {
	background-color: #eb9316;
	color: #ffffff;
}

.qp-button-warning:active, .qp-button-warning:hover, .qp-button-warning:focus
	{
	background-color: #eb9316;
	border-color: #eb9316;
}

.qp-button {
	color: #ffffff;
	background-color: #5cb85c;
	
}

.qp-button-save {
	${projectStyle ['commonButtonBgColor']}${projectStyle ['commonButtonTextColor']}
}
.qp-button-save:ACTIVE {
	${projectStyle ['commonButtonBgActiveColor']}
}

.qp-button-delete {
	${projectStyle ['commonButtonDeleteBgColor']}${projectStyle ['commonButtonDeleteTextColor']}
}
.qp-button-delete:ACTIVE {
	${projectStyle ['commonButtonDeleteBgActiveColor']}
}

.qp-button-client {
	${projectStyle ['clientButtonDeleteBgColor']}${projectStyle ['clientButtonDeleteTextColor']}
}

.qp-button-client:ACTIVE{
	${projectStyle ['clientButtonDeleteBgActiveColor']}
}

.qp-link-popup-text-color {
	${projectStyle ['commonLinkPopupTextColor']}
}

.qp-header-title {
	${projectStyle ['headerTitleColor']}${projectStyle ['headerTitleSize']}${projectStyle ['headerTitlePosition']}
}

.qp-header-link {
	${projectStyle ['headerLinkPosition']}${projectStyle ['headerLinkColor']}${projectStyle ['headerLinkFontSize']}
}

.qp-panel-list-th {
	${projectStyle ['panelListThBackgroundColor']}${projectStyle ['panelListThFontSize']}
}

.qp-button:active, .qp-button:hover, .qp-button:focus {
	color: #ffffff;
	background-color: #419641;
	border-color: #419641;
}

.qp-link-button:hover, .qp-link-button:visited, .qp-link-button:active,
	.qp-link-button:focus {
	color: #ffffff;
}

.pagination>.active>a {
	background-color: #5cb85c;
	border-color: #5cb85c;
	color: #ffffff;
}

.qp-link-popup {
	color: #0033CC;
}

.qp-root {
	width: 1004px;
}

@media ( max-width : 767px) {
	.navbar-default .navbar-nav>li.open {
		background-color: white;
	}
}
@media (min-width: 768px) {
  .navbar-nav {
    float: left;
    margin: 0;
  }
  .navbar-nav > li {
    float: left;
  }
  .navbar-nav > li > a {
    padding-top: 11px;
  }
}

.navbar-default {
    <c:if test="${projectStyle ['menuBgColor'] != null}">
        background-image: linear-gradient(to bottom, ${projectStyle ['menuBgColor']} 0%, ${projectStyle ['menuBgColor']} 100%);   
    </c:if>
}
/* menu brand */
.navbar-default .navbar-brand  {
    ${projectStyle ['menuBrandColor']}${projectStyle ['menuBrandSize']}
}

/*selected menu*/
.navbar-default .navbar-nav > .open > a, .navbar-default .navbar-nav > .open > a:hover, .navbar-default .navbar-nav > .open > a:focus {
    ${projectStyle ['menuSelectedStyle']}
    <c:if test="${projectStyle ['menuSelectedBgColor'] != null}">
    	background-image: linear-gradient(to bottom, ${projectStyle ['menuSelectedBgColor']} 0%, ${projectStyle ['menuSelectedBgColor']} 100%);
    </c:if>
}

/* menu font color*/
.navbar-default .navbar-nav > li > a {
    ${projectStyle ['menuStyle']}
}
.navbar-default .navbar-nav > li > a {
    ${projectStyle ['menuItemStyle']}
}
<c:set var="menuItemBgHoverStyle" value="${(projectStyle ['menuItemBgHoverStyle'] == null or projectStyle ['menuItemBgHoverStyle'] == '') ? '#ffffff' : projectStyle ['menuItemBgHoverStyle']}" />
.dropdown-menu > li > a:hover, .dropdown-menu > li > a:focus {
    ${projectStyle ['menuItemHoverStyle']}
    background-image: linear-gradient(to bottom, ${menuItemBgHoverStyle} 0%, ${menuItemBgHoverStyle} 100%);
    background-repeat: repeat-x;
}
.qp-header-img { 
	${sessionScope.THEME_INFOR.get("backgroundColor")}
}
.qp-root { 
	${sessionScope.THEME_INFOR.get("screenStyle")}
}
.qp-root .panel-default>.panel-heading { 
	${projectStyle ['panelHeader']}
}
.qp-root .panel-body { 
	${projectStyle ['panelBody']}
}

.qp-root .qp-table-list {
	<c:if test="${not empty projectStyle ['panelListTableBorderSpacing']}">
    	border-collapse: separate;
    </c:if>
	${projectStyle ['panelListTable']}
}
<c:if test="${not empty projectStyle ['panelListTableBorderSpacing']}">
   	.qp-root .qp-table-list tr td div { 
	border-collapse: separate;
    border-spacing: 0px;
}
</c:if>




.qp-root .qp-table-list thead tr th { ${projectStyle ['panelListTh']
	
}


}

.table > tbody > tr > th label {
	${projectStyle ['panelTableFormTh']}
}
.qp-root .result-text { ${projectStyle ['panelListTdText']
	
}

}
.qp-root .result-numeric { ${projectStyle ['panelListTdNumeric']
	
}

}
.qp-root .result-date { ${projectStyle ['panelListTdDate']
	
}

}
.qp-root .result-date-time { ${projectStyle ['panelListTdDateTime']
	
}

}
.qp-root .result-no-number { ${projectStyle ['panelListTdNoNumber']
	
}

}
.qp-root .result-action-column { ${projectStyle
	['panelListTdActionColumn']
	
}

}

.qp-root .qp-table-form {
	<c:if test="${not empty projectStyle ['panelTableFormTableBorderSpacing']}">
    	border-collapse: separate;
    </c:if>
	${projectStyle ['panelTableForm']}
}
<c:if test="${not empty projectStyle ['panelTableFormTableBorderSpacing']}">
   	.qp-root .qp-table-form tr td div { 
	border-collapse: separate;
    border-spacing: 0px;
}
</c:if>



.qp-root .qp-table-form tr th { ${projectStyle ['panelTableFormTh']
	
}

}
.qp-root .qp-table-form tr td { ${projectStyle ['panelTableFormTd']
	
}
}
.qp-root .qp-footer {
	${projectStyle ['footerStyle']}
}

</style>