package org.terasoluna.qp.domain.service.capture;

/**
 * @author vinhhv
 */
public final class CaptureConst {
	
	public static String PNG = "png";
	public static String PNG_EXT = ".png";
	public static String SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME = "screen_trans_diagram_";
	public static String BUSINESS_DESIGN_CAPTURE_NAME = "business_design_";
	
	// url
	public static String SCREEN_URL = "capture/screen?screenId={0}&projectId={1}&languageId={2}";
	public static String SCREEN_TRANSITION_URL = "capture/screentransition?moduleId={0}&projectId={1}&languageId={2}";
	public static String BUSINESS_DESIGN_URL = "capture/businessdesign?businessLogicId={0}&projectId={1}&languageId={2}";
	
	// capture path
	public static String CAPTURE_PATH = "capture";
	public static String PHANTOM_JS_PATH = "phantomjs\\bin\\phantomjs.exe";
}
