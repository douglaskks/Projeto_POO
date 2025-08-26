package dados;

public interface IRepositorio {
    void salvar(Object objeto, String nomeArquivo) throws Exception;
    Object carregar(String nomeArquivo) throws Exception;
}