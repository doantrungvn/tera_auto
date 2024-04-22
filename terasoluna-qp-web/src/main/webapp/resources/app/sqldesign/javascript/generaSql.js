/**
 * Define key for generate sql statement
 */
var KEYS = {
	SELECT : " SELECT ",
	INSERT : " INSERT INTO ",
	DELETE : " DELETE FROM ",
	UPDATE : " UPDATE ",
	FROM : " FROM ",
	WHERE : " WHERE ",
	ORDER : " ORDER BY ",
	ORDER_ACS : " ASC ",
	ORDER_DESC : " DESC ",
	VALUES : " VALUES ",
	SET : " SET ",
	HAVING : " HAVING ",
	GROUP_BY : " GROUP BY ",
	AND : " AND ", 
	DISTINCT : "DISTINCT " ,
	AS : " AS ",
	CREATE_VIEW : " CREATE VIEW "
}

var INLINE_CHAR = {
	LF_TAB : "\n\t",
	LF : "\n",
	TAB : "\t",
	NBSP : " ",
	OPEN_QOUTA : "(",
	CLOSE_QOUTA : ")"
}

var OPERATOR_CODE =  [];
OPERATOR_CODE[0] = " = ";
OPERATOR_CODE[1] = " < ";
OPERATOR_CODE[2] = " <= ";
OPERATOR_CODE[3] = " > ";
OPERATOR_CODE[4] = " >= ";
OPERATOR_CODE[5] = " <> ";
OPERATOR_CODE[6] = " LIKE ";
OPERATOR_CODE[7] = " BETWEEN ";
OPERATOR_CODE[8] = " IS NULL ";
OPERATOR_CODE[9] = " IS NOT NULL ";

var JOINTYPE_TEXT = [];
JOINTYPE_TEXT[1] = "INNER JOIN ";
JOINTYPE_TEXT[2] = "LEFT JOIN ";
JOINTYPE_TEXT[3] = "RIGHT JOIN ";
JOINTYPE_TEXT[4] = "FULL JOIN ";
JOINTYPE_TEXT[5] = "CROSS JOIN ";

var ISEXIS_FUNC = false;
var GROUP_BY = "";
var HAVING = "";

/**
 * 
 * @param select
 */
function sqlGenerateByPattern(isSilent) {
	
	var errMsg = "";
	$('#generateSQLPanel').find('textarea[id="sqlDesignForm.sqlText"]').val('');
	var value = $($('select[id="sqlDesignForm.sqlPattern"]')).val();
	var sqlText = "";
	
	switch(value) {

	case "0":
		/* SELECT statement*/
		errMsg += $.qp.common.validation.validateRequiredFromForm();
		errMsg += $.qp.common.validation.validateMappingDataTypeFromForm();
		errMsg += $.qp.common.validation.validateSelectForm();
		errMsg += $.qp.common.validation.validateWhereRequire();
		errMsg += $.qp.common.validation.validateMappingDataTypeWhere();
		errMsg += $.qp.common.validation.validateGroup();
		errMsg += $.qp.common.validation.validateRequireOrderForm();
		
		if(errMsg != "") {
			$.qp.showAlertModal(errMsg);
			return;
		}
		sqlText = buildSelect();
		break;
	case "1":
		/* INSERT statement */
		errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-insert-update');
		errMsg += $.qp.common.validation.validateRequireValueForm();
		errMsg += $.qp.common.validation.validateMappingValueForm();
		if(errMsg != "") {
			$.qp.showAlertModal(errMsg);
			return;
		}
		sqlText = buildInsert();
		break;
	case "2":
		/* UPDATE statement */
		errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-insert-update');
		errMsg += $.qp.common.validation.validateRequireValueForm();
		errMsg += $.qp.common.validation.validateMappingValueForm();
		errMsg += $.qp.common.validation.validateWhereRequire('#tab-sql-design-insert-update');
		errMsg += $.qp.common.validation.validateMappingDataTypeWhere('#tab-sql-design-insert-update');
		errMsg += $.qp.common.validation.validateGroup();
		
		if(errMsg != "") {
			$.qp.showAlertModal(errMsg);
			return;
		}
		sqlText = buildUpdate();
		break;
	case "3":
		/* DELETE statement */
		errMsg += $.qp.common.validation.validateRequireTableForm('#tab-sql-design-delete');
		errMsg += $.qp.common.validation.validateWhereRequire('#tab-sql-design-delete');
		errMsg += $.qp.common.validation.validateMappingDataTypeWhere('#tab-sql-design-delete');
		errMsg += $.qp.common.validation.validateGroup();
		
		if(errMsg != "") {
			$.qp.showAlertModal(errMsg);
			return;
		}
		sqlText = buildDelete();
		break;
	default : 
		// For autocomplete design
		errMsg += $.qp.common.validation.validateRequiredFromForm();
		errMsg += $.qp.common.validation.validateMappingDataTypeFromForm();
		errMsg += $.qp.common.validation.validateSelectForm();
		errMsg += $.qp.common.validation.validateWhereRequire();
		errMsg += $.qp.common.validation.validateMappingDataTypeWhere();
		errMsg += $.qp.common.validation.validateGroup();
		errMsg += $.qp.common.validation.validateRequireOrderForm();
	
		if(errMsg != "") {
			$.qp.showAlertModal(errMsg);
			return;
		}
		sqlText = buildSelect();
		break;
	}
	if(!isSilent){
		if($('div[id="qp-viewdesign"]') != undefined && $('div[id="qp-viewdesign"]').length > 0) {
			sqlText = KEYS.CREATE_VIEW + $('input[name="sqlDesignForm.sqlDesignCode"]').val() + KEYS.AS + INLINE_CHAR.LF + sqlText;
		} 
		
		$('#generateSQLPanel').find('textarea[id="sqlDesignForm.sqlText"]').val(sqlText);
	}else {
		$('#generateSQLPanel').find('textarea[id="sqlDesignForm.sqlText"]').append($("<input type='hidden' name='sqlDesignForm.sqlText'>").val(sqlText));
	}
}

