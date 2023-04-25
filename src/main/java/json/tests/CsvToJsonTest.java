package json.tests;

import json.CsvToJson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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

        String outputContent = new String(Files.readAllBytes(outputFile.toPath()));
        String expectedContent = new String(Files.readAllBytes(expectedFile.toPath()));

        assertEquals(outputContent, expectedContent);
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

        String outputContent = new String(Files.readAllBytes(outputFile.toPath()));
        String expectedContent = new String(Files.readAllBytes(expectedFile.toPath()));

        assertEquals(outputContent, expectedContent);
    }
}