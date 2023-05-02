package org.esProj;

import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePage {

    private ArrayList<String> courses = new ArrayList<String>();
    CalendarApp calendar = new CalendarApp();

    @FXML
    private ChoiceBox<String> cursosDropDown;



    @FXML
    private void openCalendar() {

        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);
        ChoiceBox<String> coursesDropDown = new ChoiceBox<>();

        Scene scene = new Scene(new StackPane(calendarView));
        Stage stage = new Stage();
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();

    }

    public void chooseFile(MouseEvent mouseEvent, Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        fileChooser.showOpenDialog(stage);
    }
}
