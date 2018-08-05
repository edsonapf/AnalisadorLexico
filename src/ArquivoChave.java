
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ArquivoChave {
    
    private ArrayList<String> palavraChave;
    private File arquivo;
    private String linha;
    private FileReader fr;
    private BufferedReader br;
    
    public ArquivoChave(ArrayList<String> palavraChave){
        
        this.palavraChave = palavraChave;
        arquivo = new File("chave.txt");
        
        
    }
    
    public void lerArquivoChave(){
        
        try{
            
            fr = new FileReader(arquivo);
            br = new BufferedReader(fr);
            
            while(br.ready()){
                
                linha = br.readLine();
                palavraChave.add(linha);
                
            }
            
            br.close();
            fr.close();
            
        }catch(IOException ex){
            
            ex.printStackTrace();
            
        }
        
    }
    
}
