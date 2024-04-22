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
			<th><qp:message code="sc.sys.0004" /></th>
			<th><qp:message code="sc.sqldesign.0023" /></th>
			<th><qp:message code="sc.sqldesign.0031" /></th>
			<th><qp:message code="sc.sqldesign.0029" /></th>
			<th style="text-align: center"><qp:message code="sc.autocomplete.0019" /></th>
			<th style="text-align: center"><qp:message code="sc.autocomplete.0020" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td>Output 1</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output1Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output1ColumnAutocomplete:designForm.outputForm.output1Column }"/>
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
			<td>Output 2</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output2Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output2ColumnAutocomplete:designForm.outputForm.output2Column }"/>
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
			<td>Output 3</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output3Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output3ColumnAutocomplete:designForm.outputForm.output3Column }"/>
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
			<td>Output 4</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output4Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output4ColumnAutocomplete:designForm.outputForm.output4Column }"/>
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
			<td>Output 5</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output5Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output5ColumnAutocomplete:designForm.outputForm.output5Column }"/>
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
			<td>Output 6</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output6Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output6ColumnAutocomplete:designForm.outputForm.output6Column }"/>
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
			<td>Output 7</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output7Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output7ColumnAutocomplete:designForm.outputForm.output7Column }"/>
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
			<td>Output 8</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output8Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output8ColumnAutocomplete:designForm.outputForm.output8Column }"/>
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
			<td>Output 9</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output9Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output9ColumnAutocomplete:designForm.outputForm.output9Column }"/>
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
			<td>Output 10</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output10Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output10ColumnAutocomplete:designForm.outputForm.output10Column }"/>
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
			<td>Output 11</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output11Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output11ColumnAutocomplete:designForm.outputForm.output11Column }"/>
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
			<td>Output 12</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output12Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output12ColumnAutocomplete:designForm.outputForm.output12Column }"/>
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
			<td>Output 13</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output13Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output13ColumnAutocomplete:designForm.outputForm.output13Column }"/>
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
			<td>Output 14</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output14Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output14ColumnAutocomplete:designForm.outputForm.output14Column }"/>
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
			<td>Output 15</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output15Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output15ColumnAutocomplete:designForm.outputForm.output15Column }"/>
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
			<td>Output 16</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output16Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output16ColumnAutocomplete:designForm.outputForm.output16Column }"/>
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
			<td>Output 17</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output17Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output17ColumnAutocomplete:designForm.outputForm.output17Column }"/>
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
			<td>Output 18</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output18Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output18ColumnAutocomplete:designForm.outputForm.output18Column }"/>
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
			<td>Output 19</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output19Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output19ColumnAutocomplete:designForm.outputForm.output19Column }"/>
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
			<td>Output 20</td>
			<td>Text</td>
			<td>
				<input type="text" class="form-control qp-input-text" name="outputForm.output20Column" value="${designForm.sqlDesignForm.isConversion?designForm.outputForm.output20ColumnAutocomplete:designForm.outputForm.output20Column }"/>
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