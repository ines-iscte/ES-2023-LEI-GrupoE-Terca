package json.Tests;

import json.CsvToJson;
import json.JsonToCsv;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class JsonToCsvTest {
    private static JsonToCsv jsonToCsvTest=new JsonToCsv();

    @Test
    void jsonToCsv1() {
        //Criação de ficheiro Csv, com ficheiro local
        jsonToCsvTest.jsonToCsv("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.csv");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.csv");
        assertTrue(outputFile.exists());
    }

    @Test
    void jsonToCsv2() throws IOException {
        //Ficheiro Csv igual a ficheiro de controlo, com ficheiro local
        jsonToCsvTest.jsonToCsv("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\test2.csv");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste2.csv");

        String expectedPath = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\TestCSV.csv";
        File expectedFile = new File(expectedPath);

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
        jsonToCsvTest.jsonToCsv("https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/arquivo.json", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste3.csv");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste3.csv");
        assertTrue(outputFile.exists());
    }

    @Test
    void jsonToCsv4() throws IOException {
        //Ficheiro Csv igual a ficheiro de controlo, com ficheiro remoto
        jsonToCsvTest.jsonToCsv("https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/arquivo.json", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste4.csv");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste4.csv");

        String expectedPath = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\TestCSV.csv";
        File expectedFile = new File(expectedPath);

        List<String> outputLines = Files.readAllLines(outputFile.toPath());
        List<String> expectedLines = Files.readAllLines(expectedFile.toPath());
        assertEquals(outputLines.size(), expectedLines.size());

        for (int i = 0; i < outputLines.size(); i++) {
            String outputLine = outputLines.get(i).replaceAll("\\s+", "");
            String expectedLine = expectedLines.get(i).replaceAll("\\s+", "");
            assertEquals(outputLine, expectedLine);
        }
    }
}