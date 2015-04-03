@echo off
setLocal EnableDelayedExpansion

CD ..

set JAVA=%CD%\jre8\bin\java

rem add all jar files from lib to the Classpath
FOR %%X in ("%CD%\lib\*.jar") DO (
	set CLASSPATH=%%~dpnfX;!CLASSPATH!
)

set JAVA_OPTS=%JAVA_OPTS% -Xms128m -Xmx512m

REM JPDA options. Uncomment and modify as appropriate to enable remote debugging.
set JAVA_OPTS=%JAVA_OPTS%  -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8001,server=y,suspend=n %JAVA_OPTS%

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
echo .

%JAVA%  %JAVA_OPTS% -classpath "%CLASSPATH%"  com.foursoft.gpa.clientfx.Dashboard

pause