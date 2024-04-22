package org.terasoluna.qp.domain.service.common;

import java.util.HashMap;
import java.util.Map;

public class PageSizeServiceHelper {
	public static Map<String,String> addPageSize(Map<String,String> mapPageSizeSession,PageSizeInput param)
	{

		String key = param.getAction();
		if(mapPageSizeSession == null)
		{
			mapPageSizeSession = new HashMap<String, String>();
		}
		if(mapPageSizeSession.containsKey(key))
		{
			mapPageSizeSession.replace(key, mapPageSizeSession.get(key), param.getSize());
		}
		else
		{
			mapPageSizeSession.put(key, param.getSize());
		}
		return mapPageSizeSession;
	}
	public static String RESULT_OK = "1";
	public static String RESULT_FAIL = "0";
}
