package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.Writer;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.processing.AbstractHandler;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.service.common.SystemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class GenerationHandler extends AbstractHandler<GenerateSourceCode, StringBuilder> {

	@Inject
	SystemService systemService;

	private static final Log log = LogFactory.getLog(GenerationHandler.class);

	protected Template getTemplate(String templateFile) throws Exception {
		Template template = null;
		Configuration freemarkerConfiguration = systemService.createDefaultFreemarkerConfiguration();
		try {
			template = freemarkerConfiguration.getTemplate(templateFile);
		} catch (Exception e) {
			throw e;
		}
		return template;
	}

	protected Writer getWriter(String path) throws Exception {
		return getWriter(path, false);
	}

	protected void process(Map<String, Object> data, String templateFile, String path) throws Exception {
		process(data, templateFile, path, false);
	}

	protected Writer getWriter(String path, boolean append) throws Exception {
		Writer writer = null;
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			writer = new FileWriterWithEncoding(file, DbDomainConst.CHARACTER_ENCODING, append);
		} catch (Exception e) {
			throw e;
		}
		return writer;
	}

	protected void process(Map<String, Object> data, String templateFile, String path, boolean append) throws Exception {
		Writer writer = null;
		try {
			writer = getWriter(path, append);
			getTemplate(templateFile).process(data, writer);
			writer.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

}
