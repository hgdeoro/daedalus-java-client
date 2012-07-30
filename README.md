This project
----------------------------------------

This project implements a __Java__ client to send messages to [Daedalus](https://github.com/hgdeoro/daedalus).

You can [download the the binary](https://github.com/hgdeoro/daedalus-java-client/downloads), or
download the sources and compile'em yourself.

Daedalus
----------------------------------------

Daedalus is client/server application. The __server__ (what receives the messages) is implemented
using __Django__. The log messages are stored in __Cassandra__. The __clients__ send the messages
using a POST messages, allowing to send messages from virtually any language.

Please, [report any issue here](https://github.com/hgdeoro/daedalus/issues).

Developing
----------------------------------------

This is developed with Maven and Eclipse.

To download the jars to be uses with Eclipse, you must run:

    mvn dependency:copy-dependencies

now all the requiered JARs should be accesible by Eclipse.

To create the JAR, you must run:

    mvn package

and the JARs will be at target/DaedalusJavaClient-1.0-SNAPSHOT.jar

And to run the tests, I use:

    mvn --quiet -Dsurefire.useFile=false test

