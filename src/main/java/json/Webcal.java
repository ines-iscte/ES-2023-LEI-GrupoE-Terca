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



public class Webcal {
  JSONObject eventJson = new JSONObject();


    public Webcal(String webcalURL) throws JsonProcessingException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            //para aceitar os acentos
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
              eventJson.put("summary", event.getSummary().getValue());
              eventJson.put("start", event.getDateStart().getValue());
              eventJson.put("end", event.getDateEnd().getValue());
              eventsJson.append("aulas", eventJson);
            }

               // webCalString.push(event.getSummary().getValue() + ": " + event.getDateStart().getValue());
                //System.out.println(event.getSummary().getValue() + ": " + event.getDateStart().getValue());
                //escreve na cmd, alterar(?)
            }
      String filePath = "src/jsonFiles/webCalendar.json";

      try (FileWriter file = new FileWriter(filePath)) {
        file.write(eventsJson.toString());
        file.flush();
        System.out.println("Successfully saved JSONObject to file!");
      } catch (IOException e) {
        e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }

    }

}
