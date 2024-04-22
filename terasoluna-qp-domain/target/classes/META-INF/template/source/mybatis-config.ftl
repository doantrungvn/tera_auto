<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>

	<!-- See http://mybatis.github.io/mybatis-3/configuration.html#settings -->
	
	
	
	<settings>
	
	<setting value="true" name="mapUnderscoreToCamelCase"/>
	
	<setting value="false" name="lazyLoadingEnabled"/>
	
	<setting value="false" name="aggressiveLazyLoading"/>
	
	<!-- <setting name="defaultExecutorType" value="REUSE" /> <setting name="jdbcTypeForNull" value="NULL" /> <setting name="proxyFactory" value="JAVASSIST" /> <setting name="localCacheScope" value="STATEMENT" /> -->
	<#if isOracle>
	<setting name="jdbcTypeForNull" value="NULL" />	
	</#if>
	
	</settings>
	
	
	<typeAliases>
	
		<package name="org.terasoluna.qp.domain.model"/>
		
		<package name="org.terasoluna.qp.domain.repository"/>
		
		<package name="${packageName}.domain.model"/>
		
		<package name="${packageName}.domain.repository"/>
	
		<#if !isRemoveCommonRepository>
		<package name="${packageName}.domain.model.common"/>
		
		<package name="${packageName}.domain.repository.common"/>
		</#if>
	
	<!-- <package name="org.terasoluna.qp.infra.mybatis.typehandler" /> -->
	
	
	</typeAliases>
	
	
	<typeHandlers>
	
		<!-- <package name="org.terasoluna.qp.infra.mybatis.typehandler" /> -->
	
	
	</typeHandlers>

</configuration>