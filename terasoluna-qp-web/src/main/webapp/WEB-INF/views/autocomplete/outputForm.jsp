<table style="width:30%" class="table table-borderless">
	<colgroup>
		<col width="30%" />
		<col  />
	</colgroup>
	<tbody>
		<tr>
<%-- 			<td><qp:message code='sc.sqldesign.0022'></qp:message></td> --%>
<!-- 			<td> -->
<%-- 				<form:select path="sqlDesignForm.returnType" cssClass="form-control qp-input-select pull-left"> --%>
<%-- 					<form:options items="${CL_SQL_RETURNTYPE }" /> --%>
<%-- 				</form:select> --%>
<!-- 			</td> -->
			<form:hidden path="sqlDesignForm.returnType" value="1"/>
		</tr>
	</tbody>
</table>
<table class="table table-bordered qp-table-list-none-action" id="outputForm">
	<colgroup>
		<col />
		<col />
		<col width="15%"/>
		<col width="30%"/>
		<col width="60px"/> 
		<col width="60px"/> 
	</colgroup>
	<thead>
		<tr>
			<th><qp:message code="sc.sys.0004"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0023"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0007"></qp:message></th>
			<th><qp:message code="sc.sqldesign.0029"></qp:message></th>
			<th style="text-align: center"><qp:message code="sc.autocomplete.0019"></qp:message></th>
			<th style="text-align: center"><qp:message code="sc.autocomplete.0020"></qp:message></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td><qp:message code="sc.sqldesign.0004" /> 1</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output1Column" 
								value="${designForm.outputForm.output1Column}"
								displayValue="${designForm.outputForm.output1ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output1Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="1"
									/></td>
		</tr>
		<tr>
			<td>2</td>
			<td><qp:message code="sc.sqldesign.0004" /> 2</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output2Column" 
								value="${designForm.outputForm.output2Column}"
								displayValue="${designForm.outputForm.output2ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output2Display" value="true" 
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="2"
									/></td>
		</tr>
		<tr>
			<td>3</td>
			<td><qp:message code="sc.sqldesign.0004" /> 3</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output3Column" 
								value="${designForm.outputForm.output3Column}"
								displayValue="${designForm.outputForm.output3ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output3Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="3"
									/></td>
		</tr>
		<tr>
			<td>4</td>
			<td><qp:message code="sc.sqldesign.0004" /> 4</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output4Column" 
								value="${designForm.outputForm.output4Column}"
								displayValue="${designForm.outputForm.output4ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output4Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="4"
									/></td>
		</tr>
		<tr>
			<td>5</td>
			<td><qp:message code="sc.sqldesign.0004" /> 5</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output5Column" 
								value="${designForm.outputForm.output5Column}"
								displayValue="${designForm.outputForm.output5ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output5Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="5"
									/></td>
		</tr>
		<tr>
			<td>6</td>
			<td><qp:message code="sc.sqldesign.0004" /> 6</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output6Column" 
								value="${designForm.outputForm.output6Column}"
								displayValue="${designForm.outputForm.output6ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output6Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="6"
									/></td>
		</tr>
		<tr>
			<td>7</td>
			<td><qp:message code="sc.sqldesign.0004" /> 7</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output7Column" 
								value="${designForm.outputForm.output7Column}"
								displayValue="${designForm.outputForm.output7ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output7Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="7"
									/></td>
		</tr>
		<tr>
			<td>8</td>
			<td><qp:message code="sc.sqldesign.0004" /> 8</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output8Column" 
								value="${designForm.outputForm.output8Column}"
								displayValue="${designForm.outputForm.output8ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output8Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="8"
									/></td>
		</tr>
		<tr>
			<td>9</td>
			<td><qp:message code="sc.sqldesign.0004" /> 9</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output9Column" 
								value="${designForm.outputForm.output9Column}"
								displayValue="${designForm.outputForm.output9ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output9Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="9"
									/></td>
		</tr>
		<tr>
			<td>10</td>
			<td><qp:message code="sc.sqldesign.0004" /> 10</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output10Column" 
								value="${designForm.outputForm.output10Column}"
								displayValue="${designForm.outputForm.output10ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output10Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="10"
									/></td>
		</tr>
		<tr>
			<td>11</td>
			<td><qp:message code="sc.sqldesign.0004" /> 11</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output11Column" 
								value="${designForm.outputForm.output11Column}"
								displayValue="${designForm.outputForm.output11ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output11Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="11"
									/></td>
		</tr>
		<tr>
			<td>12</td>
			<td><qp:message code="sc.sqldesign.0004" /> 12</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output12Column" 
								value="${designForm.outputForm.output12Column}"
								displayValue="${designForm.outputForm.output12ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output12Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="12"
									/></td>
		</tr>
		<tr>
			<td>13</td>
			<td><qp:message code="sc.sqldesign.0004" /> 13</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output13Column" 
								value="${designForm.outputForm.output13Column}"
								displayValue="${designForm.outputForm.output13ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output13Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="13"
									/></td>
		</tr>
		<tr>
			<td>14</td>
			<td><qp:message code="sc.sqldesign.0004" /> 14</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output14Column" 
								value="${designForm.outputForm.output14Column}"
								displayValue="${designForm.outputForm.output14ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output14Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="14"
									/></td>
		</tr>
		<tr>
			<td>15</td>
			<td><qp:message code="sc.sqldesign.0004" /> 15</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output15Column" 
								value="${designForm.outputForm.output15Column}"
								displayValue="${designForm.outputForm.output15ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output15Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="15"
									/></td>
		</tr>
		<tr>
			<td>16</td>
			<td><qp:message code="sc.sqldesign.0004" /> 16</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output16Column" 
								value="${designForm.outputForm.output16Column}"
								displayValue="${designForm.outputForm.output16ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output16Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="16"
									/></td>
		</tr>
		<tr>
			<td>17</td>
			<td><qp:message code="sc.sqldesign.0004" /> 17</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output17Column" 
								value="${designForm.outputForm.output17Column}"
								displayValue="${designForm.outputForm.output17ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output17Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="17"
									/></td>
		</tr>
		<tr>
			<td>18</td>
			<td><qp:message code="sc.sqldesign.0004" /> 18</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output18Column" 
								value="${designForm.outputForm.output18Column}"
								displayValue="${designForm.outputForm.output18ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output18Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="18"
									/></td>
		</tr>
		<tr>
			<td>19</td>
			<td><qp:message code="sc.sqldesign.0004" /> 19</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output19Column" 
								value="${designForm.outputForm.output19Column}"
								displayValue="${designForm.outputForm.output19ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output19Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="19"
									/></td>
		</tr>
		<tr>
			<td>20</td>
			<td><qp:message code="sc.sqldesign.0004" /> 20</td>
			<td>${f:h(CL_SQL_DATATYPE.get('9'))}</td>
			<td>
				<qp:autocomplete optionValue="optionValue" 
								selectSqlId="getAllTableDesignColumnAC"
								name="outputForm.output20Column" 
								value="${designForm.outputForm.output20Column}"
								displayValue="${designForm.outputForm.output20ColumnAutocomplete}"
								mustMatch="true"
								optionLabel="optionLabel"></qp:autocomplete>
			</td>
			<td><form:checkbox cssClass="qp-input-checkbox" 
								path="outputForm.output20Display" value="true"
								/></td>
			<td><form:radiobutton cssClass="radio qp-input-radio" 
									path="outputForm.submitColumn"
									value="20"
									/></td>
		</tr>
	</tbody>
</table>