rm ../../../latest_run/Bhasha*
mvn -o -f ../pom.xml clean install
cp ../target/Bhasha*.jar ../../../latest_run/
cd ../../../latest_run
ln -s Bhasha-*-jar-with-dependencies.jar Bhasha.jar
