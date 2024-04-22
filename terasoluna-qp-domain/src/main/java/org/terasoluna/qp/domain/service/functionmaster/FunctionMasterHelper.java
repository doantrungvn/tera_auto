package org.terasoluna.qp.domain.service.functionmaster;

import org.terasoluna.qp.domain.model.Project;

public class FunctionMasterHelper {
	private static final String DOT = ".";
	private static final String APP = "app";
	private static final String FUNCTIONUTILS = "functionutils";
	

	/**
	 * generate package name
	 * 
	 * @param project
	 * @return string packageName
	 */
	public static String generatePackageName(Project project) {
		StringBuilder packageName = new StringBuilder();
		packageName.append(project.getPackageName()).append(DOT)
					.append(APP).append(DOT)
					.append(FUNCTIONUTILS);
		return packageName.toString();
	}
}
