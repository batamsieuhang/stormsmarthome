# stormsmarthome
Storm topology to subscribt MQTT broker and analyze smarthome data

<b>Build with maven</b>

      $ mvn install
  
<b>Run and test (Run with windows size 5,10,15,20,30,60,120 mins)</b>

      $ java -jar target/Storm-IOTdata-1.0-SNAPSHOT-jar-with-dependencies.jar
<b>Run inside docker nimbus container</b>
```cmd
storm jar /target/Storm-IOTdata-1.0-SNAPSHOT-jar-with-dependencies.jar com.storm.iotdata.MainTopo
```
Then choose data file.

Note: Output files will be at the same folder with input file.