/**
 * 
 * @returns {String}
 */
function buildSelect() {
	
	var arrTblDetails = getTableDesignDetailsSelect();
	
	if(arrTblDetails.length > 0) {
		var mapTbl = arrTblDetails[0];
		var mapClm = arrTblDetails[1];
		var mapTblAlias = arrTblDetails[2];
		var mapClmDataType = arrTblDetails[3];

		var selectStmt;
		var selectPart = getSelectPart(mapTblAlias, mapClm);
		var fromPart = getFromPart(mapTbl, mapClm, mapTblAlias);
		var wherePart = getWherePart(mapTblAlias, mapClm);
		var orderByPart = getOderByPart(mapTblAlias, mapClm);
		
		if(selectPart == "" || fromPart == "") {
			selectStmt = "";
		} else if (selectPart != "" && fromPart != "") {
			
			selectStmt = selectPart + fromPart;
			
			if (wherePart == "" && HAVING != "" && orderByPart == "") {
				selectStmt += GROUP_BY + HAVING;
			} else if(wherePart == "" && HAVING == "" && orderByPart != "") {
				if (ISEXIS_FUNC) {
					selectStmt += GROUP_BY + orderByPart;
				} else {
					selectStmt += orderByPart;
				}
				
			} else if (wherePart == "" && HAVING != "" && orderByPart != "") {
				selectStmt += GROUP_BY + HAVING + orderByPart;
			} else if(wherePart != "" && HAVING == "" && orderByPart == "") {
				if (ISEXIS_FUNC){
					selectStmt += wherePart + GROUP_BY;
				}else {
					selectStmt += wherePart;
				}
			} else if (wherePart != "" && HAVING != "" && orderByPart == "") {
				selectStmt += wherePart + GROUP_BY + HAVING;
			} else if(wherePart != "" && HAVING == "" && orderByPart != "") {
				if (ISEXIS_FUNC) {
					selectStmt += wherePart + GROUP_BY + orderByPart;
				} else {
					selectStmt += wherePart + orderByPart;
				}
			} else if (wherePart != "" && HAVING != "" && orderByPart != "") {
				selectStmt += wherePart + GROUP_BY + HAVING + orderByPart;
			}
		}

		// reset data
		GROUP_BY = HAVING ="";
		ISEXIS_FUNC = false;
		
		return selectStmt;
	} else {
		return "";
	}
}

/**
 * Process for SELECT part
 * 
 * @param mapTbl
 * @param mapClm
 * @returns {String}
 */
