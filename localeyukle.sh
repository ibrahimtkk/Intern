echo Starting install script

/usr/local/Cellar/atlassian-plugin-sdk/8.0.16/libexec/apache-maven-3.5.4/bin/mvn -gs /usr/local/Cellar/atlassian-plugin-sdk/8.0.16/libexec/apache-maven-3.5.4/conf/settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dmaven.test.skip=true package

echo "Upload Starting"

/usr/local/Cellar/atlassian-plugin-sdk/8.0.16/libexec/apache-maven-3.5.4/bin/mvn com.atlassian.maven.plugins:amps-dispatcher-maven-plugin:8.0.2:install -gs /usr/local/Cellar/atlassian-plugin-sdk/8.0.16/libexec/apache-maven-3.5.4/conf/settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dusername=ibrahim.takak -Dpassword=Zhobada35. -Datlassian.plugin.key=com.veniture.ibrahim.takak -Dserver=localhost -Dhttp.port=8089 -Dcontext.path=

echo "Script complete"