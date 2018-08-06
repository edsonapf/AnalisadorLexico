
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

                if(linha[j] == '{' && contChave <= 1)                 
                    contChave++;
                
                if(linha[j] == '}'){
                    contChave--;
                    linha[j] = ' ';
                }    
                    
                if(contChave > 0)
                    linha[j] = ' ';
                
            }
                        
            cod.set(i, String.valueOf(linha));
            
        }
        
        //for(int i = 0; i < cod.size(); i++)
        //    System.out.println(cod.get(i));
        
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
                
                if(linha[j].matches("\\d+[a-zA-Z]\\w*")){
                    
                        for(int k = 0; k < linha[j].length(); k++){                         
                            
                            if(!(linha[j].charAt(k) >= '0' && linha[j].charAt(k) <= '9')){
   
                                quebraPalavra[0] = linha[j].substring(0, k);
                                quebraPalavra[1] = linha[j].substring(k, linha[j].length());
                                
                                for(String a: quebraPalavra)
                                    juntaQuebraPalavra.add(a);

                                linha[j] = juntaQuebraPalavra.toString();
                                break;

                            }
                            
                        }

                }
                else if(linha[j].matches("\\d+.\\d*[a-zA-Z]\\w*")){

                    boolean achouPonto = false;
                    
                    for(int k = 0; k < linha[j].length(); k++){
                        
                        
                        
                    }

                }

            }
            
            for(String a : linha)
                juntaLinha.add(a);
            
            cod.set(i, juntaLinha.toString());
            
        }

    }
    
    public void analisaCodigo(){
        
    }
    
    public void mostraCod(){
        
        for(int i = 0; i < cod.size(); i++)
            System.out.println(cod.get(i));
        
    }
    
}