function getSelectPart(mapTblAlias, mapClm) {
	
	var $Select = $('#selectForm').find('>tbody>tr').find('td:first>input:checkbox:checked').closest('tr');
	var select = "";
	var selectFunc = [];
	var selectNoneFunc = [];
	var groupBy = "";
	
	if ($Select != undefined && $Select.length > 0) {
		var countFunc = 0;
		var countNonFunc = 0;
		$Select.each(function(i) {
			var val = $(this).find('td:last>select[name$=".functionCode"]').val();
			if(val != undefined && val.length > 0){
				selectFunc[countFunc] = this;
				countFunc++;
			} else {
				selectNoneFunc[countNonFunc] = this;
				countNonFunc++
			}
		});
	}
	
	// Exist for select group in cloumn select
	if(selectFunc.length > 0) {

		ISEXIS_FUNC = true;
		
		$Select.each(function(i) {
			if(i > 0) {
				select += ", " + INLINE_CHAR.LF_TAB;
			}
			var merge = mapTblAlias[$.trim($(this).find('input[name$=".tableId"]').val())] +"."+ mapClm[$.trim($(this).find('input[name$=".columnId"]').val())];
			
			var functionId = $(this).find('select[name$=".functionCode"]').val();
			var functionText = $(this).find('select option:selected').text();
			
			if(functionId == "") {
				select += merge;
			} else {
				select += functionText + "("+merge+") AS "+ mapClm[$.trim($(this).find('input[name$=".columnId"]').val())];
			}
		});
		
		$(selectNoneFunc).each(function(i){
			if(i > 0){
				groupBy += ", " + INLINE_CHAR.LF_TAB;
			}
			
			var merge = mapTblAlias[$.trim($(this).find('input[name$=".tableId"]').val())] +"."+ mapClm[$.trim($(this).find('input[name$=".columnId"]').val())];
			groupBy += merge;
		});
		
		if(groupBy != "") GROUP_BY = INLINE_CHAR.LF + KEYS.GROUP_BY + INLINE_CHAR.LF_TAB + groupBy;
	} else {
		// In the case of none choice group by
		$Select.each(function(i) {
			if(i > 0) {
				select += ", " + INLINE_CHAR.LF_TAB;
				groupBy += ", " + INLINE_CHAR.LF_TAB;
			}

			var merge = mapTblAlias[$.trim($(this).find('input[name$=".tableId"]').val())] +"."+ mapClm[$.trim($(this).find('input[name$=".columnId"]').val())];
			select += merge;
			groupBy += merge;
		});

		if(groupBy != "") GROUP_BY = INLINE_CHAR.LF + KEYS.GROUP_BY + INLINE_CHAR.LF_TAB + groupBy;
	}

	var distinct = "";
	if($('input[name*="sqlDesignForm.omitOverlap"]').is(':checked')) distinct = KEYS.DISTINCT + INLINE_CHAR.LF_TAB
	
	var result = KEYS.SELECT + INLINE_CHAR.LF_TAB + distinct + select;
	return result;
}

/**
 * 
 * @param mapTbl
 * @param mapClm
 * @returns {String}
 */
function getFromPart(mapTbl, mapClm, mapTblAlias) {
	
	var fromSql = "";
	var tableIdFirst = $.trim($('#fromForm').find('input[name$=".tableId"]:first').val());
	
	
	if(tableIdFirst != undefined && tableIdFirst.length > 0) {
		fromSql += mapTbl[tableIdFirst] + " " + mapTblAlias[tableIdFirst];
	} else {
		return "";
	}
	
	var joinMergerOfFrom = "";
	$('#fromForm').find('tbody>tr:gt(0)').each(function(idx) {
		var $pos = $(this).find('table:first tr');
		
		var tableCode;
		var tabbleAlias;
		if($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != "" 
			&& $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val() != undefined) {
			tableCode = mapTbl[$.trim($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val())];
			tableAlias = mapTblAlias[$.trim($pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val())];

			var $posJoinCond = $pos.find('>td:eq(2)>div');

			if($posJoinCond.find('div:first input:radio[name$=".joinType"]').is(":checked")) {
				var joinText = JOINTYPE_TEXT[$posJoinCond.find('div:first input:radio[name$=".joinType"]:checked').val()];

				joinMergerOfFrom += INLINE_CHAR.LF_TAB + joinText + tableCode + " " + tableAlias + " ON ";
				// process ON condition between two more table
				var onCondition = "";
				$posJoinCond.find('table>tbody>tr').each(function(i){

					var megerLeft = mapTblAlias[$.trim($(this).find('input:hidden[name$=".tableId"]').val())] + "." 
						+ mapClm[$.trim($(this).find('input:hidden[name$=".columnId"]').val())];

					var operatorCode = $(this).find('select[name$=".operatorCode"]').val();
					var operatorText = "";
					if(operatorCode != undefined && operatorCode.length > 0) {
						operatorText =  OPERATOR_CODE[operatorCode];
					}
					
					var megerRight = tableAlias + "." + mapClm[$.trim($(this).find('input:hidden[name$=".joinColumnId"]').val())];
					
					if(megerLeft != "NaN" && megerRight != "NaN") {
						
						if (i > 0) {
							onCondition += KEYS.AND;
						}
						
						onCondition += megerLeft + operatorText + megerRight;
					}
				});
				
				if (onCondition != "") {
					joinMergerOfFrom += onCondition;
				}
			}
		}
	});
	
	var result = INLINE_CHAR.LF + KEYS.FROM + INLINE_CHAR.LF_TAB + fromSql + joinMergerOfFrom;
	
	return result;
}

/**
 * 
 * @param mapTblAlias
 * @param mapClm
 * @returns {String}
 */
