package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class WebcalTest {

    private String linkTest = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=icaoo1@iscte.pt&password=nQRVoYelgfJ2boLhNThP2Uvwr58E9WRgrNTAefwxRddrxfb70n8Y04Ef1TmvUI7N4NUVM8ekoEBsq2ftZToBNzllB4FwDKV6nyyRJBXAs7j3b4hpkWoJR0SMaU0sjkya";
    private Webcal test;
    private File filePath;

    WebcalTest() throws JsonProcessingException {
        test = new Webcal(linkTest);
        filePath= new File ("src/jsonFiles/webCalendar.json");
    }

    @Test
    public void webcal1() throws JsonProcessingException {
        assertTrue(filePath.length()!=0);
    }

    @Test
    public void webcal2() throws JsonProcessingException {
        String[] targets = {"aulas", "summary", "start", "end"};
        boolean exists=true;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null) {
                for (String target : targets) {
                    if (!line.contains(target)) {
                        exists=false;
                        break;
                    }
                }
            }
            br.close();
        } catch(IOException e) {
        }
        assertTrue(exists);
    }
}