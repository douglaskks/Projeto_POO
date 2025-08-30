package excecoes;

public class CupomException extends Exception {
    public CupomException(String mensagem) {
        super(mensagem);
    }

    public CupomException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}