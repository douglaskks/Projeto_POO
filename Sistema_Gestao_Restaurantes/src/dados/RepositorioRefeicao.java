package dados;

import negocio.Refeicao;
import negocio.Restaurante;
import excecoes.RefeicaoException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRefeicao implements IRepositorio<Refeicao> {
    private List<Refeicao> refeicoes;
    
    public RepositorioRefeicao() {
        this.refeicoes = new ArrayList<>();
    }
    
    @Override
    public void adicionar(Refeicao refeicao) throws RefeicaoException {
        if (refeicao == null) {
            throw new RefeicaoException("Refeição não pode ser nula");
        }
        
        if (refeicao.getNome() == null || refeicao.getNome().trim().isEmpty()) {
            throw new RefeicaoException("Nome da refeição é obrigatório");
        }
        
        if (refeicao.getValor() <= 0) {
            throw new RefeicaoException("Valor da refeição deve ser maior que zero");
        }
        
        if (refeicao.getRestaurante() == null) {
            throw new RefeicaoException("Refeição deve estar associada a um restaurante");
        }
        
        // Verificar se já existe refeição com mesmo nome no restaurante
        if (existeNoRestaurante(refeicao.getNome(), refeicao.getRestaurante().getCnpj())) {
            throw new RefeicaoException("Já existe uma refeição com este nome no restaurante");
        }
        
        refeicoes.add(refeicao);
    }
    
    @Override
    public void remover(String identificador) throws RefeicaoException {
        // Para refeições, usamos nome_cnpjRestaurante como identificador
        String[] partes = identificador.split("_");
        if (partes.length != 2) {
            throw new RefeicaoException("Identificador inválido para remoção de refeição");
        }
        
        String nome = partes[0];
        String cnpjRestaurante = partes[1];
        
        Refeicao refeicao = buscarPorNomeERestaurante(nome, cnpjRestaurante);
        if (refeicao == null) {
            throw new RefeicaoException("Refeição não encontrada");
        }
        
        // Verificar se não está em pedidos pendentes ou prontos
        if (estaSendoUsadaEmPedidos(refeicao)) {
            throw new RefeicaoException("Não é possível deletar refeição que está em pedidos pendentes ou prontos");
        }
        
        refeicoes.remove(refeicao);
    }
    
    @Override
    public void atualizar(Refeicao refeicao) throws RefeicaoException {
        if (refeicao == null) {
            throw new RefeicaoException("Refeição não pode ser nula");
        }
        
        String identificador = refeicao.getNome() + "_" + refeicao.getRestaurante().getCnpj();
        Refeicao existente = buscar(identificador);
        if (existente == null) {
            throw new RefeicaoException("Refeição não encontrada para atualização");
        }
        
        if (refeicao.getValor() <= 0) {
            throw new RefeicaoException("Valor da refeição deve ser maior que zero");
        }
        
        int index = refeicoes.indexOf(existente);
        refeicoes.set(index, refeicao);
    }
    
    @Override
    public Refeicao buscar(String identificador) {
        // Formato: nome_cnpjRestaurante
        String[] partes = identificador.split("_");
        if (partes.length != 2) return null;
        
        String nome = partes[0];
        String cnpjRestaurante = partes[1];
        
        return buscarPorNomeERestaurante(nome, cnpjRestaurante);
    }
    
    public Refeicao buscarPorNomeERestaurante(String nome, String cnpjRestaurante) {
        return refeicoes.stream()
                .filter(r -> r.getNome().equals(nome) && 
                           r.getRestaurante().getCnpj().equals(cnpjRestaurante))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Refeicao> listarTodos() {
        return new ArrayList<>(refeicoes);
    }
    
    public List<Refeicao> listarPorRestaurante(String cnpjRestaurante) {
        return refeicoes.stream()
                .filter(r -> r.getRestaurante().getCnpj().equals(cnpjRestaurante))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    @Override
    public boolean existe(String identificador) {
        return buscar(identificador) != null;
    }
    
    public boolean existeNoRestaurante(String nome, String cnpjRestaurante) {
        return buscarPorNomeERestaurante(nome, cnpjRestaurante) != null;
    }
    
    private boolean estaSendoUsadaEmPedidos(Refeicao refeicao) {
        // Esta verificação seria feita consultando o repositório de pedidos
        // Por simplicidade, retornamos false aqui
        // Em uma implementação completa, deveria verificar no RepositorioPedido
        return false;
    }
}