package org.terasoluna.qp.app.message.translator.microsoft;

import java.net.URL;
import java.net.URLEncoder;

public final class Speak extends MicrosoftTranslatorAPI {
    private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Speak?";

     //prevent instantiation
     private Speak() {};
     /**
	 * Detects the language of a supplied String.
	 * 
	 * @param text The String to generate a WAV for
     * @param to The language code to translate to
	 * @return A String containing the URL to a WAV of the spoken text
	 * @throws Exception on error.
	 */
	public static String execute(final String text, final SpokenDialect language) throws Exception {
                //Run the basic service validations first
                validateServiceState(text);             
		final URL url = new URL(SERVICE_URL 
                        +(apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                        +PARAM_SPOKEN_LANGUAGE+URLEncoder.encode(language.toString(),ENCODING)
                        +PARAM_TEXT_SINGLE+URLEncoder.encode(text, ENCODING));
		final String response = retrieveString(url);
                return response;
	}
        
        private static void validateServiceState(final String text) throws Exception {
        	final int byteLength = text.getBytes(ENCODING).length;
            if(byteLength>2000) {
                throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Speak) can handle up to 2000 bytes per request");
            }
            validateServiceState();
        }
}

