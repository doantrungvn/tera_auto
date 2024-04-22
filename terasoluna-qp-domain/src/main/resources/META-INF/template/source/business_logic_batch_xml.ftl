<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:util="http://www.springframework.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- アノテーションによる設定 -->
    <context:annotation-config/>

	<!-- Loading all bean using property placeholder -->
	<import resource="classpath:beansDef/placeHolderCustomContext.xml" />
	
    <!-- 共通コンテキスト(フレームワークの共通機能を使う場合、かならずインポートすること。)  -->
    <import  resource="classpath:beansDef/commonContext.xml" />

    <!-- データソース設定  -->
    <!-- <import  resource="classpath:beansDef/dataSource.xml" /> -->

	<#if isExistSqlCommon == true>
	<!-- DAO Common Configuration  -->
	<import resource="classpath:beansDef/commonSqlContext.xml" />
	</#if>
	
	<!-- Bean of blogic common is not customize  -->
    <bean id="businessLogicCommonService" class="${package}.batch.service.common.BusinessLogicCommonServiceImpl"/>
    
    <#if isExistDecision == true>
    <!-- Bean of decision service  -->
    <bean id="decisionLogicDesignCommonService" class="${package}.batch.decision.DecisionLogicDesignCommonServiceImpl"/>
    </#if>
    
    <!-- コンポーネントスキャン設定 -->
    <context:component-scan base-package="${package}.batch.${module.moduleCode?uncap_first}.${item.businessLogicCode}"/>
    
    <#if isExistSql == true>
    <!-- Repository設定 -->
    <bean class="org.mybatis.spring.mapper.MapperFactoryBean">
        <property name="mapperInterface"
            value="${package}.batch.repository.${module.moduleCode?lower_case}.${module.moduleCode?cap_first}Repository" />
        <property name="sqlSessionTemplate" ref="sqlSessionTemplate" />
    </bean>
    </#if>

</beans>