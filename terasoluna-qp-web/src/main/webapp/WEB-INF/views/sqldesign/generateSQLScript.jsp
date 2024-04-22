<div class="panel panel-default qp-div-search-result">
	<div class="panel-heading">
		<span class="glyphicon qp-heading-icon" aria-hidden="true">&nbsp;</span>
		<span class="qp-heading-text">SQL SCRIPT</span>
	</div>
	<div class="panel-body">
		<table class="table table-bordered qp-table-list-none-action" id="generateSQLPanel" >
			<colgroup>
				<col width="100%"/>
			</colgroup>
			<tbody>
                   <tr>
                       <td align="right">
                           <textarea id="sqlDesignForm.sqlText" style="width: 100%; text-align: left; height: 400px" rows="6" readonly="readonly"></textarea>
	                   </td>
		          </tr>
			</tbody>
		</table>
		<div class="qp-div-action">
                <button type="button" class="btn qp-button-client" name="mode" value="2" onclick="sqlGenerateByPattern();">Generate Sql</button>
         </div>
	</div>
</div>