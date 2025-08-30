package dados;

import java.util.List;

public interface IRepositorio<T> {
    void adicionar(T objeto) throws Exception;
    void remover(String identificador) throws Exception;
    void atualizar(T objeto) throws Exception;
    T buscar(String identificador);
    List<T> listarTodos();
    boolean existe(String identificador);
}
