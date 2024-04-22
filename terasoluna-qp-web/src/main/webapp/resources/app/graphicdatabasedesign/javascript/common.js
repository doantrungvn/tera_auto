function _(str) { /* getText */
	// if (!(str in window.LOCALE)) { return str; }
	// return window.LOCALE[str);
	switch (str) {
	case "addtable":
		return $.qp.getModuleMessage("sc.databasedesign.0001");
		break;
	case "edittable":
		return $.qp.getModuleMessage("sc.databasedesign.0002");
		break;
	case "removetable":
		return $.qp.getModuleMessage("sc.databasedesign.0003");
		break;
	case "removetables":
		return $.qp.getModuleMessage("sc.databasedesign.0004");
		break;
	case "aligntables":
		return $.qp.getModuleMessage("sc.databasedesign.0005");
		break;
	case "cleartables":
		return $.qp.getModuleMessage("sc.databasedesign.0006");
		break;
	case "confirmall":
		return $.qp.getModuleMessage("sc.databasedesign.0007");
		break;
	case "newtable":
		return $.qp.getModuleMessage("sc.databasedesign.0008");
		break;
	case "addpending":
		return $.qp.getModuleMessage("sc.databasedesign.0009");
		break;
	case "tableidlabel":
		return $.qp.getModuleMessage("sc.databasedesign.0010");
		break;
	case "tablenamelabel":
		return $.qp.getModuleMessage("sc.databasedesign.0101");
		break;
	case "tablecodelabel":
		return $.qp.getModuleMessage("sc.databasedesign.0100");
		break;
	case "tablecommentlabel":
		return $.qp.getMessage("sc.sys.0028");
		break;
	case "addrow":
		return $.qp.getModuleMessage("sc.databasedesign.0014");
		break;
	case "editrow":
		return $.qp.getModuleMessage("sc.databasedesign.0015");
		break;
	case "removerow":
		return $.qp.getModuleMessage("sc.databasedesign.0016");
		break;
	case "uprow":
		return $.qp.getModuleMessage("sc.databasedesign.0017");
		break;
	case "downrow":
		return $.qp.getModuleMessage("sc.databasedesign.0018");
		break;
	case "newrow":
		return $.qp.getModuleMessage("sc.databasedesign.0019");
		break;
	case "foreigncreate":
		return $.qp.getModuleMessage("sc.databasedesign.0020");
		break;
	case "foreignpending":
		return $.qp.getModuleMessage("sc.databasedesign.0021");
		break;
	case "foreignconnect":
		return $.qp.getModuleMessage("sc.databasedesign.0022");
		break;
	case "foreignconnectpending":
		return $.qp.getModuleMessage("sc.databasedesign.0023");
		break;
	case "foreigndisconnect":
		return $.qp.getModuleMessage("sc.databasedesign.0024");
		break;
	case "confirmtable":
		return $.qp.getModuleMessage("sc.databasedesign.0025");
		break;
	case "confirmrow":
		return $.qp.getModuleMessage("sc.databasedesign.0026");
		break;
	case "name":
		return $.qp.getModuleMessage("sc.databasedesign.0119");
		break;
	case "code":
		return $.qp.getModuleMessage("sc.databasedesign.0116");
		break;
	case "columnid":
		return $.qp.getModuleMessage("sc.databasedesign.0029");
		break;
	case "type":
		return $.qp.getModuleMessage("sc.tabledesign.0007");
		break;
	case "size":
		return $.qp.getModuleMessage("sc.databasedesign.0031");
		break;
	case "precision":
		return $.qp.getModuleMessage("sc.databasedesign.0032");
		break;
	case "def":
		return $.qp.getModuleMessage("sc.databasedesign.0033");
		break;
	case "notnull":
		return $.qp.getModuleMessage("sc.databasedesign.0034");
		break;
	case "null":
		return $.qp.getModuleMessage("sc.tabledesign.0017");
		break;
	case "ai":
		return $.qp.getModuleMessage("sc.databasedesign.0036");
		break;
	case "comment":
		return $.qp.getMessage("sc.sys.0028");
		break;
	case "commenttext":
		return $.qp.getModuleMessage("sc.databasedesign.0038");
		break;
	case "windowok":
		return $.qp.getMessage("sc.sys.0054");
		break;
	case "windowcancel":
		return $.qp.getMessage("sc.sys.0023");
		break;
	case "throbber":
		return $.qp.getModuleMessage("sc.databasedesign.0041");
		break;
	case "options":
		return $.qp.getModuleMessage("sc.databasedesign.0042");
		break;
	case "language":
		return $.qp.getModuleMessage("sc.databasedesign.0043");
		break;
	case "db":
		return $.qp.getModuleMessage("sc.databasedesign.0044");
		break;
	case "optionsnotice":
		return $.qp.getModuleMessage("sc.databasedesign.0045");
		break;
	case "snap":
		return $.qp.getModuleMessage("sc.databasedesign.0046");
		break;
	case "optionsnapnotice":
		return $.qp.getModuleMessage("sc.databasedesign.0047");
		break;
	case "pattern":
		return $.qp.getModuleMessage("sc.databasedesign.0048");
		break;
	case "optionpatternnotice":
		return $.qp.getModuleMessage("sc.databasedesign.0049");
		break;
	case "hide":
		return $.qp.getModuleMessage("sc.databasedesign.0050");
		break;
	case "vector":
		return $.qp.getModuleMessage("sc.databasedesign.0051");
		break;
	case "showsize":
		return $.qp.getModuleMessage("sc.databasedesign.0052");
		break;
	case "showtype":
		return $.qp.getModuleMessage("sc.databasedesign.0053");
		break;
	case "tablekeys":
		return $.qp.getModuleMessage("sc.databasedesign.0054");
		break;
	case "keyslistlabel":
		return $.qp.getModuleMessage("sc.databasedesign.0055");
		break;
	case "keytypelabel":
		return $.qp.getModuleMessage("sc.databasedesign.0056");
		break;
	case "keynamelabel":
		return $.qp.getModuleMessage("sc.databasedesign.0057");
		break;
	case "keyfieldslabel":
		return $.qp.getModuleMessage("sc.databasedesign.0058");
		break;
	case "keyavaillabel":
		return $.qp.getModuleMessage("sc.databasedesign.0059");
		break;
	case "keyedit":
		return $.qp.getModuleMessage("sc.databasedesign.0060");
		break;
	case "keyadd":
		return $.qp.getModuleMessage("sc.databasedesign.0061");
		break;
	case "keyremove":
		return $.qp.getModuleMessage("sc.databasedesign.0062");
		break;
	case "saveload":
		return $.qp.getModuleMessage("sc.databasedesign.0063");
		break;
	case "empty":
		return $.qp.getModuleMessage("sc.databasedesign.0064");
		break;
	case "client":
		return $.qp.getModuleMessage("sc.databasedesign.0065");
		break;
	case "server":
		return $.qp.getModuleMessage("sc.databasedesign.0066");
		break;
	case "output":
		return $.qp.getModuleMessage("sc.databasedesign.0067");
		break;
	case "clientsave":
		return $.qp.getModuleMessage("sc.databasedesign.0068");
		break;
	case "clientload":
		return $.qp.getModuleMessage("sc.databasedesign.0069");
		break;
	case "clientlocalsave":
		return $.qp.getModuleMessage("sc.databasedesign.0070");
		break;
	case "clientlocalload":
		return $.qp.getModuleMessage("sc.databasedesign.0071");
		break;
	case "clientsql":
		return $.qp.getModuleMessage("sc.databasedesign.0072");
		break;
	case "backendlabel":
		return $.qp.getModuleMessage("sc.databasedesign.0073");
		break;
	case "serversave":
		return $.qp.getModuleMessage("sc.databasedesign.0074");
		break;
	case "quicksave":
		return $.qp.getModuleMessage("sc.databasedesign.0075");
		break;
	case "serverload":
		return $.qp.getModuleMessage("sc.databasedesign.0076");
		break;
	case "serverlist":
		return $.qp.getModuleMessage("sc.databasedesign.0077");
		break;
	case "serverimport":
		return $.qp.getModuleMessage("sc.databasedesign.0078");
		break;
	case "serverloadprompt":
		return $.qp.getModuleMessage("sc.databasedesign.0079");
		break;
	case "serversaveprompt":
		return $.qp.getModuleMessage("sc.databasedesign.0080");
		break;
	case "serverimportprompt":
		return $.qp.getModuleMessage("sc.databasedesign.0081");
		break;
	case "httpresponse":
		return $.qp.getModuleMessage("sc.databasedesign.0082");
		break;
	case "http201":
		return $.qp.getModuleMessage("sc.databasedesign.0083");
		break;
	case "http404":
		return $.qp.getModuleMessage("err.databasedesign.0084");
		break;
	case "http500":
		return $.qp.getModuleMessage("err.databasedesign.0085");
		break;
	case "http501":
		return $.qp.getModuleMessage("err.databasedesign.0086");
		break;
	case "http503":
		return $.qp.getModuleMessage("err.databasedesign.0087");
		break;
	case "xmlerror":
		return $.qp.getModuleMessage("err.databasedesign.0088");
		break;
	case "docs":
		return $.qp.getModuleMessage("sc.databasedesign.0089");
		break;
	}
}

function validateExistColumn(name, code, rows) {
	var countName = 0;
	var countCode = 0;
	for (var i=0;i<rows.length;i++) {
		var row = rows[i];

		if (name.equalsIgnoreCase(row.data.title)) {
			countName ++;
		}

		if (code.equalsIgnoreCase(row.data.code)) {
			countCode ++;
		}
	}

	var str = '';

	if (countName > 1) {
		str = $.qp.getMessage("err.sys.0036").replace("{0}", dbMsgSource["sc.databasedesign.0119"]) + "\r\n";
	}

	if (countCode > 1) {
		str += $.qp.getMessage("err.sys.0036").replace("{0}", dbMsgSource["sc.databasedesign.0116"]) + "\r\n";
	}
	return str;
}