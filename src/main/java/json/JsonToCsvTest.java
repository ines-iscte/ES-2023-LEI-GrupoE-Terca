package json;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonToCsvTest {
    private static JsonToCsv test=new JsonToCsv();
    private File expectedFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\TestCSV.csv");
    private String path1 = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\outputJSON.json";
    private String path2 = "https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/arquivo.json";
    private String outputPaths = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES";

    @Test
    void jsonToCsv1() {
        //Criação de ficheiro Csv, com ficheiro local
        String outputPath = outputPaths + "\\teste1.csv";
        test.jsonToCsv(path1, outputPath);
        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists());
    }

    @Test
    void jsonToCsv2() throws IOException {
        //Ficheiro Csv igual a ficheiro de controlo, com ficheiro remoto
        String outputPath = outputPaths + "\\teste2.csv";
        test.jsonToCsv(path1, outputPath);
        File outputFile = new File(outputPath);

        List<String> outputLines = Files.readAllLines(outputFile.toPath());
        List<String> expectedLines = Files.readAllLines(expectedFile.toPath());
        assertEquals(outputLines.size(), expectedLines.size());

        for (int i = 0; i < outputLines.size(); i++) {
            String outputLine = outputLines.get(i).replaceAll("\\s+", "");
            String expectedLine = expectedLines.get(i).replaceAll("\\s+", "");
            assertEquals(outputLine, expectedLine);
        }
    }

    @Test
    void jsonToCsv3(){
        //Criação de ficheiro Csv, com ficheiro remoto
        String outputPath = outputPaths + "\\teste3.csv";
        test.jsonToCsv(path2, outputPath);
        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists());
    }

    @Test
    void jsonToCsv4() throws IOException {
        //Ficheiro Csv igual a ficheiro de controlo, com ficheiro remoto
        String outputPath = outputPaths + "\\teste4.csv";
        test.jsonToCsv(path2, outputPath);
        File outputFile = new File(outputPath);

        List<String> outputLines = Files.readAllLines(outputFile.toPath());
        List<String> expectedLines = Files.readAllLines(expectedFile.toPath());
        assertEquals(outputLines.size(), expectedLines.size());

        for (int i = 0; i < outputLines.size(); i++) {
            String outputLine = outputLines.get(i).replaceAll("\\s+", "");
            String expectedLine = expectedLines.get(i).replaceAll("\\s+", "");
            assertEquals(outputLine, expectedLine);
        }
    }

    @Test
    void jsonToCsv5(){
        //Não criação de ficheiro, dado path não existente
        String outputPath = outputPaths + "\\teste5.csv";
        String pathErrado = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\test5.json";
        test.jsonToCsv(pathErrado, outputPath);
        File outputFile = new File(outputPath);
        assertFalse(outputFile.exists());
    }
}