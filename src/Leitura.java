import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Leitura {
    private List<String> lista = new ArrayList();

    public String removeAcentos(String value) {
        String normalizer = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }


    public List<String> CarregarArquivo(int tam_palavra){
        try {
            // Le o arquivo
            FileReader fr = new FileReader("Banco_de_Palavras.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while( (linha = reader.readLine()) != null ){
                if(linha.trim().length() == tam_palavra)
                    lista.add(removeAcentos(linha.trim()));
            }

        } catch (IOException e) {
            return null;
        }
        return lista;
    }

    public String PegarPalavra(List<String> l){
        Random variavel = new Random();
        return l.get(variavel.nextInt(0,l.size()));
    }

}
