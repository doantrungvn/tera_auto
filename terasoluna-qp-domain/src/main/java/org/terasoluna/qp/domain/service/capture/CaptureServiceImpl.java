package org.terasoluna.qp.domain.service.capture;

/**
 * @author vinhhv
 */

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Service
@Transactional
public class CaptureServiceImpl implements CaptureService {

	@Inject
	SystemService systemService;

	private static PhantomJSDriverService service;
	private static DesiredCapabilities dCaps;
	private static WebDriver webDriver;

	private AccountProfile config;
	private String batchJobPath;

	private void init(String path) throws IOException {

		// get config from database
		config = systemService.getDefaultProfile();
		if (StringUtils.isEmpty(path)) {
			batchJobPath = config.getBatchJobPath();
			/*
			 * if(StringUtils.isBlank(batchJobPath)){ batchJobPath = FileUtilsQP.getBatchJobPath(); }
			 */
		} else {
			batchJobPath = path;
		}
		batchJobPath = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator);
		config.setUrlScreenCapture(StringUtils.appendIfMissing(config.getUrlScreenCapture(), "/", "/"));

		String phantomJsPath = batchJobPath + CaptureConst.PHANTOM_JS_PATH;
		service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(new File(phantomJsPath)).usingAnyFreePort().build();
		service.start();
		dCaps = DesiredCapabilities.phantomjs();
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", true);

		webDriver = new RemoteWebDriver(service.getUrl(), dCaps);
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
	}

	@Override
	public boolean checkPhantomJsPath() {

		// get config from database
		config = systemService.getDefaultProfile();
		batchJobPath = config.getBatchJobPath();
		/*
		 * if(StringUtils.isBlank(batchJobPath)){ batchJobPath = FileUtilsQP.getBatchJobPath(); }
		 */
		batchJobPath = StringUtils.appendIfMissing(batchJobPath, File.separator, File.separator);
		config.setUrlScreenCapture(StringUtils.appendIfMissing(config.getUrlScreenCapture(), "/", "/"));
		String phantomJsPath = batchJobPath + CaptureConst.PHANTOM_JS_PATH;
		File f = new File(phantomJsPath);
		return f.exists();
	}

	@Override
	public String captureScreenDesign(Long screenId, Long projectId, Long languageId, String capturePath) {

		String filePath = "";
		try {
			this.init("");
			String baseUrl = config.getUrlScreenCapture() + CaptureConst.SCREEN_URL;
			String url = MessageFormat.format(baseUrl, String.valueOf(screenId), String.valueOf(projectId), String.valueOf(languageId));
			filePath = capturePath + screenId + CaptureConst.PNG_EXT;
			// start capture
			webDriver.get(url);
			webDriver = new Augmenter().augment(webDriver);
			final byte[] screengrab = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
			ImageIO.write(img, CaptureConst.PNG, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
			if (service != null) {
				service.stop();
			}
		}
		return filePath;
	}

	@Override
	public void captureScreenDesign(List<Long> screenIdLst, Long projectId, Long languageId, String path, String capturePath) {

		try {
			this.init(path);
			// start capture
			for (Long screenId : screenIdLst) {
				if (screenId == null)
					continue;

				String baseUrl = config.getUrlScreenCapture() + CaptureConst.SCREEN_URL;
				String url = MessageFormat.format(baseUrl, String.valueOf(screenId), String.valueOf(projectId), String.valueOf(languageId));
				String filePath = capturePath + screenId + CaptureConst.PNG_EXT;
				webDriver.get(url);
				webDriver = new Augmenter().augment(webDriver);
				final byte[] screengrab = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
				ImageIO.write(img, CaptureConst.PNG, new File(filePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
			if (service != null) {
				service.stop();
			}
		}
	}

	@Override
	public String captureScreenTransDiagram(Long moduleId, Long projectId, Long languageId, String capturePath) {

		String filePath = "";
		try {
			this.init("");
			String baseUrl = config.getUrlScreenCapture() + CaptureConst.SCREEN_TRANSITION_URL;
			String url = MessageFormat.format(baseUrl, String.valueOf(moduleId), String.valueOf(projectId), String.valueOf(languageId));
			filePath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + moduleId + CaptureConst.PNG_EXT;
			// start capture
			webDriver.get(url);
			webDriver = new Augmenter().augment(webDriver);
			final byte[] screengrab = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
			ImageIO.write(img, CaptureConst.PNG, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage(), e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
			if (service != null) {
				service.stop();
			}
		}
		return filePath;
	}

	@Override
	public void captureScreenTransDiagram(List<Long> moduleIdLst, Long projectId, Long languageId, String path, String capturePath) {

		try {
			this.init(path);
			for (Long moduleId : moduleIdLst) {
				String baseUrl = config.getUrlScreenCapture() + CaptureConst.SCREEN_TRANSITION_URL;
				String url;
				if (moduleId == null) {
					url = MessageFormat.format(baseUrl, -1L, String.valueOf(projectId), String.valueOf(languageId));
				} else {
					url = MessageFormat.format(baseUrl, String.valueOf(moduleId), String.valueOf(projectId), String.valueOf(languageId));
				}
				String filePath = capturePath + CaptureConst.SCREEN_TRANSITION_DIAGRAM_CAPTURE_NAME + moduleId + CaptureConst.PNG_EXT;
				// start capture
				webDriver.get(url);
				webDriver = new Augmenter().augment(webDriver);
				final byte[] screengrab = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
				ImageIO.write(img, CaptureConst.PNG, new File(filePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
			if (service != null) {
				service.stop();
			}
		}
	}

	@Override
	public void captureBusinessDesign(List<Long> businessLogicIdLst, Long projectId, Long languageId, String path, String capturePath) {

		try {
			this.init(path);
			for (Long businessLogicId : businessLogicIdLst) {

				if (businessLogicId == null)
					continue;

				String baseUrl = config.getUrlScreenCapture() + CaptureConst.BUSINESS_DESIGN_URL;
				String url = MessageFormat.format(baseUrl, String.valueOf(businessLogicId), String.valueOf(projectId), String.valueOf(languageId));
				String filePath = capturePath + CaptureConst.BUSINESS_DESIGN_CAPTURE_NAME + businessLogicId + CaptureConst.PNG_EXT;
				// start capture
				webDriver.get(url);
				webDriver = new Augmenter().augment(webDriver);
				final byte[] screengrab = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(screengrab));
				ImageIO.write(img, CaptureConst.PNG, new File(filePath));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException("", e);
		} finally {
			if (webDriver != null) {
				webDriver.quit();
			}
			if (service != null) {
				service.stop();
			}
		}
	}

}
