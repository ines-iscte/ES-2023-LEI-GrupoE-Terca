package org.esProj;

import com.calendarfx.view.CalendarView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class HomePage {

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
        //coursesDropDown.getItems().addAll(courses);
        ComboBox cursosDropDown = new ComboBox();
        //cursosDropDown.setValue(courses.get(0));

    }

}
