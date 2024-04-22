package org.terasoluna.qp.domain.service.capture;
/**
 * @author vinhhv
 */
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CaptureService {
	
	boolean checkPhantomJsPath();
	
	// screen design
    void captureScreenDesign(List<Long> screenIdLst, Long projectId, Long languageId, String path, String capturePath);
    String captureScreenDesign(Long screenId, Long projectId, Long languageId, String capturePath);
    
    // screen transaction diagram
    void captureScreenTransDiagram(List<Long> moduleIdLst, Long projectId, Long languageId, String path, String capturePath);
    String captureScreenTransDiagram(Long moduleId, Long projectId, Long languageId, String capturePath);
    
    // business design
    void captureBusinessDesign(List<Long> businessLogicIdLst, Long projectId, Long languageId, String path, String capturePath);
}
