package json;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Path;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Webcal {
    private static final Logger logger = Logger.getLogger(Webcal.class.getName());
    JSONObject eventJson = new JSONObject();
    private String filePath = "src/jsonFiles/webCalendar.json";

    public Webcal(String webcalURL) throws JsonProcessingException {
        try{
            FileWriter file = new FileWriter (filePath);
            file.write("");
            file.close();
            logger.log(Level.INFO, "Ficheiro de saída pronto a receber horário.");
        } catch (IOException e){
            logger.log(Level.INFO, "Erro ao limpar ficheiro de saída.");
        }


        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            //para aceitar os acentos
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.INFO, "Erro ao ler o ficheiro.");
        }

        if (webcalURL.startsWith("webcal")) {
            webcalURL = "https" + webcalURL.substring(6);
            //resolver a leitura de webcal
        }


        List<VEvent> events = getEventsFromWebcal(webcalURL);
        JSONObject eventsJson = new JSONObject();
        if (events != null) {
            for (VEvent event : events) {
                JSONObject eventJson = new JSONObject();
                eventJson.put("Unidade Curricular", event.getSummary().getValue());
                eventJson.put("Data da aula", event.getDateStart().getValue());
                eventJson.put("Data fim da aula", event.getDateEnd().getValue());
                eventsJson.append("aulas", eventJson);
            }

            // webCalString.push(event.getSummary().getValue() + ": " + event.getDateStart().getValue());
            //System.out.println(event.getSummary().getValue() + ": " + event.getDateStart().getValue());
            //escreve na cmd, alterar(?)
        }

      try (FileWriter file = new FileWriter(filePath)) {
        file.write(eventsJson.toString());
        file.flush();
        System.out.println("Ficheiro JsonObject guardado corretamente!");
      } catch (IOException e) {
          logger.log(Level.INFO, "Ficheiro JsonObject não foi guardado corretamente.");
      }       // } else {
            //System.out.println("No events found in webcal URL");
       // }
    }



    public static List<VEvent> getEventsFromWebcal(String webcalUrl) {
        try {
            URL url = new URL(webcalUrl);

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            ICalendar ical = Biweekly.parse(inputStream).first();
            return (ical != null) ? ical.getEvents() : null;

        } catch (IOException e) {
            logger.log(Level.INFO, "Erro ao ler eventos.");
            return null;
        }

    }

}
