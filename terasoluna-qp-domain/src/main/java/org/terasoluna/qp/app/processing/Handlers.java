package org.terasoluna.qp.app.processing;

import java.util.List;

public interface Handlers<TInput> {
	public List<Handler<TInput>> getHandlers();
}
