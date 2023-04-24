FROM openjdk:17-jdk-alpine3.14

#workspace
WORKDIR /usr/share/UITestAutomationFramework

# jar files under target folder can be generated using maven command - mvn package -DskipTests=true
#ADD .jar under target from host into this image 
ADD target/POMBasedFramework.jar          POMBasedFramework.jar
ADD target/POMBasedFramework-tests.jar    POMBasedFramework-tests.jar
ADD target/libs                            libs

#in case of any other dependency like .csv / .json / .xls
#please add that as well

#ADD suite files
ADD testNGConfigFiles/testng.xml              testng.xml

ENTRYPOINT java -cp POMBasedFramework.jar:POMBasedFramework-tests.jar:libs/* -DHUB_HOST=$HUB_HOST org.testng.TestNG testng.xml
