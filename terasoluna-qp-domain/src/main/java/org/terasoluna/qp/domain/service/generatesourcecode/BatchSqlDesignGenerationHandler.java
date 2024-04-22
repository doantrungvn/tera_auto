package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SQLDesignType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignCompound;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderService;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

import freemarker.template.Template;

@Component("BatchSqlDesignGenerationHandler")
public class BatchSqlDesignGenerationHandler extends GenerationHandler {
	@Inject
	SystemService systemService;
	
	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject
	AutocompleteDesignService autocompleteDesignService;
	
	@Inject 
	SqlBuilderService sqlBuilderService;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	AutocompleteDesignRepository autocompleteDesignRepository;
	
	@Inject
    @Named("CL_SQL_AGGREGATE_FUNCTIONS")
	private CodeList CL_AggregateFunctions;
	
	private final String SQLMAP_TEMPLATE = "sql_batch_repository_xml.ftl";
	
	private final String REPOSITORY_TEMPLATE = "sql_batch_repository_java.ftl";
	
	private final String INPUTMODEL_TEMPLATE = "sql_batch_input_java.ftl";
	
	private final String OUTPUTMODEL_TEMPLATE = "sql_batch_output_java.ftl";
	
	private final String INPUTBEAN_TEMPLATE = "sql_batch_object_input_java.ftl";
	
	private final String OUTPUTBEAN_TEMPLATE = "sql_batch_object_output_java.ftl";
	
	public static final String SQL_COUNT_CODE = "countTableRowsWithConditions";
	public static final String SQL_COUNT_TEMPLATE = "SELECT count(*) FROM ${tableCode} WHERE ${conditions}";
	public static final String SQL_COUNT_TABLE_CODE = "tableCode";
	public static final String SQL_COUNT_CONDITIONS = "condition";
	
