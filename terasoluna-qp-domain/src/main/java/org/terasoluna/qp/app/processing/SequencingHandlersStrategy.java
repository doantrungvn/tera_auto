package org.terasoluna.qp.app.processing;

import org.terasoluna.qp.domain.model.CommonModel;

public class SequencingHandlersStrategy<TInput> implements ProcessingStrategy<Handlers<TInput>,TInput> {

	@Override
	public void process(Handlers<TInput> processUnit,TInput input, CommonModel common) {
		if(processUnit !=null){
			for(Handler<TInput> handler:processUnit.getHandlers()){
				handler.handle(input, common);
			}
		}
	}
}
