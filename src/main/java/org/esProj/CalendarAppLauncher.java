package org.esProj;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import json.CsvToJson;
import json.JsonToCsv;
import json.Webcal;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarAppLauncher extends Application {
    private static final Logger logger = Logger.getLogger(CalendarAppLauncher.class.getName());
    static Stage startStage;
    private static Scene scene;
    private File file;
    private String jsonPATH;
    private String webCalLink;
    ArrayList<String> ucArray = new ArrayList<>();
    ArrayList<CheckBox> cbArray = new ArrayList<>();
    ArrayList<String> cbSelected = new ArrayList<>();
    FileWriter fileNewSchedule;
    private ListView<String> overlaps = new ListView<>();;
    private ListView<String> overcrowdeds = new ListView<>();
    ArrayList<String> horariosAulas = new ArrayList<>();

    @FXML
    private FlowPane checkBoxes;

    @FXML
    private Button seeSchedule;

    @FXML
    private Button unseeList = new Button("Back");

    @FXML
    private Button overlap = new Button("Sobreposições");

    @FXML
    private Button overcrowded = new Button("Aulas sobrelotadas");

    @FXML
    private TextField webCal;


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("homePage"), 640, 480);
        startStage = stage;
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
        logger.log(Level.INFO, "Escolhido ficheiro a converter");
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
        if(jsonPATH.contains(".csv")) {
            String filePath = file.getAbsolutePath();
            String outputPath = new File(filePath).getParent();
            CsvToJson tojson = new CsvToJson();
            tojson.csvToJson(filePath, outputPath + "/FicheiroConvertido.json");
            jsonPATH = outputPath + "/FicheiroConvertido.json";
        }
        data(jsonPATH, calendarView);

    }

    public void datesJSON(String jsonPATH){

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        String title;
        try {
            rootNode = objectMapper.readTree(new File(jsonPATH));
        } catch (IOException e) {
            logger.log(Level.INFO, "Erro na leitura dos dados.");
        }

        //System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            //System.out.println(event);
            if (event != null) {
                title = event.get("Unidade Curricular").asText();
                String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();
                horariosAulas.add(title + ": " + startDateTimeString);
            }
        }
    }

    public void data(String jsonPATH, CalendarView cv) throws ParseException {
        System.out.println(jsonPATH);

        ArrayList<String> ucArray = new ArrayList<>();
        Calendar calendar = new Calendar("My Calendar");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        String title;
        try {
            rootNode = objectMapper.readTree(new File(jsonPATH));
        } catch (IOException e) {
            logger.log(Level.INFO, "Começar a tratar dos dados para o calendário");
        }

        if(!jsonPATH.contains("src/jsonFiles")){
        datesJSON(jsonPATH);}
        //System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            //System.out.println(event);
            if (event != null) {
                

                if (jsonPATH.contains("src/jsonFiles")) {
                    title = event.get("Unidade Curricular").asText();
                    String allDateStart = event.get("Data da aula").asText();
                    String[] dateStartString = allDateStart.split(" ");
                    String dateStartMonth = dateStartString[1];

                    String allDateEnd = event.get("Data fim da aula").asText();
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

                    horariosAulas.add(title + ": " + allDateStart);
                    for (String horarioA: horariosAulas) {
                        if(horarioA.contains(allDateStart) && !(horarioA.contains(title))){
                            overlaps.getItems().add(horarioA + " X " + title + ": " + allDateStart);
                        }
                    }

                    Entry entry = new Entry(title);
                    entry.setInterval(localDateTimeStart, localDateTimeEnd);
                    calendar.addEntry(entry);
                    calendar.addEntry(entry);

                } else {
                    title = event.get("Unidade Curricular").asText();
                    //System.out.println(title);
                    String description = event.get("Turno").asText() + " - " + event.get("Turma").asText();
                    //estava a dar erro pq algumas aulas n tinham sala atribuida
                    if (event.get("Sala atribuida a aula") != null) {
                        String location = event.get("Sala atribuida a aula").asText();
                    }

                    if (event.get("Data da aula") != null && event.get("Hora inicio da aula") != null && event.get("Hora fim da aula") != null) {
                        String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();

                        String endDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora fim da aula").asText();

                        if(event.get("Inscritos no turno")!=null && event.get("Lotacao da sala")!=null) {
                            String inscritos = event.get("Inscritos no turno").asText();
                            String lotacao = event.get("Lotacao da sala").asText();
                            if (Integer.parseInt(inscritos) > Integer.parseInt(lotacao)) {
                                String info = title + ": inscritos- " + inscritos + "; lotação- " + lotacao + " // data: " + startDateTimeString;
                                overcrowdeds.getItems().add(info);
                                System.out.println(title + " sobrelotada");
                            }
                        }

                        for (String horarioA: horariosAulas) {
                            if(horarioA.contains(startDateTimeString) && !(horarioA.contains(title))){
                                overlaps.getItems().add(horarioA + " X " + title + ": " + startDateTimeString);
                            }
                        }

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
        setCalendarView(cv);
    }

    //define botões e vista do calendário
    public void setCalendarView(CalendarView cv){
        // Criação da barra de botões
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(overlap, overcrowded); // Adiciona os botões à barra

        // Criação do VBox para agrupar os componentes
        VBox contentBox = new VBox(cv, buttonBar);

        // Criação do painel principal
        BorderPane root = new BorderPane();
        root.setCenter(contentBox); // Define o VBox como o conteúdo central

        Scene scene = new Scene(new StackPane(root));
        Stage stage = new Stage();
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();

        overlap.setOnAction(e -> seeOverlaps(stage));
        overcrowded.setOnAction(e -> seeOvercrowded(stage));

    }

    private void seeOvercrowded(Stage stage) {
        // Criação do HBox para agrupar a lista e o botão
        VBox bottomContent = new VBox(overcrowdeds, unseeList);

        // Define o conteúdo do BorderPane como o HBox
        BorderPane root = (BorderPane) ((StackPane) stage.getScene().getRoot()).getChildren().get(0);
        root.setBottom(bottomContent);

        unseeList.setOnAction(e -> hideList(stage));
    }

    private void hideList(Stage stage) {
        BorderPane root = (BorderPane) ((StackPane) stage.getScene().getRoot()).getChildren().get(0);
        root.setBottom(null); // Remove a lista do bottom do BorderPane
    }

    private void seeOverlaps(Stage stage) {
        // Criação do HBox para agrupar a lista e o botão
        VBox bottomContent = new VBox(overlaps, unseeList);

        // Define o conteúdo do BorderPane como o HBox
        BorderPane root = (BorderPane) ((StackPane) stage.getScene().getRoot()).getChildren().get(0);
        root.setBottom(bottomContent);

        unseeList.setOnAction(e -> hideList(stage));
    }

    public void showWebCalendar(MouseEvent mouseEvent) throws JsonProcessingException {
        Webcal wCal = new Webcal(webCalLink);
        jsonPATH =  "src/jsonFiles/webCalendar.json";
    }

    @FXML
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        webCalLink = webCal.getText();
        System.out.println(webCalLink);
    }

    public void createSchedule(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) startStage.getScene().getWindow();
        Scene scene = new Scene(loadFXML("createSchedule"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public void addCheckBoxes() throws IOException {
        int n = ucArray.size(); // Number of checkboxes to create
        for (int i = 0; i <= n-1; i++) {
            CheckBox checkBox = new CheckBox(ucArray.get(i));
            if(checkBoxes != null){
                cbArray.add(checkBox);
                checkBoxes.getChildren().add(checkBox);
            }
        }

    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) startStage.getScene().getWindow();
        Scene scene = new Scene(loadFXML("homePage"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public void chooseFileNew(MouseEvent mouseEvent) throws IOException {
        checkBoxes.setVisible(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        file = fileChooser.showOpenDialog(scene.getWindow());
        jsonPATH = file.getAbsolutePath();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        String title;
        try {
            rootNode = objectMapper.readTree(new File(jsonPATH));
        } catch (IOException e) {
            logger.log(Level.INFO, "Começar a tratar dos dados para o calendário");
        }

        System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            if (event != null) {
                //System.out.println(ucArray);
                if (!(ucArray.contains(event.get("Unidade Curricular").asText())) || ucArray.isEmpty()) {
                    ucArray.add((event.get("Unidade Curricular").asText()));
                    //System.out.println(ucArray);
                }
            }
        }
        addCheckBoxes();
        System.out.println(ucArray);
    }

    //devolve um array com as UCs das checkboxes selecionadas (em String) para comparar na função a seguir (i.e com o ficheiro json)
    public void checkSelected(){
        for(CheckBox cbS : cbArray){
            if(cbS.isSelected()){
                cbSelected.add(cbS.getText());
            }
        }
    }

    public void seeSchedule(MouseEvent mouseEvent) {
        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);
        checkSelected();
        System.out.println(cbSelected);
        Calendar calendar = new Calendar("My Calendar");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        String title;
        try {
            rootNode = objectMapper.readTree(new File(jsonPATH));
        } catch (IOException e) {
            logger.log(Level.INFO, "Ucs escolhidas para horário");
        }
        datesJSON(jsonPATH);
        //System.out.println(rootNode);
        ArrayNode events = (ArrayNode) rootNode.get("aulas");
        // Loop through each event in the array
        for (JsonNode event : events) {
            //System.out.println(event);
            if (event != null) {
                title = event.get("Unidade Curricular").asText();
                //System.out.println(cbSelected.contains(title));
                if (cbSelected.contains(title)) {
                    String description = event.get("Turno").asText() + " - " + event.get("Turma").asText();
                    //estava a dar erro pq algumas aulas n tinham sala atribuida
                    if (event.get("Sala atribuida a aula") != null) {
                        String location = event.get("Sala atribuida a aula").asText();
                    }

                    if (event.get("Data da aula") != null && event.get("Hora inicio da aula") != null && event.get("Hora fim da aula") != null) {
                        String startDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora inicio da aula").asText();

                        String endDateTimeString = event.get("Data da aula").asText() + " " + event.get("Hora fim da aula").asText();

                        if(event.get("Inscritos no turno")!=null && event.get("Lotacao da sala")!=null) {
                            String inscritos = event.get("Inscritos no turno").asText();
                            String lotacao = event.get("Lotacao da sala").asText();
                            if (Integer.parseInt(inscritos) > Integer.parseInt(lotacao)) {
                                String info = title + ": inscritos- " + inscritos + "; lotação- " + lotacao + " // data: " + startDateTimeString;
                                overcrowdeds.getItems().add(info);
                                System.out.println(title + " sobrelotada");
                            }
                        }

                        for (String horarioA: horariosAulas) {
                            if(horarioA.contains(startDateTimeString) && !(horarioA.contains(title))){
                                overlaps.getItems().add(horarioA + " X " + title + ": " + startDateTimeString);
                            }
                        }

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
            }
        }
        CalendarSource horario = new CalendarSource("Horário");
        horario.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().setAll(horario);
        setCalendarView(calendarView);
    }

    //func aux para converter e criar json e CSV ponto 4
    public JSONObject jsonFromSchedule(){
        checkSelected();
        String content = null;
        try {
            content = new String(Files.readAllBytes(Path.of(jsonPATH)));
        } catch (IOException e) {
            logger.log(Level.INFO, "Guardar horario num ficheiro");
        }
        // Criar um JSONArray a partir do JSON
        JSONObject jsonObject = new JSONObject(content);
        JSONArray aulasArray = jsonObject.getJSONArray("aulas");
        // Criar um novo JSONArray apenas com os objetos que deseja manter
        JSONArray newJsonArray = new JSONArray();
        for (int i = 0; i < aulasArray.length(); i++) {
            JSONObject aulaObject = aulasArray.getJSONObject(i);
            //System.out.println(aulaObject);
            // Filtre aqui a lógica para manter os objetos desejados
            if (cbSelected.contains(aulaObject.getString("Unidade Curricular"))) {
                newJsonArray.put(aulaObject);

            }
        }
        //converter este jsonArray com as UCs selecionadas num JSON
        JSONObject newSchedule = new JSONObject();
        return newSchedule.put("aulas", newJsonArray);
    }

    public void convertNewJson() throws IOException {
        JSONObject newSchedule = jsonFromSchedule();
        // Escrever o novo JSONArray num ficheiro JSON
        try {
            fileNewSchedule = new FileWriter("novoHorario.json");
            fileNewSchedule.write(newSchedule.toString());
            fileNewSchedule.flush();
            fileNewSchedule.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Horário guardado em JSON");
        }
    }

    public void convertNewCSV(MouseEvent mouseEvent) throws IOException {
        JSONObject newSchedule = jsonFromSchedule();
        String newScheduleString = newSchedule.toString(); // Convertendo o objeto JSON em uma string JSON
        try {
            // Criação do arquivo temporário
            File tempDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "secure_temp_dir");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            File tempFile = File.createTempFile("temp", ".json", tempDir);

            // Salvando a string JSON no arquivo temporário
            FileWriter writer = new FileWriter(tempFile);
            writer.write(newScheduleString);
            writer.close();

            String directoryPath = saveItHere();
            String fileName = "novoHorario.csv"; // Nome do arquivo CSV
            String filePath = directoryPath + File.separator + fileName;
            // para CSV
            JsonToCsv toCSV = new JsonToCsv();
            toCSV.jsonToCsv(tempFile.getAbsoluteFile().getPath(), filePath);
            // Excluindo o arquivo temporário
            tempFile.delete();
            if (!tempFile.delete())
                logger.log(Level.INFO, "Impossível eliminar ficheiro temporário");
        } catch (IOException e) {
            logger.log(Level.INFO, "");
        }
    }

    public String saveItHere() {
        String directoryPath = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Exibe o diálogo de escolha de diretório
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtém o caminho do diretório escolhido pelo usuário
            directoryPath = fileChooser.getSelectedFile().getAbsolutePath();

            System.out.println("Diretório escolhido: " + directoryPath);
        }
        return directoryPath;
    }

}
