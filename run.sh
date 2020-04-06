#!/bin/bash

javac -d temp src/main/java/com/github/DMain.java

cd temp

jar cvfm ../DM.jar META-INF/MANIFEST.MF *

cd ..

java -jar DM.jar
