<tiles:insertDefinition name="layouts-popup">
	<tiles:putAttribute name="additionalHeading">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/javascript/Srcgen/jquery-ui-1.10.3.custom.min.js"></script>
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/treetable/jquery.treetable.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/resources/media/js/treetable/jquery.treetable.theme.default.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/media/js/treetable/jquery.treetable.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/app/javascript/Srcgen/Srcgen0202.js"></script>
	</tiles:putAttribute>

	<tiles:putAttribute name="header-name">	
	    Advance Business Logic design
	</tiles:putAttribute>
	<tiles:putAttribute name="body">
		
		<form:form method="post"
			action="${pageContext.request.contextPath}/businessdesign/advancedForm" 
			modelAttribute="businessDesignForm">
						
			<div id="dialog-test-data" class="modal fade" style="display: none;">
		        <div class="modal-dialog">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                    <h5 class="modal-title"><qp:message code="sc.businesslogicdesign.0178" /></h5>
		                </div>
		                <div class="modal-body">
		                    <table class="table table-bordered qp-table-form">
		                    	<colgroup>
									<col width="40%"/>
									<col width="60%"/>
									</colgroup>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0179" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="10001"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0180" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Hiro kana"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0181" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Hiro"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0182" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="30"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0183" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Male"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0184" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Toshimaku-Tokyo-100-211-22-103"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0185" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Toshima, 1-18-1 Higashi-Ikebukuro, Toshima-ku"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0186" /></th>
									<td><input type="text" class="input-group qp-input-datepicker" name="dataValue" maxlength="200" value="2014/12/24"/></td>
								</tr>
								<tr>
									<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0187" /></th>
									<td><input type="text" class="form-control qp-input-text" name="dataValue" maxlength="200" value="Hiro"/></td>
								</tr>
		                    </table>
		                </div>
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-primary" data-dismiss="modal"><qp:message code="sc.sys.0031" /></button>
		                </div>
		            </div>
		        </div>
		    </div>
			
			<!-- Block for display information maybe edit data -->
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0018" /></span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0188" />
							</th>
							<td>
								<input type="text" class="form-control qp-input-text" value="Register Inbound"/></td>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0005" /></th>
							<td><qp:message code="sc.businesslogicdesign.0190" /></td>
						</tr>
						<tr>
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0189" /></th>
							<td>
								<input type="text" class="form-control qp-input-text" value="RegisterInbound"/>
							</td>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr style="display: none;" class="qp-toggle-more-row">
							<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0191" /></th>
							<td>
								<input type="text" class="form-control qp-input-text" value=""/>
							</td>
							<th><qp:message code="sc.businesslogicdesign.0192" /></th>
							<td>
								<select class="form-control qp-input-select">
									<option><qp:message code="sc.businesslogicdesign.0193" /></option>
									<option><qp:message code="sc.businesslogicdesign.0194" /></option>
									<option><qp:message code="sc.businesslogicdesign.0195" /></option>
									<option><qp:message code="sc.businesslogicdesign.0196" /></option>
								</select>
							</td>
						</tr>
					</table>
				</div>
			</div>
				
			<!-- Block for display tab -->
			<div id="tabs">
				<ul class="nav nav-tabs" id="com-menu-sidebar">
					<li class="active"><a href="#tabs-1" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0008" /></a></li>
					<li><a href="#tabs-2" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0027" /></a></li>
					<li><a href="#tabs-3" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0025" /></a></li>
					<li><a href="#tabs-4" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0026" /></a></li>
					<li><a href="#tabs-5" data-toggle="tab" style="font: bold;"><qp:message code="sc.businesslogicdesign.0197" /></a></li>
				</ul>
				
				<div class="tab-content">
			
					<div id="tabs-1" style="height: auto;" class="tab-pane active">
						<div style="float: right; padding-top: 7px;" class="com-sub-title">
			          		&nbsp;<a href="#" class="com-link-popup" onclick="jQuery('#businessLogicTable').treetable('expandAll'); return false;"><qp:message code="sc.businesslogicdesign.0198" /></a>&nbsp;&nbsp;|
			          		<a href="#" class="com-link-popup" onclick="jQuery('#businessLogicTable').treetable('collapseAll'); return false;"><qp:message code="sc.businesslogicdesign.0199" /></a>
			          	</div>
			          	<div class="qp-div-action-left" id="divButtonControl" style="background-color: #fafcfc;">
			          		<input type="button" class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0200" />'/>
			          		<input type="button" class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0201" />'/>
			          		<input type="button" class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0202" />'/>
			          		<input type="button" class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0203" />'/>
			          		<input type="button" class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0204" />'/>
			          	</div>
						<table class="table qp-table treetable" id="businessLogicTable">
							<colgroup>
								<col />
								<col width="20%"/>
								<col width="20%"/>
								<col width="15%"/>
							</colgroup>
							<thead>
								<tr>
									<th><qp:message code="sc.businesslogicdesign.0205" /></th>
									<th><qp:message code="sc.businesslogicdesign.0206" /></th>
									<th><qp:message code="sc.businesslogicdesign.0207" /></th>
									<th><qp:message code="sc.businesslogicdesign.0208" /></th>
								</tr>
							</thead>
							<tbody>
								<tr data-tt-id='1'>
									<td  ondblclick="openDialogSetting()"><b><span class='folder'><qp:message code="sc.businesslogicdesign.0209" /></span></b></td>						
									<td >&nbsp;</td>	
									<td >&nbsp;</td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000" value="Assign input parameter to Inbound entity"/></td>			
								</tr>
								<tr data-tt-id='1-1' data-tt-parent-id='1'>
									<td ><input type="text" class="form-control qp-input-autocomplete qp-disable" maxlength="100" readonly="readonly" name="functionName" value="fc.set()"/></td>						
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="100" name="objectName" value="ob.inboundDTO.productId"/></td>	
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="200" name="contentName" value="in.paramProductId"/></td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000"/></td>			
								</tr>
								<tr data-tt-id='1-2' data-tt-parent-id='1'>
									<td ><input type="text" class="form-control qp-input-autocomplete qp-disable" readonly="readonly" maxlength="100" name="functionName" value="fc.set()"/></td>						
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="100" name="objectName" value="ob.inboundDTO.quantity"/></td>	
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="200" name="contentName" value="in.paramQty"/></td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000"/></td>			
								</tr>
								<tr data-tt-id='1-3' data-tt-parent-id='1'>
									<td ><input type="text" class="form-control qp-input-autocomplete qp-disable" readonly="readonly" maxlength="100" name="functionName" value="fc.set()"/></td>						
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="100" name="objectName" value="ob.inboundDTO.inboundDate"/></td>	
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="200" name="contentName" value="in.paramOrderDate"/></td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000"/></td>			
								</tr>
								<tr data-tt-id='1-4' data-tt-parent-id='1'>
									<td ><input type="text" class="form-control qp-input-autocomplete qp-disable" readonly="readonly" maxlength="100" name="functionName" value="fc.set()"/></td>						
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="100" name="objectName" value="ob.inboundDTO.location"/></td>	
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" readonly="readonly" maxlength="200" name="contentName" value="in.paramLocation"/></td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000"/></td>			
								</tr>
								<tr data-tt-id='2'>
									<td  ondblclick="openDialogSetting()"><b><span class='folder'>(Execution) Register Inbound</span></b></td>						
									<td >&nbsp;</td>	
									<td >&nbsp;</td>	
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000" value="Insert Inbound information into database"/></td>			
								</tr>
								<tr data-tt-id='2-1' data-tt-parent-id='2'>
									<td ><input type="text" class="form-control qp-input-autocomplete qp-disable" maxlength="100" name="functionName" value="sql.inbound.insert()" style="background-color: #FAF8D8;"/></td>						
									<td ><input type="text" class="form-control qp-input-autocomplete-detail qp-disable" maxlength="100" name="objectName" value="ob.inboundDTO"/></td>
									<td ><input type="text" class="form-control qp-input-autocomplete-detail" readonly="readonly" maxlength="200" name="contentName" value=""/></td>
									<td ><input type="text" class="form-control qp-input-text-detail" maxlength="1000" value=""/></td>
								</tr>
							</tbody>
						</table>
					</div>
				
					<div id="tabs-2" style="height: auto;" class="tab-pane">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered qp-table-list"
										id="tmp_list_table">
										<colgroup>
											<col width="3%"/>
											<col width="42%"/>
											<col width="32%"/>
											<col width="20%"/>
											<col width="3%"/>
										</colgroup>
										<thead>
											<tr>
												<th ><qp:message code="sc.sys.0004" /></th>
												<th ><qp:message code="sc.businesslogicdesign.0037" /></th>
												<th><qp:message code="sc.businesslogicdesign.0038" /></th>
												<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="com-output-fixlength">1</td>
												<td><input type="text" maxlength="100"  value="Inbound"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100"  value="inboundDTO"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" maxlength="100" value="Entity"/></td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="qp-div-action-table">
									<a title='<qp:message code="sc.businesslogicdesign.0200" />'
										class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
										href="javascript:void(0)"></a>
								</div>
							</div>
						</div>
					</div>
				
					<div id="tabs-3" style="height: auto;" class="tab-pane">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text"><qp:message code="sc.businesslogicdesign.0035" /></span>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered qp-table-list"
										id="tmp_list_table">
										<colgroup>
											<col width="3%"/>
											<col width="24%"/>
											<col width="20%"/>
											<col width="15%"/>
											<col width="20%"/>
											<col width="15%"/>
											<col width="3%"/>
										</colgroup>
										<thead>
											<tr>
												<th ><qp:message code="sc.sys.0004" /></th>
												<th ><qp:message code="sc.businesslogicdesign.0037" /></th>
												<th><qp:message code="sc.businesslogicdesign.0038" /></th>
												<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												<th><qp:message code="sc.businesslogicdesign.0210" />&nbsp;<label class="com-required-field">(*)</label></th>
												<th><qp:message code="sc.businesslogicdesign.0211" /></th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="com-output-fixlength">1</td>
												<td><input type="text" maxlength="100" value="Product Identity"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="paramProductId"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" value="Long"></td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" maxlength="100" name="contentName" value="in.productId" style="text-align: left;"
													class="form-control qp-input-text" >
												</td>
												<td><input type="text" class="form-control qp-input-text" maxlength="100" value=""/></td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
											<tr>
												<td class="com-output-fixlength">2</td>
												<td><input type="text" maxlength="100" value="Order quantity"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="paramQty"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" value="Integer"></td>
												<td>
													<input type="text" name="contentName" value="in.quantity" class="form-control qp-input-autocomplete-detail">
												</td>
												<td><input type="text" class="form-control qp-input-text" maxlength="100" value=""/></td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
											<tr>
												<td class="com-output-fixlength">3</td>
												<td><input type="text" maxlength="100" value="Inbound date"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="paramOrderDate"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" value="Datetime"></td>
												<td>
													<input type="text" maxlength="100" name="contentName" value="in.orderDate" class="form-control qp-input-autocomplete-detail">
												</td>
												<td><input type="text" class="form-control qp-input-text" maxlength="100" value=""/></td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
											<tr>
												<td class="com-output-fixlength">4</td>
												<td><input type="text" maxlength="100" value="Location"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="paramLocation"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" value="Text"></td>
												<td><input type="text" class="form-control qp-input-autocomplete-detail" maxlength="100" name="contentName" value="in.location" style="text-align: left;"
													class="form-control qp-input-text" >
												</td>
												<td><input type="text" class="form-control qp-input-text" maxlength="100" value=""/></td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="qp-div-action-table">
									<a title='<qp:message code="sc.businesslogicdesign.0200" />'
										class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
										href="javascript:void(0)"></a>
									<a href="javascript:" style="margin-top: 5px; float: right; color: blue;" class="com-link">Copy input</a>
								</div>
							</div>
						</div>
					</div>
				
					<div id="tabs-4" style="height: auto;" class="tab-pane">
						<div class="panel panel-default qp-div-select">
							<div class="panel-heading">
								<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
								<span class="pq-heading-text">Object list</span>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered qp-table-list"
										id="tmp_list_table">
										<colgroup>
											<col width="3%"/>
											<col width="32%"/>
											<col width="22%"/>
											<col width="20%"/>
											<col width="20%"/>
											<col width="3%"/>
										</colgroup>
										<thead>
											<tr>
												<th ><qp:message code="sc.sys.0004" /></th>
												<th ><qp:message code="sc.businesslogicdesign.0037" /></th>
												<th><qp:message code="sc.businesslogicdesign.0038" /></th>
												<th><qp:message code="sc.businesslogicdesign.0039" /></th>
												<th><qp:message code="sc.businesslogicdesign.0211" /></th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="com-output-fixlength">1</td>
												<td><input type="text" maxlength="100" value="Inbound identity"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="inboundId"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><input type="text" maxlength="100" value="Long" class="form-control qp-input-autocomplete-detail"></td>
												<td><input type="text" maxlength="100" value="2" style="text-align: right;"
													class="form-control qp-input-text" readonly="readonly">
												</td>
												<td><a class="btn btn-default btn-xs glyphicon glyphicon-minus qp-button-action" title='<qp:message code="sc.sys.0014" />' href="javascript:void(0)"></a></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="qp-div-action-table">
									<a title='<qp:message code="sc.businesslogicdesign.0200" />'
										class="btn btn-default btn-xs glyphicon glyphicon-plus qp-button-action"
										href="javascript:void(0)"></a>
								</div>
							</div>	
						</div>
					</div>
					
					<!-- Start tab-05 -->
					<div id="tabs-5" style="height: auto;" class="tab-pane">
						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapse1"><qp:message code="sc.businesslogicdesign.0212" /></a>
									</h4>
								</div>
								<div id="collapse1" class="panel-collapse collapse in">
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0092" /></th>
												<td><input type="text"
													class="form-control qp-input-text" /></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0213" />
												</th>
												<td><input type="text"
													class="form-control qp-input-text" /></td>
											</tr>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0214" /></th>
												<td><select class="form-control qp-input-select">
														<option><qp:message code="sc.businesslogicdesign.0119" /></option>
														<option selected="selected"><qp:message code="sc.businesslogicdesign.0215" /></option>
														<option><qp:message code="sc.businesslogicdesign.0216" /></option>
														<option><qp:message code="sc.businesslogicdesign.0217" /></option>
												</select></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0218" /></th>
												<td><input type="text"
													class="form-control qp-input-text" value="Member DTO" /></td>
											</tr>
											<tr>
												<td colspan="4" align="right"><textarea rows="7"
														style="width: 100%; text-align: left;"
														class="form-control qp-input-textarea">INSERT INTO
		member ( memberId, nameKana, name, birthday, age, gender, address, co, updateDate, updateBy ) 
	VALUES ( #memberId#, #nameKana#, #name#, #birthday#, #age#, #gender#, #address#, #co#, #updateDate#, #updateBy# )
													</textarea> (<a class="com-link-popup" style="color: blue;"
													href="javascript:" onclick="openDialogTestData()"><qp:message code="sc.businesslogicdesign.0219" /></a>) &nbsp;&nbsp;<input type="button"
													class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0223" />' />
													&nbsp;&nbsp;<input type="button"
													class="btn btn-default btn-xs" value='<qp:message code="sc.sys.0031" />' /></td>
											</tr>
										</table>
										<br />
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"	href="#collapse2"><qp:message code="sc.businesslogicdesign.0220" /></a>
									</h4>
								</div>
								<div id="collapse2" class="panel-collapse collapse in">
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0092" /></th>
												<td><input type="text"
													class="form-control qp-input-text"
													value="Select member by identity" /></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0213" />
												</th>
												<td><input type="text"
													class="form-control qp-input-text" value="selectById" /></td>
											</tr>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0214" /></th>
												<td><select class="form-control qp-input-select"
													disabled="disabled">
                                                        <option selected="selected"><qp:message code="sc.businesslogicdesign.0119" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0215" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0216" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0217" /></option>
												</select></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0218" />
												</th>
												<td><input type="text"
													class="form-control qp-input-text" value="Member DTO"
													disabled="disabled" /></td>
											</tr>
											<tr>
												<td colspan="4" align="right"><textarea rows="7"
														style="width: 100%; text-align: left;"
														class="form-control qp-input-textarea">SELECT
		memberId, nameKana, name, birthday, age, gender, address, co, updateDate, updateBy 
	FROM member
	WHERE memberId = #memberId#
													</textarea> (<a class="com-link-popup" style="color: blue;"
													href="javascript:" onclick="openDialogTestData()"><qp:message code="sc.businesslogicdesign.0219" /></a>) &nbsp;&nbsp;<input type="button"
													class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0223" />' />
													&nbsp;&nbsp;<input type="button"
													class="btn btn-default btn-xs" value='<qp:message code="sc.sys.0031" />' /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapse3"><qp:message code="sc.businesslogicdesign.0221" /></a>
									</h4>
								</div>
								<div id="collapse3" class="panel-collapse collapse in">
									<div class="panel-body">
										<table class="table table-bordered qp-table-form">
											<colgroup>
												<col width="20%" />
												<col width="30%" />
												<col width="20%" />
												<col width="30%" />
											</colgroup>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0092" /></th>
												<td><input type="text"
													class="form-control qp-input-text"
													value="Select member by identity" /></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0213" />
												</th>
												<td><input type="text"
													class="form-control qp-input-text" value="selectById" /></td>
											</tr>
											<tr>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0214" /></th></th>
												<td><select class="form-control qp-input-select"
													disabled="disabled">
														<option selected="selected"><qp:message code="sc.businesslogicdesign.0119" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0215" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0216" /></option>
                                                        <option><qp:message code="sc.businesslogicdesign.0217" /></option>
												</select></td>
												<th class="qp-table-th-text style-header-table"><qp:message code="sc.businesslogicdesign.0218" />
												</th>
												<td><input type="text"
													class="form-control qp-input-text" value="Member DTO"
													disabled="disabled" /></td>
											</tr>
											<tr>
												<td colspan="4" align="right"><textarea rows="7"
														style="width: 100%; text-align: left;"
														class="form-control qp-input-textarea">SELECT
		memberId, nameKana, name, birthday, age, gender, address, co, updateDate, updateBy 
	FROM member
	WHERE memberId = #memberId#
													</textarea> (<a class="com-link-popup" style="color: blue;"
													href="javascript:" onclick="openDialogTestData()"><qp:message code="sc.businesslogicdesign.0219" /></a>) &nbsp;&nbsp;<input type="button"
													class="btn btn-default btn-xs" value='<qp:message code="sc.businesslogicdesign.0222" />' /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- End tab-05 -->
					
				</div>
			</div>
			
			<br />
			<div class="qp-div-action">
				<button type="button" class="btn qp-button qp-dialog-confirm" name="mode" value="2"><qp:message code="sc.businesslogicdesign.0224" /></button>
				<span>&nbsp;&nbsp;</span>
				<button type="button" class="btn qp-button qp-dialog-confirm" name="mode" value="2"><qp:message code="sc.sys.0031" /></button>
			</div>
			<br />
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>