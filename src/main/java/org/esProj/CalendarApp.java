package org.esProj;

import com.calendarfx.model.*;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.calendarfx.model.Calendar;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.sql.DriverManager.println;

public class CalendarApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        //String jsonFilePath = "C:\\Users\\Nicole\\OneDrive - ISCTE-IUL\\3o Ano\\2oSem\\ES\\ProjetoCerto\\ES-2023-LEI-GrupoE-Terca\\arquivo.json";
        String jsonFilePath = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\2º semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json";

        Calendar calendar = new Calendar("My Calendar");

        ObjectMapper objectMapper = new ObjectMapper();

        // Read the JSON file and parse it into a JsonNode object
        JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));
        System.out.println(rootNode);

        // Get the array of events from the JSON file
        ArrayNode events = (ArrayNode) rootNode.get("aulas");

        // Loop through each event in the array
        for (JsonNode event : events) {
            System.out.println(event);
            if (event != null) {
                String title = event.get("Unidade Curricular").asText();
                println(title);
                String description = event.get("Turno").asText() + " - " + event.get("Turma").asText();
                //estava a dar erro pq algumas aulas n tinham sala atribuida
                if(event.get("Sala atribuida a aula") != null){
                    String location = event.get("Sala atribuida a aula").asText();}
                String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();
                String endDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora fim da aula").asText();

                // Convert the start and end date/time strings into Java Date objects
                Date startDateTime = Date.from(LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant());
                Date endDateTime = Date.from(LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant());


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate1 = LocalDate.parse(event.get("Data da aula").asText(), formatter);
                LocalDate localDate2 = LocalDate.parse(event.get("Data da aula").asText(), formatter);

                LocalDateTime startDateTime1 = LocalDateTime.ofInstant(startDateTime.toInstant(), ZoneId.systemDefault());
                LocalDateTime endDateTime1 = LocalDateTime.ofInstant(endDateTime.toInstant(), ZoneId.systemDefault());

                Entry entry = new Entry(title);
                entry.setInterval(startDateTime1, endDateTime1);
                calendar.addEntry(entry);
                System.out.println(entry);
            }else{
                println("calhou coco");
            }
        }
        CalendarView calendarView = new CalendarView();
        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().setAll(familyCalendarSource);

        calendarView.setEnableTimeZoneSupport(true);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(calendarView);
        FXMLLoader loader =new FXMLLoader(App.class.getResource("homePage.fxml"));
        Parent root = loader.load();
        HomePage controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(600);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
/*
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Minha aplicação JavaFX");
        primaryStage.show();
/*
        Calendar katja = new Calendar("Katja");
        Calendar dirk = new Calendar("Dirk");
        Calendar philip = new Calendar("Philip");
        Calendar jule = new Calendar("Jule");
        Calendar armin = new Calendar("Armin");
        Calendar birthdays = new Calendar("Birthdays");
        Calendar holidays = new Calendar("Holidays");

        katja.setShortName("K");
        dirk.setShortName("D");
        philip.setShortName("P");
        jule.setShortName("J");
        armin.setShortName("A");
        birthdays.setShortName("B");
        holidays.setShortName("H");

        katja.setStyle(Style.STYLE1);
        dirk.setStyle(Style.STYLE2);
        philip.setStyle(Style.STYLE3);
        jule.setStyle(Style.STYLE4);
        armin.setStyle(Style.STYLE5);
        birthdays.setStyle(Style.STYLE6);
        holidays.setStyle(Style.STYLE7);

        CalendarSource familyCalendarSource = new CalendarSource("Family");
       familyCalendarSource.getCalendars().addAll(birthdays, holidays, katja, dirk, philip, jule, armin);

        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(calendarView); // introPane);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        scene.setRoot(stackPane);
        scene.focusOwnerProperty().addListener(it -> System.out.println("focus owner: " + scene.getFocusOwner()));
        CSSFX.start(scene);

        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(700);
        primaryStage.setHeight(600);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
*/
    public static void main(String[] args) {
        launch(args);
    }
}