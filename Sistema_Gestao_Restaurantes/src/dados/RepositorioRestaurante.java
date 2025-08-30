package dados;

import negocio.Restaurante;
import excecoes.RestauranteException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRestaurante implements IRepositorio<Restaurante> {
    private List<Restaurante> restaurantes;

    public RepositorioRestaurante() {
        this.restaurantes = new ArrayList<>();
    }

    @Override
    public void adicionar(Restaurante restaurante) throws RestauranteException {
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não pode ser nulo");
        }

        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new RestauranteException("Nome do restaurante é obrigatório");
        }

        if (!validarCNPJ(restaurante.getCnpj())) {
            throw new RestauranteException("CNPJ deve ter 14 dígitos");
        }

        if (existe(restaurante.getCnpj())) {
            throw new RestauranteException("CNPJ já está cadastrado no sistema");
        }

        restaurantes.add(restaurante);
    }

    @Override
    public void remover(String cnpj) throws RestauranteException {
        Restaurante restaurante = buscar(cnpj);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }

        if (!restaurante.getFuncionarios().isEmpty()) {
            throw new RestauranteException("Não é possível deletar restaurante com funcionários ativos");
        }

        restaurantes.remove(restaurante);
    }

    @Override
    public void atualizar(Restaurante restaurante) throws RestauranteException {
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não pode ser nulo");
        }

        Restaurante existente = buscar(restaurante.getCnpj());
        if (existente == null) {
            throw new RestauranteException("Restaurante não encontrado para atualização");
        }

        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new RestauranteException("Nome do restaurante é obrigatório");
        }

        int index = restaurantes.indexOf(existente);
        restaurantes.set(index, restaurante);
    }

    @Override
    public Restaurante buscar(String cnpj) {
        return restaurantes.stream()
                .filter(r -> r.getCnpj().equals(cnpj))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Restaurante> listarTodos() {
        return new ArrayList<>(restaurantes);
    }

    @Override
    public boolean existe(String cnpj) {
        return buscar(cnpj) != null;
    }

    private boolean validarCNPJ(String cnpj) {
        return cnpj != null && cnpj.replaceAll("[^0-9]", "").length() == 14;
    }
}
