#!/usr/bin/bash
# Copyright (C) 2020 Lukasz Gajerski <https://github.com/Ukasz09>

##### FLAGS MAINTAINING
 for arg in "$@"
 do
    case $arg in
        -d|--default)
		JAVA_PATH=$JAVA_HOME
		shift;;
	-p=*|--path=*)
		JAVA_PATH="${arg#*=}"
		shift;;
    esac
 done

##### MAIN
$JAVA_PATH/bin/java --module-path $JAVA_PATH/lib/javafx.base.jar:\
$JAVA_PATH/lib/javafx.controls.jar:\
$JAVA_PATH/lib/javafx.fxml.jar:\
$JAVA_PATH/lib/javafx.graphics.jar:\
$JAVA_PATH/lib/javafx.media.jar:\
$JAVA_PATH/lib/javafx.swing.jar:\
$JAVA_PATH/lib/javafx.web.jar:\
$JAVA_PATH/lib/javafx-swt.jar:\
$JAVA_PATH/lib/javafx.base.jar:\
$JAVA_PATH/lib/javafx.controls.jar:\
$JAVA_PATH/lib/javafx.fxml.jar:\
$JAVA_PATH/lib/javafx.graphics.jar:\
$JAVA_PATH/lib/javafx.media.jar:\
$JAVA_PATH/lib/javafx.swing.jar:\
$JAVA_PATH/lib/javafx.web.jar:\
$JAVA_PATH/lib/javafx-swt.jar --add-modules ALL-MODULE-PATH -jar *.jar
