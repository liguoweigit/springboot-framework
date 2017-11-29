#!/bin/bash
SHELL_FOLDER=$( cd -P "$( dirname "$0" )" && pwd )

ROOT_PATH=$(cd -P $SHELL_FOLDER"/.." && pwd)

SERVICE_NAME=set-web
## Adjust log dir if necessary
LOG_DIR=/yongche/logs
PID=0
## Adjust server port if necessary
SERVER_PORT=8080

CLASSPATH_NAME="config"

PATH_CLASSPATH=$ROOT_PATH"/"$CLASSPATH_NAME

## Adjust memory settings if necessary
#export JAVA_OPTS="-Xms6144m -Xmx6144m -Xss256k -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=384m -XX:NewSize=4096m -XX:MaxNewSize=4096m -XX:SurvivorRatio=8"

## Only uncomment the following when you are using server jvm
#export JAVA_OPTS="$JAVA_OPTS -server -XX:-ReduceInitialCardMarks"

########### The following is the same for configservice, adminservice, portal ###########
export JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:ParallelGCThreads=4 -XX:MaxTenuringThreshold=9 -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC -XX:+UseCMSInitiatingOccupancyOnly -XX:+ScavengeBeforeFullGC -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=9 -XX:CMSInitiatingOccupancyFraction=60 -XX:+CMSClassUnloadingEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSPermGenSweepingEnabled -XX:CMSInitiatingPermOccupancyFraction=70 -XX:+ExplicitGCInvokesConcurrent -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintHeapAtGC -XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom"
export JAVA_OPTS="$JAVA_OPTS -Dserver.port=$SERVER_PORT -Dlogging.file=$LOG_DIR/$SERVICE_NAME.log -Xloggc:$LOG_DIR/heap_trace.txt -XX:HeapDumpPath=$LOG_DIR/HeapDumpOnOutOfMemoryError/"

PATH_TO_JAR=$ROOT_PATH"/"$SERVICE_NAME".jar"
SERVER_URL="http://localhost:$SERVER_PORT"

function checkPidAlive {
    P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
}

if [ "$(uname)" == "Darwin" ]; then
    windows="0"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    windows="0"
elif [ "$(expr substr $(uname -s) 1 5)" == "MINGW" ]; then
    windows="1"
else
    windows="0"
fi

# for Windows
if [ "$windows" == "1" ] && [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]]; then
    tmp_java_home=`cygpath -sw "$JAVA_HOME"`
    export JAVA_HOME=`cygpath -u $tmp_java_home`
    echo "Windows new JAVA_HOME is: $JAVA_HOME"
fi

startup(){
    printf "\n$(date) ==== Starting ==== \n"
    printf "\nROOT_PATH: $ROOT_PATH \n"
    printf "\nCLASSPATH:$PATH_CLASSPATH \n"
    checkPidAlive
    if [ -n "$P_ID" ]; then
      printf "\n$SERVICE_NAME already started(PID=$P_ID) \n"
      return 0
    fi
    #ln "-s" $PATH_TO_JAR $SERVICE_NAME".jar"
    #ln "-s" $PATH_CLASSPATH $LN_CLASSPATH
    chmod a+x $PATH_TO_JAR
    nohup java $JAVA_OPTS "-Dloader.path="$PATH_CLASSPATH -jar $PATH_TO_JAR >/dev/null 2>error_log &
    printf "\nPATH_TO_JAR:$PATH_TO_JAR \n"

    declare -i counter=0
    declare -i max_counter=48 # 48*5=240s
    declare -i total_time=0

    printf "\nWaiting for server startup\n"
    until [[ (( counter -ge max_counter )) || "$(curl -X GET --silent --connect-timeout 1 --max-time 2 --head $SERVER_URL | grep "Coyote")" != "" ]];
    do
        printf "..."
        counter+=1
        sleep 5
        checkPidAlive
        if [ $P_ID -ne 0 ]; then
            printf "\nSERVICE=$SERVICE_NAME STARTUP SUCCESS (PID=$P_ID)...\n"
            printf "\n=========================================================\n"
            return 0
        else
            printf "\n[Failed]\n"
            printf "\n=========================================================\n"
            return 0
        fi
    done

    total_time=counter*5

    if [[ (( counter -ge max_counter )) ]];
    then
        printf "\n$(date) Server failed to start in $total_time seconds!\n"
        exit 1;
    fi

    printf "\n$(date) Server started in $total_time seconds!\n"

    exit 0;
}
startup