
module org.ESproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires org.json;
    requires org.apache.commons.io;
    requires org.junit.platform.commons;
    requires junit;
    requires java.logging;
    requires net.sf.biweekly;

    requires org.junit.jupiter.api;
    requires org.junit.jupiter.params;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.csv;

    opens  json to org.apache.commons.csv;

    requires com.calendarfx.view;
    requires fr.brouillard.oss.cssfx;
    requires java.sql;

    requires jdk.incubator.httpclient; // needed for making HTTP requests from Node.js
    requires jdk.internal.vm.ci; // needed for using the Nashorn JavaScript engine
    requires jdk.scripting.nashorn; // needed for using the Nashorn JavaScript engine
    requires jdk.jsobject; // needed for using the Nashorn JavaScript engine
    requires jdk.dynalink; // needed for using the Nashorn JavaScript engine
    requires jdk.internal.le;

    requires jdk.incubator.httpclient;
    requires org.apache.commons.io;
    requires org.slf4j;
    requires org.jsoup;
    requires org.json;
    requires jackson.databind;
    requires node;
    requires npm;


    opens org.esProj to javafx.fxml;
    exports org.esProj;
    exports json;
}

