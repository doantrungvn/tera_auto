package org.terasoluna.qp.domain.service.common;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.xml.utils.XML11Char;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

public class XmlUtils implements CharacterEscapeHandler {

	static public String xmlEscapeText(String t) {
		if (StringUtils.isBlank(t))
			return t;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			case '\n':
				sb.append("");
				break;
			case '\r':
				sb.append("");
				break;
			default:
				if (c > 0x7e) {
					sb.append("&#" + ((int) c) + ";");
				} else
					sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Escape characters inside the buffer and send the output to the writer.
	 *
	 * @exception IOException
	 *                if something goes wrong, IOException can be thrown to stop the marshalling process.
	 */
	public void escape(char[] buf, int start, int len, boolean isAttValue, Writer out) throws IOException {
		for (int i = start; i < (start + len); i++) {
			char ch = buf[i];

			// you are supposed to do the standard XML character escapes
			// like & ... &amp; < ... &lt; etc
			if (ch == '&') {
				out.write("&amp;");

				continue;
			}

			if (ch == '<') {
				out.write("&lt;");
				continue;
			}

			if (ch == '>') {
				out.write("&gt;");
				continue;
			}

			if ((ch == '"') && isAttValue) {
				// isAttValue is set to true when the marshaller is processing
				// attribute values. Inside attribute values, there are more
				// things you need to escape, usually.
				out.write("&quot;");

				continue;
			}

			if ((ch == '\'') && isAttValue) {
				out.write("&apos;");

				continue;
			}

			// you should handle other characters like < or >
			if (ch > 0x7F) {
				// escape everything above ASCII to &#xXXXX;
				out.write("&#x");
				out.write(Integer.toHexString(ch));
				out.write(";");

				continue;
			}

			// use apache util to check for valid xml character
			if (XML11Char.isXML11Valid(ch)) {
				out.write(ch);
			}
		}
	}

}
