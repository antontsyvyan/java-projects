@echo off
setLocal EnableDelayedExpansion

rem if not "%JAVA_HOME8%" == "" goto SET_JAVA

rem set JAVA=java

:SET_JAVA
rem set JAVA=%JAVA_HOME8%\bin\java

CD ..

set JAVA=.\jre8\bin\java

set CLASSPATH="./lib/*"

set LOG4J_PROPERTIES=file:.\conf\log4j.properties

set JAVA_OPTS=%JAVA_OPTS% -Xms128m -Xmx512m

REM JPDA options. Uncomment and modify as appropriate to enable remote debugging.
set JAVA_OPTS=%JAVA_OPTS%  -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8001,server=y,suspend=n 

REM log4j setup for Jasper reports
set JAVA_OPTS=%JAVA_OPTS% -Dlog4j.configuration=%LOG4J_PROPERTIES% 

echo ===============================================================================
echo   Environment
echo .
echo   JAVA: %JAVA%
echo .
echo   JAVA_OPTS: %JAVA_OPTS%
echo .
echo   CLASSPATH: %CLASSPATH%
echo .
echo ===============================================================================


%JAVA%  %JAVA_OPTS%  -classpath "%CLASSPATH%"  com.foursoft.gpa.clientfx.DashboardNew

pause