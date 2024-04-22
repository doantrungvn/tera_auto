@ECHO OFF

PUSHD %~DP0..\

REM SET libDir=deployment\lib
SET libDir=deployment\lib

CALL mvn dependency:copy-dependencies -DoutputDirectory=%libDir%
CALL mvn clean compile jar:jar --debug  > log.txt

COPY target\*.jar %libDir%

POPD

pause