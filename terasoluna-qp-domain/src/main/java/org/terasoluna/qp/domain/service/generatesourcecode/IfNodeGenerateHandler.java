package org.terasoluna.qp.domain.service.generatesourcecode;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component("IfNodeGenerateHandler")
public class IfNodeGenerateHandler {

	public static final String NL = "\n\t\t";
	public static final String TAB = "\t";

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
}
