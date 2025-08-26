package dados;

import java.io.*;

public class RepositorioSerializacao implements IRepositorio {
    private static final String DIRETORIO_DADOS = "dados/";
    
    public RepositorioSerializacao() {
        File diretorio = new File(DIRETORIO_DADOS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
    
    @Override
    public void salvar(Object objeto, String nomeArquivo) throws Exception {
        String caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
        
        try (FileOutputStream fileOut = new FileOutputStream(caminhoCompleto);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            objectOut.writeObject(objeto);
            objectOut.flush();
        }
    }
    
    @Override
    public Object carregar(String nomeArquivo) throws Exception {
        String caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
        File arquivo = new File(caminhoCompleto);
        
        if (!arquivo.exists()) {
            return null;
        }
        
        try (FileInputStream fileIn = new FileInputStream(caminhoCompleto);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            return objectIn.readObject();
        }
    }
}
