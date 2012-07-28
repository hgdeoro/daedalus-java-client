Daedalus
----------------------------------------

Daedalus is a __Django__ application to store log messages on __Cassandra__.
The messages are sent using HTTP POST.

This is the Java client to send messages to Daedalus.

The Daedalus project is hosted at [GitHub](https://github.com/hgdeoro/daedalus).


Developing
----------------------------------------

This is developed with Maven and Eclipse.

To download the jars to be uses with Eclipse, you must run:

    mvn dependency:copy-dependencies

now all the requiered JARs should be accesible by Eclipse.
