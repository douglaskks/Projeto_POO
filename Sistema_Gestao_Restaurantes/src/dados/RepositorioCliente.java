package dados;

import negocio.Cliente;
import excecoes.ClienteException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCliente implements IRepositorio<Cliente> {
    private List<Cliente> clientes;
    
    public RepositorioCliente() {
        this.clientes = new ArrayList<>();
    }
    
    @Override
    public void adicionar(Cliente cliente) throws ClienteException {
        if (cliente == null) {
            throw new ClienteException("Cliente não pode ser nulo");
        }
        
        if (!validarCPF(cliente.getDocumento())) {
            throw new ClienteException("CPF deve ter 11 dígitos");
        }
        
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new ClienteException("Nome do cliente é obrigatório");
        }
        
        if (existe(cliente.getDocumento())) {
            throw new ClienteException("CPF já está cadastrado no sistema");
        }
        
        clientes.add(cliente);
    }
    
    @Override
    public void remover(String cpf) throws ClienteException {
        Cliente cliente = buscar(cpf);
        if (cliente == null) {
            throw new ClienteException("Cliente não encontrado");
        }
        
        clientes.remove(cliente);
    }
    
    @Override
    public void atualizar(Cliente cliente) throws ClienteException {
        if (cliente == null) {
            throw new ClienteException("Cliente não pode ser nulo");
        }
        
        Cliente existente = buscar(cliente.getDocumento());
        if (existente == null) {
            throw new ClienteException("Cliente não encontrado para atualização");
        }
        
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new ClienteException("Nome do cliente é obrigatório");
        }
        
        int index = clientes.indexOf(existente);
        clientes.set(index, cliente);
    }
    
    @Override
    public Cliente buscar(String cpf) {
        return clientes.stream()
                .filter(c -> c.getDocumento().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    public Cliente buscarPorNome(String nome) {
        return clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
    @Override
    public boolean existe(String cpf) {
        return buscar(cpf) != null;
    }
    
    private boolean validarCPF(String cpf) {
        return cpf != null && cpf.replaceAll("[^0-9]", "").length() == 11;
    }
}