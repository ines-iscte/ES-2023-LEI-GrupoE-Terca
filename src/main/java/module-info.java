
module org.ESproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires org.json;
    requires org.apache.commons.io;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires junit;
    requires java.logging;
    requires net.sf.biweekly;
    requires com.calendarfx.view;
    requires fr.brouillard.oss.cssfx;
    opens org.esProj to javafx.fxml;
    exports org.esProj;
}