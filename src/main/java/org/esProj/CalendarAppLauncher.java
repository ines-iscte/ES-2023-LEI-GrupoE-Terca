package org.esProj;

import biweekly.component.VEvent;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import json.CsvToJson;
import json.JsonToCsv;
import json.Webcal;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static json.Webcal.getEventsFromWebcal;

public class CalendarAppLauncher extends Application {

    private static Scene scene;
    private File file;
    private String jsonPATH;
    private String webCalLink;

    @FXML
    private TextField webCal;


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
        FXMLLoader fxmlLoader = new FXMLLoader(CalendarAppLauncher.class.getResource(fxml + ".fxml"));
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
        jsonPATH = file.getAbsolutePath();
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

    public void showCalendar(MouseEvent mouseEvent) throws IOException, ParseException {
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

    public void data(String jsonPATH, CalendarView cv) throws ParseException {
        System.out.println(jsonPATH);

        ArrayList<String> courses = new ArrayList<>();
        Calendar calendar = new Calendar("My Calendar");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        String title;
        try {
            rootNode = objectMapper.readTree(new File(jsonPATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            //System.out.println(event);
            if (event != null) {
                //  if(!(courses.contains(event.get("﻿Curso").asText())) || courses.isEmpty() ){
                //  courses.add((event.get("﻿Curso").asText()));
                //}
                if (jsonPATH.contains("src/jsonFiles")) {
                    title = event.get("summary").asText();
                    String allDateStart = event.get("start").asText();
                    String[] dateStartString = allDateStart.split(" ");
                    String dateStartMonth = dateStartString[1];

                    String allDateEnd = event.get("end").asText();
                    String[] dateEndString = allDateStart.split(" ");
                    String dateEndMonth = dateEndString[1];

                    Map<String, Integer> monthMap = new HashMap<>();
                    monthMap.put("Jan", 1);
                    monthMap.put("Feb", 2);
                    monthMap.put("Mar", 3);
                    monthMap.put("Apr", 4);
                    monthMap.put("May", 5);
                    monthMap.put("Jun", 6);
                    monthMap.put("Jul", 7);
                    monthMap.put("Aug", 8);
                    monthMap.put("Sep", 9);
                    monthMap.put("Oct", 10);
                    monthMap.put("Nov", 11);
                    monthMap.put("Dec", 12);

                    int monthStartNumber = monthMap.get(dateStartMonth);

                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    TemporalAccessor temporalStart = inputFormatter.parse(allDateStart);
                    ZonedDateTime zonedDateTimeStart = ZonedDateTime.from(temporalStart).withZoneSameInstant(ZoneId.systemDefault());
                    LocalDateTime localDateTimeStart = zonedDateTimeStart.toLocalDateTime();

                    TemporalAccessor temporalEnd = inputFormatter.parse(allDateEnd);
                    ZonedDateTime zonedDateTimeEnd = ZonedDateTime.from(temporalEnd).withZoneSameInstant(ZoneId.systemDefault());
                    LocalDateTime localDateTimeEnd = zonedDateTimeEnd.toLocalDateTime();

                    Entry entry = new Entry(title);
                    entry.setInterval(localDateTimeStart, localDateTimeEnd);
                    calendar.addEntry(entry);
                    calendar.addEntry(entry);

                } else {
                    title = event.get("Unidade Curricular").asText();
                    String description = event.get("Turno").asText() + " - " + event.get("Turma").asText();
                    //estava a dar erro pq algumas aulas n tinham sala atribuida
                    if (event.get("Sala atribuida a aula") != null) {
                        String location = event.get("Sala atribuida a aula").asText();
                    }
                    if (event.get("Data da aula") != null && event.get("Hora inicio da aula") != null && event.get("Hora fim da aula") != null) {

                        String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();
                        System.out.println(startDateTimeString);
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
                    }

                }

            } else {
                System.out.println("A aula não existe!");
            }
        }
        CalendarSource horario = new CalendarSource("Horário");
        horario.getCalendars().addAll(calendar);
        cv.getCalendarSources().setAll(horario);
    }


    public void showWebCalendar(MouseEvent mouseEvent) throws JsonProcessingException {
        Webcal wCal = new Webcal(webCalLink);
        jsonPATH = "src/jsonFiles/webCalendar.json";
    }


    @FXML
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        webCalLink = webCal.getText();
        System.out.println(webCalLink);
    }

    public void createSchedule(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(loadFXML("criarHorario"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}


