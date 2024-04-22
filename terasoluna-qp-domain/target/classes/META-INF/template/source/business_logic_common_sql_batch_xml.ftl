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
    <!-- コンポーネントスキャン設定 -->
       
   <!-- Sql common configuration  -->
	<context:component-scan base-package="${package}.batch.repository.common"/>

	<bean class="org.mybatis.spring.mapper.MapperFactoryBean">
        <property name="mapperInterface"
            value="${package}.batch.repository.common.CommonRepository" />
        <property name="sqlSessionTemplate" ref="sqlSessionTemplate" />
    </bean>

</beans>