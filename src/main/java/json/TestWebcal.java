package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import json.Webcal;

public class TestWebcal {

     public static void main(String[] args) throws JsonProcessingException {
        String webcalUrl = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=icaoo1@iscte.pt&password=nQRVoYelgfJ2boLhNThP2Uvwr58E9WRgrNTAefwxRddrxfb70n8Y04Ef1TmvUI7N4NUVM8ekoEBsq2ftZToBNzllB4FwDKV6nyyRJBXAs7j3b4hpkWoJR0SMaU0sjkya";
        Webcal teste = new Webcal(webcalUrl);
    }
}
