package excecoes;

public class ClienteException extends Exception {
    public ClienteException(String mensagem) {
        super(mensagem);
    }
    
    public ClienteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
