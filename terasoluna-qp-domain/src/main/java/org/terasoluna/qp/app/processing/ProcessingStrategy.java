package org.terasoluna.qp.app.processing;

import org.terasoluna.qp.domain.model.CommonModel;

public interface ProcessingStrategy<T,TInput> {
	public void process(T processUnit,TInput input, CommonModel common);
}
