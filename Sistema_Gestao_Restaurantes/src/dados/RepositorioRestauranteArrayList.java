package dados;

import java.util.ArrayList;
import java.util.List;
import negocio.Restaurante;

public class RepositorioRestauranteArrayList {
    private List<Restaurante> restaurantes; 

    public RepositorioRestauranteArrayList() {
        this.restaurantes = new ArrayList<>();
    }

    public void inserir(Restaurante restaurante) { 
        this.restaurantes.add(restaurante);
    }

    public Restaurante buscarPorCnpj(String cnpj) {
        for (Restaurante r : restaurantes) {
            if (r.getCnpj().equals(cnpj)) {
                return r;
            }
        }
        return null;
    }

    public boolean remover(String cnpj) {
        Restaurante restauranteParaRemover = buscarPorCnpj(cnpj);
        if (restauranteParaRemover != null) {
            return this.restaurantes.remove(restauranteParaRemover);
        }
        return false;
    }

    public List<Restaurante> listarTodos() {
        return this.restaurantes;
    }
}