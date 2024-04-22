(function(mod) {
	if (typeof exports == "object" && typeof module == "object") // CommonJS
	mod(require("../../lib/codemirror"));
	else if (typeof define == "function" && define.amd) // AMD
	define(["../../lib/codemirror"], mod);
	else // Plain browser env
	mod(CodeMirror);
})(function(CodeMirror) {
"use strict";

CodeMirror.defineMode("mybatis", function(config, parserConfig) {
	var indentUnit = config.indentUnit;
	var multilineTagIndentFactor = parserConfig.multilineTagIndentFactor || 1;
	var multilineTagIndentPastTag = parserConfig.multilineTagIndentPastTag;
	if (multilineTagIndentPastTag == null) multilineTagIndentPastTag = true;

	var Kludges = {
	autoSelfClosers: {},
	implicitlyClosed: {},
	contextGrabbers: {},
	doNotIndent: {},
	allowUnquoted: false,
	allowMissing: false,
	caseFold: false
	};
	var alignCDATA = parserConfig.alignCDATA;

	// Return variables for tokenizers
	var type, setStyle;

	function inText(stream, state) {
	function chain(parser) {
		state.tokenize = parser;
		return parser(stream, state);
	}

	var ch = stream.next();
	if (ch == "<") {
		if (stream.eat("!")) {
		if (stream.eat("[")) {
			if (stream.match("CDATA[")) return chain(inBlock("atom", "]]>"));
			else return null;
		} else if (stream.match("--")) {
			return chain(inBlock("comment", "-->"));
		} else if (stream.match("DOCTYPE", true, true)) {
			stream.eatWhile(/[\w\._\-]/);
			return chain(doctype(1));
		} else {
			return null;
		}
		} else if (stream.eat("?")) {
		stream.eatWhile(/[\w\._\-]/);
		state.tokenize = inBlock("meta", "?>");
		return "meta";
		} else {
		type = stream.eat("/") ? "closeTag" : "openTag";
		state.tokenize = inTag;
		return "tag bracket";
		}
	} else if (ch == "&") {
		var ok;
		if (stream.eat("#")) {
		if (stream.eat("x")) {
			ok = stream.eatWhile(/[a-fA-F\d]/) && stream.eat(";");
		} else {
			ok = stream.eatWhile(/[\d]/) && stream.eat(";");
		}
		} else {
		ok = stream.eatWhile(/[\w\.\-:]/) && stream.eat(";");
		}
		return ok ? "atom" : "error";
	} else {
		stream.eatWhile(/[^&<]/);
		return null;
	}
	}
	inText.isInText = true;

	function inTag(stream, state) {
	var ch = stream.next();
	if (ch == ">" || (ch == "/" && stream.eat(">"))) {
		state.tokenize = inText;
		type = ch == ">" ? "endTag" : "selfcloseTag";
		return "tag bracket";
	} else if (ch == "=") {
		type = "equals";
		return null;
	} else if (ch == "<") {
		state.tokenize = inText;
		state.state = baseState;
		state.tagName = state.tagStart = null;
		var next = state.tokenize(stream, state);
		return next ? next + " tag error" : "tag error";
	} else if (/[\'\"]/.test(ch)) {
		state.tokenize = inAttribute(ch);
		state.stringStartCol = stream.column();
		return state.tokenize(stream, state);
	} else {
		stream.match(/^[^\s\u00a0=<>\"\']*[^\s\u00a0=<>\"\'\/]/);
		return "word";
	}
	}

	function inAttribute(quote) {
	var closure = function(stream, state) {
		while (!stream.eol()) {
		if (stream.next() == quote) {
			state.tokenize = inTag;
			break;
		}
		}
		return "string";
	};
	closure.isInAttribute = true;
	return closure;
	}

	function inBlock(style, terminator) {
	return function(stream, state) {
		while (!stream.eol()) {
		if (stream.match(terminator)) {
			state.tokenize = inText;
			break;
		}
		stream.next();
		}
		return style;
	};
	}
	function doctype(depth) {
	return function(stream, state) {
		var ch;
		while ((ch = stream.next()) != null) {
		if (ch == "<") {
			state.tokenize = doctype(depth + 1);
			return state.tokenize(stream, state);
		} else if (ch == ">") {
			if (depth == 1) {
			state.tokenize = inText;
			break;
			} else {
			state.tokenize = doctype(depth - 1);
			return state.tokenize(stream, state);
			}
		}
		}
		return "meta";
	};
	}

	function Context(state, tagName, startOfLine) {
	this.prev = state.context;
	this.tagName = tagName;
	this.indent = state.indented;
	this.startOfLine = startOfLine;
	if (Kludges.doNotIndent.hasOwnProperty(tagName) || (state.context && state.context.noIndent))
		this.noIndent = true;
	}
	function popContext(state) {
	if (state.context) state.context = state.context.prev;
	}
	function maybePopContext(state, nextTagName) {
	var parentTagName;
	while (true) {
		if (!state.context) {
		return;
		}
		parentTagName = state.context.tagName;
		if (!Kludges.contextGrabbers.hasOwnProperty(parentTagName) ||
			!Kludges.contextGrabbers[parentTagName].hasOwnProperty(nextTagName)) {
		return;
		}
		popContext(state);
	}
	}

	function baseState(type, stream, state) {
	if (type == "openTag") {
		state.tagStart = stream.column();
		return tagNameState;
	} else if (type == "closeTag") {
		return closeTagNameState;
	} else {
		return baseState;
	}
	}
	function tagNameState(type, stream, state) {
	if (type == "word") {
		state.tagName = stream.current();
		setStyle = "tag";
		return attrState;
	} else {
		setStyle = "error";
		return tagNameState;
	}
	}
	function closeTagNameState(type, stream, state) {
	if (type == "word") {
		var tagName = stream.current();
		if (state.context && state.context.tagName != tagName &&
			Kludges.implicitlyClosed.hasOwnProperty(state.context.tagName))
		popContext(state);
		if (state.context && state.context.tagName == tagName) {
		setStyle = "tag";
		return closeState;
		} else {
		setStyle = "tag error";
		return closeStateErr;
		}
	} else {
		setStyle = "error";
		return closeStateErr;
	}
	}

	function closeState(type, _stream, state) {
	if (type != "endTag") {
		setStyle = "error";
		return closeState;
	}
	popContext(state);
	return baseState;
	}
	function closeStateErr(type, stream, state) {
	setStyle = "error";
	return closeState(type, stream, state);
	}

	function attrState(type, _stream, state) {
	if (type == "word") {
		setStyle = "attribute";
		return attrEqState;
	} else if (type == "endTag" || type == "selfcloseTag") {
		var tagName = state.tagName, tagStart = state.tagStart;
		state.tagName = state.tagStart = null;
		if (type == "selfcloseTag" ||
			Kludges.autoSelfClosers.hasOwnProperty(tagName)) {
		maybePopContext(state, tagName);
		} else {
		maybePopContext(state, tagName);
		state.context = new Context(state, tagName, tagStart == state.indented);
		}
		return baseState;
	}
	setStyle = "error";
	return attrState;
	}
	function attrEqState(type, stream, state) {
	if (type == "equals") return attrValueState;
	if (!Kludges.allowMissing) setStyle = "error";
	return attrState(type, stream, state);
	}
	function attrValueState(type, stream, state) {
	if (type == "string") return attrContinuedState;
	if (type == "word" && Kludges.allowUnquoted) {setStyle = "string"; return attrState;}
	setStyle = "error";
	return attrState(type, stream, state);
	}
	function attrContinuedState(type, stream, state) {
	if (type == "string") return attrContinuedState;
	return attrState(type, stream, state);
	}

	return {
	startState: function() {
		return {tokenize: inText,
				state: baseState,
				indented: 0,
				tagName: null, tagStart: null,
				context: null};
	},

	token: function(stream, state) {
		if (!state.tagName && stream.sol())
		state.indented = stream.indentation();

		if (stream.eatSpace()) return null;
		type = null;
		var style = state.tokenize(stream, state);
		if ((style || type) && style != "comment") {
		setStyle = null;
		state.state = state.state(type || style, stream, state);
		if (setStyle)
			style = setStyle == "error" ? style + " error" : setStyle;
		}
		return style;
	},

	indent: function(state, textAfter, fullLine) {
		var context = state.context;
		// Indent multi-line strings (e.g. css).
		if (state.tokenize.isInAttribute) {
		if (state.tagStart == state.indented)
			return state.stringStartCol + 1;
		else
			return state.indented + indentUnit;
		}
		if (context && context.noIndent) return CodeMirror.Pass;
		if (state.tokenize != inTag && state.tokenize != inText)
		return fullLine ? fullLine.match(/^(\s*)/)[0].length : 0;
		// Indent the starts of attribute names.
		if (state.tagName) {
		if (multilineTagIndentPastTag)
			return state.tagStart + state.tagName.length + 2;
		else
			return state.tagStart + indentUnit * multilineTagIndentFactor;
		}
		if (alignCDATA && /<!\[CDATA\[/.test(textAfter)) return 0;
		var tagAfter = textAfter && /^<(\/)?([\w_:\.-]*)/.exec(textAfter);
		if (tagAfter && tagAfter[1]) { // Closing tag spotted
		while (context) {
			if (context.tagName == tagAfter[2]) {
			context = context.prev;
			break;
			} else if (Kludges.implicitlyClosed.hasOwnProperty(context.tagName)) {
			context = context.prev;
			} else {
			break;
			}
		}
		} else if (tagAfter) { // Opening tag spotted
		while (context) {
			var grabbers = Kludges.contextGrabbers[context.tagName];
			if (grabbers && grabbers.hasOwnProperty(tagAfter[2]))
			context = context.prev;
			else
			break;
		}
		}
		while (context && !context.startOfLine)
		context = context.prev;
		if (context) return context.indent + indentUnit;
		else return 0;
	},

	electricInput: /<\/[\s\w:]+>$/,
	blockCommentStart: "<!--",
	blockCommentEnd: "-->",

	configuration: "mybatis",
	helperType: "mybatis"
	};
});
		
var sqlKeywords = "alter and as asc between by count create delete desc distinct drop from group having in insert into is join like not on or order select set table union update values where ";

function set(str) {
	var obj = {}, words = str.split(" ");
	for (var i = 0; i < words.length; ++i) obj[words[i]] = true;
	return obj;
}
	
CodeMirror.defineMIME("text/mybatis-xml",{
	name: "mybatis",
	keywords: set(sqlKeywords + "begin"),
	builtin: set("bool boolean bit blob enum long longblob longtext medium mediumblob mediumint mediumtext time timestamp tinyblob tinyint tinytext text bigint int int1 int2 int3 int4 int8 integer float float4 float8 double char varbinary varchar varcharacter precision real date datetime year unsigned signed decimal numeric"),
	atoms: set("false true null unknown"),
	operatorChars: /^[*+\-%<>!=]/,
	dateSQL: set("date time timestamp"),
	support: set("ODBCdotTable doubleQuote binaryNumber hexNumber")
	});
});
