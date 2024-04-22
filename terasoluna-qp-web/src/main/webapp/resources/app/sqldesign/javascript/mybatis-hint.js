// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

(function(mod) {
	if (typeof exports == "object" && typeof module == "object") // CommonJS
	mod(require("../../lib/codemirror"));
	else if (typeof define == "function" && define.amd) // AMD
	define(["../../lib/codemirror"], mod);
	else // Plain browser env
	mod(CodeMirror);
})(function(CodeMirror) {
	"use strict";
	var tables;
		var defaultTable;
		var keywords;
		var CONS = {
		QUERY_DIV: ";",
		ALIAS_KEYWORD: "AS"
		};
		var Pos = CodeMirror.Pos;

		function getKeywords(editor) {
			var mode = editor.doc.modeOption;
			if (mode === "sql") mode = "text/x-sql";
			return CodeMirror.resolveMode(mode).keywords;
		}

		function getText(item) {
			return typeof item == "string" ? item : item.text;
		}

		function getItem(list, item) {
			if (!list.slice) return list[item];
			for (var i = list.length - 1; i >= 0; i--) if (getText(list[i]) == item)
				return list[i];
		}

		function shallowClone(object) {
			var result = {};
			for (var key in object) if (object.hasOwnProperty(key))
				result[key] = object[key];
			return result;
		}

		function match(string, word) {
			var len = string.length;
			var sub = getText(word).substr(0, len);
			return string.toUpperCase() === sub.toUpperCase();
		}

		function addMatches(result, search, wordlist, formatter) {
			for (var word in wordlist) {
				if (!wordlist.hasOwnProperty(word)) continue;
				if (wordlist.slice) word = wordlist[word];
	
				if (match(search, word)) result.push(formatter(word));
			}
		}

		function cleanName(name) {
			// Get rid name from backticks(`) and preceding dot(.)
			if (name.charAt(0) == ".") {
				name = name.substr(1);
			}
			return name.replace(/`/g, "");
		}

		function insertBackticks(name) {
			var nameParts = getText(name).split(".");
			for (var i = 0; i < nameParts.length; i++)
				nameParts[i] = "`" + nameParts[i] + "`";
			var escaped = nameParts.join(".");
			if (typeof name == "string") return escaped;
			name = shallowClone(name);
			name.text = escaped;
			return name;
		}

		function nameCompletion(cur, token, result, editor) {
			// Try to complete table, colunm names and return start position of completion
			var useBacktick = false;
			var nameParts = [];
			var start = token.start;
			var cont = true;
			while (cont) {
				cont = (token.string.charAt(0) == ".");
				useBacktick = useBacktick || (token.string.charAt(0) == "`");
	
				start = token.start;
				nameParts.unshift(cleanName(token.string));
	
				token = editor.getTokenAt(Pos(cur.line, token.start));
				var spaceLast = token.string.lastIndexOf(" ")+1;
				token.string = token.string.substring(spaceLast);
				token.start = token.start + spaceLast;
				token.end = token.start + token.string.length;
				var matches = token.string.match(/^(\$?|#?\{)?\D*\}?$/);
				if(!(!matches || matches.length==0)){
					var string = matches[0].replace("$","").replace("#","").replace("{","").replace("}","");
					
					token.start = token.start + token.string.indexOf(string);
					token.end = token.start + string.length-1;
					token.string = string;
				}
				
				
				if (token.string == ".") {
					cont = true;
					token = editor.getTokenAt(Pos(cur.line, token.start));
				}
			}

		// Try to complete table names
		for (var i = 0; i < nameParts.length; i++) { 
			nameParts[i] = nameParts[i].replace('.','');
		}
		var string = nameParts.join(".");
		addMatches(result, string, tables, function(w) {
			return useBacktick ? insertBackticks(w) : w;
		});

		// Try to complete columns from defaultTable
		addMatches(result, string, defaultTable, function(w) {
			return useBacktick ? insertBackticks(w) : w;
		});

		// Try to complete columns
		string = nameParts.pop();
		var table = nameParts.join(".");

		var alias = false;
		var aliasTable = table;
		// Check if table is available. If not, find table by Alias
		if (!getItem(tables, table)) {
			var oldTable = table;
			table = findTableByAlias(table, editor);
			if (table !== oldTable) alias = true;
		}

		var columns = getItem(tables, table);
		if (columns && columns.columns)
			columns = columns.columns;

		if (columns) {
			addMatches(result, string, columns, function(w) {
			var tableInsert = table;
			if (alias == true) tableInsert = aliasTable;
			if (typeof w == "string") {
				w = tableInsert + "." + w;
			} else {
				w = shallowClone(w);
				w.text = tableInsert + "." + w.text;
			}
			return useBacktick ? insertBackticks(w) : w;
			});
		}

		return start;
		}

		function eachWord(lineText, f) {
			if (!lineText) return;
			var excepted = /[,;]/g;
			var words = lineText.split(" ");
			for (var i = 0; i < words.length; i++) {
				f(words[i]?words[i].replace(excepted, '') : '');
			}
		}

		function convertCurToNumber(cur) {
			// max characters of a line is 999,999.
			return cur.line + cur.ch / Math.pow(10, 6);
		}

		function convertNumberToCur(num) {
			return Pos(Math.floor(num), +num.toString().split('.').pop());
		}

		function findTableByAlias(alias, editor) {
			var doc = editor.doc;
			var fullQuery = doc.getValue();
			var aliasUpperCase = alias.toUpperCase();
			var previousWord = "";
			var table = "";
			var separator = [];
			var validRange = {
				start: Pos(0, 0),
				end: Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).length)
			};
	
			//add separator
			var indexOfSeparator = fullQuery.indexOf(CONS.QUERY_DIV);
			while(indexOfSeparator != -1) {
				separator.push(doc.posFromIndex(indexOfSeparator));
				indexOfSeparator = fullQuery.indexOf(CONS.QUERY_DIV, indexOfSeparator+1);
			}
			separator.unshift(Pos(0, 0));
			separator.push(Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).text.length));
	
			//find valid range
			var prevItem = 0;
			var current = convertCurToNumber(editor.getCursor());
			for (var i=0; i< separator.length; i++) {
				var _v = convertCurToNumber(separator[i]);
				if (current > prevItem && current <= _v) {
				validRange = { start: convertNumberToCur(prevItem), end: convertNumberToCur(_v) };
				break;
				}
				prevItem = _v;
			}
	
			var query = doc.getRange(validRange.start, validRange.end, false);
	
			for (var i = 0; i < query.length; i++) {
				var lineText = query[i];
				eachWord(lineText, function(word) {
				var wordUpperCase = word.toUpperCase();
				if (wordUpperCase === aliasUpperCase && getItem(tables, previousWord))
					table = previousWord;
				if (wordUpperCase !== CONS.ALIAS_KEYWORD)
					previousWord = word;
				});
				if (table) break;
			}
			return table;
		}
		
	// xml
	var Pos = CodeMirror.Pos;
	
	function completeAfter(cm, pred) {
		var cur = cm.getCursor();
		if (!pred || pred()) setTimeout(function() {
			if (!cm.state.completionActive)
			cm.showHint({completeSingle: false});
		}, 100);
		return CodeMirror.Pass;
	}

	function completeIfAfterLt(cm) {
		return completeAfter(cm, function() {
			var cur = cm.getCursor();
			return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == "<";
		});
	}

	function completeIfInTag(cm) {
		return completeAfter(cm, function() {
		var tok = cm.getTokenAt(cm.getCursor());
		if (tok.type == "string" && (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) || tok.string.length == 1)) return false;
		var inner = CodeMirror.innerMode(cm.getMode(), tok.state).state;
		return inner.tagName;
		});
		}
	
	function getHints(editor, options) {
		var result = [];
		var tags = (options && options.schemaInfo());
		var quote = (options && options.quoteChar) || '"';
		if (!tags) return;
		var cur = editor.getCursor(), token = editor.getTokenAt(cur),start, end, search;
		if (token.end > cur.ch) {
			token.end = cur.ch;
			token.string = token.string.slice(0, cur.ch - token.start);
		}
		var lineTokens = editor.getLineTokens(editor.getCursor().line);
		var tokenIndex;
		var isInAttrArea = false;
		for(tokenIndex=0;tokenIndex<lineTokens.length;tokenIndex++){
			if(lineTokens[tokenIndex].start==token.start &&
					lineTokens[tokenIndex].end==token.end &&
					lineTokens[tokenIndex].string==token.string){
				for(var i=tokenIndex;i>=0;i--){
					if(lineTokens[i].string=="<"){
						isInAttrArea = true;
						break;
					} else if(lineTokens[i].string==">") {
						break;
					}
				}
				if(isInAttrArea){
					for(var i=tokenIndex;i<lineTokens.length;i++){
						if(lineTokens[i].string==">"){
							break;
						} else if(lineTokens[i].string=="<" ) {
							isInAttrArea = false;
							break;
						}
					}
				}
				break;
			}
		}
		
		
		if (token.string.match(/^[<]|[</]$/) || isInAttrArea){
			var inner = CodeMirror.innerMode(editor.getMode(), token.state);
			var replaceToken = false, prefix;
			var tag = /\btag\b/.test(token.type) && !/>$/.test(token.string);
			var tagName = tag && /^\w/.test(token.string), tagStart;
		
			if (tagName) {
				var before = editor.getLine(cur.line).slice(Math.max(0, token.start - 2), token.start);
				var tagType = /<\/$/.test(before) ? "close" : /<$/.test(before) ? "open" : null;
				if (tagType) tagStart = token.start - (tagType == "close" ? 2 : 1);
			} else if (tag && token.string == "<") {
				tagType = "open";
			} else if (tag && token.string == "</") {
				tagType = "close";
			}
		
			if (!tag && !inner.state.tagName || tagType) {
				if (tagName)
					prefix = token.string;
				replaceToken = tagType;
				var cx = inner.state.context, curTag = cx && tags[cx.tagName];
				var childList = cx ? curTag && curTag.children : tags["!top"];
				if (childList && tagType != "close") {
					for (var i = 0; i < childList.length; ++i) if (!prefix || childList[i].lastIndexOf(prefix, 0) == 0)
					result.push("<" + childList[i]);
				} else if (tagType != "close") {
					for (var name in tags)
					if (tags.hasOwnProperty(name) && name != "!top" && name != "!attrs" && (!prefix || name.lastIndexOf(prefix, 0) == 0))
						result.push("<" + name);
				}
				if (cx && (!prefix || tagType == "close" && cx.tagName.lastIndexOf(prefix, 0) == 0))
					result.push("</" + cx.tagName + ">");
			} else {
				// Attribute completion
				var curTag = tags[inner.state.tagName], attrs = curTag && curTag.attrs;
				var globalAttrs = tags["!attrs"];
				if (!attrs && !globalAttrs) return;
				if (!attrs) {
					attrs = globalAttrs;
				} else if (globalAttrs) { // Combine tag-local and global attributes
					var set = {};
					for (var nm in globalAttrs) if (globalAttrs.hasOwnProperty(nm)) set[nm] = globalAttrs[nm];
					for (var nm in attrs) if (attrs.hasOwnProperty(nm)) set[nm] = attrs[nm];
					attrs = set;
				}
				if (token.type == "string" || token.string == "=") { // A value
					var before = editor.getRange(Pos(cur.line, Math.max(0, cur.ch - 60)),
													Pos(cur.line, token.type == "string" ? token.start : token.end));
					var atName = before.match(/([^\s\u00a0=<>\"\']+)=$/), atValues;
					if (!atName || !attrs.hasOwnProperty(atName[1]) || !(atValues = attrs[atName[1]])) return;
					if (typeof atValues == 'function') atValues = atValues.call(this, editor); // Functions can be used to supply values for autocomplete widget
					if (token.type == "string") {
					prefix = token.string;
					var n = 0;
					if (/['"]/.test(token.string.charAt(0))) {
						quote = token.string.charAt(0);
						prefix = token.string.slice(1);
						n++;
					}
					var len = token.string.length;
					if (/['"]/.test(token.string.charAt(len - 1))) {
						quote = token.string.charAt(len - 1);
						prefix = token.string.substr(n, len - 2);
					}
					replaceToken = true;
					}
					for (var i = 0; i < atValues.length; ++i) if (!prefix || atValues[i].lastIndexOf(prefix, 0) == 0)
					result.push(quote + atValues[i] + quote);
				} else { // An attribute name
					if (token.type == "attribute") {
					prefix = token.string;
					replaceToken = true;
					}
					for (var attr in attrs) if (attrs.hasOwnProperty(attr) && (!prefix || attr.lastIndexOf(prefix, 0) == 0))
					result.push(attr);
				}
			}
			return {
				list: result,
				from: replaceToken ? Pos(cur.line, tagStart == null ? token.start : tagStart) : cur,
				to: replaceToken ? Pos(cur.line, token.end) : cur
			};
		}
		else {
			tables = (options && options.tables()) || {};
			var defaultTableName = options && options.defaultTable;
			var disableKeywords = options && options.disableKeywords;
			defaultTable = defaultTableName && getItem(tables, defaultTableName);
			keywords = keywords || getKeywords(editor);

			if (defaultTableName && !defaultTable)
				defaultTable = findTableByAlias(defaultTableName, editor);

			defaultTable = defaultTable || [];

			if (defaultTable.columns)
				defaultTable = defaultTable.columns;
			if (token.string.match(/\S[.`\w@]\w*$/)) {
				var lastIndex = token.string.lastIndexOf(".");
				token.string = token.string.substring(lastIndex,token.string.length);
				token.start = token.start + lastIndex;
				token.end = token.start + token.string .length+1;
				search = token.string;
				start = token.start;
				end = token.end;
			} else {
				start = end = cur.ch;
				search = "";
			}
			if (search.charAt(0) == "." || search.charAt(0) == "`") {
				start = nameCompletion(cur, token, result, editor);
			} else {
				addMatches(result, search, tables, function(w) {return w;});
				addMatches(result, search, defaultTable, function(w) {return w;});
				if (!disableKeywords)
				addMatches(result, search, keywords, function(w) {return w.toUpperCase();});
			}
			return {list: result, from: Pos(cur.line, start), to: Pos(cur.line, end)};
		}
	}

	CodeMirror.registerHelper("hint", "mybatis", getHints);
});