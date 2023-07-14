package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WebcalTest {

    private String linkTest="";
    private Webcal test;
    private File filePath;

    WebcalTest() throws JsonProcessingException, SQLException {
        test = new Webcal(linkTest);
        filePath= new File ("src/jsonFiles/webCalendar.json");
    }

    @Test
    public void webcal1() throws JsonProcessingException {
        assertTrue(filePath.length()!=0);
    }

    @Test
    public void webcal2() throws JsonProcessingException, IOException {
        String[] targets = {"aulas", "summary", "start", "end"};
        boolean exists=true;
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
        assertTrue(exists);
    }




}
