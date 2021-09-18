FROM tomcat:latest
RUN mv /usr/local/tomcat/webapps.dist/* /usr/local/tomcat/webapps
COPY tomcat-users.xml /usr/local/tomcat/conf
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF