@echo off
echo building package...
cd %userprofile%\documents\github\myshelfie
start /wait cmd /c "mvn clean package -DskipTests"
echo.
cd %userprofile%
echo starting server...
start cmd /k "cd documents\github\myshelfie\target & java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar"
timeout /t 3 >nul
echo starting first client...
start cmd /k "cd documents\github\myshelfie\target & java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar CLI 1"
timeout /t 5 >nul
echo starting second client...
start cmd /k "cd documents\github\myshelfie\target & java -jar PSP000-1.0-SNAPSHOT-jar-with-dependencies.jar CLI 2"
timeout /t 2 >nul
exit
