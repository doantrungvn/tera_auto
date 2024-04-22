package org.terasoluna.qp.app.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CommonController {

	@Inject
	SystemService systemService;
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	/**
	 * Home page when not logging
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String home(Locale locale, Model model) {
		logger.info("Welcome index! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "login/index";
	}

	/**
	 * handle when access deny
	 */
	@RequestMapping(value = "/accessDeniedPage", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String accessDeniedPage(Model model) {
//		return "common/error/accessDeniedError";
		return "login/index";
	}

	/**
	 * handle when delete successfully
	 */
	@RequestMapping(value = "/complete", method = { RequestMethod.GET, RequestMethod.POST })
	public String displayDeleteComplete(Model model) {
		String msgHeader = model.asMap().getOrDefault("msgHeader", MessageUtils.getMessage(CommonMessageConst.SC_SYS_0036)).toString();
		model.addAttribute("msgHeader", msgHeader);
		return "common/deleteCompleteForm";
	}
	
	/**
	 * handle when modify successfully
	 */
	@RequestMapping(value = "/modifyComplete", method = { RequestMethod.GET, RequestMethod.POST })
	public String displayModifyComplete(Model model) {
		String msgHeader = model.asMap().getOrDefault("msgHeader", MessageUtils.getMessage(CommonMessageConst.SC_SYS_0053)).toString();
		model.addAttribute("msgHeader", msgHeader);

		return "common/modifyCompleteForm";
	}

	/**
	 * process download file
	 * 
	 * @param functionMasterForm
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/downloadExport", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> processDownloadFile(@RequestParam(value = "fileName", required = true) String fileName) {
		String path = FileUtilsQP.getExportFolder() + fileName;
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Content-Disposition",  "attachment; filename=\"" + fileName + "\"");
		ResponseEntity<byte[]> response = null;
		File file = new File(path);
		try {
			response = new ResponseEntity<byte[]>(FileUtilsQP.readFileToByteArray(file), headers, HttpStatus.OK); 
		} catch (IOException e) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0125));
		} finally {
			FileUtilsQP.deleteQuietly(file);
		}
		return response;
	}
	
	/**
	 * process download file
	 * 
	 * @param functionMasterForm
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/downloadExportDocument", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> processDownloadExportDocument(@RequestParam(value = "fileName", required = true) String fileName) {
		String path = FileUtilsQP.getExportFolder() + fileName;
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Content-Disposition",  "attachment; filename=\"" + fileName + "\"");
		ResponseEntity<byte[]> response = null;
		File file = new File(path);
		try {
			response = new ResponseEntity<byte[]>(FileUtilsQP.readFileToByteArray(file), headers, HttpStatus.OK); 
		} catch (IOException e) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0125));
		} finally {
			FileUtilsQP.deleteQuietly(file);
		}
		return response;
	}
	
	/**
	 * Download large file
	 * @param fileName
	 * @param resp
	 * @author dungnn1
	 */
	@RequestMapping(value = "/downloadLarge", method = RequestMethod.GET)
	public void downloadLargeFile(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse resp){
		String path = FileUtilsQP.getExportFolder() + fileName;
		File file = new File(path);
		Long fileSize = file.length();

		resp.reset();
		resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		resp.setHeader("Content-Disposition",  "attachment; filename=\"" + fileName + "\"");
		resp.setContentLength(fileSize.intValue());

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			final BufferedInputStream in = new BufferedInputStream(fis);
			FileCopyUtils.copy(in, resp.getOutputStream());
			resp.flushBuffer();
			IOUtils.closeQuietly(in);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0125));
		} finally {
			IOUtils.closeQuietly(fis);
			FileUtilsQP.deleteQuietly(file);
		}

	}
	
	/**
	 * Download large file
	 * @param fileName
	 * @param resp
	 * @author vinhhv
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public String downloadFile(@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "redirectPath", required = false) String redirectPath, HttpServletResponse resp,
			RedirectAttributes attributes){
		
		if( redirectPath !=  null){
			redirectPath = "redirect:/"+redirectPath;
		}
		else {
			redirectPath = "common/error/resourceNotFoundError";
		}
		AccountProfile config = systemService.getDefaultProfile();
		String batchJobPath = config.getBatchJobPath();
		/*if(StringUtils.isBlank(batchJobPath)){
			batchJobPath = FileUtilsQP.getBatchJobPath();
		}*/
		
		String path = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator) + "export" + File.separator + fileName;
		File file = new File(path);
		Long fileSize = file.length();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			final BufferedInputStream in = new BufferedInputStream(fis);
			resp.reset();
			resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			resp.setHeader("Content-Disposition",  "attachment; filename=\"" + fileName + "\"");
			resp.setContentLength(fileSize.intValue());
			FileCopyUtils.copy(in, resp.getOutputStream());
			resp.flushBuffer();
			IOUtils.closeQuietly(in);
			return null;
		} catch (final IOException e) {	
			e.printStackTrace();
			attributes.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0125));
			return redirectPath;
		} finally {
			IOUtils.closeQuietly(fis);
		}
		

	}
	
	/**
	 * Download import file
	 * @param fileName
	 * @param resp
	 * @author vinhhv
	 */
	@RequestMapping(value = "/downloadImportFile", method = RequestMethod.GET)
	public String downloadImportFile(@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "redirectPath", required = false) String redirectPath, HttpServletResponse resp,
			RedirectAttributes attributes){
		
		if( redirectPath !=  null){
			redirectPath = "redirect:/"+redirectPath;
		}
		else {
			redirectPath = "common/error/resourceNotFoundError";
		}
		
		String path = FileUtilsQP.getUploadFolder() + fileName;
		File file = new File(path);
		Long fileSize = file.length();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			final BufferedInputStream in = new BufferedInputStream(fis);
			resp.reset();
			resp.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			resp.setHeader("Content-Disposition",  "attachment; filename=\"" + fileName + "\"");
			resp.setContentLength(fileSize.intValue());
			FileCopyUtils.copy(in, resp.getOutputStream());
			resp.flushBuffer();
			IOUtils.closeQuietly(in);
			return null;
		} catch (final IOException e) {	
			e.printStackTrace();
			attributes.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0125));
			return redirectPath;
		} finally {
			IOUtils.closeQuietly(fis);
		}
		

	}
}
