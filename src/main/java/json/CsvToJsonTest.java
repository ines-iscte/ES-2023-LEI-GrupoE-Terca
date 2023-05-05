
package json;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvToJsonTest {

    private static CsvToJson test=new CsvToJson();
    private File expectedFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json");
    private String path1 = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv";
    private String path2 = "https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/exemplo.csv";
    private String outputPaths="C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES";

    @Test
    void csvToJson1() {
        //Criação de ficheiro Json, com ficheiro local
        String outputPath = outputPaths + "\\teste1.json";
        test.csvToJson(path1, outputPath);
        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists());
    }

    @Test
    void csvToJson2() throws IOException {
        //Ficheiro Json igual a ficheiro de controlo, com ficheiro local
        String outputPath = outputPaths + "\\teste2.json";
        test.csvToJson(path1, outputPath);
        File outputFile = new File(outputPath);

        String json1 = readFile(outputFile);
        String json2 = readFile(expectedFile);

        JSONObject jsonObj1 = new JSONObject(json1);
        JSONObject jsonObj2 = new JSONObject(json2);

        boolean isEqual = jsonObj1.similar(jsonObj2);
        assertTrue(isEqual);
    }

    @Test
    void csvToJson3(){
        //Criação de ficheiro Json, com ficheiro remoto
        String outputPath = outputPaths + "\\teste3.json";
        test.csvToJson(path2, outputPath);
        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists());
    }

    @Test
    void csvToJson4() throws IOException {
        //Ficheiro Json igual a ficheiro de controlo, com ficheiro remoto
        String outputPath = outputPaths + "\\teste4.json";
        test.csvToJson(path2, outputPath);
        File outputFile = new File(outputPath);

        String json1 = readFile(outputFile);
        String json2 = readFile(expectedFile);

        JSONObject jsonObj1 = new JSONObject(json1);
        JSONObject jsonObj2 = new JSONObject(json2);

        boolean isEqual = jsonObj1.similar(jsonObj2);
        assertTrue(isEqual);
    }

    @Test
    void csvToJson5(){
        //Não criação de ficheiro, dado path não existente
        String outputPath = outputPaths + "\\teste5.json";
        String pathErrado = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\test5.csv";
        test.csvToJson(pathErrado, outputPath);
        File outputFile = new File(outputPath);
        assertFalse(outputFile.exists());
    }

    //Função auxiliar csvToJson2() e csvToJson4()
    private static String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}