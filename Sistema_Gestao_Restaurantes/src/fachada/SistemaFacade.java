package fachada;

import negocio.*;
import dados.IRepositorio;
import dados.RepositorioSerializacao;
import excecoes.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaFacade {
    private Restaurante restaurante;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Pedido> pedidos;
    private int proximoIdPedido = 1;
    private IRepositorio repositorio;
    
    public SistemaFacade() {
        this.repositorio = new RepositorioSerializacao();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.restaurante = new Restaurante("Restaurante Padrão", "00.000.000/0001-00");
        
        carregarDados();
    }

    // NOVOS MÉTODOS DE PEDIDO
    /**
     * Cria um novo pedido para um cliente com uma lista de itens.
     * @param cpfCliente O CPF do cliente que está fazendo o pedido.
     * @param itens A lista de itens (Comida/Bebida) do pedido.
     * @throws FuncionarioException 
     */
    public void criarPedido(String cpfCliente, List<Item> itens) {
        Cliente cliente = buscarClientePorCpf(cpfCliente);
        if (cliente == null) {
            throw new FuncionarioException("Cliente com CPF " + cpfCliente + " não encontrado para criar pedido.");
        }

        Pedido novoPedido = new Pedido(this.proximoIdPedido, cliente);
        
        for (Item item : itens) {
            novoPedido.adicionarItem(item);
        }

        this.pedidos.add(novoPedido);
        this.proximoIdPedido++; 
        
        cliente.adicionarPedido("Pedido ID: " + novoPedido.getId());

        salvarDados(); 
    }

     // @return Uma lista de Pedidos.
     
    public List<Pedido> listarPedidos() {
        return new ArrayList<>(this.pedidos);
    }

    public Restaurante getRestaurante() {
        return this.restaurante;
    }

    public void configurarRestaurante(String nome, String cnpj) {
        if (nome != null && !nome.isEmpty()) {
            this.restaurante.setNome(nome);
        }
        if (cnpj != null && !cnpj.isEmpty()) {
            this.restaurante.setCnpj(cnpj);
        }
        salvarDados();
    }

    public void adicionarItemCardapio(Item item) {
        this.restaurante.getCardapio().adicionarItem(item);
        salvarDados();
    }

    public List<Item> listarCardapio() {
        return new ArrayList<>(this.restaurante.getCardapio().getItens());
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
    public void atualizarCliente(String cpf, String novoEndereco, String novoTelefone) throws FuncionarioException {
        Cliente clienteParaAtualizar = buscarClientePorCpf(cpf);

        if (clienteParaAtualizar == null) {
            throw new FuncionarioException("Cliente com CPF " + cpf + " não encontrado para atualização.");
        }

        clienteParaAtualizar.setEndereco(novoEndereco);
        clienteParaAtualizar.setTelefone(novoTelefone);

        salvarDados();

        System.out.println(">>> Dados do cliente " + clienteParaAtualizar.getNome() + " atualizados com sucesso! <<<");
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
    
    public String gerarRelatorioGeral() {
        int totalClientes = this.clientes.size();
        int totalFuncionarios = this.funcionarios.size();
        int totalPedidos = this.pedidos.size();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n--- RELATÓRIO DO SISTEMA ---\n");
        relatorio.append("Total de Clientes Cadastrados: ").append(totalClientes).append("\n");
        relatorio.append("Total de Funcionários Cadastrados: ").append(totalFuncionarios).append("\n");
        relatorio.append("Total de Pedidos Realizados: ").append(totalPedidos).append("\n");
        relatorio.append("----------------------------\n");

        return relatorio.toString();
    }

    private void salvarDados() {
        try {
            repositorio.salvar(clientes, "clientes.dat");
            repositorio.salvar(funcionarios, "funcionarios.dat");
            repositorio.salvar(pedidos, "pedidos.dat");
            repositorio.salvar(restaurante, "restaurante.dat");
            repositorio.salvar(proximoIdPedido, "proximoIdPedido.dat");
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

            Object pedidosCarregados = repositorio.carregar("pedidos.dat");
            if (pedidosCarregados != null) {
                this.pedidos = (List<Pedido>) pedidosCarregados;
            }

            Object restauranteCarregado = repositorio.carregar("restaurante.dat");
            if (restauranteCarregado != null) {
                this.restaurante = (Restaurante) restauranteCarregado;
            }

            Object proximoIdCarregado = repositorio.carregar("proximoIdPedido.dat");
            if (proximoIdCarregado != null) {
                this.proximoIdPedido = (int) proximoIdCarregado;
            }

        } catch (Exception e) {
            System.out.println("Iniciando sistema com dados em branco: " + e.getMessage());
        }
    }
}