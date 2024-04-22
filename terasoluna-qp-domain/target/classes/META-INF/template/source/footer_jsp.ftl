	<div class="qp-footer pull-left" style="width:100%">	
		<div type="4" class="pull-left">
			<#if listFooterLeft?has_content>
				<#list listFooterLeft as footerLeft>
					<#if footerLeft.itemType == 0>
						<#if (footerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${footerLeft.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${footerLeft.messageString}</span>
						</#if>
					</#if>
					<#if footerLeft.itemType == 1>
						<#if (footerLeft.style)?has_content && (footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}; ${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}; ${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							</#if>
							
						<#elseif (footerLeft.style)?has_content && !(footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
							</#if>
							
						<#elseif !(footerLeft.style)?has_content && (footerLeft.hoverStyle)?has_content>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.hoverStyle}">${footerLeft.messageString}</a>
							</#if>
							
						<#else>
							<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if footerLeft.itemType == 2>
						<#if footerLeft.componentType == 1 || footerLeft.componentType == 0>
							<#if (footerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${account.username}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (footerLeft.style)?has_content>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerLeft.itemType == 3>
						<#if footerLeft.componentType == 1 || footerLeft.componentType == 0>
							<#if (footerLeft.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (footerLeft.style)?has_content>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerLeft.style}">${footerLeft.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerLeft.moduleName)?has_content && (footerLeft.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerLeft.moduleName}/${footerLeft.screenName}" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerLeft.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerLeft.itemType == 4>
						<#if (footerLeft.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerLeft.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
				
			<#else>
				<ul class="pull-left">
					<li><a href="">About this Site</a></li>
					<li><a href="">Contact Us</a></li>
				</ul>
			</#if>
		</div>
			
		<div type="5" class="pull-right">
			<#if listFooterRight?has_content>
				<#list listFooterRight as footerRight>
					<#if footerRight.itemType == 0>
						<#if (footerRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${footerRight.messageString}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${footerRight.messageString}</span>
						</#if>
					</#if>
					<#if footerRight.itemType == 1>
						<#if (footerRight.style)?has_content && (footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}; ${footerRight.hoverStyle}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}; ${footerRight.hoverStyle}">${footerRight.messageString}</a>
							</#if>
							
						<#elseif (footerRight.style)?has_content && !(footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
							</#if>
							
						<#elseif !(footerRight.style)?has_content && (footerRight.hoverStyle)?has_content>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.hoverStyle}">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.hoverStyle}">${footerRight.messageString}</a>
							</#if>
							
						<#else>
							<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
								<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
							<#else>
								<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
							</#if>
							
						</#if>
					</#if>
					<#if footerRight.itemType == 2>
						<#if footerRight.componentType == 1 || footerRight.componentType == 0>
							<#if (footerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${userName}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${userName}</span>
							</#if>
						<#else>
							<#if (footerRight.style)?has_content>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								</#if>
							</#if>
						</#if>
					</#if>
					<#if footerRight.itemType == 3>
						<#if footerRight.componentType == 1 || footerRight.componentType == 0>
							<#if (footerRight.style)?has_content>
								<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${dateTime}</span>
							<#else>
								<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
							</#if>
						<#else>
							<#if (footerRight.style)?has_content>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="${footerRight.style}">${footerRight.messageString}</a>
								</#if>
								
							<#else>
								<#if (footerRight.moduleName)?has_content && (footerRight.screenName)?has_content>
									<a class="item" href="${pageContext}/${footerRight.moduleName}/${footerRight.screenName}" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								<#else>
									<a class="item" href="#" ondblclick="settingLink(this)" style="">${footerRight.messageString}</a>
								</#if>
								
							</#if>
						</#if>
					</#if>
					<#if footerRight.itemType == 4>
						<#if (footerRight.style)?has_content>
							<span class="item" ondblclick="settingText(this)" style="${footerRight.style}">${dateTime}</span>
						<#else>
							<span class="item" ondblclick="settingText(this)" style="">${dateTime}</span>
						</#if>
						
					</#if>
				</#list>
				
			<#else>
				<address class="pull-right">Copyright © 2015 NTT DATA Corporation</address>
			</#if>
		</div>

		<!-- End body panel -->
</div>
</div>
</body>
</html>