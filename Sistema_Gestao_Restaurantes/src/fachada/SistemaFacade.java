package fachada;

import negocio.*;
import dados.IRepositorio;
import dados.RepositorioSerializacao;
import excecoes.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaFacade {
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private IRepositorio repositorio;
    
    public SistemaFacade() {
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.repositorio = new RepositorioSerializacao();
        carregarDados();
    }
    
    public void cadastrarCliente(String nome, String cpf, String endereco, String telefone) {
        if (clientes.stream().anyMatch(c -> c.getDocumento().equals(cpf))) {
            throw new FuncionarioException("CPF já cadastrado no sistema");
        }
        
        Cliente cliente = new Cliente(nome, cpf);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
        clientes.add(cliente);
        salvarDados();
    }
    
    public Cliente buscarClientePorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getDocumento().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }
    
    public void removerCliente(String cpf) {
        clientes.removeIf(c -> c.getDocumento().equals(cpf));
        salvarDados();
    }
    
    public void cadastrarFuncionario(String nome, String cpf, int idade, String nomeRestaurante) {
        if (funcionarios.stream().anyMatch(f -> f.getDocumento().equals(cpf))) {
            throw new FuncionarioException("CPF já cadastrado no sistema");
        }
        
        Funcionario funcionario = new Funcionario(nome, cpf, idade);
        funcionario.setNomeRestaurante(nomeRestaurante);
        funcionarios.add(funcionario);
        salvarDados();
    }
    
    public Funcionario buscarFuncionarioPorCpf(String cpf) {
        return funcionarios.stream()
                .filter(f -> f.getDocumento().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }
    
    public void removerFuncionario(String cpf) {
        funcionarios.removeIf(f -> f.getDocumento().equals(cpf));
        salvarDados();
    }
    
    public void adicionarPedidoCliente(String cpfCliente, String descricaoPedido) {
        Cliente cliente = buscarClientePorCpf(cpfCliente);
        if (cliente == null) {
            throw new FuncionarioException("Cliente não encontrado");
        }
        
        cliente.adicionarPedido(descricaoPedido);
        salvarDados();
    }
    
    private void salvarDados() {
        try {
            repositorio.salvar(clientes, "clientes.dat");
            repositorio.salvar(funcionarios, "funcionarios.dat");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void carregarDados() {
        try {
            Object clientesCarregados = repositorio.carregar("clientes.dat");
            if (clientesCarregados != null) {
                this.clientes = (List<Cliente>) clientesCarregados;
            }
            
            Object funcionariosCarregados = repositorio.carregar("funcionarios.dat");
            if (funcionariosCarregados != null) {
                this.funcionarios = (List<Funcionario>) funcionariosCarregados;
            }
        } catch (Exception e) {
            System.out.println("Iniciando sistema com dados em branco: " + e.getMessage());
        }
    }
}