<tiles:insertDefinition name="layouts">
	<tiles:putAttribute name="header-name">	
        Register task
	</tiles:putAttribute>

	<tiles:putAttribute name="header-link">
        <qp:authorization permission="businesstypeRegister">
        	<span class="qp-link-header-icon glyphicon glyphicon-search"></span>
            <a href="${pageContext.request.contextPath}/schedule/search">Search task</a>
        </qp:authorization>
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<form:form method="post" role="form" >
			<qp:ColumnSortHidden/>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">Task infomation</span>
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
							<th>Title</th>
							<td><input type="text" class="form-control" /></td>
							<th>Class</th>
							<td><qp:autocomplete optionValue="" displayValue="org.terasoluna.qp.app.schelule.CustomerService" optionLabel=""></qp:autocomplete> </td>
						</tr>
						<tr>
							<th>Description</th>
							<td><textarea class="form-control" rows="" cols=""></textarea> </td>
							<th>Enabled</th>
							<td><input type="checkbox" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="panel panel-default qp-div-information">
				<div class="panel-heading">
					<span class="glyphicon  qp-heading-icon" aria-hidden="true">&nbsp;</span>
					<span class="pq-heading-text">Schedule config</span>
				</div>
				<div class="panel-body">
					<table class="table table-bordered qp-table-form">
						<colgroup>
							<col width="20%" />
							<col width="70%" />
						</colgroup>
						<tr>
							<td>
								<script>
								$(document).ready(function(){
									
									$("#one-time").show();
									$("#Daily").hide();
									$("#Weekly").hide();
									$("#Monthly").hide();
									$("#Advanced-setting").hide();
								});
									function showConfig(obj) {
										var value= $(obj).val();
										
										if (value == 1) {
											$("#one-time").show();
											$("#Daily").hide();
											$("#Weekly").hide();
											$("#Monthly").hide();
											$("#Advanced-setting").hide();
											
										} else if (value == 2) {
											$("#one-time").hide();
											$("#Daily").show();
											$("#Weekly").hide();
											$("#Monthly").hide();
											$("#Advanced-setting").hide();
										}
										 else if (value == 3) {
											 $("#one-time").hide();
												$("#Daily").hide();
												$("#Weekly").show();
												$("#Monthly").hide();
												$("#Advanced-setting").hide();
										}
										 else if (value == 4) {
											 $("#one-time").hide();
												$("#Daily").hide();
												$("#Weekly").hide();
												$("#Monthly").show();
												$("#Advanced-setting").hide();
											} else if (value == 5) {
												$("#one-time").hide();
												$("#Daily").hide();
												$("#Weekly").hide();
												$("#Monthly").hide();
												$("#Advanced-setting").show();
											}
									}
								</script>
								<label><input type="radio" checked="checked" name="name" value="1" onclick="showConfig(this)" /> One time </label> <br />
								<label><input type="radio" name="name" value="2" onclick="showConfig(this)" /> Daily </label> <br />
								<label><input type="radio" name="name" value="3" onclick="showConfig(this)" /> Weekly</label> <br />
								<label><input type="radio" name="name" value="4" onclick="showConfig(this)" /> Monthly</label> <br />
								<label><input type="radio" name="name" value="5" onclick="showConfig(this)" /> Advanced setting</label> <br />
							</td>
							<td valign="top" style="vertical-align: top;">
								<div id="one-time" style="vertical-align: top;">
									<table class="table table-bordered qp-table-form" style="margin-top: 0px;">
										<tr>
											<th>Start</th>
											<td>
												<div id="test" class='input-group date qp-input-datepicker'>
									
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
											</td>
											<td>
												<div class='input-group date qp-input-timepicker'>
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
											</td>
										</tr>
										
									</table>
								</div>
								<div id="Daily">
									<table class="table table-bordered qp-table-form" style="margin-top: 0px;">
										<tr>
											<th>Start</th>
											<td>
												<div id="test" class='input-group date qp-input-datepicker'>
									
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
											</td>
											<td>
												<div class='input-group date qp-input-timepicker'>
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
											</td>
										</tr>
										<tr>
											<th>Recur every: </th>
											<td colspan="2"><input type="text" class="form-control" style="width: 100px; float: left;" /> <span style="float: left;">days</span></span></td>
										</tr>
									</table>
								</div>
								<div id="Weekly">
									<table class="table table-bordered qp-table-form" style="margin-top: 0px;">
										<tr>
											<th>Start</th>
											<td>
												<div id="test" class='input-group date qp-input-datepicker'>
									
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
											</td>
											<td>
												<div class='input-group date qp-input-timepicker'>
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
											</td>
										</tr>
										<tr>
											<th>Recur every: </th>
											<td colspan="2"><input type="text" class="form-control" style="width: 100px; float: left;" /> <span style="float: left;"> weeks on:</span> <br /><br />
								<label><input type="radio" name="name1" value="2"  /> Sunday </label> 
								<label><input type="radio" name="name1" value="3"/> Monday</label> 
								<label><input type="radio" name="name1" value="4"  /> Tuesday</label> 
								<label><input type="radio" name="name1" value="5"  /> Wednesday</label><br />
								<label><input type="radio" name="name1" value="5"  /> thursday</label>
								<label><input type="radio" name="name1" value="5"  /> Friday</label>
								<label><input type="radio" name="name1" value="5"  /> Saturday</label>
								 
											</td>
										</tr>
									</table>
								</div>
								<div id="Monthly">
									<table class="table table-bordered qp-table-form" style="margin-top: 0px;">
										<tr>
											<th>Start</th>
											<td>
												<div id="test" class='input-group date qp-input-datepicker'>
									
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
											</td>
											<td>
												<div class='input-group date qp-input-timepicker'>
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
											</td>
										</tr>
										<tr>
											<th>Month: </th>
											<td colspan="2">
								 				<qp:autocomplete optionValue="" displayValue="January" optionLabel=""></qp:autocomplete>
											</td>
										</tr>
										<tr>
											<th>Day: </th>
											<td colspan="2">
								 				<qp:autocomplete optionValue="" displayValue="last" optionLabel=""></qp:autocomplete>
											</td>
										</tr>
									</table>
								</div>
								<div id="Advanced-setting">
									<table class="table table-bordered qp-table-form" style="margin-top: 0px;">
										<tr>
											<th>Repeat task every: </th>
											<td><select>
												<option>5 minutes</option>
												<option>10 minutes</option>
												<option>30 minutes</option>
												<option>1 hour</option>
											</select></td>
											<th>for a duration of: </th>
											<td><select>
												<option>1 hour</option>
												<option>12 hour</option>
												<option>1 Day</option>
											</select></td>
										</tr>
										<tr>
											<th>Expire</th>
											<td colspan="3"><div style="float: left;"><input type="checkbox" /></div>
												<div id="test" class='input-group date qp-input-datepicker' style="width: 47%; float: left;margin-left: 4px;">
									
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div> &nbsp;&nbsp;&nbsp;<div class='input-group date qp-input-timepicker' style="width: 20%; float: left; margin-left: 4px;">
									<input type="text" class="form-control" />
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-time"></span>
									</span>
								</div>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
            <!-- Style for button submit -->
            <div class="qp-div-action">
                
                    <button type="submit" class="btn qp-button"><qp:message code="sc.sys.0001" /></button>
                
            </div>

		</form:form>	
	</tiles:putAttribute>
</tiles:insertDefinition>
