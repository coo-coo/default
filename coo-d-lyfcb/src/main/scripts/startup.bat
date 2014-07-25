cd ../lib
java -Xms1024M -Xmx1024M -XX:MaxPermSize=512M -jar ${pom.artifactId}-${pom.version}.jar
pause