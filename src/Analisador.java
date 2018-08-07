
import java.util.ArrayList;
import java.util.StringJoiner;

public class Analisador {
    
    private ArrayList<String> cod;
    private ArrayList<String> chave;
    
    public Analisador(ArrayList<String> cod, ArrayList<String> chave){
        
        this.cod = cod;
        this.chave = chave;
        
    }
    
    public void excluiTabulacao(){
        
        for(int i = 0; i < cod.size(); i++){
            
            cod.set(i, cod.get(i).trim());
            
        }
        
    }
    
    //Se a função retornar falso, quer dizer que o comentário não foi fechado
    //Ajeitar a parte do comentario quando tem mais de uma chave ou quando fecha antes de abrir
    public boolean excluiComentario(){
        
        char linha[];
        int contChave = 0;
        
        for(int i = 0; i < cod.size(); i++){
            
            linha = cod.get(i).toCharArray();
            
            for(int j = 0; j < linha.length; j++){

                if(linha[j] == '{' && contChave < 1)                 
                    contChave++;
                
                if(linha[j] == '}' && contChave > 0){
                    contChave--;
                    linha[j] = ' ';
                }else if(linha[j] == '}' && contChave == 0){
                    
                    //caso ele ache um fecha chave sem ter aberto
                    return false;
                    
                }    
                    
                if(contChave > 0)
                    linha[j] = ' ';
                
            }
                        
            cod.set(i, String.valueOf(linha));
            
        }
        
        if(contChave == 0)
            return true;
        
        return false;
        
    }
    
