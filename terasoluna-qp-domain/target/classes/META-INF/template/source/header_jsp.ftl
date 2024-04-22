<style type="text/css">
.label-menu-corner {
	font-size: 10px;
	line-height: 12px;
	position: absolute;
	left: 24px;
	top: 8px;
}

.dropdown-alerts {
	min-width: 250px;
}
		
/* menu brand */
.navbar-default .navbar-brand  {
    ${projectStyle['menuBrandColor']}${projectStyle['menuBrandSize']}
}

/*selected menu*/
.navbar-default .navbar-nav > .open > a, .navbar-default .navbar-nav > .open > a:hover, .navbar-default .navbar-nav > .open > a:focus {
    ${projectStyle ['menuSelectedStyle']}
    <#assign menuSelectedBgColor = projectStyle['menuSelectedBgColor']>
    <#if menuSelectedBgColor?has_content>
    	background-image: linear-gradient(to bottom, ${projectStyle ['menuSelectedBgColor']} 0%, ${projectStyle ['menuSelectedBgColor']} 100%);
    </#if>
}

/* menu font color*/
.navbar-default .navbar-nav > li > a {
    ${projectStyle['menuStyle']}
}
.navbar-default .navbar-nav > li > a {
    ${projectStyle['menuItemStyle']}
}

.dropdown-menu > li > a:hover, .dropdown-menu > li > a:focus {
    ${projectStyle ['menuItemHoverStyle']}
    background-image: linear-gradient(to bottom, ${projectStyle ['menuItemBgHoverStyle']} 0%, ${projectStyle ['menuItemBgHoverStyle']} 100%);
    background-repeat: repeat-x;
}

.qp-input-from{
	width: 47%;
}
.qp-input-to{
	width: 47%;
}

.panel-default>.panel-heading{
	${projectStyle['panelHeader']}
}

.panel-body{
	${projectStyle['panelBody']}
}

.qp-table-list{
	${projectStyle['panelListTable']}
}

.qp-table-list thead tr th{
	${projectStyle['panelListTh']}
}

.result-text{
	${projectStyle['panelListTdText']}
}

.result-numeric{
	${projectStyle['panelListTdNumeric']}
}

.result-date{
	${projectStyle['panelListTdDate']}
}

.result-date-time{
	${projectStyle['panelListTdDateTime']}
}

.result-no-number{
	${projectStyle['panelListTdNoNumber']}
}

.result-action-column{
	${projectStyle['panelListTdActionColumn']}
}

.qp-table-form{
	${projectStyle['panelTableForm']}
}

.qp-table-form tr th{
	${projectStyle['panelTableFormTh']}
}

.qp-table-form tr td{
	${projectStyle['panelTableFormTd']}
}


