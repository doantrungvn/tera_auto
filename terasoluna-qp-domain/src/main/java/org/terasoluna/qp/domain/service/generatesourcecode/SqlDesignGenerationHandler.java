package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
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
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.project.ProjectRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignCompound;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderService;
import org.terasoluna.qp.domain.service.autocomplete.SqlBuilderServiceImpl.Const;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDataType;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;

@Component("SqlDesignGenerationHandler")
public class SqlDesignGenerationHandler extends GenerationHandler {
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
	
	@Inject
	@Named("CL_SQL_SQLPATTERN")
	private CodeList CL_SQL_SQLPATTERN;
	
	@Inject
	private ProjectRepository projectRepository;
	
	private final String SQLMAP_TEMPLATE = "sql_repository_xml.ftl";
	
	private final String REPOSITORY_TEMPLATE = "sql_repository_java.ftl";
	
	private final String INPUTMODEL_TEMPLATE = "sql_input_java.ftl";
	
	private final String OUTPUTMODEL_TEMPLATE = "sql_output_java.ftl";
	
	private final String INPUTBEAN_TEMPLATE = "sql_object_input_java.ftl";
	
	private final String OUTPUTBEAN_TEMPLATE = "sql_object_output_java.ftl";
	
	private final String MYBATIC_CONFIG_TEMPLATE = "mybatis-config.ftl";
	
	public static final String SQL_COUNT_TEMPLATE = "sql_count_template.ftl";
	
	public static final String SQL_COUNT_CODE = "countTableRowsWithConditions";
	public static final String SQL_COUNT_TABLE_CODE = "tableCode";
	public static final String SQL_COUNT_CONDITIONS = "condition";
	
	private static final String OUTPUTBEAN_SUFFIX = "OutputBean";
	private static final String INPUTBEAN_SUFFIX = "InputBean";
	
