<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
/*************************************************************
    change Theme
*************************************************************/

/* body background color */
.qp-header-img {
    <c:if test="${CL_THEME_COLOR.get('body-bg-color')!= null and sessionScope.THEME_INFOR.get('body-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("body-bg-color")};
    </c:if>
    <c:if test="${sessionScope.THEME_INFOR.get('body-bg-color') != null}">
        background-color: ${sessionScope.THEME_INFOR.get("body-bg-color")};
    </c:if>
}
/* menu bg*/
.navbar-default {
    <c:if test="${CL_THEME_COLOR.get('menu-bg-color') != null and sessionScope.THEME_INFOR.get('menu-bg-color') == null}">
        background-image: linear-gradient(to bottom, ${CL_THEME_COLOR.get("menu-bg-color")} 0%, ${CL_THEME_COLOR.get("menu-bg-color")} 100%);   
    </c:if>
    <c:if test="${sessionScope.THEME_INFOR.get('menu-bg-color') != null}">
        background-image: linear-gradient(to bottom, ${sessionScope.THEME_INFOR.get('menu-bg-color')} 0%, ${sessionScope.THEME_INFOR.get('menu-bg-color')} 100%);   
    </c:if>
}
/* menu brand */
.navbar-default .navbar-brand  {
    <c:if test="${CL_THEME_COLOR.get('menu-brand-color') !=null and sessionScope.THEME_INFOR.get('menu-brand-color') == null}">
        color: ${CL_THEME_COLOR.get("menu-brand-color")} ;
    </c:if>
    <c:if test="${sessionScope.THEME_INFOR.get('menu-brand-color') != null}">
        color: ${sessionScope.THEME_INFOR.get('menu-brand-color')} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('menu-brand-size') !=null and sessionScope.THEME_INFOR.get('menu-brand-size') == null}">
        font-size: ${CL_THEME_COLOR.get("menu-brand-size")}px ;
    </c:if>
    <c:if test="${sessionScope.THEME_INFOR.get('menu-brand-size') != null}">
        font-size: ${sessionScope.THEME_INFOR.get('menu-brand-size')}px;
    </c:if>
}
/* menu text color*/
.navbar-default .navbar-nav > li > a {
    <c:if test="${CL_THEME_COLOR.get('menu-text-color') != null and sessionScope.THEME_INFOR.get('menu-text-color') == null}">
        color: ${CL_THEME_COLOR.get("menu-text-color")} ;
    </c:if>
    <c:if test="${sessionScope.THEME_INFOR.get('menu-text-color') != null}">
        color: ${sessionScope.THEME_INFOR.get('menu-text-color')} ;
    </c:if>
}

