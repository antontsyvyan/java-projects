@echo off
setLocal EnableDelayedExpansion

rem if not "%JAVA_HOME%" == "" goto SET_JAVA

rem set JAVA=java

:SET_JAVA
rem set JAVA=%JAVA_HOME%\bin\java

CD ..
set JAVA=.\jre8\bin\java
rem add all jar files from lib to the Classpath
FOR %%X in ("%CD%\lib\*.jar") DO (
	set CLASSPATH=%%~dpnfX;!CLASSPATH!
)

REM JPDA options. Uncomment and modify as appropriate to enable remote debugging.
set JAVA_OPTS=%JAVA_OPTS%  -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n %JAVA_OPTS%

echo ===============================================================================
echo   DIR: %DIR_NAME%\test
echo .
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


%JAVA%  %JAVA_OPTS% -classpath "%CLASSPATH%"  ExchangeRateService