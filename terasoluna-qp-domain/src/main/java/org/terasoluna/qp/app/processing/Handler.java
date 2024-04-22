package org.terasoluna.qp.app.processing;

import org.terasoluna.qp.domain.model.CommonModel;

public interface Handler<TInput> {
	 void handle(TInput handlePaream, CommonModel common);
}
