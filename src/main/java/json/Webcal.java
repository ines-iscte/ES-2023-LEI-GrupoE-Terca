package json;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;



public class Webcal {

    public Webcal(String webcalURL){
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
        if (events != null) {
            for (VEvent event : events) {
                System.out.println(event.getSummary().getValue() + ": " + event.getDateStart().getValue());
                //escreve na cmd, alterar(?)
            }
        } else {
            System.out.println("No events found in webcal URL");
        }
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
