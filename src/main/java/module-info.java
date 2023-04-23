module org.ESproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires org.json;
    requires org.apache.commons.io;

    opens org.ESproj to javafx.fxml;
    exports org.ESproj;
}