Setting up for Development
--------------------------
1) cd into where you checked out the project
2) mvn eclipse:eclipse

Building
--------
1) cd into where you checked out the project
2) mvn clean && mvn package && mvn install && mvn assembly:assembly

Debugging
---------
1) cd into target
2) Unzip perpetuum-1.0-snapshot-dev.zip (Or similar .zip)
3) Refresh Eclipse project in Eclipse
4) In Eclipse Run > Run... > New Java Application
5) Remove project Reference in Classpath tab
6) Add reference to target/perpetuum-1.0.jar
7) For program arguments, use start/stop for respective commands

All of this will be fixed within the next day or so.  Since the project uses
.properties files for the commands, that is why you have to go through all 
of this.  This file will be updated when these are fixed.

Running With JDK 1.5
--------------------
Since JDK 1.5 comes with JMX/JMX Remoting classes bundled with them, you need
to do a little tweaking to work properly, per the MX4J documentation.

Add all mx4j, log4j and logkit jars to the boot classpath.