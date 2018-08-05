
import java.util.ArrayList;

public class Analisador {
    
    private ArrayList<String> cod;
    private ArrayList<String> chave;
    
    public Analisador(ArrayList<String> cod, ArrayList<String> chave){
        
        this.cod = cod;
        this.chave = chave;
        
    }
    
    //Se a função retornar falso, quer dizer que o comentário não foi fechado
    public boolean excluiComentario(){
        
        char linha[];
        int contChave = 0;
        
        for(int i = 0; i < cod.size(); i++){
            
            linha = cod.get(i).toCharArray();
            
            for(int j = 0; j < linha.length; j++){

                if(linha[j] == '{')                 
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
        
        if(contChave > 0)
            return false;
        
        return true;
        
    }
    
    public void analisaCodigo(){
        
    }
    
}
