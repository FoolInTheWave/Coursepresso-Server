# Coursepresso-Server

Course schedule management software server built using the following technologies:

* Java 8
* Maven
* MySQL
* Spring
* Spring JDBC
* Spring Web MVC
* Spring Data JPA
* Spring Security
* Hibernate
* Simple Logging Facade for Java (SLF4J)
* Tomcat 8


Provides service implementations for the Coursepresso Client application.  This server application can be deployed on a Tomcat instance (Tomcat 8 or above).  Please note that the Coursepresso Client application is already configured to connect to the Tomcat instance hosted on Amazon AWS.  If you decide to run Coursepresso Server from your own Tomcat instance, you must configure the client application accordingly:

The server URL in the Courspresso Client application is located in the createService() method in the ServiceConfig class, in the package 'com.coursepresso.project'.  Note that you need to change this URL only if you decide to run Coursepresso Server on your own Tomcat instance.

To deploy this application on a Tomcat instance simply build the application in an IDE (i.e. Netbeans, Eclipse, IntelliJ) and upload the WAR file found in the target file (coursepresso.war) to the Tomcat instance.  It’s that simple.
