REM Written by Berk
echo Starting install script

CALL "C:\Applications\Atlassian\atlassian-plugin-sdk-8.0.16\apache-maven-3.5.4\bin\mvn.cmd" -gs C:\Applications\Atlassian\atlassian-plugin-sdk-8.0.16\apache-maven-3.5.4/conf/settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dmaven.test.skip=true package

if %ERRORLEVEL% == 0 goto :upload
echo "Errors encountered during build.  Exited with status: %errorlevel%"
goto :endofscript

:upload
echo Starting Upload of plugin to the server
CALL "C:\Applications\Atlassian\atlassian-plugin-sdk-8.0.16\apache-maven-3.5.4\bin\mvn.cmd" com.atlassian.maven.plugins:amps-dispatcher-maven-plugin:8.0.2:install -gs C:\Applications\Atlassian\atlassian-plugin-sdk-8.0.16\apache-maven-3.5.4/conf/settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dusername=venitureAdm -Dpassword=asd123 -Datlassian.plugin.key=com.veniture.PortfolioManagement -Dserver=http://localhost -Dhttp.port=8080 -Dcontext.path=

:endofscript
echo "Script complete"