	private String getSqlMapWriter(String baseSourcePath,String projectCode,String moduleCode){
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		if(StringUtils.isNotBlank(moduleCode)){
			pathBuilder.append("src")
			.append(File.separator)
			.append("main")
			.append(File.separator)
			.append("resources")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(projectCode).replace(".", File.separator))
			.append(File.separator)
			.append("domain")
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
			.append("domain")
			.append(File.separator)
            .append("repository")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append("Common")
			.append("Repository")
			.append(".xml");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	private String getRepositoryWriter(String baseSourcePath,String projectCode,String moduleCode){
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
			.append("domain")
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
			.append("domain")
			.append(File.separator)
			.append("repository")
			.append(File.separator)
            .append("common")
			.append(File.separator)
			.append("Common")
			.append("Repository")
			.append(".java");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	
	/**
	 * 
	 * @param baseSourcePath
	 * @param projectCode
	 * @param moduleCode
	 * @param entityCode
	 * @return path gen InputBean
	 */
	private String getInputWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode){
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
			.append("domain")
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
			.append("domain")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("InputBean.java");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	/**
	 * 
	 * @param baseSourcePath
	 * @param projectCode
	 * @param moduleCode
	 * @param entityCode
	 * @return path Object Inside InputBean
	 */
	private String getInputObjectWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode, String parentObjectCode){
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
			.append("domain")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedPackageName(moduleCode));
			if (StringUtils.isNotBlank(parentObjectCode)) {
			    pathBuilder.append(File.separator)
			    .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode));
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
			.append("domain")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common");
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode));
            } else {
                //Do nothing
            }
			pathBuilder.append(File.separator)
			.append(StringUtils.capitalize(GenerateSourceCodeUtil.normalizedClassName(entityCode)))
			.append(".java");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	
	/**
	 * 
	 * @param baseSourcePath
	 * @param projectCode
	 * @param moduleCode
	 * @param entityCode
	 * @return return path Object Inside OutputBean
	 */
	private String getOutputObjectWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode, String parentObjectCode){
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
			.append("domain")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append(StringUtils.uncapitalize(moduleCode));
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode));
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
			.append("domain")
			.append(File.separator)
			.append("model")
			.append(File.separator)
			.append("common");
			if (StringUtils.isNotBlank(parentObjectCode)) {
                pathBuilder.append(File.separator)
                .append(GenerateSourceCodeUtil.normalizedPackageName(parentObjectCode));
            } else {
                //Do nothing
            }
			pathBuilder.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append(".java");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	/**
	 * 
	 * @param baseSourcePath
	 * @param projectCode
	 * @param moduleCode
	 * @param entityCode
	 * @return path gen output bean
	 */
	private String getOutputWriter(String baseSourcePath,String projectCode,String moduleCode,String entityCode){
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
			.append("domain")
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
			.append("domain")
			.append(File.separator)
            .append("model")
			.append(File.separator)
			.append("common")
			.append(File.separator)
			.append(GenerateSourceCodeUtil.normalizedClassName(entityCode))
			.append("OutputBean.java");
		}
		return pathBuilder.toString();
		//return this.getWriter(pathBuilder.toString());
	}
	
	private String getMyBaticConfigWrite(String baseSourcePath){
		StringBuilder path = new StringBuilder(baseSourcePath);
		path.append(File.separator).append("src").append(File.separator).append("main").append(File.separator).append("resources").append(File.separator).append("META-INF").append(File.separator).append("mybatis").append(File.separator).append("mybatis-config.xml");
		return path.toString();
		//return this.getWriter(path.toString());
	}
	
	public void handle(GenerateSourceCode generateSourceCode, CommonModel common) {
		Map<String, Object> data = null;
		List<SqlDesignCompound> sqlDesignCompounds = null;
		SqlDesignCompound sqlDesignCompound = null;
		List<SqlDesign> sqlDesigns = null;
		List<AutocompleteDesign> autocompleteDesigns = null;

		String bbType = (generateSourceCode.getProject().getDbType() == null)?DbDomainConst.DatabaseType.PostgreSQL.toString(): generateSourceCode.getProject().getDbType().toString();
		
		if(DataTypeUtils.equals(generateSourceCode.getScopeGenerateSource(),GenerateSourceCodeConst.PROJECT_SCOPRE)){
			sqlDesigns = sqlDesignRepository.getAllSqlDesignByProjectIdWithCommon(generateSourceCode.getProject().getProjectId(), DbDomainConst.FunctionType.ONLINE);
			autocompleteDesigns = autocompleteDesignRepository.getAllAutocompleteDesignByProjectId(generateSourceCode.getProject().getProjectId());
		} else if(DataTypeUtils.equals(generateSourceCode.getScopeGenerateSource(),GenerateSourceCodeConst.MODULE_SCOPRE)){
			sqlDesigns = new ArrayList<SqlDesign>();
			autocompleteDesigns = new ArrayList<AutocompleteDesign>();

			// for common
			sqlDesigns.addAll(sqlDesignRepository.getAllSqlDesignByModuleId(null,generateSourceCode.getProject().getProjectId()));
			autocompleteDesigns.addAll(autocompleteDesignRepository.getAllAutocompleteDesignByModuleId(null,generateSourceCode.getProject().getProjectId()));
			for(int i=0; i < generateSourceCode.getModules().size(); i++){
				if(generateSourceCode.getModules().get(i).getModuleType() == DbDomainConst.FunctionType.ONLINE){
					sqlDesigns.addAll(sqlDesignRepository.getAllSqlDesignByModuleId(generateSourceCode.getModules().get(i).getModuleId(),generateSourceCode.getProject().getProjectId()));
					autocompleteDesigns.addAll(autocompleteDesignRepository.getAllAutocompleteDesignByModuleId(generateSourceCode.getModules().get(i).getModuleId(),generateSourceCode.getProject().getProjectId()));
				}
			}
		}
		
		Map<String,List<SqlDesignCompound>> modules = new HashMap<String, List<SqlDesignCompound>>();
		boolean isRemoveCommonRepository = true;
		for(SqlDesign sqlDesign: sqlDesigns){
			
			if(sqlDesign.getDesignType()==2||sqlDesign.getDesignType()==3){
				sqlDesignCompound = autocompleteDesignService.findCompoundById(sqlDesign.getSqlDesignId());
			} else {
				sqlDesignCompound = sqlDesignService.findCompoundForGenerationById(sqlDesign.getSqlDesignId());
			}
			
			sqlDesignCompound.getSqlDesign().setSqlPatternString(CL_SQL_SQLPATTERN.asMap().get(String.valueOf(sqlDesign.getSqlPattern())));
			sqlDesignCompound.getSqlDesign().setSqlText(sqlBuilderService.buildSql(sqlDesignCompound, bbType, common));
			sqlDesignCompound.setDialect(bbType);
			if(StringUtils.isNotBlank(sqlDesign.getModuleCode())){
				if(!modules.containsKey(sqlDesign.getModuleCode())){
					modules.put(sqlDesign.getModuleCode(), new ArrayList<SqlDesignCompound>());
				}
				modules.get(sqlDesign.getModuleCode()).add(sqlDesignCompound);
			} else {
				isRemoveCommonRepository = false;
				if(!modules.containsKey(StringUtils.EMPTY)){
					modules.put(StringUtils.EMPTY, new ArrayList<SqlDesignCompound>());
				}
				modules.get(StringUtils.EMPTY).add(sqlDesignCompound);
			}
		}
		
		for(AutocompleteDesign autocompleteDesign: autocompleteDesigns){
			sqlDesignCompound = autocompleteDesignService.findCompoundById(autocompleteDesign.getAutocompleteId());
			sqlDesignCompound.getSqlDesign().setAutoCompleteId(autocompleteDesign.getAutocompleteId().toString());
			sqlDesignCompound.getSqlDesign().setSqlText(sqlBuilderService.buildSql(sqlDesignCompound,bbType, common));
			sqlDesignCompound.getSqlDesign().setSqlPatternString(CL_SQL_SQLPATTERN.asMap().get(String.valueOf(sqlDesignCompound.getSqlDesign().getSqlPattern())));
			sqlDesignCompound.setDialect(bbType);
			if(StringUtils.isNotBlank(autocompleteDesign.getModuleCode())){
				if(!modules.containsKey(autocompleteDesign.getModuleCode())){
					modules.put(autocompleteDesign.getModuleCode(), new ArrayList<SqlDesignCompound>());
				}
				modules.get(autocompleteDesign.getModuleCode()).add(sqlDesignCompound);
			} else {
				if(!modules.containsKey(StringUtils.EMPTY)){
					modules.put(StringUtils.EMPTY, new ArrayList<SqlDesignCompound>());
				}
				modules.get(StringUtils.EMPTY).add(sqlDesignCompound);				
			}
		}
		this.addCommonSql(modules);
		
		for (Map.Entry<String, List<SqlDesignCompound>> moduleEntry : modules.entrySet()) {
			sqlDesignCompounds = moduleEntry.getValue();
			//sqlMapWriter = this.getSqlMapWriter(generateSourceCode.getSourcePathDomain(),generateSourceCode.getProject().getPackageName(),moduleEntry.getKey());
			String sqlMapPath = this.getSqlMapWriter(generateSourceCode.getSourcePathDomain(),generateSourceCode.getProject().getPackageName(),moduleEntry.getKey());
			//repositoryWriter = this.getRepositoryWriter(generateSourceCode.getSourcePathDomain(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey());
			String repositoryPath = getRepositoryWriter(generateSourceCode.getSourcePathDomain(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey());
			if(StringUtils.isNotBlank(sqlMapPath) & CollectionUtils.isNotEmpty(sqlDesignCompounds)){
				for(int i=0;i<sqlDesignCompounds.size();i++){
					sqlDesignCompound = sqlDesignCompounds.get(i);
					this.processDataForGeneration(sqlDesignCompound);
					data = new HashMap<String, Object>();
					data.put("sqlDesignCompound", sqlDesignCompound);
					data.put("projectCode", generateSourceCode.getProject().getPackageName());
					data.put("moduleCode", moduleEntry.getKey());
					//inputWriter = this.getInputWriter(generateSourceCode.getSourcePathDomain(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),sqlDesignCompound.getSqlDesign().getSqlDesignCode());
					String inputPath = this.getInputWriter(generateSourceCode.getSourcePathDomain(), generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),sqlDesignCompound.getSqlDesign().getSqlDesignCode());
					if(StringUtils.isNotBlank(inputPath)){
						try {
							if(sqlDesignCompound.getSqlDesign().getDesignType() != SQLDesignType.AUTOCOMPLETE && sqlDesignCompound.getSqlDesign().getDesignType() != SQLDesignType.ADVANCED_AUTOCOMPLETE){
								this.process(data, INPUTMODEL_TEMPLATE, inputPath);
							}
						} catch (Exception e) {
							GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
							GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
						}
						//inputTemplate.process(data, inputWriter);
					}
					
					if(sqlDesignCompound.getSqlDesign().getDesignType() == 4 || sqlDesignCompound.getSqlDesign().getDesignType() == 5){
						if(sqlDesignCompound.getSqlDesign().getSqlPattern() == SqlPattern.SELECT){
							if(ArrayUtils.isNotEmpty(sqlDesignCompound.getSqlDesignOutputs())){
								String outputPath = this.getOutputWriter(generateSourceCode.getSourcePathDomain(), 
										generateSourceCode.getProject().getPackageName(), 
										moduleEntry.getKey(),
										sqlDesignCompound.getSqlDesign().getSqlDesignCode());
								if(StringUtils.isNotBlank(outputPath)){
									try {
										this.process(data, OUTPUTMODEL_TEMPLATE, outputPath);
									} catch (Exception e) {
										GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
										GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
									}
									//outputTemplate.process(data, outputWriter);
								}
							}
						}
					}
					Map<SqlDesignInput,List<SqlDesignInput>> sqlInputObjects = this.getInputObjects(sqlDesignCompound);
					//Fix package gen source Object
					for (Map.Entry<SqlDesignInput,List<SqlDesignInput>> inputEntry : sqlInputObjects.entrySet()){
					    if (DataTypeUtils.equals(DbDomainConst.DesignType.DESIGN_TYPE, inputEntry.getKey().getDesignType())) {
					      /*  inputBeanWriter = this.getInputObjectWriter(generateSourceCode.getSourcePathDomain(),
	                                generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),
	                                inputEntry.getKey().getSqlDesignInputCode(),
	                                sqlDesignCompound.getSqlDesign().getSqlDesignCode() + INPUTBEAN_SUFFIX);*/
					    	String pathInputBean = this.getInputObjectWriter(generateSourceCode.getSourcePathDomain(),
	                                generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),
	                                inputEntry.getKey().getSqlDesignInputCode(),
	                                sqlDesignCompound.getSqlDesign().getSqlDesignCode() + INPUTBEAN_SUFFIX);
	                        data = new HashMap<String, Object>();
	                        data.put("parentCode",inputEntry.getKey().getSqlDesignInputCode());
	                        data.put("sqlDesignInputs", inputEntry.getValue());
	                        data.put("projectCode", generateSourceCode.getProject().getPackageName());
	                        data.put("moduleCode", moduleEntry.getKey());
	                        data.put("sqlDesign", sqlDesignCompound.getSqlDesign());
	                        if(StringUtils.isNotBlank(pathInputBean)){
                            	try {
                            		this.process(data, INPUTBEAN_TEMPLATE, pathInputBean);          		
								} catch (Exception e) {
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
								}
                                //inputBeanTemplate.process(data, inputBeanWriter);
	                        }
						    }else {
						        //do nothing
						    }
					}
					
					Map<SqlDesignOutput,List<SqlDesignOutput>> sqlOutputObjects = this.getOutputObjects(sqlDesignCompound);
					for (Map.Entry<SqlDesignOutput,List<SqlDesignOutput>> outputEntry : sqlOutputObjects.entrySet()){
					    if (DataTypeUtils.equals(DbDomainConst.DesignType.DESIGN_TYPE, outputEntry.getKey().getDesignType())) {
					       /* outputBeanWriter = this.getOutputObjectWriter(generateSourceCode.getSourcePathDomain(),
	                                generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),
	                                outputEntry.getKey().getSqlDesignOutputCode(),
	                                sqlDesignCompound.getSqlDesign().getSqlDesignCode() + OUTPUTBEAN_SUFFIX);*/
					    	String outputBeanPath = this.getOutputObjectWriter(generateSourceCode.getSourcePathDomain(),
	                                generateSourceCode.getProject().getPackageName(), moduleEntry.getKey(),
	                                outputEntry.getKey().getSqlDesignOutputCode(),
	                                sqlDesignCompound.getSqlDesign().getSqlDesignCode() + OUTPUTBEAN_SUFFIX);
	                        data = new HashMap<String, Object>();
	                        data.put("parentCode",outputEntry.getKey().getSqlDesignOutputCode());
	                        data.put("sqlDesignOutputs", outputEntry.getValue());
	                        data.put("projectCode", generateSourceCode.getProject().getPackageName());
	                        data.put("moduleCode", moduleEntry.getKey());
	                        data.put("sqlDesign", sqlDesignCompound.getSqlDesign());
	                        if(StringUtils.isNotBlank(outputBeanPath)){
	                            try {
									this.process(data, OUTPUTBEAN_TEMPLATE, outputBeanPath);
								} catch (Exception e) {
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
									GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
								}
	                        }
					    } else {
					        //Do nothing
					    }
					}
				}
				data = new HashMap<String, Object>();
				data.put("sqlDesignCompounds", sqlDesignCompounds);
				data.put("projectCode", generateSourceCode.getProject().getPackageName());
				data.put("moduleCode", moduleEntry.getKey());
				if(StringUtils.isNotBlank(sqlMapPath)){
					//sqlmapTemplate.process(data, sqlMapWriter);
					try {
						this.process(data, SQLMAP_TEMPLATE, sqlMapPath);
					} catch (Exception e) {
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
					}
				}
				if(StringUtils.isNotBlank(repositoryPath)){
					try {
						this.process(data, REPOSITORY_TEMPLATE, repositoryPath);
					} catch (Exception e) {
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(),"Sql design code" + sqlDesignCompound.getSqlDesign().getSqlDesignCode());
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
					}
					//repositoryTemplate.process(data, repositoryWriter);
				}
			}
		}
		
		generateMyBaticConfig(generateSourceCode ,isRemoveCommonRepository, common);
		
	}
	
	
	
	private void generateMyBaticConfig(GenerateSourceCode generateSourceCode ,boolean isRemoveCommonRepository, CommonModel common){
		String baseSourcePath = generateSourceCode.getSourcePathDomain();
		String packageName = generateSourceCode.getProject().getPackageName();
		Project project = this.projectRepository.findById(common.getWorkingProjectId(), common.getCreatedBy());
		boolean isOracle = false;
		if(DbDomainConst.DatabaseType.ORACLE.equals(project.getDbType())){
			isOracle = true;
		}
		Map<String, Object> data = new HashMap<>();
		data.put("isOracle", isOracle);
		data.put("packageName", packageName);
		data.put("isRemoveCommonRepository", isRemoveCommonRepository);
		//template.process(data, getMyBaticConfigWrite(baseSourcePath));
		String mybaticConfigPath = getMyBaticConfigWrite(baseSourcePath);
		try {
			this.process(data, MYBATIC_CONFIG_TEMPLATE, mybaticConfigPath);
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
	
	}
	
	private void addCommonSql(Map<String, List<SqlDesignCompound>> modules) {
		List<SqlDesignCompound> commonSql = null;
		if(!modules.containsKey(StringUtils.EMPTY)){
			commonSql = new ArrayList<SqlDesignCompound>();
			modules.put(StringUtils.EMPTY,commonSql);
		} else {
			commonSql = modules.get(StringUtils.EMPTY);
		}
		commonSql.add(this.buildCountRowSql());
		
		
	}
	private SqlDesignCompound buildCountRowSql(){
		SqlDesignCompound sqlDesignCompound = new SqlDesignCompound();
		
		SqlDesignInput[] sqlDesignInputs = new SqlDesignInput[2];
		SqlDesignInput sqlDesignInput = new SqlDesignInput();
		sqlDesignInput.setSqlDesignInputCode("tableCode");
		sqlDesignInput.setArrayFlag(0);
		sqlDesignInput.setDesignType(0);
		sqlDesignInput.setDataType(DataTypeUtils.convertTo(SQLDataType.String,Integer.class));
		sqlDesignInputs[0] = sqlDesignInput;
		sqlDesignInput = new SqlDesignInput();
		sqlDesignInput.setArrayFlag(1);
		sqlDesignInput.setDesignType(0);
		sqlDesignInput.setSqlDesignInputCode("conditions");
		sqlDesignInput.setSqlDesignInputName("Conditions");
		sqlDesignInput.setPackageName("org.terasoluna.qp.domain.model");
		sqlDesignInput.setDataType(BusinessDesignConst.DataType.ENTITY);
		sqlDesignInputs[1] = sqlDesignInput;
		
		sqlDesignCompound.setSqlDesignInputs(sqlDesignInputs);
		
		SqlDesign sqlDesign = new SqlDesign();
		sqlDesign.setSqlDesignCode(SQL_COUNT_CODE);
		sqlDesign.setDesignType(SQLDesignType.ADVANCED_SQL);
		sqlDesign.setSqlPattern(SqlPattern.SELECT);
		sqlDesign.setReturnType(0);
		try {
			sqlDesign.setSqlText(getTemplate(SQL_COUNT_TEMPLATE).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sqlDesignCompound.setSqlDesign(sqlDesign);
		return sqlDesignCompound;
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
	
	/*public void initData(Long projectId, Long languageId, Long accountId, Project project) {
		sqlBuilderService.initData(projectId, languageId, accountId);
		sqlBuilderService.setWorkingProject(project);
	}*/
}
