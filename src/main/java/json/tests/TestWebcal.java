package json.tests;

import json.Webcal;

public class TestWebcal {

     public static void main(String[] args) {
        String webcalUrl = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=mcgoo1@iscte.pt&password=on3HvF7trmxu1QzKnTPIGAJjyXHIKLBppaADXkK94hhyDBxEOKtb3hDOuygwkjk5Ni4ha1sryy6EjW8CChWLiRLWuLwTvvG21l25jiqqCG0GP0OZC6WjUEEJjWR4jI2Z";
        Webcal teste = new Webcal(webcalUrl);
    }


}
