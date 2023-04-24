package json;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jsonToCsv {

    private static final Logger logger = Logger.getLogger(jsonToCsv.class.getName());

    private String inputFilePath;
    private String outputFilePath;

    public jsonToCsv(String inputFilePath, String outputFilePath) {
        try {
            BufferedReader reader;
            if (inputFilePath.startsWith("http")) { // If the input is a URL
                URL url = new URL(inputFilePath);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                logger.log(Level.INFO, "Escolhido ficheiro remoto");
            } else { // Otherwise, assume the input is a local file path
                reader = getReader(inputFilePath);
                logger.log(Level.INFO, "Escolhido ficheiro local");
            }

            if (reader==null)
                return;

            String jsonString = readJsonString(reader);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray docs = jsonObject.getJSONArray("aulas");

            File file = new File(outputFilePath);
            String csvString = CDL.toString(docs);
            FileUtils.writeStringToFile(file, csvString);

            logger.log(Level.INFO, "Ficheiro CSV criado com sucesso");
        } catch (IOException e) {
            String erroCSV = "Não foi possível criar o ficheiro CSV";
            logger.log(Level.INFO, erroCSV);
        }
    }

    private BufferedReader getReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Ficheiro não encontrado: " + filePath);
            return null;
        }
    }

    private String readJsonString(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
