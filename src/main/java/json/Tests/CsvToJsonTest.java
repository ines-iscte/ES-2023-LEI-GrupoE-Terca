package json.Tests;

import json.CsvToJson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CsvToJsonTest {

    private static CsvToJson csvToJsonTest=new CsvToJson();

    @Test
    void csvToJson1() {
        //Criação de ficheiro Json, com ficheiro local
        csvToJsonTest.csvToJson("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.json");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.json");
        assertTrue(outputFile.exists());
    }

    @Test
    void csvToJson2() throws IOException {
        //Ficheiro Json igual a ficheiro de controlo, com ficheiro local
        csvToJsonTest.csvToJson("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste2.json");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste2.json");

        String expectedPath = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json";
        File expectedFile = new File(expectedPath);

        String outputContent = new String(Files.readAllBytes(outputFile.toPath()));
        String expectedContent = new String(Files.readAllBytes(expectedFile.toPath()));

        assertEquals(outputContent, expectedContent);
    }

    @Test
    void csvToJson3(){
        //Criação de ficheiro Json, com ficheiro remoto
        csvToJsonTest.csvToJson("https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/exemplo.csv", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste3.json");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste3.json");
        assertTrue(outputFile.exists());
    }

    @Test
    void csvToJson4() throws IOException {
        //Ficheiro Json igual a ficheiro de controlo, com ficheiro remoto
        csvToJsonTest.csvToJson("https://raw.githubusercontent.com/ines-iscte/ES-2023-LEI-GrupoE-Terca/main/exemplo.csv", "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste4.json");
        File outputFile = new File("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste4.json");

        String expectedPath = "C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json";
        File expectedFile = new File(expectedPath);

        String outputContent = new String(Files.readAllBytes(outputFile.toPath()));
        String expectedContent = new String(Files.readAllBytes(expectedFile.toPath()));

        assertEquals(outputContent, expectedContent);
    }
}