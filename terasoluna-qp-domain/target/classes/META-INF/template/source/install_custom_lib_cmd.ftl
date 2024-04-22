@ECHO OFF

echo PATH:%PATH%

PUSHD %~DP0

<#if dbType == 2>
	call mvn install:install-file -DgroupId=oracle -DartifactId=ojdbc6 -Dversion=11.2.0.2.0 -Dpackaging=jar -Dfile=ojdbc6-11.2.0.2.0.jar 
</#if>

<#list customLibs as item>
	call mvn install:install-file -DgroupId=${item.groupId} -DartifactId=${item.groupId} -Dversion=${item.version} -Dpackaging=${item.type} -Dfile=${item.getFileName()}
</#list>