	private Writer getSqlMapWriter(String baseSourcePath, String projectCode, String moduleCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		
		if (StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("resources")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("repository")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode))
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(moduleCode))
			.append("Repository")
			.append(".xml");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("resources")
			.append(File.separator)
			.append(projectCode.replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
            .append("repository")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append("Common")
			.append("Repository")
			.append(".xml");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	private Writer getRepositoryWriter(String baseSourcePath, String projectCode, String moduleCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		
		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("repository")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode))
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(moduleCode))
			.append("Repository")
			.append(".java");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("repository")
			.append(File.separator)
            .append("common")
			.append(File.separator)
			.append("Common")
			.append("Repository")
			.append(".java");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	private Writer getInputWriter(String baseSourcePath, String projectCode, String moduleCode, String entityCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		
		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode))
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("InputBean.java");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("InputBean.java");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	private Writer getOutputWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		
		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode))
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("OutputBean.java");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("OutputBean.java");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	private Writer getInputObjectWriter(String baseSourcePath, String projectCode, String moduleCode, String entityCode, String parentObjectCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);

		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode));
			if (StringUtils.isNotBlank(parentObjectCode)) {
			    pathBuilder.append(File.separator)
			    .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode+"inputbean"));
			} else {
			    //Do nothing
			}
			pathBuilder.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append(".java");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common");
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode+"inputbean"));
            } else {
                //Do nothing
            }
			pathBuilder.append(File.separator)
			.append(StringUtils.capitalize(GenerateSourceCodeUtil.normalizedClassName(entityCode)))
			.append(".java");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	private Writer getOutputObjectWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode, String parentObjectCode) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);

		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(projectCode.replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(StringUtils.uncapitalize(moduleCode));
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode+"outputbean"));
            } else {
                //Do nothing
            }
			pathBuilder.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append(".java");
		} else {
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("java")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("batch")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append("common");
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode+"outputbean"));
            } else {
                //Do nothing
            }
			pathBuilder.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append(".java");
		}
		return this.getWriter(pathBuilder.toString());
	}
	
	
	public void handle(GenerateSourceCode generateSourceCode, CommonModel common) {
		
		Map<String, Object> data = null;
		Writer sqlMapWriter = null;
		Writer inputWriter = null;
		Writer outputWriter = null;
		Writer inputBeanWriter = null;
		Writer outputBeanWriter = null;
		Writer repositoryWriter = null;
		List<SqlDesignCompound> sqlDesignCompounds = null;
		SqlDesignCompound sqlDesignCompound = null;
		List<SqlDesign> sqlDesigns = null;
		Template sqlmapTemplate = null;
		Template repositoryTemplate = null;
		Template inputTemplate = null;
		Template outputTemplate = null;
		Template inputBeanTemplate = null;
		Template outputBeanTemplate = null;
		try {
			sqlmapTemplate = this.getTemplate(SQLMAP_TEMPLATE);
			repositoryTemplate = this.getTemplate(REPOSITORY_TEMPLATE);
			inputTemplate = this.getTemplate(INPUTMODEL_TEMPLATE);
			outputTemplate = this.getTemplate(OUTPUTMODEL_TEMPLATE);
			inputBeanTemplate = this.getTemplate(INPUTBEAN_TEMPLATE);
			outputBeanTemplate = this.getTemplate(OUTPUTBEAN_TEMPLATE);
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
		
		if(DataTypeUtils.equals(generateSourceCode.getScopeGenerateSource(),GenerateSourceCodeConst.PROJECT_SCOPRE)){
			sqlDesigns = sqlDesignRepository.getAllSqlDesignByProjectIdWithCommon(generateSourceCode.getProject().getProjectId(), DbDomainConst.FunctionType.BATCH);
		}
		else if(DataTypeUtils.equals(generateSourceCode.getScopeGenerateSource(),GenerateSourceCodeConst.MODULE_SCOPRE)){
			sqlDesigns = new ArrayList<SqlDesign>();
			// for common
			sqlDesigns.addAll(sqlDesignRepository.getAllSqlDesignByModuleId(null,generateSourceCode.getProject().getProjectId()));
			
			for(int i=0; i < generateSourceCode.getModules().size(); i++){
				if(generateSourceCode.getModules().get(i).getModuleType() == DbDomainConst.FunctionType.BATCH ){
					sqlDesigns.addAll(sqlDesignRepository.getAllSqlDesignByModuleId(generateSourceCode.getModules().get(i).getModuleId(),generateSourceCode.getProject().getProjectId()));
				}
			}
		}
		
		Map<String,List<SqlDesignCompound>> modules = new HashMap<String, List<SqlDesignCompound>>();
		
		for(SqlDesign sqlDesign: sqlDesigns){
			
			sqlDesignCompound = sqlDesignService.findCompoundForGenerationById(sqlDesign.getSqlDesignId());	
			sqlDesignCompound.getSqlDesign().setSqlText(sqlBuilderService.buildSql(sqlDesignCompound,String.valueOf(generateSourceCode.getProject().getDbType()), common));
			if(StringUtils.isNotBlank(sqlDesign.getModuleCode())){
				if(!modules.containsKey(sqlDesign.getModuleCode())){
					modules.put(sqlDesign.getModuleCode(), new ArrayList<SqlDesignCompound>());
				}
				modules.get(sqlDesign.getModuleCode()).add(sqlDesignCompound);
			} else {
				if(!modules.containsKey(StringUtils.EMPTY)){
					modules.put(StringUtils.EMPTY, new ArrayList<SqlDesignCompound>());
				}
				modules.get(StringUtils.EMPTY).add(sqlDesignCompound);
			}
		}
		
		for (Map.Entry<String, List<SqlDesignCompound>> moduleEntry : modules.entrySet()) {
			try {
				sqlDesignCompounds = moduleEntry.getValue();
				sqlMapWriter = this.getSqlMapWriter(generateSourceCode.getSourcePathBatch(),generateSourceCode.getProject().getPackageName(),moduleEntry.getKey());
				repositoryWriter = this.getRepositoryWriter(generateSourceCode.getSourcePathBatch(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey());
				
				if(sqlMapWriter!=null & CollectionUtils.isNotEmpty(sqlDesignCompounds)){
					for(int i=0;i<sqlDesignCompounds.size();i++){
						sqlDesignCompound = sqlDesignCompounds.get(i);
						this.processDataForGeneration(sqlDesignCompound);
						data = new HashMap<String, Object>();
						data.put("sqlDesignCompound", sqlDesignCompound);
						data.put("projectCode", generateSourceCode.getProject().getPackageName());
						data.put("module", generateSourceCode.getModule());
						data.put("moduleCode", moduleEntry.getKey());
						inputWriter = this.getInputWriter(generateSourceCode.getSourcePathBatch(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),sqlDesignCompound.getSqlDesign().getSqlDesignCode());
						if(inputWriter != null){
							try {	
								inputTemplate.process(data, inputWriter);
							} catch (Exception e) {
								GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), "getSqlDesignCode = " + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
								GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
							} finally{
								IOUtils.closeQuietly(inputWriter);
							}
						}
						
						if(sqlDesignCompound.getSqlDesign().getDesignType() == 4 || sqlDesignCompound.getSqlDesign().getDesignType() == 5){
							if(sqlDesignCompound.getSqlDesign().getSqlPattern() == SqlPattern.SELECT){
								if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
									outputWriter = this.getOutputWriter(generateSourceCode.getSourcePathBatch(), 
											generateSourceCode.getProject().getPackageName(), 
											moduleEntry.getKey(),
											sqlDesignCompound.getSqlDesign().getSqlDesignCode());
									if(outputWriter!=null){
										try {
											outputTemplate.process(data, outputWriter);
										} catch (Exception e) {
											GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), "getSqlDesignCode = " + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
											GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
										} finally{
											IOUtils.closeQuietly(outputWriter);
										}
									}
								}
							}
						}
						Map<SqlDesignInput,List<SqlDesignInput>> sqlInputObjects = this.getInputObjects(sqlDesignCompound);
						for (Map.Entry<SqlDesignInput,List<SqlDesignInput>> inputEntry : sqlInputObjects.entrySet()){
							inputBeanWriter = this.getInputObjectWriter(generateSourceCode.getSourcePathBatch(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(), inputEntry.getKey().getSqlDesignInputCode(), sqlDesignCompound.getSqlDesign().getSqlDesignCode());
							data = new HashMap<String, Object>();
							data.put("parentCode", sqlDesignCompound.getSqlDesign().getSqlDesignCode());
							data.put("objectCode", inputEntry.getKey().getSqlDesignInputCode());
							data.put("sqlDesignInputs", inputEntry.getValue());
							data.put("projectCode", generateSourceCode.getProject().getPackageName());
							data.put("module", generateSourceCode.getModule());
							data.put("moduleCode", moduleEntry.getKey());
							if(inputBeanWriter!=null){
								try {
									inputBeanTemplate.process(data, inputBeanWriter);
								} catch (Exception e) {
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), "parentCode = " + inputEntry.getKey().getSqlDesignInputCode());
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
								} finally{
									IOUtils.closeQuietly(inputBeanWriter);
								}
							}
						}
						
						Map<SqlDesignOutput,List<SqlDesignOutput>> sqlOutputObjects = this.getOutputObjects(sqlDesignCompound);
						for (Map.Entry<SqlDesignOutput,List<SqlDesignOutput>> outputEntry : sqlOutputObjects.entrySet()){
							outputBeanWriter = this.getOutputObjectWriter(generateSourceCode.getSourcePathBatch(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(), outputEntry.getKey().getSqlDesignOutputCode(), sqlDesignCompound.getSqlDesign().getSqlDesignCode());
							data = new HashMap<String, Object>();
							data.put("parentCode", sqlDesignCompound.getSqlDesign().getSqlDesignCode());
							data.put("objectCode", outputEntry.getKey().getSqlDesignOutputCode());
							data.put("sqlDesignOutputs", outputEntry.getValue());
							data.put("projectCode", generateSourceCode.getProject().getPackageName());
							data.put("module", generateSourceCode.getModule());
							data.put("moduleCode", moduleEntry.getKey());
							if(outputBeanWriter!=null){
								try {
									outputBeanTemplate.process(data, outputBeanWriter);
								} catch (Exception e) {
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), "parentCode = " + outputEntry.getKey().getSqlDesignOutputCode());
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
								} finally{
									IOUtils.closeQuietly(outputBeanWriter);
								}
							}
						}
					}
					try {
						data = new HashMap<String, Object>();
						data.put("sqlDesignCompounds", sqlDesignCompounds);
						data.put("projectCode", generateSourceCode.getProject().getPackageName());
						data.put("module", generateSourceCode.getModule());
						data.put("moduleCode", moduleEntry.getKey());
						if(sqlMapWriter != null){
							sqlmapTemplate.process(data, sqlMapWriter);
						}
						if(repositoryWriter != null){
							repositoryTemplate.process(data, repositoryWriter);
						}
					} catch (Exception e) {
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
					} finally{
						IOUtils.closeQuietly(sqlMapWriter);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String getOutputParam(SqlDesignOutput[] sqlDesignOutputs,String itemSeqNo){
		SqlDesignOutput sqlDesignOutput = null;
		if(ArrayUtils.isNotEmpty(sqlDesignOutputs)){
			for(int i=0;i<sqlDesignOutputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignOutputs[i].getGroupIndex(),itemSeqNo)){
					sqlDesignOutput = sqlDesignOutputs[i];
					break;
				}
			}
			if(sqlDesignOutput != null){
				return getOutputObjectPath(sqlDesignOutputs,sqlDesignOutput);
			}
		}	
		return StringUtils.EMPTY;
	}
	
	private String getOutputObjectPath(SqlDesignOutput[] sqlDesignOutputs,SqlDesignOutput sqlDesignOutput){
		String path=sqlDesignOutput.getSqlDesignOutputCode();
		if(sqlDesignOutput.getSqlDesignOutputParentId()!=null){
			SqlDesignOutput sqlDesignOutputParent = null;
			for(int i=0;i<sqlDesignOutputs.length;i++){
				if(DataTypeUtils.equals(sqlDesignOutputs[i].getSqlDesignOutputId(),sqlDesignOutput.getSqlDesignOutputParentId())){
					sqlDesignOutputParent = sqlDesignOutputs[i];
					break;
				}
			}
			path = getOutputObjectPath(sqlDesignOutputs,sqlDesignOutputParent) +Const.DOT+ path;
		}
		return path;
	}
	private void processDataForGeneration(SqlDesignCompound sqlDesignCompound) {
		if(sqlDesignCompound != null){
			if(sqlDesignCompound instanceof AutocompleteDesignCompound){
				AutocompleteDesign autocompleteDesign = ((AutocompleteDesignCompound)sqlDesignCompound).getAutocomplete();
				SqlDesignOutput[] sqlDesignOutputs = new SqlDesignOutput[20];
				SqlDesignOutput sqlDesignOutput =null;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output01");
				sqlDesignOutput.setPropertyPath("Output01");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput01());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[0] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output02");
				sqlDesignOutput.setPropertyPath("Output02");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput02());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[1] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output03");
				sqlDesignOutput.setPropertyPath("Output03");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput03());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[2] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output04");
				sqlDesignOutput.setPropertyPath("Output04");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput04());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[3] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output05");
				sqlDesignOutput.setPropertyPath("Output05");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput05());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[4] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output06");
				sqlDesignOutput.setPropertyPath("Output06");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput06());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[5] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output07");
				sqlDesignOutput.setPropertyPath("Output07");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput07());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[6] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output08");
				sqlDesignOutput.setPropertyPath("Output08");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput08());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[7] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output09");
				sqlDesignOutput.setPropertyPath("Output09");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput09());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[8] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output10");
				sqlDesignOutput.setPropertyPath("Output10");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput10());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[9] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output11");
				sqlDesignOutput.setPropertyPath("Output11");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput11());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[10] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output12");
				sqlDesignOutput.setPropertyPath("Output12");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput12());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[11] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output13");
				sqlDesignOutput.setPropertyPath("Output13");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput13());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[12] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output14");
				sqlDesignOutput.setPropertyPath("Output14");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput14());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[13] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output15");
				sqlDesignOutput.setPropertyPath("Output15");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput15());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[14] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output16");
				sqlDesignOutput.setPropertyPath("Output16");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput16());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[15] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output17");
				sqlDesignOutput.setPropertyPath("Output17");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput17());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[16] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output18");
				sqlDesignOutput.setPropertyPath("Output18");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput18());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[17] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output19");
				sqlDesignOutput.setPropertyPath("Output19");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput19());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[18] = sqlDesignOutput;
				
				sqlDesignOutput = new SqlDesignOutput();
				sqlDesignOutput.setSqlDesignOutputCode("Output20");
				sqlDesignOutput.setPropertyPath("Output20");
				sqlDesignOutput.setMappingColumn(autocompleteDesign.getOutput20());
				sqlDesignOutput.setDataType(9);
				sqlDesignOutputs[19] = sqlDesignOutput;
				
				sqlDesignCompound.setSqlDesignOutputs(sqlDesignOutputs);
			
			} 
			if(sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.SQL_BUILDER || sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.AUTOCOMPLETE){
				if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
					for(SqlDesignOutput sqlDesignOutput : sqlDesignCompound.getSqlDesignOutputs()){
						SqlDesignResult sqlDesignResult = this.findSqlDesignResult(sqlDesignCompound,sqlDesignOutput.getMappingColumn());
						if(sqlDesignResult != null){
							sqlDesignOutput.setMappingColumn(sqlDesignResult.getMappingAlias());
						}
					}
				}
				
			}
			if(sqlDesignCompound.getSqlDesign().getSqlPattern() == SqlPattern.SELECT && (sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.SQL_BUILDER || sqlDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.ADVANCED_SQL)){
				if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())) {
					for(SqlDesignOutput sqlDesignOutput : sqlDesignCompound.getSqlDesignOutputs()){
						sqlDesignOutput.setPropertyPath(this.getOutputParam(sqlDesignCompound.getSqlDesignOutputs(), sqlDesignOutput.getGroupIndex()));
					}
				}
			}
		}
		
	}

	private SqlDesignResult findSqlDesignResult(SqlDesignCompound sqlDesignCompound,String mappingColumn) {
		SqlDesignResult sqlDesignResult = null;
		for(int i=0;i<sqlDesignCompound.getSqlDesignResults().length;i++){
			if(DataTypeUtils.equals(sqlDesignCompound.getSqlDesignResults()[i].getItemSeqNo(), mappingColumn)){
				sqlDesignResult = sqlDesignCompound.getSqlDesignResults()[i];
				break;
			}
		}
		return sqlDesignResult;
	}

	private Map<SqlDesignOutput, List<SqlDesignOutput>> getOutputObjects(SqlDesignCompound sqlDesignCompound) {
		Map<SqlDesignOutput, List<SqlDesignOutput>> resultMap = new HashMap<SqlDesignOutput, List<SqlDesignOutput>>();
		SqlDesignOutput sqlDesignOutput;
		if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())) {
			for(int i=0;i<sqlDesignCompound.getSqlDesignOutputs().length;i++){
				sqlDesignOutput = sqlDesignCompound.getSqlDesignOutputs()[i];
				if(sqlDesignOutput.getDataType()== 0){
					if(!resultMap.containsKey(sqlDesignOutput)){
						resultMap.put(sqlDesignOutput, new ArrayList<SqlDesignOutput>());
					} 
					for(int j=i;j<sqlDesignCompound.getSqlDesignOutputs().length;j++){
						if(DataTypeUtils.equals(sqlDesignCompound.getSqlDesignOutputs()[j].getSqlDesignOutputParentId(),sqlDesignOutput.getSqlDesignOutputId())){
							List<SqlDesignOutput> sqlDesignOutputChildren = null;
							if(!resultMap.containsKey(sqlDesignOutput)){
								sqlDesignOutputChildren = new ArrayList<SqlDesignOutput>();
								resultMap.put(sqlDesignOutput, sqlDesignOutputChildren);
							} else {
								sqlDesignOutputChildren = resultMap.get(sqlDesignOutput);
							}
							sqlDesignOutputChildren.add(sqlDesignCompound.getSqlDesignOutputs()[j]);
						}
					}
				}
			}
		}
		return resultMap;
	}

	private Map<SqlDesignInput, List<SqlDesignInput>> getInputObjects(SqlDesignCompound sqlDesignCompound) {
		Map<SqlDesignInput, List<SqlDesignInput>> resultMap = new HashMap<SqlDesignInput, List<SqlDesignInput>>();
		SqlDesignInput sqlDesignInput;
		
		if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignInputs())) {
			for(int i=0;i<sqlDesignCompound.getSqlDesignInputs().length;i++){
				sqlDesignInput = sqlDesignCompound.getSqlDesignInputs()[i];
				if(sqlDesignInput.getDataType()== 0){
					if(!resultMap.containsKey(sqlDesignInput)){
						resultMap.put(sqlDesignInput, new ArrayList<SqlDesignInput>());
					} 
					for(int j=i;j<sqlDesignCompound.getSqlDesignInputs().length;j++){
						if(DataTypeUtils.equals(sqlDesignCompound.getSqlDesignInputs()[j].getSqlDesignInputParentId(),sqlDesignInput.getSqlDesignInputId())){
							List<SqlDesignInput> sqlDesignInputChildren = null;
							if(!resultMap.containsKey(sqlDesignInput)){
								sqlDesignInputChildren = new ArrayList<SqlDesignInput>();
								resultMap.put(sqlDesignInput, sqlDesignInputChildren);
							} else {
								sqlDesignInputChildren = resultMap.get(sqlDesignInput);
							}
							sqlDesignInputChildren.add(sqlDesignCompound.getSqlDesignInputs()[j]);
						}
					}
				}
			}
		}
		return resultMap;
	}
	
/*	public void initData(Long projectId, Long languageId, Long accountId, Project project) {
		sqlBuilderService.initData(projectId, languageId, accountId);
		sqlBuilderService.setWorkingProject(project);
	}*/
}
