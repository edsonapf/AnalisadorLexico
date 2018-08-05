
import java.util.ArrayList;


public class Main {
    
    public static void main(String args[]){
        
        ArrayList<String> codigo = new ArrayList<String>();
        ArrayList<String> palavraChave = new ArrayList<String>();
        ArquivoCodigo arq = new ArquivoCodigo(codigo);
        ArquivoChave arqChave = new ArquivoChave(palavraChave);
        
        arq.lerArquivoCodigo();
        arqChave.lerArquivoChave();

        
    }
    
}
