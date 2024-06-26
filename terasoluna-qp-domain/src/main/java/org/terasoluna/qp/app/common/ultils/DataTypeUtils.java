package org.terasoluna.qp.app.common.ultils;
import java.io.IOException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataTypeUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T convertTo(String numberString,final Class<T> clazz){
		return (T)ConvertUtils.convert(numberString,clazz);
	}
	
	public static boolean equals(Object arg01,Object arg02){
		boolean result= false;
		if(arg01==null && arg02==null){
			result = true;
		} else if(arg01==null || arg02==null){
			result = false;
		} else {
			if(arg01.getClass()==arg02.getClass()){
				result = !(arg01 ==null || !(arg01.equals(arg02)));
			} else {
				result = !(arg01 ==null || !(arg01.toString().equals(arg02.toString())));
			}
		}
		return result;
	}
	
	public static boolean equalsIgnoreCase(Object arg01,Object arg02){
		boolean result= false;
		if(arg01==null && arg02==null){
			result = true;
		} else if(arg01==null || arg02==null){
			result = false;
		} else {
			if(arg01.getClass()==arg02.getClass()){
				result = !(arg01 ==null || !(arg01.equals(arg02)));
			} else {
				result = !(arg01 ==null || !(arg01.toString().equalsIgnoreCase(arg02.toString())));
			}
		}
		return result;
	}
	
	public static boolean notEquals(Object arg01,Object arg02){
		
		return !equals(arg01, arg02);
	}
	
	public static String toString(Object obj){
		if(obj==null){
			return StringUtils.EMPTY;
		}
		return obj.toString();
	}
	
	public static String toJson(Object obj){
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		ObjectWriter writer = mapper.writer().without(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
		try {
			return writer.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}
	
	public static<T> T toObject(String  objJson,final Class<T> clazz){
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return mapper.readValue(objJson, clazz);
		}  catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
