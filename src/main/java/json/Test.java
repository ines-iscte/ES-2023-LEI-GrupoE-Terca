package json;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args){
        final Logger logger = Logger.getLogger(Test.class.getName());

        CsvToJson teste1 =  new CsvToJson("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\exemplo1.json");
        logger.log(Level.INFO, "Done 1");

        CsvToJson teste2 = new CsvToJson("https://raw.githubusercontent.com/anamoreira-iscte/ES-2023-LEI-GrupoE-Terca/main/exemplo.csv","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\exemplo2.json");
        logger.log(Level.INFO, "Done 2");

        JsonToCsv teste3 = new JsonToCsv("C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo.json","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo1.csv");
        logger.log(Level.INFO, "Done 3");

        JsonToCsv teste4 = new JsonToCsv("https://raw.githubusercontent.com/anamoreira-iscte/ES-2023-LEI-GrupoE-Terca/main/arquivo.json","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo2.csv");
        logger.log(Level.INFO, "Done 4");
    }

   /* public void teste1 (){
        csvToJson teste_1 =  new csvToJson("C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv","C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.json");
        String path1="C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\teste1.json";
        String path2="C:\\Users\\inesc\\OneDrive - ISCTE-IUL\\Documentos\\Iscte\\3º Ano\\2º Semestre\\ES\\ES-2023-LEI-GrupoE-Terca\\arquivo.json";
        File file1 = new File(path1);
        File file2 = new File(path2);
        assertEquals(file1.toString(), file2.toString());
    }*/
}
