# in order to use, execute permissions must be given to this file. 
# run `chmod +x make.sh` in the terminal
# then you can clean, test, package, and execute the jar file by
# simply running `./make.sh` in the terminal

mvn clean
# mvn test
mvn package
java -jar target/CodeScreen_wyvdyw2a-1.0.0.jar