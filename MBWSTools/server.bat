@echo off
set JAVA=%JAVA_HOME%\bin\java
set cp=%CLASSPATH%
for %%i in (lib\*.jar) do call cp.bat %%i
set cp=%cp%;.


%JAVA% -classpath %CP% -Djavax.management.builder.initial=mx4j.server.MX4JMBeanServerBuilder de.mbws.server.ServerStarter