</style>
<!-- Start header info -->
<div class="com-header-info-panel" style="min-height: 47px;">
	<div class="com-header-logo-panel" style="width: 17%; float: ${projectStyle ['logoPosition']}; padding-top: 12px; height: 100%;">
		<div style="float: left; ">
			<#if !(projectStyle['logo'])?has_content>
				<a href="${pageContext}/${urlHome}">
					<img src="${pageContext}/resources/media/images/logo_header_01.png" style="border: 0px; height: 25px; ${projectStyle['logoHeight']} ${projectStyle['logoWidth']}" />
				</a>
			<#else>
				<#if (projectStyle['logoHeight']?has_content && !projectStyle['logoWidth']?has_content) || (!projectStyle['logoHeight']?has_content && projectStyle['logoWidth']?has_content) 
						|| (projectStyle['logoHeight']?has_content && projectStyle['logoWidth']?has_content)>
					<#assign height = projectStyle['logoHeight'] >
					<#assign width = projectStyle['logoWidth'] >
				<#else>
					<#assign height = "height : 25px;" >
					<#assign width = "width : 162px" >
				</#if>
				
				<a href="${pageContext}/${urlHome}"><img src="data:image/jpeg;base64,${projectStyle['logo'] }" style="border: 0px; ${height} ${width}" /> </a>
			</#if>
		</div>
	</div>
	<div class="com-header-logo-panel" style="width: 83%; float: left; padding-top: 12px; height: 50%;">
		<div type="0" style="float: left;  width: 38%;">&nbsp;
			<#if listLogoLeft?has_content>
				<#list listLogoLeft as logoLeft>
					<#if logoLeft.itemType == 0>
						<#if (logoLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${logoLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${logoLeft.messageString}</span>
						</#if>
					</#if>
					<#if logoLeft.itemType == 1>
						<#if (logoLeft.style)?has_content && (logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}; ${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}; ${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							</#if>
							
						<#elseif (logoLeft.style)?has_content && !(logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
							</#if>
							
						<#elseif !(logoLeft.style)?has_content && (logoLeft.hoverStyle)?has_content>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.hoverStyle}">${logoLeft.messageString}</a>
							</#if>
						<#else>
							<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if logoLeft.itemType == 2>
						<#if logoLeft.componentType == 1 || logoLeft.componentType == 0>
							<#if (logoLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${account.username}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (logoLeft.style)?has_content>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								</#if>
							<#else>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
						
					</#if>
					<#if logoLeft.itemType == 3>
						<#if logoLeft.componentType == 1 || logoLeft.componentType == 0>
							<#if (logoLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (logoLeft.style)?has_content>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoLeft.style}">${logoLeft.messageString}</a>
								</#if>
							<#else>
								<#if (logoLeft.moduleName)?has_content && (logoLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoLeft.moduleName}/${logoLeft.screenName}" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if logoLeft.itemType == 4>
						<#if (logoLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
		<div type="1" style="float: right;  width: 38%; text-align: right;">&nbsp;
			<#if listLogoRight?has_content>
				<#list listLogoRight as logoRight>
					<#if logoRight.itemType == 0>
						<#if (logoRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${logoRight.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${logoRight.messageString}</span>
						</#if>
					</#if>
					<#if logoRight.itemType == 1>
						<#if (logoRight.style)?has_content && (logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}; ${logoRight.hoverStyle}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}; ${logoRight.hoverStyle}">${logoRight.messageString}</a>
							</#if>
							
						<#elseif (logoRight.style)?has_content && !(logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
							</#if>
							
						<#elseif !(logoRight.style)?has_content && (logoRight.hoverStyle)?has_content>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.hoverStyle}">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.hoverStyle}">${logoRight.messageString}</a>
							</#if>
							
						<#else>
							<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if logoRight.itemType == 2>
						<#if logoRight.componentType == 1 || logoRight.componentType == 0>
							<#if (logoRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${userName}</span>
							<#else>
								<#assign name = "" />
								<#if userName?has_content>
									<#assign name = userName >
								</#if>
								
								<span class="item" ondblclick="settingText(this)" style="">${name}</span>
							</#if>
						<#else>
							<#if (logoRight.style)?has_content>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								</#if>
							<#else>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if logoRight.itemType == 3>
						<#if logoRight.componentType == 1 || logoRight.componentType == 0>
							<#if (logoRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (logoRight.style)?has_content>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${logoRight.style}">${logoRight.messageString}</a>
								</#if>
							<#else>
								<#if (logoRight.moduleName)?has_content && (logoRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${logoRight.moduleName}/${logoRight.screenName}" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${logoRight.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if logoRight.itemType == 4>
						<#if (logoRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${logoRight.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
	</div>
	<div class="com-header-button-panel" style="width: 83%; float: left; height: 50%;">
		<div type="2" style="width: 48%; float: left;">&nbsp;
			<#if listHeaderLeft?has_content>
				<#list listHeaderLeft as headerLeft>
					<#if headerLeft.itemType == 0>
						<#if (headerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${headerLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${headerLeft.messageString}</span>
						</#if>
					</#if>
					<#if headerLeft.itemType == 1>
						<#if (headerLeft.style)?has_content && (headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}; ${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}; ${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							</#if>
							
						<#elseif (headerLeft.style)?has_content && !(headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
							</#if>
							
						<#elseif !(headerLeft.style)?has_content && (headerLeft.hoverStyle)?has_content>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.hoverStyle}">${headerLeft.messageString}</a>
							</#if>
							
						<#else>
							<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 2>
						<#if headerLeft.componentType == 1 || headerLeft.componentType == 0>
							<#if (headerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (headerLeft.style)?has_content>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 3>
						<#if headerLeft.componentType == 1 || headerLeft.componentType == 0>
							<#if (headerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (headerLeft.style)?has_content>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerLeft.style}">${headerLeft.messageString}</a>
								</#if>
							<#else>
								<#if (headerLeft.moduleName)?has_content && (headerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerLeft.moduleName}/${headerLeft.screenName}" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerLeft.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if headerLeft.itemType == 4>
						<#if (headerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${headerLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
			</#if>
		</div>
		<div type="3" style="width: 48%; float: right; text-align: right;">
			<#if listHeaderRight?has_content>
					<#list listHeaderRight as headerRight>
						<#if headerRight.itemType == 0>
							<#if (headerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${headerRight.messageString}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${headerRight.messageString}</span>
							</#if>
						</#if>
						<#if headerRight.itemType == 1>
							<#if (headerRight.style)?has_content && (headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}; ${headerRight.hoverStyle}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}; ${headerRight.hoverStyle}">${headerRight.messageString}</a>
								</#if>
								
							<#elseif (headerRight.style)?has_content && !(headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
								</#if>
								
							<#elseif !(headerRight.style)?has_content && (headerRight.hoverStyle)?has_content>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.hoverStyle}">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.hoverStyle}">${headerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
						<#if headerRight.itemType == 2>
							<#if headerRight.componentType == 1 || headerRight.componentType == 0>
								<#if (headerRight.style)?has_content>
									<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${userName}</span>
								<#else>
									<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
								</#if>
							<#else>
								<#if (headerRight.style)?has_content>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									</#if>
									
								<#else>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									</#if>
									
								</#if>
							</#if>
						</#if>
						<#if headerRight.itemType == 3>
							<#if headerRight.componentType == 1 || headerRight.componentType == 0>
								<#if (headerRight.style)?has_content>
									<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${dateTime}</span>
								<#else>
									<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
								</#if>
							<#else>
								<#if (headerRight.style)?has_content>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="${headerRight.style}">${headerRight.messageString}</a>
									</#if>
									
								<#else>
									<#if (headerRight.moduleName)?has_content && (headerRight.screenName)?has_content>
										<a class="item" href="${pageContext}/${headerRight.moduleName}/${headerRight.screenName}" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									<#else>
										<a class="item" href="#" ondblclick="settingLink(this)" style="">${headerRight.messageString}</a>
									</#if>
									
								</#if>
							</#if>
						</#if>
						<#if headerRight.itemType == 4>
							<#if (headerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${headerRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
							
						</#if>
					</#list>
				</#if>
		</div>
	</div>
</div>
<!-- End header info -->

<!-- Start Menu panel -->
		${menuContent}
