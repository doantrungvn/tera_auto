<div class="modal-dialog" style="min-height: 500px;width: 800px;">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		</div>
		<div class="modal-body">
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span aria-hidden="true" class="glyphicon  qp-heading-icon">&nbsp;</span>
					<span class="pq-heading-text" name="span-name"><qp:message code="sc.screendesign.0375" /></span>
				</div>
				<div class="panel-body">
					<table class='table table-bordered qp-table-form'>
						<colgroup>
							<col width='30%' />
							<col width='65%' />
						</colgroup>
						<tr>
							<th><qp:message code="sc.screendesign.0460" /></th>
							<td>
								<label><input name="tabType" class="qp-input-radio-margin qp-input-radio" checked="checked" value="0" type="radio"><qp:message code="sc.screendesign.0144" /></label>&nbsp;
								<label><input name="tabType" class="qp-input-radio-margin qp-input-radio" type="radio" value="1"><qp:message code="sc.screendesign.0145" /></label>&nbsp;
								<label><input name="tabType" class="qp-input-radio-margin qp-input-radio" type="radio" value="2"><qp:message code="sc.screendesign.0461" /></label>
								<label><input name="tabType" class="qp-input-radio-margin qp-input-radio" type="radio" value="3"><qp:message code="sc.screendesign.0462" /></label>
							</td>
						</tr>
					</table>
					<br />
					<ul class="nav nav-tabs">
						<li class="active">
							<a href="#tab-setting" data-toggle="tab"><qp:message code="sc.screendesign.0463" /></a>
						</li>
						<li><a href="#tab-style" data-toggle="tab"><qp:message code="sc.screendesign.0339" /></a></li>
					</ul>
					<div class="tab-content" >
						<div id="tab-setting" class="tab-pane active">
							<ul class="nav nav-tabs">
								<li onclick="addTab(this)"><a style="cursor: pointer;"><span style="font-weight: normal; font-size: smaller;" class='glyphicon glyphicon-plus'></span></a></li>
							</ul>
							<div class="tab-content">
								
							</div>										
						</div>
						<div id="tab-style" class="tab-pane">
							<div class="panel panel-default  qp-div-information">
						<div class="panel-heading">
							<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
							<span class="pq-heading-text"><qp:message code="sc.screendesign.0464" /></span>
						</div>
						<div class="panel-body">
							<table class="table table-bordered qp-table-form">
								<colgroup>
									<col width="30%">
									<col width="70%">
								</colgroup>
								<tr>
									<th><qp:message code="sc.screendesign.0346"/></th>
									<td>
										<div class="input-group qp-input-pickcolor colorpicker-element" style="width: 100%;">
											<span class="input-group-addon"> <i></i></span>
											<input id="mapTheme'common-button-bg-active-color'" name="mapTheme['common-button-bg-active-color']" class="form-control" type="text" >
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0347"/></th>
									<td>
										<div class="input-group" style="width: 100%;">
											<input class="form-control qp-input-integer" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0348"/></th>
									<td>
										<select class="form-control">
											<option><qp:message code="sc.screendesign.0142"/></option>
											<option><qp:message code="sc.screendesign.0350"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0352"/></th>
									<td>
										<div style="width: 100%;">
											<qp:autocomplete optionValue="optionValue" optionLabel="optionValue" mustMatch="true" sourceType="local" sourceCallback="fontWeight"></qp:autocomplete> </td>
										</div>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0361"/></th>
									<td>
										<div class="input-group qp-input-pickcolor colorpicker-element" style="width: 100%;">
											<span class="input-group-addon"> <i></i></span>
											<input id="mapTheme'common-button-bg-active-color'" name="mapTheme['common-button-bg-active-color']" class="form-control" type="text" >
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0362" /></th>
									<td>
										<div class="input-group" style="width: 100%;">
											<input class="form-control qp-input-integer" type="text" value="18"> <span class="hex-pound input-group-addon"><qp:message code="sc.screendesign.0147"/></span>
										</div>
									</td>
								</tr>
								<tr>
									<th><qp:message code="sc.screendesign.0364" /></th>
									<td>
										<div class="input-group qp-input-pickcolor colorpicker-element" style="width: 100%;">
											<span class="input-group-addon"> <i></i></span>
											<input id="mapTheme'common-button-bg-active-color'" name="mapTheme['common-button-bg-active-color']" class="form-control" type="text" >
										</div>
									</td>
								</tr>
							</table>
							
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<input type="button" onclick="saveTabSetting(this)"
				class="btn qp-button-client"
				value='<qp:message code="sc.sys.0054"></qp:message>' />
		</div>
	</div>
	<!-- /.modal-content -->
</div>