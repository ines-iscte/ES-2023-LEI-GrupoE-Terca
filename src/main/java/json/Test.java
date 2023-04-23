package json;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        csvToJson teste_1 =  new csvToJson("C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\exemplo.csv","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\exemplo1.json");
        System.out.println("done 1");

        csvToJson teste_2 = new csvToJson("https://raw.githubusercontent.com/anamoreira-iscte/ES-2023-LEI-GrupoE-Terca/main/exemplo.csv","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\exemplo2.json");
        System.out.println("done 2");

        jsonToCsv teste_3 = new jsonToCsv("C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo.json","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo1.csv");
        System.out.println("done 3");

        jsonToCsv teste_4 = new jsonToCsv("https://raw.githubusercontent.com/anamoreira-iscte/ES-2023-LEI-GrupoE-Terca/main/arquivo.json","C:\\Users\\maria\\Documents\\GitHub\\ES-2023-LEI-GrupoE-Terca\\arquivo2.csv");
        System.out.println("done 4");
    }
}
