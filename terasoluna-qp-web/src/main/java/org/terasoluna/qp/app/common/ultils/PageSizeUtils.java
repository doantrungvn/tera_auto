package org.terasoluna.qp.app.common.ultils;

import java.util.Map;

public class PageSizeUtils {
	public static int PAGE_SIZE_DEFAULT = 10;
	@SuppressWarnings("unchecked")
	public static int getPageSizeOfAction(String actionName)
	{
		Map<String,String> mapPageSizeSession = (Map<String, String>) SessionUtils.get(SessionUtils.PAGESIZE_INFOR);
		if(mapPageSizeSession == null)
		{
			return PAGE_SIZE_DEFAULT;
		}
		else
		{
			String pageSize = mapPageSizeSession.getOrDefault(String.valueOf(actionName), String.valueOf(PAGE_SIZE_DEFAULT));
			return Integer.parseInt(pageSize);
		}
	}
}