    public void separaSimbolos(){
        
        char linha[];
        StringBuilder auxLinha;
        int aumentou;
        
        for(int i = 0; i < cod.size(); i++){
            
            linha = cod.get(i).toCharArray();
            auxLinha = new StringBuilder(cod.get(i));
            aumentou = 0;
            
            
            for(int j = 0; j < linha.length; j++){
                
                if(linha[j] == ';' || (linha[j] == ':' && linha[j+1] != '=') ||
                   linha[j] == '(' || linha[j] == ')' ||
                   linha[j] == ',' || linha[j] == '+' ||
                   linha[j] == '-' || linha[j] == '*' ||
                   linha[j] == '/'){
                    
                    if(j > 0 && linha[j-1] != ' '){
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length - 1) && linha[j+1] != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                    
                else if( (linha[j] == '>' && linha[j+1] == '=') ||
                    (linha[j] == '<' && linha[j+1] == '=') ||
                    (linha[j] == '<' && linha[j+1] == '>') ||
                    (linha[j] == ':' && linha[j+1] == '=') ){
                    
                    if((j > 0) && (linha[j-1] != ' ')){                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length - 2) && linha[j+2] != ' '){
                        auxLinha.insert(j+2+aumentou, ' ');
                        aumentou++;
                    }
                    
                                               
                }
                
                //Verifica apenas o =
                else if((linha[j] == '=') && (j > 0) && (linha[j-1] != '<' && linha[j-1] != '>' && linha[j-1] != ':')){
                    
                    if(linha[j-1] != ' '){                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length - 1) && linha[j+1] != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                else if( (linha[j] == '>' && linha[j+1] != '=') ||
                    (linha[j] == '<' && linha[j+1] != '=') ){
                    
                    if(j > 0 && linha[j-1] != ' '){                       
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }    
                    if(j < (linha.length - 1) && linha[j+1] != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                
                //Ajeitar o ponto final quando ele é o ultimo
                else if((linha[j] == '.' && !(linha[j-1] >= '0' && linha[j-1] <= '9'))){
                    
                    if(j > 0 && linha[j-1] != ' '){
                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                        
                    }
                    if(j < (linha.length - 1) && linha[j+1] != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
            }
            
            cod.set(i, auxLinha.toString());
            
        }
        
    }

    public String dividiString(String linha, int meioString){
        
        StringJoiner juntaQuebraString = new StringJoiner(" ");
        String quebraString[] = new String[2];
        
        quebraString[0] = linha.substring(0, meioString);
        quebraString[1] = linha.substring(meioString, linha.length());

        for(String a: quebraString)
            juntaQuebraString.add(a);
        
        
        return juntaQuebraString.toString();
        
    }
    
    /*public void casoEspecial(String linha){
        
        boolean achouPonto = false;
        
        for(int i = 0; i < linha.length(); i++){
            
            while(linha.charAt(i) >= '0' && linha.charAt(i) <= '9'){
                
                if(linha.charAt(i) == '.'){
                    break;
                }
                i++;
                
            }
            
        }
        
        
    }*/
    
    
    public void separaVariavel(){

        String linha[];
        String quebraPalavra[] = new String[2];
        StringJoiner juntaQuebraPalavra;
        StringJoiner juntaLinha;
        

        for(int i = 0; i < cod.size(); i++){
            
            
            juntaLinha = new StringJoiner(" ");
            linha = cod.get(i).split(" ");

            for(int j = 0; j < linha.length; j++){

                juntaQuebraPalavra = new StringJoiner(" ");
                
                //Verifica se tem um inteiro antes da variavel
                if(linha[j].matches("\\d+[a-zA-Z]\\w*")){
                    
                        for(int k = 0; k < linha[j].length(); k++){                         
                            
                            if(!(linha[j].charAt(k) >= '0' && linha[j].charAt(k) <= '9')){
   
                                linha[j] = dividiString(linha[j], k);
                                break;

                            }
                            
                        }

                }
                //verifica se tem um real antes da variavel
                else if(linha[j].matches("\\d+[.]\\d*[a-zA-Z]\\w*")){

                    boolean achouPonto = false;
                    
                    for(int k = 0; k < linha[j].length(); k++){
                        
                        if(linha[j].charAt(k) == '.'){

                            achouPonto = true;                            
                            
                        }
                        
                        if(achouPonto){

                            //Verifica se o caracter depois do ponto eh uma letra
                            if(k < (linha[j].length() - 1) &&
                               !(linha[j].charAt(k+1) >= '0' && linha[j].charAt(k+1) <= '9')){
                                
                                linha[j] = dividiString(linha[j], k+1);
                                break;
                                
                            }
                            
                        }
                        
                    }

                }
                //else if(linha[j].matches("\\w+[.].*")){
                    
                  //  casoEspecial(linha[j]);
                    
                //}

            }
            
            for(String a : linha)
                juntaLinha.add(a);
            
            cod.set(i, juntaLinha.toString());
            
        }

    }
    
    public void analisaCodigo(){
        
        String linhaQuebrada[];
        
        System.out.println("Token\t||\tClassificacao\t||\tLinha");
        
        for(int i = 0; i < cod.size(); i++){
            
            linhaQuebrada = cod.get(i).split(" ");
            
            for(int j = 0; j < linhaQuebrada.length; j++){
                
                if(linhaQuebrada[j].equals(chave.get(0)) ||
                   linhaQuebrada[j].equals(chave.get(1)) ||
                   linhaQuebrada[j].equals(chave.get(2)) ||
                   linhaQuebrada[j].equals(chave.get(3)) ||
                   linhaQuebrada[j].equals(chave.get(4)) ||
                   linhaQuebrada[j].equals(chave.get(5)) ||
                   linhaQuebrada[j].equals(chave.get(6)) ||
                   linhaQuebrada[j].equals(chave.get(7)) ||
                   linhaQuebrada[j].equals(chave.get(8)) ||
                   linhaQuebrada[j].equals(chave.get(9)) ||
                   linhaQuebrada[j].equals(chave.get(10)) ||
                   linhaQuebrada[j].equals(chave.get(11)) ||
                   linhaQuebrada[j].equals(chave.get(12)) ||
                   linhaQuebrada[j].equals(chave.get(13))){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tPalavra reservada\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals("+") || 
                        linhaQuebrada[j].equals("-") || 
                        linhaQuebrada[j].equals("or")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tOperador aditivo\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals("*") || 
                        linhaQuebrada[j].equals("/") || 
                        linhaQuebrada[j].equals("and")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tOperador multiplicativo\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].matches("\\d+")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tNumero Inteiro\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].matches("\\d+[.]\\d*")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tNumero Real\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals(":=")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tAtribuicao\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals("<") || linhaQuebrada[j].equals(">") ||
                        linhaQuebrada[j].equals("<=") || linhaQuebrada[j].equals(">=") ||
                        linhaQuebrada[j].equals("<>") || linhaQuebrada[j].equals("=")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tOperador Relacional\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals(";") || linhaQuebrada[j].equals(":") ||
                        linhaQuebrada[j].equals(",") || linhaQuebrada[j].equals(".") ||
                        linhaQuebrada[j].equals("(") || linhaQuebrada[j].equals(")")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tDelimitador\t\t"+(i+1));
                }
                else if(linhaQuebrada[j].matches("[a-zA-Z]\\w*")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tIdentificador\t\t"+(i+1));
                }
                
            }
            
        }
        
    }
    
    public void mostraCod(){
        
        for(int i = 0; i < cod.size(); i++)
            System.out.println(cod.get(i));
        
    }
    
}
