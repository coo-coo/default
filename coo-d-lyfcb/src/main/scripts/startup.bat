cd ../lib
java -Xms512M -Xmx512M -XX:MaxPermSize=512M -jar ${pom.artifactId}-${pom.version}.jar
pause