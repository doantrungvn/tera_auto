package org.terasoluna.qp.app.message.translator.microsoft;

import java.net.URL;
import java.net.URLEncoder;
public final class TranslatorUtil extends MicrosoftTranslatorAPI {
    
    private static String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate?";
    private static String ARRAY_SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/TranslateArray?";
    private static String ARRAY_JSON_OBJECT_PROPERTY = "TranslatedText";
    
    //prevent instantiation
    private TranslatorUtil(){};

    public static void init(String serviceUrl, String arrServiceUrl, String arrJsonObjectProperty, String datamarketAccessUri) {
    	SERVICE_URL = serviceUrl;
    	ARRAY_SERVICE_URL = arrServiceUrl;
		ARRAY_JSON_OBJECT_PROPERTY = arrJsonObjectProperty;
		DatamarketAccessUri = datamarketAccessUri;
    }

    /**
     * Translates text from a given Language to another given Language using Microsoft Translator.
     * 
     * @param text The String to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String execute(final String text, final Language from, final Language to) throws Exception {
        //Run the basic service validations first
        validateServiceState(text); 
        final String params = 
                (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                + PARAM_FROM_LANG + URLEncoder.encode(from.toString(),ENCODING) 
                + PARAM_TO_LANG + URLEncoder.encode(to.toString(),ENCODING) 
                + PARAM_TEXT_SINGLE + URLEncoder.encode(text,ENCODING);
        
        final URL url = new URL(SERVICE_URL + params);
    	final String response = retrieveString(url);
    	return response;
    }
    
    /**
     * Translates text from a given Language to another given Language using Microsoft Translator.
     * 
     * Default the from to AUTO_DETECT
     * 
     * @param text The String to translate.
     * @param to The language code to translate to.
     * @return The translated String.
     * @throws Exception on error.
     */
    public static String execute(final String text, final Language to) throws Exception {
        return execute(text,Language.AUTO_DETECT,to);
    }
    
    /**
     * Translates an array of texts from a given Language to another given Language using Microsoft Translator's TranslateArray
     * service
     * 
     * Note that the Microsoft Translator expects all source texts to be of the SAME language. 
     * 
     * @param texts The Strings Array to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated Strings Array[].
     * @throws Exception on error.
     */
    public static String[] execute(final String[] texts, final Language from, final Language to) throws Exception {
        //Run the basic service validations first
        validateServiceState(texts); 
        final String params = 
                (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                + PARAM_FROM_LANG + URLEncoder.encode(from.toString(),ENCODING) 
                + PARAM_TO_LANG + URLEncoder.encode(to.toString(),ENCODING) 
                + PARAM_TEXT_ARRAY + URLEncoder.encode(buildStringArrayParam(texts),ENCODING);
        
        final URL url = new URL(ARRAY_SERVICE_URL + params);
    	final String[] response = retrieveStringArr(url,ARRAY_JSON_OBJECT_PROPERTY);
    	return response;
    }
    
    public static String[] executeNew(final String[] texts, final String from, final String to) throws Exception {
        //Run the basic service validations first
        validateServiceState(texts); 
        final String params = 
                (apiKey != null ? PARAM_APP_ID + URLEncoder.encode(apiKey,ENCODING) : "") 
                + PARAM_FROM_LANG + URLEncoder.encode(from.toString(),ENCODING) 
                + PARAM_TO_LANG + URLEncoder.encode(to.toString(),ENCODING) 
                + PARAM_TEXT_ARRAY + URLEncoder.encode(buildStringArrayParam(texts),ENCODING);
        
        final URL url = new URL(ARRAY_SERVICE_URL + params);
    	final String[] response = retrieveStringArr(url,ARRAY_JSON_OBJECT_PROPERTY);
    	return response;
    }
    
    /**
     * Translates an array of texts from an Automatically detected language to another given Language using Microsoft Translator's TranslateArray
     * service
     * 
     * Note that the Microsoft Translator expects all source texts to be of the SAME language. 
     * 
     * This is an overloaded convenience method that passes Language.AUTO_DETECT as fromLang to
     * execute(texts[],fromLang,toLang)
     * 
     * @param texts The Strings Array to translate.
     * @param from The language code to translate from.
     * @param to The language code to translate to.
     * @return The translated Strings Array[].
     * @throws Exception on error.
     */
    public static String[] execute(final String[] texts, final Language to) throws Exception {
        return execute(texts,Language.AUTO_DETECT,to);
    }
    
    private static void validateServiceState(final String[] texts) throws Exception {
        int length = 0;
        for(String text : texts) {
            length+=text.getBytes(ENCODING).length;
        }
        if(length>10240) {
            throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Translate) can handle up to 10,240 bytes per request");
        }
        validateServiceState();
    }
    
    private static void validateServiceState(final String text) throws Exception {
    	final int byteLength = text.getBytes(ENCODING).length;
        if(byteLength>10240) {
            throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Translate) can handle up to 10,240 bytes per request");
        }
        validateServiceState();
    }
}
