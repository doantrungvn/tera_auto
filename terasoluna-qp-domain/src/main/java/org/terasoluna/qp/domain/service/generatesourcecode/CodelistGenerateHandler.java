package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemCodelistDetail;
import org.terasoluna.qp.domain.model.UserDefineCodelist;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.screenitemcodelist.ScreenItemCodelistRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.common.XmlUtils;


@Component("CodelistGenerateHandler")
public class CodelistGenerateHandler extends GenerationHandler{
	
	private static final String TEMPLATE_CODE_LIST_XML = "code_list_xml.ftl";
	private static final String UNDER_LINE = "_";
	
	private static final Map<String, String> CONSTANT_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

				{
					put("\"", "&quot;");
					put("&", "&amp;");
					put("<", "&lt;");
					put(">", "&gt;");
					put("^", "&circ;");
				}
			});
	
	private String sourcePath;
	
	@Inject
	CodeListRepository codeListRepository;
	
	@Inject
	CodeListDetailRepository codeListDetailRepository;
	
	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;
	
	@Inject
	TableDesignRepository tableDesignRepository;
	
	@Inject
	ScreenItemCodelistRepository screenItemCodelistRepository;
	
	public void handle(GenerateSourceCode generateSourceCode, CommonModel comon){
		sourcePath = generateSourceCode.getSourcePathDomain();
		
		FileOutputStream fileStream =  null;
		OutputStreamWriter out = null;
		
		try{
			String path = this.createFolderCodelist(generateSourceCode);
			List<CodeList> systemCodelist = this.prepareSystemCodelist(generateSourceCode.getProject());
			//List<UserDefineCodelist> prepareTableCodelist = this.prepareTableCodelist(generateSourceCode.getProject());
			List<UserDefineCodelist> prepareTableCodelist = new ArrayList<UserDefineCodelist>();
			List<UserDefineCodelist> prepareScreenDefineCodelist = this.prepareScreenDefineCodelist(generateSourceCode.getProject());
			List<ScreenItemCodelist> prepareScreenItemCodelistByProject = this.prepareScreenItemCodelistByProject(generateSourceCode.getProject());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("systemCodelist", systemCodelist);
			data.put("prepareTableCodelist", prepareTableCodelist);
			data.put("prepareScreenDefineCodelist", prepareScreenDefineCodelist);
			data.put("prepareScreenItemCodelistByProject", prepareScreenItemCodelistByProject);
			String newPackageName = generateSourceCode.getProject().getPackageName().replaceAll("\\.", "-") + "-codelist";

			this.process(data, TEMPLATE_CODE_LIST_XML, path + newPackageName + ".xml");
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fileStream);
		}
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	private List<CodeList> prepareSystemCodelist(Project project){
		
		String newPackageName = "CL_" + project.getPackageName().replaceAll("\\.", "_") + "_";
		
		List<CodeList> codeLists = new ArrayList<CodeList>();
		List<CodeList> codelistByProject = codeListRepository.getCodeListByProject(project.getProjectId());
		List<CodeListDetail> codelistDetailByProject = codeListDetailRepository.findCodeListDetailByProject(project.getProjectId());
		List<CodeListDetail> listCodelistDetail = new ArrayList<CodeListDetail>();
		
		// KhanhTH: Edit key from message to code
		for(CodeList codeList : codelistByProject) {
			int counter = 1;
			for(CodeListDetail codeListDetail : codelistDetailByProject) {
				if(FunctionCommon.equals(codeList.getCodeListId(), codeListDetail.getCodeListId())) {
					StringBuilder messageCode = new StringBuilder();
					messageCode.append("cl.");
					messageCode.append(codeList.getCodeListCode() + "." + counter);
					if(codeListDetail.getName() != null) {
						codeListDetail.setName(messageCode.toString());
					} else {
						codeListDetail.setValue(messageCode.toString());
					}
					listCodelistDetail.add(codeListDetail);
					counter++;
				}
			}
		}
		
		CodeListDetail[] codelistDetails = null;
		for (CodeList codeList : codelistByProject) {
			codeList.setCodeListCode((newPackageName + codeList.getCodeListCode()).toUpperCase());
			codelistDetails = new CodeListDetail[listCodelistDetail.size()];
			int count = 0;
			for (CodeListDetail codeListDetail : listCodelistDetail) {
				if(FunctionCommon.equals(codeList.getCodeListId(), codeListDetail.getCodeListId())){
					codelistDetails[count] = codeListDetail;
					if(codeListDetail.getName() != null){
						codeListDetail.setName(XmlUtils.xmlEscapeText((codeListDetail.getName())));
					}
					if(codeListDetail.getValue() != null){
						codeListDetail.setValue(XmlUtils.xmlEscapeText((codeListDetail.getValue())));
					}
					count++;
				}
			}
			codeList.setCodelistDetails(codelistDetails);
			codeLists.add(codeList);
		}
		return codeLists;
	}
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	private List<UserDefineCodelist> prepareTableCodelist(Project project){
		
		String newPackageName = "CL_" + project.getPackageName().replaceAll("\\.", "_") + "_";
		
		// Get code list from Table
		List<UserDefineCodelistDetails> userDefineCodelistDetails = userDefineCodelistRepository.getTableCodelistByProject(project.getProjectId());
		return arrangeUserDefineCodelist(userDefineCodelistDetails, "tableDeignCodeList", newPackageName);
	}
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	private List<UserDefineCodelist> prepareScreenDefineCodelist(Project project){
		String newPackageName = "CL_" + project.getPackageName().replaceAll("\\.", "_") + "_";
		List<UserDefineCodelistDetails> getScreenUserDefineCodelistByProject = userDefineCodelistRepository.getScreenUserDefineCodelistByProject(project.getProjectId());
		return arrangeUserDefineCodelist(getScreenUserDefineCodelistByProject, "screenDesignCodeList", newPackageName);
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	private List<ScreenItemCodelist> prepareScreenItemCodelistByProject(Project project){
		String newPackageName = "CL_" + project.getPackageName().replaceAll("\\.", "_") + "_";
		List<ScreenItemCodelistDetail> getScreenItemCodelistByProject = screenItemCodelistRepository.getScreenItemCodelistByProject(project.getProjectId());
		int size = getScreenItemCodelistByProject.size();
		List<ScreenItemCodelist> screenItemCodelists = new ArrayList<ScreenItemCodelist>();
		Long currentCodelistId = null;
		ScreenItemCodelist screenItemCodelist = null;
		List<ScreenItemCodelistDetail> screenItemCodelistDetails = null;
		
		StringBuilder newNameCodelist = new StringBuilder();
		newNameCodelist.append("screenDesignCodeList");
		newNameCodelist.append(UNDER_LINE);
		
		for (ScreenItemCodelistDetail screenItemCodelist2 : getScreenItemCodelistByProject) {
			//if first init groupDataType
			if (currentCodelistId == null) {
				screenItemCodelist = new ScreenItemCodelist();
				screenItemCodelist.setCodelistName(screenItemCodelist2.getOtherName() + "_" + screenItemCodelist2.getScreenItemId());
				screenItemCodelistDetails = new ArrayList<ScreenItemCodelistDetail>();
				currentCodelistId = screenItemCodelist2.getScreenItemId();
			}
			
			//if next group then add and init new element
			if (!currentCodelistId.equals(screenItemCodelist2.getScreenItemId())) {
				screenItemCodelist.setScreenItemCodelistDetails(screenItemCodelistDetails);
				screenItemCodelists.add(screenItemCodelist);
				
				screenItemCodelist = new ScreenItemCodelist();
				currentCodelistId = screenItemCodelist2.getScreenItemId();
				screenItemCodelist.setCodelistName(screenItemCodelist2.getOtherName()+ "_" + screenItemCodelist2.getScreenItemId());
				screenItemCodelistDetails = new ArrayList<ScreenItemCodelistDetail>();
			}
			screenItemCodelistDetails.add(screenItemCodelist2);
			
			if (--size == 0) {
				screenItemCodelist = new ScreenItemCodelist();
				screenItemCodelist.setCodelistName(screenItemCodelist2.getOtherName() + "_" + screenItemCodelist2.getScreenItemId());
				screenItemCodelist.setScreenItemCodelistDetails(screenItemCodelistDetails);
				screenItemCodelists.add(screenItemCodelist);
			}
		}
		
		for (ScreenItemCodelist screenItemCodelistTemp : screenItemCodelists) {
			screenItemCodelistTemp.setCodelistName((newPackageName + screenItemCodelistTemp.getCodelistName()).toUpperCase());
			for (ScreenItemCodelistDetail screenItemCodelistDetail : screenItemCodelistTemp.getScreenItemCodelistDetails()) {
				screenItemCodelistDetail.setCodelistName(XmlUtils.xmlEscapeText(screenItemCodelistDetail.getCodelistName()));
				screenItemCodelistDetail.setCodelistVal(XmlUtils.xmlEscapeText(screenItemCodelistDetail.getCodelistVal()));
			}
		}
		return screenItemCodelists;
	}

	/**
	 * @param project
	 * @param userDefineCodelistDetails
	 * @return
	 */
	private List<UserDefineCodelist> arrangeUserDefineCodelist(List<UserDefineCodelistDetails> userDefineCodelistDetails, String prefixName, String newPackageName) {
		
		List<UserDefineCodelist> codelists = new ArrayList<UserDefineCodelist>();
		
		Long currentCodelistId = null;
		UserDefineCodelist serDefineCodelist = null;
		List<UserDefineCodelistDetails> listCodelistDetails = null;
		int size = userDefineCodelistDetails.size();
		
		StringBuilder newNameCodelist = new StringBuilder();
		newNameCodelist.append(prefixName);
		newNameCodelist.append(UNDER_LINE);
		
		for (UserDefineCodelistDetails codelistDetails : userDefineCodelistDetails) {
			//if first init groupDataType
			if (currentCodelistId == null) {
				serDefineCodelist = new UserDefineCodelist();
				serDefineCodelist.setCodelistName((codelistDetails.getOtherName()+ "_" + codelistDetails.getCodelistId()).toUpperCase());
				listCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
				currentCodelistId = codelistDetails.getCodelistId();
			}
			
			//if next group then add and init new element
			if (!currentCodelistId.equals(codelistDetails.getCodelistId())) {
				serDefineCodelist.setUserDefineCodelistDetails(listCodelistDetails);
				codelists.add(serDefineCodelist);
				
				serDefineCodelist = new UserDefineCodelist();
				currentCodelistId = codelistDetails.getCodelistId();
				serDefineCodelist.setCodelistName(codelistDetails.getOtherName() + "_" + currentCodelistId);
				listCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
			}
			listCodelistDetails.add(codelistDetails);
			
			if (--size == 0) {
				serDefineCodelist = new UserDefineCodelist();
				serDefineCodelist.setCodelistName(codelistDetails.getOtherName() + "_" + codelistDetails.getCodelistId());
				serDefineCodelist.setUserDefineCodelistDetails(listCodelistDetails);
				codelists.add(serDefineCodelist);
			}
		}
		for (UserDefineCodelist userDefineCodelist : codelists) {
			userDefineCodelist.setCodelistName((newPackageName + userDefineCodelist.getCodelistName()).toUpperCase());
			for (UserDefineCodelistDetails userDefineCodelistDetails2 : userDefineCodelist.getUserDefineCodelistDetails()) {
				userDefineCodelistDetails2.setCodelistName(XmlUtils.xmlEscapeText(userDefineCodelistDetails2.getCodelistName()));
				userDefineCodelistDetails2.setCodelistValue(XmlUtils.xmlEscapeText(userDefineCodelistDetails2.getCodelistValue()));
			}
		}
		return codelists;
	}
	
	
	
	/**
	 * 
	 * @param project
	 * @return
	 */
	private String createFolderCodelist(GenerateSourceCode generateSourceCode){
		StringBuilder pathModule = new StringBuilder();
		pathModule.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("resources").append(File.separator).append("META-INF").append(File.separator).append("spring").append(File.separator);
		return GenerateSourceCodeUtil.createSaveFileDirectory(pathModule.toString(), sourcePath);
	}

	public static Map<String, String> getConstantMap() {
		return CONSTANT_MAP;
	}
}
