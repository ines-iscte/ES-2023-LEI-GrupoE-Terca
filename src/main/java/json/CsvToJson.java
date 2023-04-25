package json;

import com.google.gson.*;
import org.json.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.*;

public class CsvToJson {

    private static final Logger logger = Logger.getLogger(CsvToJson.class.getName());

    private String inputFilePath;
    private String outputFilePath;

    public void csvToJson(String inputFilePath, String outputFilePath) {
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
            // Ler a primeira linha e definir os nomes das colunas
            String primeiraLinha = reader.readLine();
            String[] nomesColunas = primeiraLinha.split(";");

            List<String[]> aulas = new ArrayList<String[]>();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                aulas.add(campos);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Criar um objeto JsonObject para adicionar os nomes das colunas ao JSON
            JSONObject jsonObject = new JSONObject();
            JsonArray jsonArray = new JsonArray();
            for (String[] campos : aulas) {
                JsonObject linhaObjeto = new JsonObject();
                for (int i = 0; i < campos.length; i++) {
                    linhaObjeto.addProperty(nomesColunas[i], campos[i]);
                }
                jsonArray.add(linhaObjeto);
            }


            FileWriter writer = new FileWriter(outputFilePath);
            jsonObject.put("aulas", jsonArray);
            gson.toJson(jsonObject.toMap(), writer);

            reader.close();
            writer.close();

            logger.log(Level.INFO, "Ficheiro JSON criado com sucesso");

        } catch (IOException e){
            String erroJSON = "Não foi possível criar o ficheiro JSON";
            logger.log(Level.INFO, erroJSON);
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

}