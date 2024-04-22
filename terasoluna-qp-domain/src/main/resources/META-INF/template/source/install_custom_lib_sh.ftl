#!/bin/bash

echo "PATH in install custom lib: $PATH"

${r"called_path=${0%/*}"}
${r"real_path=${called_path#[^/]*}"}

echo "build war"
echo "real_path: $real_path"

cd $real_path

<#if dbType == 2>
	mvn install:install-file -DgroupId=oracle -DartifactId=ojdbc6 -Dversion=11.2.0.2.0 -Dpackaging=jar -Dfile=ojdbc6-11.2.0.2.0.jar
</#if>

<#list customLibs as item>
	mvn install:install-file -DgroupId=${item.groupId} -DartifactId=${item.groupId} -Dversion=${item.version} -Dpackaging=${item.type} -Dfile=${item.getFileName()}
</#list>