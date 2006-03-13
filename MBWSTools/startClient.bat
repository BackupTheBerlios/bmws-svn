@echo off
set JAVA=%JAVA_HOME%\bin\java
set cp=%CLASSPATH%
for %%i in (*.jar) do call cp.bat %%i
set cp=%cp%;.
for %%i in (lib\*.jar) do call cp.bat %%i
set cp=%cp%;.


JAVA -classpath %CP% -Djava.library.path=./lib; de.mbws.client.MBWSClient clientremote.properties