function getWherePart(mapTblAlias, mapClm){
		
	var arrTmp = getDataBuildWhere();
	var arrWhere = arrTmp[0];
	var arrHaving = arrTmp[1];
	
	// Call function build where
	var sqlWhere = getWhereCommon(arrWhere, mapTblAlias, mapClm, KEYS.SELECT);
		
	// Building having
	buildHaving(arrHaving, mapTblAlias, mapClm);
	
	if (sqlWhere == "") return "";
	
	var result = INLINE_CHAR.LF + KEYS.WHERE + INLINE_CHAR.LF_TAB + sqlWhere;
	
	return result;
}

/**
 * 
 * @param arrWhere
 * @param mapTbl
 * @param mapClm
 * @param sqlType
 * @returns {String}
 */
function getWhereCommon(arrWhere, mapTblAlias, mapClm, sqlType){
	
	var attrName = "mark-number-is-group";
	var sqlWhere = "";
	var textGroup = "";
	var isFirstFlg = false;
	var isFirstInGrp = false;
	var logicTextMark = "";
	var countItemGrp = 0;
	var inOneGrp = "";
	
	if(arrWhere != undefined && arrWhere.length > 0) {
		for(var i = 0; i < arrWhere.length; i++) {
			
			var linefeed = INLINE_CHAR.LF_TAB;
			
			if(i == arrWhere.length-1){
				linefeed = "";
			}
			
			var cssDspl = $(arrWhere[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"]').css('display');
			
			var merger;
			if (sqlType == KEYS.SELECT) {
				merger =  mapTblAlias[$(arrWhere[i]).find('table>tbody>tr:eq(0) input:hidden[name$=".leftTableId"]').val()]
							+ "." + mapClm[$(arrWhere[i]).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val()];
			} else {
				merger = mapClm[$(arrWhere[i]).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val()];
			}
			
			var operatorCodeText =  OPERATOR_CODE[$(arrWhere[i]).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val()];
			var whereTmp = getWhereGroup($(arrWhere[i]), mapTblAlias, mapClm, sqlType);
			
			// Check item if group
			if($(arrWhere[i]).attr(attrName) != undefined) {
				
				if (inOneGrp == "") {
					inOneGrp = $(arrWhere[i]).attr(attrName);
				}
				
				// In the case in one group
				if(inOneGrp == $(arrWhere[i]).attr(attrName)){
					var logicText = "";
					if(cssDspl!= undefined && cssDspl != "none") {
						logicText = $(arrWhere[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
					}
					
					// If item is first
					if(!isFirstFlg) {
						logicText = "";
					}
					
					// If item is first in group
					if (!isFirstInGrp) {
						logicTextMark = logicText;
						logicText = "";
					}
					
					if(logicText != "") {
						textGroup += logicText + INLINE_CHAR.NBSP + merger + operatorCodeText + whereTmp+ INLINE_CHAR.NBSP;
					} else {
						textGroup += merger + operatorCodeText + whereTmp+ INLINE_CHAR.NBSP;
					}
					
					// Set flag is not item fisrt in group
					$(arrWhere[i]).removeAttr(attrName);
					isFirstInGrp = true;
					countItemGrp++;
				} else {
					
					// in the case have two more item in group
					if (countItemGrp > 1 && textGroup != "") {
						if(logicTextMark != ""){
							sqlWhere += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						} else {
							sqlWhere += INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						}
					} else if (countItemGrp <= 1 && textGroup != ""){
						if(logicTextMark != ""){
							sqlWhere += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
						} else {
							sqlWhere += textGroup + linefeed;
						}
					}
					
					inOneGrp = $(arrWhere[i]).attr(attrName);
					isFirstInGrp = false;
					countItemGrp = 0;
					textGroup = "";
					logicTextMark = "";
					
					var logicText = "";
					if(cssDspl!= undefined && cssDspl != "none") {
						logicText = $(arrWhere[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
					}
					
					// If item is first
					if(!isFirstFlg) {
						logicText = "";
					}
					
					// If item is first in group
					if (!isFirstInGrp) {
						logicTextMark = logicText;
						logicText = "";
					}
					
					if(logicText != "") {
						textGroup += logicText + " " + merger + operatorCodeText + whereTmp+ INLINE_CHAR.NBSP;
					} else {
						textGroup += merger + operatorCodeText + whereTmp+ INLINE_CHAR.NBSP;
					}
					
					// Set flag is not item fisrt in group
					$(arrWhere[i]).removeAttr(attrName);
					isFirstInGrp = true;
					countItemGrp++;
				}
				
				if(i == arrWhere.length-1) {
					// in the case have two more item in group
					if (countItemGrp > 1 && textGroup != "") {
						if(logicTextMark != ""){
							sqlWhere += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						} else {
							sqlWhere += INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						}
					} else if (countItemGrp <= 1 && textGroup != ""){
						if(logicTextMark != ""){
							sqlWhere += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
						} else {
							sqlWhere += textGroup + linefeed;
						}
					}
				}
				
			} else {
				
				// in the case have two more item in group
				if (countItemGrp > 1 && textGroup != "") {
					if(logicTextMark != ""){
						sqlWhere += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
					} else {
						sqlWhere += INLINE_CHAR.OPEN_QOUTA + textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
					}
				} else if (countItemGrp <= 1 && textGroup != ""){
					if(logicTextMark != ""){
						sqlWhere += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
					} else {
						sqlWhere += textGroup + linefeed;
					}
				}
				
				// Reset default
				isFirstInGrp = false;
				countItemGrp = 0;
				textGroup = "";
				logicTextMark = "";
				inOneGrp = "";
				
				// build text single item
				var logicText = "";
				if(cssDspl!= undefined && cssDspl != "none") {
					logicText = $(arrWhere[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
				}
				
				// If item is first
				if(!isFirstFlg) {
					logicText = "";
				}
				
				if(logicText != "") {
					sqlWhere += logicText + INLINE_CHAR.NBSP + merger + operatorCodeText + whereTmp + linefeed;
				} else {
					sqlWhere += merger + operatorCodeText + whereTmp + linefeed;
				}
			}
			
			isFirstFlg = true;
		}
	}
	
	if (sqlWhere == "") return "";
	
	return sqlWhere;
}

/**
 * 
 * @param arrObject
 * @param mapTbl
 * @param mapClm
 */
function buildHaving(arrObject, mapTblAlias, mapClm) {
	
	var arrHaving = arrObject;
	var attrName = "mark-number-is-group";
	var sqlHaving = "";
	var textGroup = "";
	var isFirstFlg = false;
	var isFirstInGrp = false;
	var logicTextMark = "";
	var countItemGrp = 0;
	var inOneGrp = "";
	
	if(arrHaving != undefined && arrHaving.length > 0) {
		for(var i = 0; i < arrHaving.length; i++) {
			
			var linefeed = INLINE_CHAR.LF_TAB;
			
			if(i == arrHaving.length-1){
				linefeed = "";
			}
			
			var cssDspl = $(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"]').css('display');
			var functionCodeText = $(arrHaving[i]).find('table>tbody>tr:eq(2) select[name$=".functionCode"] option:selected').text();
			var merger =  mapTblAlias[$(arrHaving[i]).find('table>tbody>tr:eq(0) input:hidden[name$=".leftTableId"]').val()] + "."
					+ mapClm[$(arrHaving[i]).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val()];
			
			var operatorCode = $(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val();
			var operatorCodeText = OPERATOR_CODE[$(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val()];
			var val;
			
			switch (operatorCode) {
			
			case "7":
				val1 = $(arrHaving[i]).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
				val2 = $(arrHaving[i]).find('table>tbody>tr:eq(1) input[name$=".value2"]').val();
				val = val1 + " AND " + val2;
				break;

			case "8" : break;	
			case "9" : break;
			
			default:
				val = $(arrHaving[i]).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
				break;
			}
			
			// Check is group or single item
			if($(arrHaving[i]).attr(attrName) != undefined) {
				
				if (inOneGrp == "") {
					inOneGrp = $(arrHaving[i]).attr(attrName);
				}
				
				// In the case in one group
				if(inOneGrp == $(arrHaving[i]).attr(attrName)){
					var logicText = "";
					if(cssDspl!= undefined && cssDspl != "none") {
						logicText = $(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
					}
					
					// If item is first
					if(!isFirstFlg) {
						logicText = "";
					}
					
					// If item is first in group
					if (!isFirstInGrp) {
						logicTextMark = logicText;
						logicText = "";
					}
					
					if(logicText != "") {
						textGroup += logicText + INLINE_CHAR.NBSP + functionCodeText + INLINE_CHAR.OPEN_QOUTA 
							+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + INLINE_CHAR.NBSP;
					} else {
						textGroup += functionCodeText + INLINE_CHAR.OPEN_QOUTA 
							+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + INLINE_CHAR.NBSP;
					}
					
					// Set flag is not item fisrt in group
					$(arrHaving[i]).removeAttr(attrName);
					isFirstInGrp = true;
					countItemGrp++;
				} else {
					
					// in the case have two more item in group
					if (countItemGrp > 1 && textGroup != "") {
						if(logicTextMark != ""){
							sqlHaving += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA
								+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						} else {
							sqlHaving += INLINE_CHAR.OPEN_QOUTA 
								+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						}
					} else if (countItemGrp <= 1 && textGroup != ""){
						if(logicTextMark != ""){
							sqlHaving += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
						} else {
							sqlHaving += textGroup + linefeed;
						}
					}
					
					inOneGrp = $(arrHaving[i]).attr(attrName);
					isFirstInGrp = false;
					countItemGrp = 0;
					textGroup = "";
					logicTextMark = "";
					
					var logicText = "";
					if(cssDspl!= undefined && cssDspl != "none") {
						logicText = $(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
					}
					
					// If item is first
					if(!isFirstFlg) {
						logicText = "";
					}
					
					// If item is first in group
					if (!isFirstInGrp) {
						logicTextMark = logicText;
						logicText = "";
					}
					
					if(logicText != "") {
						textGroup += logicText + INLINE_CHAR.NBSP + functionCodeText + INLINE_CHAR.OPEN_QOUTA 
							+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + INLINE_CHAR.NBSP;
					} else {
						textGroup += functionCodeText + INLINE_CHAR.OPEN_QOUTA 
							+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + INLINE_CHAR.NBSP;
					}
					
					// Set flag is not item fisrt in group
					$(arrHaving[i]).removeAttr(attrName);
					isFirstInGrp = true;
					countItemGrp++;
				}
				
				if(i == arrHaving.length-1){
					// in the case have two more item in group
					if (countItemGrp > 1 && textGroup != "") {
						if(logicTextMark != ""){
							sqlHaving += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA
								+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						} else {
							sqlHaving += INLINE_CHAR.OPEN_QOUTA 
								+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
						}
					} else if (countItemGrp <= 1 && textGroup != ""){
						if(logicTextMark != ""){
							sqlHaving += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
						} else {
							sqlHaving += textGroup + linefeed;
						}
					}
				}
				
			} else {
				
				// in the case have two more item in group
				if (countItemGrp > 1 && textGroup != "") {
					if(logicTextMark != ""){
						sqlHaving += logicTextMark + INLINE_CHAR.NBSP + INLINE_CHAR.OPEN_QOUTA
							+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
					} else {
						sqlHaving += INLINE_CHAR.OPEN_QOUTA 
							+ textGroup + INLINE_CHAR.CLOSE_QOUTA + linefeed;
					}
				} else if (countItemGrp <= 1 && textGroup != ""){
					if(logicTextMark != ""){
						sqlHaving += logicTextMark + INLINE_CHAR.NBSP + textGroup + linefeed;
					} else {
						sqlHaving += textGroup + linefeed;
					}
				}
				
				// Reset default
				isFirstInGrp = false;
				countItemGrp = 0;
				textGroup = "";
				logicTextMark = "";
				inOneGrp = "";
				
				// build text single item
				var logicText = "";
				if(cssDspl!= undefined && cssDspl != "none") {
					logicText = $(arrHaving[i]).find('table>tbody>tr:eq(0) select[name$=".logicCode"] option:selected').text();
				}
				
				// If item is first
				if(!isFirstFlg) {
					logicText = "";
				}
				
				if(logicText != "") {
					sqlHaving += logicText + INLINE_CHAR.NBSP + functionCodeText + INLINE_CHAR.OPEN_QOUTA 
						+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + linefeed;
				} else {
					sqlHaving += functionCodeText + INLINE_CHAR.OPEN_QOUTA 
						+ merger + INLINE_CHAR.CLOSE_QOUTA + operatorCodeText + val + linefeed;
				}
			}
			
			isFirstFlg = true;
		}
	}
	
	if (sqlHaving != "") HAVING = INLINE_CHAR.LF + KEYS.HAVING + INLINE_CHAR.LF_TAB + sqlHaving;
}

/**
 * 
 * @param item
 * @param mapTblAlias
 * @param mapClm
 * @returns {String}
 */
function getWhereGroup(item, mapTblAlias, mapClm, sqlType) {
	var val  = $(item).find('table>tbody>tr:eq(1) select[name$=".conditionType"]').val();
	var clmId = $(item).find('table>tbody>tr:eq(1) input:hidden[name$=".leftColumnId"]').val();
	var mapColumID = $.qp.sqlbuilder.reloadMapColumnId();
	var whereGroup ="";
	
	var dataType = mapColumID[clmId];
	
	switch (val) {
	case "0":
		
		var operatorCode = $(item).find('table>tbody>tr:eq(0) select[name$=".operatorCode"]').val();
		
		switch (operatorCode) {
		
		case "0":
		case "5":
		case "6":

			var valueTmp = $(item).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
			
			// If text or char
			if(dataType == "1" || dataType == "2" || dataType == "3") valueTmp = "'"+valueTmp+ "'";
			
			whereGroup += valueTmp;
			
			break;

		case "7":
			var val1 = $(item).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
			var val2 = $(item).find('table>tbody>tr:eq(1) input[name$=".value2"]').val();
			whereGroup += val1 + " AND " + val2;
			break;
		
		case "8" : break;
		case "9" : break;
			
		default:
			whereGroup += $(item).find('table>tbody>tr:eq(1) input[name$=".value"]').val();
			break;
		}
		
		break;
	case "1":
		
		var $tdPos = $(item).find('table>tbody>tr:eq(1) select[name$=".conditionType"]').closest('td');
		var clmId  = $tdPos.next().find('input:hidden[name$=".rightColumnId"]').val();
		var tblId = $(item).find('table>tbody>tr:eq(0) input:hidden[name$=".rightTableId"]').val();
		
		var merger;
		if(sqlType = KEYS.SELECT) {
			merger = mapTblAlias[$.trim(tblId)] + "." + mapClm[$.trim(clmId)];
		} else {
			merger = mapClm[$.trim(clmId)];
		}
		
		whereGroup += merger;
		break;
	
	case "2":
		
		whereGroup += "#{" + $(item).find('table>tbody>tr:eq(1) select[name$=".arg"] option:selected').text() + "}";
		
		break;
		
	default:
		break;
	}
	
	return whereGroup;
}

function getOderByPart(mapTblAlias, mapClm) {
	
	var oderSql = "";
	var count = 0;
	
	var $arrOderBy =  $('#orderByForm').find('tbody>tr input:hidden[name$=".tableColumn"]');
	if ($arrOderBy != undefined && $arrOderBy.length > 0){
		var $Select = $('#selectForm').find('>tbody>tr');
		$Select.each(function(idx){
			var rowNum = idx + 1;
			var $tr = $(this);
			
			$arrOderBy.each(function(){
				
				if(rowNum == $(this).val()){
					
					if(count > 0){
						oderSql += ", " + INLINE_CHAR.LF_TAB;
					}
					
					var orderDirect = $(this).closest('tr').find('select[name$=".orderType"]').val();
					if (orderDirect == "0"){
						orderDirect = KEYS.ORDER_ACS;
					} else if (orderDirect == "1") {
						orderDirect = KEYS.ORDER_DESC;
					} else {
						orderDirect = "";
					}

					var tableCd = $tr.find('input:hidden[name$=".tableId"]').val();
					var clmCd = $tr.find('input:hidden[name$=".columnId"]').val();
					var meger = mapTblAlias[tableCd] + "." + mapClm[clmCd];
					var functionText = "";
					if($tr.find('select[name$=".functionCode"]').val() != undefined 
							&& $tr.find('select[name$=".functionCode"]').val().length > 0) {
						functionText = $tr.find('select[name$=".functionCode"] option:selected').text();
						
						oderSql += functionText + "(" + meger +")" + orderDirect;
					} else {
						oderSql += meger + " " + orderDirect;
					}

					count++;
					
					return false;
				}
			});
		});
	} else {
		return "";
	}
	
	var result = INLINE_CHAR.LF + KEYS.ORDER + INLINE_CHAR.LF_TAB + oderSql;
	return result;
}

function getTableDesignDetailsSelect() {
	var arrRequestData = [];
	var param = "";
	param = $('#fromForm').find('input[name$=".tableId"]:first').val();
	$('#fromForm').find('>tbody>tr:gt(0)').each(function(index) {
		var $pos = $(this).find('table:first tr');
		if($pos.find('>td:first>div[class="input-group pull-left"]>input:hidden[name$=".joinTableId"]').val().length > 0) {
			param = param + ":" + $pos.find('>td:first>div:first>input:hidden[name$=".joinTableId"]').val();
		}
	});
	
	if (param != "") return callAjax(param, projectId);
	
	return arrRequestData;
}

function callAjax(param, projectId){
	// Get table design from ajax
	var url = CONTEXT_PATH + "/sqldesign/getListTableDesignDetails?tblDesignId="+param+"&projectId="+projectId+"&r="+Math.random();
	var arrRequestData = $.qp.getData(url);

	return arrRequestData;
}

function buildInsert() {
	
	var arrTblDetails = getTableDesignDetailsInsert('#tab-sql-design-insert-update');
	
	if (arrTblDetails.length > 0) {
		var mapTbl = arrTblDetails[0];	
		var mapClm = arrTblDetails[1];
		
		var tableCode = mapTbl[$('#tab-sql-design-insert-update #intoForm').find('input[name="intoForm.tableId"]').val()];
		
		var fields = "";
		var values = "";
		
		$('#valueForm').find('>tbody>tr').each(function(i){
			if(i > 0){
				fields += ", "  + INLINE_CHAR.LF_TAB;
				values += ", " + INLINE_CHAR.LF_TAB;
			}

			fields += mapClm[$(this).find('input[name$=".columnId"]').val()];
			var valueType = $(this).find("select[name$='.valueType']").val();
			if(valueType == 0){
				values += "#{"+ $(this).find('select[name$=".parameter"] option:selected').text() + "}";
			}else{
				values += "'"+ $(this).find('input[name$=".parameter"]').val() + "'";
			}
		});
		
		var result = KEYS.INSERT + INLINE_CHAR.LF_TAB + tableCode 
				+ INLINE_CHAR.LF_TAB + "(" + INLINE_CHAR.LF_TAB + fields + INLINE_CHAR.LF_TAB + ")" + INLINE_CHAR.LF
				+ KEYS.VALUES + INLINE_CHAR.LF_TAB + "("+ INLINE_CHAR.LF_TAB + values + INLINE_CHAR.LF_TAB + ");";
		return result;
	} else {
		return "";
	}
}

function getTableDesignDetailsInsert(tabId){
	var arrResquest = [];
	var param = $(tabId+' #intoForm').find('input[name="intoForm.tableId"]').val();
	
	if (param != undefined && param.length > 0) return callAjax(param, projectId);
	
	return arrResquest;
}

/**
 * 
 * @returns {String}
 */
function buildUpdate() {
	
	var arrTblDetails = getTableDesignDetailsInsert("#tab-sql-design-insert-update");
	
	if(arrTblDetails.length > 0){
		var mapTbl = arrTblDetails[0];	
		var mapClm = arrTblDetails[1];
		var mapTblAlias = arrTblDetails[2];
		
		var tableCode = mapTbl[$('#tab-sql-design-insert-update #intoForm').find('input[name="intoForm.tableId"]').val()];
		var set = "";
		var fields = "";
		
		$('#valueForm').find('>tbody>tr').each(function(i){
			if(i > 0){
				set += ", " + INLINE_CHAR.LF_TAB;;
			}
			
			set += mapClm[$(this).find('input[name$=".columnId"]').val()] + "=" 
				+ getValueForUpdate(this);
		});

		var whereSql = buildWhereUpdtAndDel('#tab-sql-design-insert-update', mapTblAlias, mapClm);
		
		var result = KEYS.UPDATE + INLINE_CHAR.LF_TAB + tableCode + INLINE_CHAR.LF 
				+ KEYS.SET + INLINE_CHAR.LF_TAB + set + INLINE_CHAR.LF 
				+ KEYS.WHERE + INLINE_CHAR.LF_TAB + whereSql; 
		
		return result;
	} else {
		return "";
	}
}

function getValueForUpdate(row){
	var valueType = $(row).find("select[name$='.valueType']").val();
	if(valueType == 0){
		return "#{"+ $(row).find('select[name$=".parameter"] option:selected').text() + "}";
	}
	if(valueType == 1){
		return "'"+ $(row).find('input[name$=".parameter"]').val() + "'";
	}
	return  $(row).find('input[name$=".parameterAutocomplete"]').val();
	
}

/**
 * 
 * @returns {String}
 */
function buildDelete() {
	
	var arrTblDetails = getTableDesignDetailsInsert("#tab-sql-design-delete");
	
	if (arrTblDetails.length > 0) {
		var mapTbl = arrTblDetails[0];	
		var mapClm = arrTblDetails[1];
		var mapTblAlias = arrTblDetails[2];
		
		var tableCode = mapTbl[$('#tab-sql-design-delete #intoForm').find('input[name="intoForm.tableId"]').val()];
		var whereSql = buildWhereUpdtAndDel('#tab-sql-design-delete', mapTblAlias, mapClm);
		var result = KEYS.DELETE + INLINE_CHAR.LF_TAB + tableCode + INLINE_CHAR.LF + KEYS.WHERE + INLINE_CHAR.LF_TAB + whereSql 
		
		return result;
	} else {
		return "";
	}	
}

/**
 * 
 * @param tabName
 * @param mapTbl
 * @param mapClm
 * @returns {Array}
 */
function buildWhereUpdtAndDel(tabName, mapTbl, mapClm) {
	
	var arrTmp = getDataBuildWhere(tabName);
	var arrWhere = arrTmp[0];
	var sqlWhere = getWhereCommon(arrWhere, mapTbl, mapClm);
	
	return sqlWhere;
}

function getDataBuildWhere(tabName) {
	
	var arrWhere = [];
	var arrHaving = [];
	var attrVal = 0;
	var attrName = "mark-number-is-group";
	var $Tr;
	
	if (tabName != undefined) {
		$Tr = $(tabName+' #whereForm').find('>tbody>tr');
	} else {
		$Tr = $('#whereForm').find('>tbody>tr');
	}
	
	for(var i = 0; i < $Tr.length; i++) {
		var functionCode = $Tr.eq(i).find('table>tbody>tr:eq(2) select[name$=".functionCode"]').val();
		
		if($Tr.eq(i).find('>td:eq(1)>input:checkbox').is(":checked")) {
			$Tr.eq(i).attr(attrName, attrVal);
		} else {
			attrVal++;
		}

		if (functionCode != undefined && functionCode.length > 0) {
			arrHaving.push($Tr.eq(i));
		} else {
			arrWhere.push($Tr.eq(i));
		}
	}
	
	var arrTmp = [];
	arrTmp.push(arrWhere);
	arrTmp.push(arrHaving);
	
	return arrTmp;
}

