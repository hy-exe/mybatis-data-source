#!/bin/sh

FINDNAME=$0
while [ -h $FINDNAME ] ; do FINDNAME=`ls -ld $FINDNAME | awk '{print $NF}'` ; done
SERVER_HOME=`echo $FINDNAME | sed -e 's@/[^/]*$@@'`
unset FINDNAME

if [ "$SERVER_HOME" = '.' ]; then
   SERVER_HOME=$(echo `pwd` | sed 's/\/bin//')
else
   SERVER_HOME=$(echo $SERVER_HOME | sed 's/\/bin//')
fi


HEAP_MEMORY=256m
PERM_MEMORY=64m
    
case $1 in
start)

    JAVA_OPTS="-server -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider -XX:+HeapDumpOnOutOfMemoryError -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
    
    shift
    ARGS=($*)
    for ((i=0; i<${#ARGS[@]}; i++)); do
        case "${ARGS[$i]}" in
        -D*)    JAVA_OPTS="${JAVA_OPTS} ${ARGS[$i]}" ;;
        -Module*) MODULE="${ARGS[$i+1]}" ;;
        -Heap*) HEAP_MEMORY="${ARGS[$i+1]}" ;;
        -Perm*) PERM_MEMORY="${ARGS[$i+1]}" ;;
        -JmxPort*)  JMX_PORT="${ARGS[$i+1]}" ;;
        -HttpPort*)  HTTP_PORT="${ARGS[$i+1]}" ;;
        -JmxHost*)  JMX_HOST="${ARGS[$i+1]}" ;;
        -Log*) LOG="${ARGS[$i+1]}" ;;        
          *) parameters="${parameters} ${ARGS[$i]}" ;;
        esac
    done
    
    JAVA_OPTS="${JAVA_OPTS} -Xms$HEAP_MEMORY -Xmx$HEAP_MEMORY -XX:PermSize=$PERM_MEMORY -XX:MaxPermSize=$PERM_MEMORY -Duser.dir=$SERVER_HOME"
    
    if [ "$JMX_PORT" != '' ]; then
        JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=${JMX_PORT}"
    fi

    if [ "$JMX_HOST" != '' ]; then
        JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.hostname=${JMX_HOST}"
    fi
            
    echo  "Starting ${MODULE} ... "
    if [ "$HTTP_PORT" = '' ]; then
        nohup java $JAVA_OPTS -jar ${SERVER_HOME}/bin/${MODULE}.jar > ${LOG}.out &
    else
        nohup java $JAVA_OPTS -jar ${SERVER_HOME}/bin/${MODULE}.jar -httpPort=${HTTP_PORT} > ${LOG}.out &
    fi
    echo STARTED
    ;;

stop)

    shift
    ARGS=($*)
    for ((i=0; i<${#ARGS[@]}; i++)); do
        case "${ARGS[$i]}" in
        -Module*) MODULE="${ARGS[$i+1]}" ;;
          *) parameters="${parameters} ${ARGS[$i]}" ;;
        esac
    done
    
    echo "Stopping ${MODULE} ... "
    PROID=`ps -ef|grep ${MODULE}.jar|grep -v grep|awk '{print $2}'`
	if [ -n "$PROID" ]; then
  		echo "Kill process id - ${PROID}"
  		kill -9 ${PROID}
  		echo STOPPED
	else
  		echo "No process running."
	fi
    ;;

    
esac

exit 0
