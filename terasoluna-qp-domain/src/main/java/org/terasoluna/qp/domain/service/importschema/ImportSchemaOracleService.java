package org.terasoluna.qp.domain.service.importschema;

import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ImportSchema;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;

public interface ImportSchemaOracleService {
	GraphicDbDesign loadGraphicDesign(ImportSchema importSchema, CommonModel common);
}
