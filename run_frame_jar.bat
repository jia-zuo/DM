javac -d temp Main.java DM.java
cd temp
jar cvfm ../DM.jar META-INF/MANIFEST.MF *
cd ..
java -jar DM.jar