#!/bin/bash
javac -cp libs/craftbukkit-818.jar -sourcepath src/ -d build src/pinoygamers/AngryMobs/*
jar cf AngryMobs-$BUILD_NUMBER build/* plugin.yml
cd src
javadoc -d "../docs/" pinoygamers/AngryMobs/*
