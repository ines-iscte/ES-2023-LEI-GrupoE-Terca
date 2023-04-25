module org.ESproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires org.json;
    requires org.apache.commons.io;
    requires org.junit.jupiter.api;
    requires junit;

    opens org.esProj to javafx.fxml;
    exports org.esProj;
}