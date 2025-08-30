package excecoes;

public class RefeicaoException extends Exception {
    public RefeicaoException(String mensagem) {
        super(mensagem);
    }
    
    public RefeicaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}