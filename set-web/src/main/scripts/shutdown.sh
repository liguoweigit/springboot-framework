#!/bin/bash
SERVICE_NAME=set-web
P_ID=0
function checkPidAlive {
    P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
    if [ ! "$P_ID" ]; then
        P_ID=0
    fi
}
shutdown(){
    checkPidAlive
    echo "================================================================================================================"
    if [ $P_ID -ne 0 ]; then
        echo "Stopping $SERVICE_NAME(PID=$P_ID)..."
        kill -15 $P_ID
        if [ $? -eq 0 ]; then
            echo "[Success]"
            echo "================================================================================================================"
        else
            echo "[Failed]"
            echo "================================================================================================================"
        fi
	sleep 5s
        checkPidAlive
        if [ $P_ID -ne 0 ]; then
            shutdown
        fi
    else
        echo "$SERVICE_NAME is not running"
        echo "================================================================================================================"
    fi
}

shutdown
