package org.terasoluna.qp.app.processing;

import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.domain.model.CommonModel;

public class CompositeHandler<T> implements Handlers<T>,Handler<T>{

	List<Handler<T>> handlers = new ArrayList<Handler<T>>();

	ProcessingStrategy<Handlers<T>,T> processingStrategy;
	
	public CompositeHandler(ProcessingStrategy<Handlers<T>,T> processingStrategy){
		this.processingStrategy = processingStrategy;
	}
	
	@Override
	public void handle(T handlePaream, CommonModel common) {
		if(processingStrategy!=null){
			processingStrategy.process(this,handlePaream, common);
		}
	}
	
	public void addHandler(Handler<T> handler){
		if(handler!=null){
			this.handlers.add(handler);
		}
	}
	
	public List<Handler<T>> getHandlers() {
		return handlers;
	}
}