/* header */
.qp-header-main-text{
    <c:if test="${CL_THEME_COLOR.get('header-title-size') !=null and sessionScope.THEME_INFOR.get('header-title-size') == null}">
        font-size: ${CL_THEME_COLOR.get("header-title-size")} ;
    </c:if>
     <c:if test="${sessionScope.THEME_INFOR.get('header-title-size') != null}">
        font-size: ${sessionScope.THEME_INFOR.get("header-title-size")}px;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('header-title-color') !=null and sessionScope.THEME_INFOR.get('header-title-color') == null }">
        color: ${CL_THEME_COLOR.get("header-title-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('header-title-color')}">
        color: ${sessionScope.THEME_INFOR.get("header-title-color")} ;
    </c:if>
}
.qp-link-header{
    <c:if test="${CL_THEME_COLOR.get('header-link-position')!= null and sessionScope.THEME_INFOR.get('header-link-position')}==null}">
        text-align: ${CL_THEME_COLOR.get("header-link-position")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('header-link-position')}">
        text-align: ${sessionScope.THEME_INFOR.get("header-link-position")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('header-link-color')!=null and sessionScope.THEME_INFOR.get('header-link-color')==null}">
        color:  ${CL_THEME_COLOR.get("header-link-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('header-link-color')}">
        color:  ${sessionScope.THEME_INFOR.get("header-link-color")} ;
    </c:if>
}
/* panel */
.panel-default>.panel-heading {
    <c:if test="${CL_THEME_COLOR.get('panel-header-text-color') != null and sessionScope.THEME_INFOR.get('panel-header-text-color') == null}">
        color: ${CL_THEME_COLOR.get("panel-header-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-header-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("panel-header-text-color")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-header-bg-color') != null and sessionScope.THEME_INFOR.get('panel-header-bg-color') == null}">
        background-color:  ${CL_THEME_COLOR.get("panel-header-bg-color")} ;
    </c:if>
     <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-header-bg-color')}">
        background-color:  ${sessionScope.THEME_INFOR.get("panel-header-bg-color")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-border') != null and sessionScope.THEME_INFOR.get('panel-border') == null}">
        border-color: ${CL_THEME_COLOR.get("panel-border")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-border')}">
        border-color: ${sessionScope.THEME_INFOR.get("panel-border")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-header-text-size') != null and sessionScope.THEME_INFOR.get('panel-header-text-size') == null}">
        font-size: ${CL_THEME_COLOR.get("panel-header-text-size")}px;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-header-text-size')}">
        font-size: ${sessionScope.THEME_INFOR.get("panel-header-text-size")}px;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-header-height') != null and sessionScope.THEME_INFOR.get('panel-header-height') == null}">
        height: ${CL_THEME_COLOR.get("panel-header-height")}px;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-header-height')}">
        height: ${sessionScope.THEME_INFOR.get("panel-header-height")}px;
    </c:if>
}
.panel-body {
    <c:if test="${CL_THEME_COLOR.get('panel-body-padding') != null and sessionScope.THEME_INFOR.get('panel-body-padding') == null}">
        padding: ${CL_THEME_COLOR.get("panel-body-padding")}px;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-body-padding')}">
        padding: ${sessionScope.THEME_INFOR.get("panel-body-padding")}px;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-body-bg-color') != null and sessionScope.THEME_INFOR.get('panel-body-bg-color') == null}">
        background:  ${CL_THEME_COLOR.get("panel-body-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-body-bg-color')}">
        background:  ${sessionScope.THEME_INFOR.get("panel-body-bg-color")} ;
    </c:if>
}
/* panel table form */
.qp-table-form tr th{
    <c:if test="${CL_THEME_COLOR.get('panel-table-form-border') != null and sessionScope.THEME_INFOR.get('panel-table-form-border') == null}">
        border: ${CL_THEME_COLOR.get("panel-table-form-border")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-form-border')}">
        border: ${sessionScope.THEME_INFOR.get("panel-table-form-border")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('panel-table-form-th-bg-color') !=null and sessionScope.THEME_INFOR.get('panel-table-form-th-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("panel-table-form-th-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-form-th-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("panel-table-form-th-bg-color")} ;
    </c:if>
} 
.qp-table-form tr th {
    <c:if test="${CL_THEME_COLOR.get('panel-table-form-th-text-position') !=null and sessionScope.THEME_INFOR.get('panel-table-form-th-text-position') == null }">
        text-align: ${CL_THEME_COLOR.get("panel-table-form-th-text-position")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-form-th-text-position')}">
        text-align: ${sessionScope.THEME_INFOR.get("panel-table-form-th-text-position")} ;
    </c:if>
}
/* panel table list */
.qp-table-list thead tr th {
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-border') !=null and sessionScope.THEME_INFOR.get('panel-table-list-border') == null}">
        border: ${CL_THEME_COLOR.get("panel-table-list-border")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-border')}">
        border: ${sessionScope.THEME_INFOR.get("panel-table-list-border")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-th-bg-color') !=null and sessionScope.THEME_INFOR.get('panel-table-list-border') == null}">
        background-color: ${CL_THEME_COLOR.get("panel-table-list-th-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-th-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("panel-table-list-th-bg-color")} ;
    </c:if>
}
.qp-table-list tbody tr td:first-child{
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-td-firstChild-position') !=null and sessionScope.THEME_INFOR.get('panel-table-list-td-firstChild-position') == null}">
        text-align: ${CL_THEME_COLOR.get("panel-table-list-td-firstChild-position")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-td-firstChild-position')}">
        text-align: ${sessionScope.THEME_INFOR.get("panel-table-list-td-firstChild-position")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-td-firstChild-width') !=null and sessionScope.THEME_INFOR.get('panel-table-list-td-firstChild-width') == null}">
        width: ${CL_THEME_COLOR.get("panel-table-list-td-firstChild-width")}px;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-td-firstChild-width')}">
        width: ${sessionScope.THEME_INFOR.get("panel-table-list-td-firstChild-width")}px;
    </c:if>
}
.qp-table-list tbody tr td:last-child {
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-td-lastChild-position') !=null and sessionScope.THEME_INFOR.get('panel-table-list-td-lastChild-position') == null}">
        text-align: ${CL_THEME_COLOR.get("panel-table-list-td-lastChild-position")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-td-lastChild-position')}">
        text-align: ${sessionScope.THEME_INFOR.get("panel-table-list-td-lastChild-position")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-td-lastChild-width') !=null and sessionScope.THEME_INFOR.get('panel-table-list-td-lastChild-width') == null}">
        width: ${CL_THEME_COLOR.get("panel-table-list-td-lastChild-width")}px;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-td-lastChild-width')}">
        width: ${sessionScope.THEME_INFOR.get("panel-table-list-td-lastChild-width")}px;
    </c:if>
}
.qp-table-list thead tr th {
    <c:if test="${CL_THEME_COLOR.get('panel-table-list-td-anotherChild-position') !=null and sessionScope.THEME_INFOR.get('panel-table-list-td-anotherChild-position') == null}">
        text-align: ${CL_THEME_COLOR.get("panel-table-list-td-anotherChild-position")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-table-list-td-anotherChild-position')}">
        text-align: ${sessionScope.THEME_INFOR.get('panel-table-list-td-anotherChild-position')} ;
    </c:if>
}

/* badge */
.panel-default > .panel-heading .badge {
    <c:if test="${CL_THEME_COLOR.get('panel-badge-text-color') !=null and sessionScope.THEME_INFOR.get('panel-badge-text-color') == null}">
        color: ${CL_THEME_COLOR.get("panel-badge-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-badge-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("panel-badge-text-color")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('panel-badge-bg-color') !=null and sessionScope.THEME_INFOR.get('panel-badge-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("panel-badge-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('panel-badge-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("panel-badge-bg-color")} ;
    </c:if>
}
/* footer */
.qp-footer {
    <c:if test="${CL_THEME_COLOR.get('footer-border') !=null and sessionScope.THEME_INFOR.get('footer-border') == null}">
        border-top: ${CL_THEME_COLOR.get("footer-border")} ;
    </c:if>
     <c:if test="${not empty sessionScope.THEME_INFOR.get('footer-border')}">
        border-top: ${sessionScope.THEME_INFOR.get("footer-border")} ;
    </c:if>
}
/* common */

.qp-button-warning {
    <c:if test="${CL_THEME_COLOR.get('common-button-delete-bg-color') !=null and sessionScope.THEME_INFOR.get('common-button-delete-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("common-button-delete-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-delete-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("common-button-delete-bg-color")} ;
    </c:if>
    
    <c:if test="${CL_THEME_COLOR.get('common-button-delete-text-color') !=null and sessionScope.THEME_INFOR.get('common-button-delete-text-color') == null}">
        color: ${CL_THEME_COLOR.get("common-button-delete-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-delete-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("common-button-delete-text-color")} ;
    </c:if>
}
.qp-button-warning:active ,.qp-button-warning:hover, .qp-button-warning:focus {
    <c:if test="${CL_THEME_COLOR.get('common-button-delete-bg-active-color') !=null and sessionScope.THEME_INFOR.get('common-button-delete-bg-active-color') == null}">
        background-color: ${CL_THEME_COLOR.get("common-button-delete-bg-active-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-delete-bg-active-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("common-button-delete-bg-active-color")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('common-button-delete-bg-active-color') !=null and sessionScope.THEME_INFOR.get('common-button-delete-bg-active-color') == null}">
        border-color: ${CL_THEME_COLOR.get("common-button-delete-bg-active-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-delete-bg-active-color')}">
        border-color: ${sessionScope.THEME_INFOR.get("common-button-delete-bg-active-color")} ;
    </c:if>
}
.qp-button {
    <c:if test="${CL_THEME_COLOR.get('common-button-text-color') !=null and sessionScope.THEME_INFOR.get('common-button-text-color') == null}">
        color: ${CL_THEME_COLOR.get("common-button-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("common-button-text-color")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('common-button-bg-color') !=null and sessionScope.THEME_INFOR.get('common-button-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("common-button-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("common-button-bg-color")} ;
    </c:if>
}

.qp-button:active, .qp-button:hover, .qp-button:focus{
    <c:if test="${CL_THEME_COLOR.get('common-button-text-color') !=null and sessionScope.THEME_INFOR.get('common-button-text-color') == null}">
        color: ${CL_THEME_COLOR.get('common-button-text-color')} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-text-color')}">
        color: ${sessionScope.THEME_INFOR.get('common-button-text-color')} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('common-button-bg-active-color') !=null and sessionScope.THEME_INFOR.get('common-button-bg-active-color') == null}">
        background-color: ${CL_THEME_COLOR.get('common-button-bg-active-color')} ;
        border-color: ${CL_THEME_COLOR.get('common-button-bg-active-color')} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-bg-active-color')}">
        background-color: ${sessionScope.THEME_INFOR.get('common-button-bg-active-color')} ;
        border-color: ${sessionScope.THEME_INFOR.get('common-button-bg-active-color')} ;
    </c:if>
}
.qp-link-button:hover, .qp-link-button:visited, .qp-link-button:active, .qp-link-button:focus {
    <c:if test="${CL_THEME_COLOR.get('common-button-text-color') !=null and sessionScope.THEME_INFOR.get('common-button-text-color') == null}">
        color: ${CL_THEME_COLOR.get("common-button-text-color")};
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("common-button-text-color")};
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('common-button-bg-color-active') !=null and sessionScope.THEME_INFOR.get('common-button-bg-color-active') == null}">
        background-color: ${CL_THEME_COLOR.get("common-button-bg-color-active")} ;
         border-color: ${CL_THEME_COLOR.get("common-button-bg-color-active")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-bg-color-active')}">
        background-color: ${sessionScope.THEME_INFOR.get("common-button-bg-color-active")} ;
         border-color: ${sessionScope.THEME_INFOR.get("common-button-bg-color-active")} ;
    </c:if>
}
.pagination > .active > a {
    <c:if test="${CL_THEME_COLOR.get('common-button-bg-color') !=null and sessionScope.THEME_INFOR.get('common-button-bg-color') == null}">
        background-color: ${CL_THEME_COLOR.get("common-button-bg-color")} ;
        border-color: ${CL_THEME_COLOR.get("common-button-bg-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-bg-color')}">
        background-color: ${sessionScope.THEME_INFOR.get("common-button-bg-color")} ;
        border-color: ${sessionScope.THEME_INFOR.get("common-button-bg-color")} ;
    </c:if>
    
    
    <c:if test="${CL_THEME_COLOR.get('common-button-text-color') !=null and sessionScope.THEME_INFOR.get('common-button-text-color') == null}">
        color: ${CL_THEME_COLOR.get("common-button-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-button-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("common-button-text-color")} ;
    </c:if>
}

.qp-link-popup{
    <c:if test="${CL_THEME_COLOR.get('common-link-popup-text-color')} !=null and sessionScope.THEME_INFOR.get('common-button-text-color') == null}">
        color: ${CL_THEME_COLOR.get("common-link-popup-text-color")} ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-link-popup-text-color')}">
        color: ${sessionScope.THEME_INFOR.get("common-link-popup-text-color")} ;
    </c:if>
}
.qp-root {
	<c:if test="${CL_THEME_COLOR.get('common-screen-size')} !=null and sessionScope.THEME_INFOR.get('common-screen-size') == null}">
        width: ${CL_THEME_COLOR.get('common-screen-size')}px ;
    </c:if>
    <c:if test="${not empty sessionScope.THEME_INFOR.get('common-screen-size')}">
        width: ${sessionScope.THEME_INFOR.get("common-screen-size")}px ;
    </c:if>
}

@media(min-width: 992px){
	.modal-lg {
		 <c:if test="${not empty sessionScope.THEME_INFOR.get('common-screen-size')}">
			width: ${sessionScope.THEME_INFOR.get("common-screen-size")}px;
		</c:if>
	}
}

@media (max-width: 767px) {
	.navbar-default .navbar-nav > li.open {
	    background-color:white;
	}
}
.qp-header-img { 
	${sessionScope.THEME_INFOR.get("backgroundColor")}
}
.qp-root { 
	${sessionScope.THEME_INFOR.get("screenStyle")}
}
.qp-root .panel-default>.panel-heading { 
	${sessionScope.THEME_INFOR.get("panelHeader")}
}
.qp-root .panel-body { 
	${sessionScope.THEME_INFOR.get("panelBody")}
}
.qp-root .qp-table-list { 
	${sessionScope.THEME_INFOR.get("panelListTable")}
}
.qp-root .qp-table-list thead tr th { 
	${sessionScope.THEME_INFOR.get("panelListTh")}

}
.qp-root .result-text { 
	${sessionScope.THEME_INFOR.get("panelListTdText")}
}
.qp-root .result-numeric { 
	${sessionScope.THEME_INFOR.get("panelListTdNumeric")}
}
.qp-root .result-date { 
	${sessionScope.THEME_INFOR.get("panelListTdDate")}
}
.qp-root .result-date-time { 
	${sessionScope.THEME_INFOR.get("panelListTdDateTime")}
}
.qp-root .result-no-number { 
	${sessionScope.THEME_INFOR.get("panelListTdNoNumber")}

}
.qp-root .result-action-column { 
	${sessionScope.THEME_INFOR.get("panelListTdActionColumn")}
}
.qp-root .qp-table-form { 
	${sessionScope.THEME_INFOR.get("panelTableForm")}
}
.qp-root .qp-table-form tr th { 
	${sessionScope.THEME_INFOR.get("panelTableFormTh")}

}
.qp-root .qp-table-form tr td { 
	${sessionScope.THEME_INFOR.get("panelTableFormTd")}
}
.qp-root .qp-footer {
	${sessionScope.THEME_INFOR.get("footerStyle")}
}
</style>
