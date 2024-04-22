package org.terasoluna.qp.app.processing;


public abstract class AbstractHandler<TInput,TCalbackParam> implements Handler<TInput>{
	protected Callback<TCalbackParam> callback;

	public Callback<TCalbackParam> getCallback() {
		return callback;
	}

	public void setCallback(Callback<TCalbackParam> callback) {
		this.callback = callback;
	}
	
	
}
