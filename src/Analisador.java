
import java.util.ArrayList;
import java.util.StringJoiner;

public class Analisador {
    
    private ArrayList<String> cod;
    private ArrayList<String> chave;
    
    public Analisador(ArrayList<String> cod, ArrayList<String> chave){
        
        this.cod = cod;
        this.chave = chave;
        
    }
    
    private boolean verificaSimbolos(){
        
        for(int i = 0; i < cod.size(); i++){
            
            for(int j = 0; j < cod.get(i).length(); j++){
                
                //Se nao estiver nesse intervalo de caracteres, possui caracteres invalidos
                if(!((cod.get(i).charAt(j) >= '(' && cod.get(i).charAt(j) <= '>') ||
                    (cod.get(i).charAt(j) >= 'A' && cod.get(i).charAt(j) <= 'Z') ||
                    (cod.get(i).charAt(j) >= 'a' && cod.get(i).charAt(j) <= 'z') ||
                    (cod.get(i).charAt(j) == ' ') || (cod.get(i).charAt(j) == '\t'))){
                    
                    return false;
                }
                
            }
            
        }
        
        return true;
        
    }
    
    private void excluiTabulacao(){
        
        for(int i = 0; i < cod.size(); i++){
            
            cod.set(i, cod.get(i).trim());
            
        }
        
    }
    
