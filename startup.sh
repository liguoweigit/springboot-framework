#!/bin/bash
ROOT_PATH=/Users/mma/yongche/workspace/yongche-set/set-web/target/
FILE_PATH=$ROOT_PATH"set-web-1.0-SNAPSHOT-yongche.zip"
UNZIP_DIR=/yongche/app
DATA_NAME=$(date +%Y%m%d-%H)
SERVICE_NAME=set-web
P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
start(){
    if [ -n "$P_ID" ]; then
      echo "$SERVICE_NAME already started(PID=$P_ID)"
      return 0
    fi
    if [ "`ls -A $UNZIP_DIR`" = "" ]; then
      echo "$UNZIP_DIR is indeed empty,starting service..."
    else
      echo "$UNZIP_DIR is not empty,backup to /tmp..."
      zip -q -r -m set-web-$DATA_NAME.zip $UNZIP_DIR
      sleep 3
      mv $UNZIP_DIR"/"set-web-$DATA_NAME.zip "/"tmp"/"
    fi

    if [ ! -x "$UNZIP_DIR" ]; then
      mkdir -p "$UNZIP_DIR"
      chmod -R a+x $UNZIP_DIR
    fi
    unzip -q $FILE_PATH -d $UNZIP_DIR
    sleep 3
    echo "starting service ..."
    sh $UNZIP_DIR/scripts/startup.sh
}

stop(){
   echo "stoping service ..."
    sh $UNZIP_DIR/scripts/shutdown.sh
}
status(){
   if [ -z "$P_ID" ]; then
      echo "not find service:"$SERVICE_NAME
   else
      echo "service:"$SERVICE_NAME" is running,pid:$P_ID"
   fi
}

case $1 in
   start)
      start
   ;;
   stop)
      stop
   ;;
   restart)
      $0 stop
      sleep 2
      $0 start
    ;;
   status)
      status
   ;;
   *)
      echo "Usage: {start|stop|status}"
   ;;
esac

exit 0