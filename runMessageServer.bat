java -server -Xms128M -Xmx256M -Xloggc:./gc/com.plutozone.util.MessageServer.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -classpath .\bin com.plutozone.util.messenger.MessageServer