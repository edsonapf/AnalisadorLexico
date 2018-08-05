
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoCodigo {
    
    private ArrayList<String> codigo;
    private File arquivo;
    private String linha;
    private FileReader fr;
    private BufferedReader br;
    
    public ArquivoCodigo(ArrayList<String> codigo){
        
        this.codigo = codigo;
        arquivo = new File("pascal.txt");
        
        
    }
    
    public void lerArquivoCodigo(){
        
        try{
            
            fr = new FileReader(arquivo);
            br = new BufferedReader(fr);
            
            while(br.ready()){
                
                linha = br.readLine();
                codigo.add(linha);
                
            }
            
            br.close();
            fr.close();
            
        }catch(IOException ex){
            
            ex.printStackTrace();
            
        }
        
    }
    
    
    
}
