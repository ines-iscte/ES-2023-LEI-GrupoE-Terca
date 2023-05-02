package org.esProj;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import json.CsvToJson;
import json.JsonToCsv;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CalendarAppLauncher extends Application {

    private static Scene scene;
    private File file;
    private String jsonPATH;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("homePage"), 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public void chooseFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        file = fileChooser.showOpenDialog(scene.getWindow());
    }

    public void convert(MouseEvent mouseEvent) {
        CsvToJson toJson = new CsvToJson();
        JsonToCsv toCSV = new JsonToCsv();
        String filePath = file.getAbsolutePath();
        String outputPath = new File(filePath).getParent();
        System.out.println(outputPath);
        if (filePath.contains(".csv")) {
            toJson.csvToJson(filePath, outputPath + "/FicheiroConvertido.json");
            jsonPATH = outputPath + "/FicheiroConvertido.json";
        }
        if (filePath.contains(".json")) {
            toCSV.jsonToCsv(filePath, outputPath + "/FicheiroConvertido.csv");
            jsonPATH = filePath;
        }
    }

    public void showCalendar(MouseEvent mouseEvent) throws IOException {
        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);
        data(jsonPATH, calendarView);
        Scene scene = new Scene(new StackPane(calendarView));
        Stage stage = new Stage();
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

    public void data(String jsonPATH, CalendarView cv) {
        ArrayList<String> courses = new ArrayList<>();
        String jsonFilePath = "arquivo.json";
        Calendar calendar = new Calendar("My Calendar");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(new File(jsonFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            //System.out.println(event);
            if (event != null) {
          /*     if(!(courses.contains(event.get("﻿Curso").asText())) || courses.isEmpty() ){
                  courses.add((event.get("﻿Curso").asText()));
               }
*/
                String title = event.get("Unidade Curricular").asText();
                //System.out.println(title);
                String description = event.get("Turno").asText() + " - " + event.get("Turma").asText();
                //estava a dar erro pq algumas aulas n tinham sala atribuida
                if (event.get("Sala atribuida a aula") != null) {
                    String location = event.get("Sala atribuida a aula").asText();
                }
                String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();
                String endDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora fim da aula").asText();

                // Convert the start and end date/time strings into Java Date objects
                Date startDateTime = Date.from(LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant());
                Date endDateTime = Date.from(LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).atZone(ZoneId.systemDefault()).toInstant());


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate1 = LocalDate.parse(event.get("Data da aula").asText(), formatter);
                LocalDate localDate2 = LocalDate.parse(event.get("Data da aula").asText(), formatter);
                //java.util.Date.from( startDateTime.atStartOfDay( ZoneId.of( "America/Montreal" ) )  .toInstant());
                LocalDateTime startDateTime1 = LocalDateTime.ofInstant(startDateTime.toInstant(), ZoneId.systemDefault());
                LocalDateTime endDateTime1 = LocalDateTime.ofInstant(endDateTime.toInstant(), ZoneId.systemDefault());

                Entry entry = new Entry(title);
                entry.setInterval(startDateTime1, endDateTime1);
                calendar.addEntry(entry);
            } else {
                System.out.println("A aula não existe!");
            }
        }
        CalendarSource horario = new CalendarSource("Horário");
        horario.getCalendars().addAll(calendar);
        cv.getCalendarSources().setAll(horario);
    }
}
