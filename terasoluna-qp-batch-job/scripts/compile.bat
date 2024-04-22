@ECHO OFF

PUSHD %~DP0..\..\terasoluna-qp-env
CALL mvn clean install
POPD

PUSHD %~DP0..\..\terasoluna-qp-domain
CALL mvn clean install
POPD

PUSHD %~DP0..\

REM SET libDir=deployment\ForWindows\lib
SET libDir=deployment\ForWindows\lib

CALL mvn dependency:copy-dependencies -DoutputDirectory=%libDir%
CALL mvn clean compile jar:jar --debug  > log.txt

COPY target\*.jar %libDir%

POPD

pause