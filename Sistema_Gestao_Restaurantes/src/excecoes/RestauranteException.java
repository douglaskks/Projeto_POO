package excecoes;

public class RestauranteException extends Exception {
    public RestauranteException(String mensagem) {
        super(mensagem);
    }
    
    public RestauranteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}