package org.esProj;

import com.calendarfx.view.CalendarView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage {
    @FXML
    private void openCalendar() {

        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);

        Scene scene = new Scene(new StackPane(calendarView));
        Stage stage = new Stage();
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

}
