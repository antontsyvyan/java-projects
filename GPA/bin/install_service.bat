@echo off
rem setLocal EnableDelayedExpansion
CD ..

rem set classpath=%CD%\lib\gpa.jar;%CD%\lib\mysql-connector-java-3.0.10-stable-bin.jar;%CD%\lib\openedge.jar;%CD%\lib\util.jar;%CD%\lib\base.jar;

rem add all jar files from lib to the Classpath
rem FOR %%X in ("%CD%\lib\*.jar") DO (
rem	set CLASSPATH=%%~dpnfX;!CLASSPATH!
rem )

set "CLASSPATH=./lib/*"

set class=com.foursoft.gpa.Main
set method=windowsService
set service=%CD%\prunsrv.exe
set log_path=%CD%\log
set prefix=procrun
rem set java=auto

java=%CD%\jre8

set SERVICE_NAME="GPAService"
rem Uninstall service first
prunsrv //DS//%SERVICE_NAME%

prunsrv.exe //IS//%SERVICE_NAME% ^
	--Install=%service% ^
	--DisplayName=%SERVICE_NAME% ^
	--Description=%SERVICE_NAME% ^
	--JavaHome=%java% ^
	--Jvm=%CD%\jre8\bin\server\jvm.dll ^
	--Classpath=%CLASSPATH% ^
	--Startup=auto ^
	--StartMode=jvm ^
	--StartClass=%class% ^
	--StartMethod=%method% ^
	--StartParams=start ^
	--StopMode=jvm ^
	--StopClass=%class% ^
	--StopMethod=%method% ^
	--StopParams=stop ^
	--LogPath=%log_path% ^
	--LogPrefix=%prefix% ^
	--StdOutput=%log_path%\%prefix%_output.log ^
	--StdError=%log_path%\%prefix%_error.log ^
	++JvmOptions=-Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n 
	pause