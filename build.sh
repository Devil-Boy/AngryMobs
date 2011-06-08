#!/bin/bash
javac -cp libs/craftbukkit-818.jar -sourcepath src/ -d build src/pinoygamers/AngryMobs/*
rm AngryMobs-*.jar
jar cf AngryMobs-$BUILD_NUMBER.jar plugin.yml -C build pinoygamers
cd src/
javadoc -private -d "../docs/" pinoygamers/AngryMobs/*
/home/joshua/Documents/findbugs-1.3.9/bin/findbugs -textui -effort:max -low -xml:withMessages -output "findbugs.xml" -auxclasspath "$WORKSPACE/libs/craftbukkit-818.jar" -sourcepath "$WORKSPACE/src" $WORKSPACE/build
