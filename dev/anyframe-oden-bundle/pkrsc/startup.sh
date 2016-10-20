PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
 
PRGDIR=`dirname "$PRG"`
ARGS="-Xmx256m -XX:NewRatio=1 -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=25 -Dfile.encoding=utf-8"

ODENF=`ls $PRGDIR | grep jar`
java $ARGS -jar ${PRGDIR}/${ODENF} &