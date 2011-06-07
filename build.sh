#!/bin/bash
javac -cp libs/craftbukkit-818.jar -sourcepath src/ -d build src/pinoygamers/AngryMobs/*
rm AngryMobs-*.jar
jar cf AngryMobs-$BUILD_NUMBER.jar plugin.yml -C build pinoygamers
cd src
javadoc -d "../docs/" pinoygamers/AngryMobs/*
