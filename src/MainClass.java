import java.util.*;

public class MainClass {
    public static int[] acharLetrasNoLugar(String palavra1, String palavra2){
        if(palavra1.length() != palavra2.length()) return null;
        int[] lista = new int[palavra1.length()];

        for(int s=0; s<palavra1.length(); s++){
            if(palavra1.charAt(s) == palavra2.charAt(s)) lista[s] ++;
        }
        return lista;
    }

    public static int[] contarLetrasDaTentativa(String palavra, String tentativa){
        if(tentativa.length() != palavra.length()) return null;
        int[] list = new int[palavra.length()];

        for(int s=0; s<palavra.length(); s++){
            for(int t=0; t<palavra.length(); t++){
                if(tentativa.charAt(s) == palavra.charAt(t)){
                    list[s] ++;
                }
            }
        }

        return list;
    }

    public static String letrasEmCumum(String palavra, String tentativa){
        if(tentativa.length() != palavra.length()) return null;
        int[] list = contarLetrasDaTentativa(palavra, tentativa);
        if(list == null) return null;

        List<Character> letras = new ArrayList<>();
        for(int i=0; i < palavra.length(); i++){
            if(list[i] > 0){
                if(!letras.contains(tentativa.charAt(i))) {
                    letras.add(tentativa.charAt(i));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(char c : letras){
            sb.append(c);
        }

        return sb.toString();
    }

    public static String getAcertos(String palavra, String tentativa){
        if(tentativa.length() != palavra.length()) return null;
        int[] listaEmComum = acharLetrasNoLugar(palavra, tentativa);

        String letrasEmComum = letrasEmCumum(palavra, tentativa);

        if(listaEmComum == null || letrasEmComum == null) return null;

        List<Character> retorno = new ArrayList<>();

        for(int i=0; i < listaEmComum.length; i++){
            if(listaEmComum[i] == 1)
                retorno.add(tentativa.toUpperCase(Locale.ROOT).charAt(i));

            else if(listaEmComum[i] > 1){
                if(!retorno.contains(tentativa.charAt(i)))
                    for(int l = 0; l < listaEmComum[i] - 1; l++)
                        retorno.add(tentativa.charAt(i));
            }
        }

        char[] letrasEmComumArray = letrasEmComum.toCharArray();
        int[] contagemLetrasTentativa = new int[letrasEmComum.length()];
        int[] contagemLetrasPalavra = new int[letrasEmComum.length()];

        for(int i=0; i < letrasEmComum.length(); i++){
            for(int j=0; j<tentativa.length(); j++){
                if(letrasEmComumArray[i] == tentativa.charAt(j))
                    contagemLetrasTentativa[i] ++;
                if(letrasEmComumArray[i] == palavra.charAt(j))
                    contagemLetrasPalavra[i] ++;
            }
        }

        for(int s=0; s < contagemLetrasTentativa.length;s++){
            if(contagemLetrasTentativa[s] > 0) {
                int freqRet = Collections.frequency(retorno, Character.toUpperCase(letrasEmComumArray[s]));
                //System.out.println(letrasEmComumArray[s]);
                //System.out.println(freqRet);
                int falt = contagemLetrasPalavra[s] - freqRet;
                //System.out.println(letrasEmComumArray[s] + " faltam: " + falt);
                if(falt > 0){
                    for(int i=0; i<falt; i++){
                        retorno.add(letrasEmComumArray[s]);
                    }
                }

            }

        }

        StringBuilder ret = new StringBuilder();
        for(char c : retorno){
            ret.append(c);
        }

        return ret.toString();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira o número de tentativas: ");
        int num_tentativas = scan.nextInt();
        if(num_tentativas <= 0) {
            num_tentativas = 6;
            System.out.println("Numero de tentativas inválido! Número de tentativas definido para 6.");
        }

        System.out.print("Insira o tamanho da palavra: ");
        int tam_palavra = scan.nextInt();
        if(tam_palavra > 23 || tam_palavra <= 0) {
            tam_palavra = 23;
            System.out.println("O tamanho mínimo para a palavra é 1,e o máximo é 23. O tamanho será definido em 23.");
        }

        Leitura leitura = new Leitura();

        String palavra = leitura.PegarPalavra(leitura.CarregarArquivo(tam_palavra)).toLowerCase();
        palavra = leitura.removeAcentos(palavra);

        System.out.println(palavra);

        Scanner scanString = new Scanner(System.in);
        for(int i=1; i<=num_tentativas; i++){
            System.out.print("Tentativa " + i + ": ");
            String tentativa = " ";
            while(tentativa.length() != tam_palavra) {
                tentativa = scanString.nextLine().toLowerCase();
                if(tentativa.length() != tam_palavra) {
                    System.out.println("Palavra muito curta ou muito longa. Tente novamente!");
                    System.out.print("Tentativa " + i + ": ");
                }
            }

            if (tentativa.contains(" ")) {
                String[] split = tentativa.split(" ");
                tentativa = split[0];
            }

            if (tentativa.equals(palavra)) {
                System.out.println("Você venceu!");
                break;
            }

            String acertos = getAcertos(palavra, tentativa);



            System.out.print("Tentativa " + i + ": ");
            System.out.println(acertos);

            if (num_tentativas == i) {
                System.out.println("Você perdeu! A palavra era : \"" + palavra + "\".");
            }

        }
    }

}
