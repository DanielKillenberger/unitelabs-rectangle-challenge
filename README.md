# unitelabs-rectangle-challenge

Challenge given by unitelabs.ch 

The task was to calculate all intersections 
of all rectangles given in json format.

For the format of the rectangles see test.json

## Build

The following will run all tests, compile the class files and 
build a jar with all dependencies.
### Prerequisites

Java jdk installed
#### Unix

Cd into the working directory and execute the gradlew script 
as follows:

```
chmod +x gradlew
./gradlew build
```
#### Windows

Cd into the working directory and then execute the gradlew.bat
file as follows:
```
gradlew build
```

## Running the Jar

To execute the built jar and parse test.json in the working directory
enter the following command:

```
java -jar build/libs/unitelabs-rectangle-challenge-all.jar test.json
```

## Tests

The build has already run the tests.
The results can be found in build/reports/tests/test/index.html
