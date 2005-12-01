#! /bin/sh

if [ -z "$JAVA_HOME" ] ; then
  JAVA=`which java`
  if [ -z "$JAVA" ] ; then
    echo "Cannot find JAVA. Please set your PATH."
    exit 1
  fi
  JAVA_BIN=`dirname $JAVA`
  JAVA_HOME=$JAVA_BIN/..
fi

JAVA=$JAVA_HOME/bin/java

CLASSPATH=`echo lib/*.jar | tr ' ' ':'`:$CLASSPATH:.

$JAVA -cp $CLASSPATH -Djavax.management.builder.initial=mx4j.server.MX4JMBeanServerBuilder de.mbws.server.ServerStarter