    //Se a função retornar falso, quer dizer que o comentário não foi fechado
    //Ajeitar a parte do comentario quando tem mais de uma chave ou quando fecha antes de abrir
    private boolean excluiComentario(){
        
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
    
    private void separaSimbolos(){
        
        String linha;
        StringBuilder auxLinha;
        int aumentou;
        
        for(int i = 0; i < cod.size(); i++){
            
            linha = cod.get(i);
            auxLinha = new StringBuilder(linha);
            aumentou = 0;
            
            
            for(int j = 0; j < linha.length(); j++){
                
                if(linha.charAt(j) == ';' || (linha.charAt(j) == ':' && linha.charAt(j+1) != '=') ||
                   linha.charAt(j) == '(' || linha.charAt(j) == ')' ||
                   linha.charAt(j) == ',' || linha.charAt(j) == '*' ||
                   linha.charAt(j) == '/' || linha.charAt(j) == '+' ||
                   linha.charAt(j) == '-'){
                    
                    if(j > 0 && linha.charAt(j-1) != ' '){
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length() - 1) && linha.charAt(j+1) != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                else if(linha.charAt(j) == 'o' && linha.charAt(j+1) == 'r'){
                    
                    if(j > 0 &&
                       !((linha.charAt(j-1) >= 'a' && linha.charAt(j-1) <= 'z') ||
                         (linha.charAt(j-1) >= 'A' && linha.charAt(j-1) <= 'Z'))){
                        
                        if(j < (linha.length() - 2) &&
                           !((linha.charAt(j+2) >= 'a' && linha.charAt(j+2) <= 'z') ||
                           (linha.charAt(j+2) >= 'A' && linha.charAt(j+2) <= 'Z'))){
                            
                            if(linha.charAt(j-1) != ' '){
                                auxLinha.insert(j+aumentou, ' ');
                                aumentou++;
                            }
                                
                            if(linha.charAt(j+2) != ' '){
                                auxLinha.insert(j+2+aumentou, ' ');
                                aumentou++;
                            }
                            
                            
                        }
                    }
                    
                }
                
                else if(linha.charAt(j) == 'a' && linha.charAt(j+1) == 'n' &&  linha.charAt(j+2) == 'd'){
                    
                    if(j > 0 &&
                       !((linha.charAt(j-1) >= 'a' && linha.charAt(j-1) <= 'z') ||
                         (linha.charAt(j-1) >= 'A' && linha.charAt(j-1) <= 'Z'))){
                        
                        if(j < (linha.length() - 3) &&
                           !((linha.charAt(j+3) >= 'a' && linha.charAt(j+3) <= 'z') ||
                           (linha.charAt(j+3) >= 'A' && linha.charAt(j+3) <= 'Z'))){
                            
                            if(linha.charAt(j-1) != ' '){
                                auxLinha.insert(j+aumentou, ' ');
                                aumentou++;
                            }
                                
                            if(linha.charAt(j+3) != ' '){
                                auxLinha.insert(j+3+aumentou, ' ');
                                aumentou++;
                            }
                            
                            
                        }
                    }
                    
                }
                    
                else if( (linha.charAt(j) == '>' && linha.charAt(j+1) == '=') ||
                    (linha.charAt(j) == '<' && linha.charAt(j+1) == '=') ||
                    (linha.charAt(j) == '<' && linha.charAt(j+1) == '>') ||
                    (linha.charAt(j) == ':' && linha.charAt(j+1) == '=') ){
                    
                    if((j > 0) && (linha.charAt(j-1) != ' ')){                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length() - 2) && linha.charAt(j+2) != ' '){
                        auxLinha.insert(j+2+aumentou, ' ');
                        aumentou++;
                    }
                    
                                               
                }
                
                //Verifica apenas o =
                else if((linha.charAt(j) == '=') && (j > 0) && 
                        (linha.charAt(j-1) != '<' && linha.charAt(j-1) != '>' && linha.charAt(j-1) != ':')){
                    
                    if(linha.charAt(j-1) != ' '){                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }
                    if(j < (linha.length() - 1) && linha.charAt(j+1) != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                else if( (linha.charAt(j) == '>' && linha.charAt(j+1) != '=') ||
                    (linha.charAt(j) == '<' && linha.charAt(j+1) != '=') ){
                    
                    if(j > 0 && linha.charAt(j-1) != ' '){                       
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                    }    
                    if(j < (linha.length() - 1) && linha.charAt(j+1) != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
                
                //Ajeitar o ponto final quando ele é o ultimo
                else if((linha.charAt(j) == '.' && !(linha.charAt(j-1) >= '0' && linha.charAt(j-1) <= '9'))){
                    
                    if(j > 0 && linha.charAt(j-1) != ' '){
                        
                        auxLinha.insert(j+aumentou, ' ');
                        aumentou++;
                        
                    }
                    if(j < (linha.length() - 1) && linha.charAt(j+1) != ' '){
                        auxLinha.insert(j+1+aumentou, ' ');
                        aumentou++;
                    }
                    
                    
                }
                
            }
            
            cod.set(i, auxLinha.toString());
            
        }
        
    }

    private String dividiString(String linha, int meioString){
        
        StringJoiner juntaQuebraString = new StringJoiner(" ");
        String quebraString[] = new String[2];
        
        quebraString[0] = linha.substring(0, meioString);
        quebraString[1] = linha.substring(meioString, linha.length());

        for(String a: quebraString)
            juntaQuebraString.add(a);
        
        
        return juntaQuebraString.toString();
        
    }
    
    private String casoEspecial(String linha){
        
        boolean achouPonto = false;
        int inicio = 0;
        
        for(int i = 0; i < linha.length(); i++){
            
            if(linha.charAt(inicio) >= '0' && linha.charAt(inicio) <= '9'){
                
                if(linha.charAt(i) == '.'){
                    
                    if(achouPonto){
                        
                        linha = dividiString(linha, i);
                        achouPonto = false;//caso seja um real saber se o ponto ja foi visto
                        inicio = i + 1;
                        
                    }else{
                        
                        achouPonto = true;
                        
                    }
                    
                }
                else if((linha.charAt(i) >= 'a' && linha.charAt(i) <= 'z') ||
                        (linha.charAt(i) >= 'A' && linha.charAt(i) <= 'Z')){
                    
                    linha = dividiString(linha, i);
                    inicio = i + 1;
                    achouPonto = false;
                }
                
            }
            else if((linha.charAt(inicio) >= 'a' && linha.charAt(inicio) <= 'z') ||
                    (linha.charAt(inicio) >= 'A' && linha.charAt(inicio) <= 'Z')){
                
                if(linha.charAt(i) == '.'){
                    
                    linha = dividiString(linha, i);
                    inicio = i + 1;
                    
                }
                
            }
            else if(linha.charAt(inicio) == '.'){

                linha = dividiString(linha, inicio+1);
                inicio = i + 1;

            }
            else if(linha.charAt(inicio) == ' '){
                inicio = i +1;
            }
            
            
        }
               
        return linha;
        
    }
    
    
    private void separaVariavel(){

        String linha[];
        String quebraPalavra[] = new String[2];
        StringJoiner juntaLinha;
        

        for(int i = 0; i < cod.size(); i++){
            
            
            juntaLinha = new StringJoiner(" ");
            linha = cod.get(i).split(" ");

            for(int j = 0; j < linha.length; j++){
                
                linha[j] = casoEspecial(linha[j]);                    

            }
            
            for(String a : linha)
                juntaLinha.add(a);
            
            cod.set(i, juntaLinha.toString());
            
        }

    }
    
    public void analisaCodigo(){
        
        String linhaQuebrada[];
        
        if(!excluiComentario()){
            System.out.println("O codigo erro nos comentarios!");
            System.exit(0);
        }
        
        if(!verificaSimbolos()){
            System.out.println("O codigo possui caracteres invalidos!");
            System.exit(0);
        }
        separaSimbolos();
        excluiTabulacao();
        separaVariavel();
        
        System.out.println("Token\t||\tClassificacao\t||\tLinha");
        
        
        for(int i = 0; i < cod.size(); i++){
            
            linhaQuebrada = cod.get(i).split(" ");
            
            for(int j = 0; j < linhaQuebrada.length; j++){
                
                if(chave.contains(linhaQuebrada[j])){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tPalavra reservada\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals("+") || 
                        linhaQuebrada[j].equals("-") || 
                        linhaQuebrada[j].equals("or")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tOperador aditivo\t"+(i+1));
                }
                else if(linhaQuebrada[j].equals("*") || 
                        linhaQuebrada[j].equals("/") || 
                        linhaQuebrada[j].equals("and")){
                    
                    System.out.println(linhaQuebrada[j]+"\t\tOperador multiplicativo\t"+(i+1));
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
        
        if(!excluiComentario()){
            System.out.println("O codigo erro nos comentarios!");
            System.exit(0);
        }
        
        if(!verificaSimbolos()){
            System.out.println("O codigo possui caracteres invalidos!");
            System.exit(0);
        }
        separaSimbolos();
        excluiTabulacao();
        separaVariavel();
        
        for(int i = 0; i < cod.size(); i++)
            System.out.println(cod.get(i));
        
    }
    
}
