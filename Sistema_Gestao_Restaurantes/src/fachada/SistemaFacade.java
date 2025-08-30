package fachada;

import dados.*;
import negocio.*;
import excecoes.*;
import java.util.List;
import java.util.Date;

public class SistemaFacade {
    private static SistemaFacade instancia;
    
    private RepositorioCliente repositorioCliente;
    private RepositorioRestaurante repositorioRestaurante;
    private RepositorioFuncionario repositorioFuncionario;
    private RepositorioPedido repositorioPedido;
    private RepositorioRefeicao repositorioRefeicao;
    private RepositorioCupom repositorioCupom;
    
    private SistemaFacade() {
        this.repositorioCliente = new RepositorioCliente();
        this.repositorioRestaurante = new RepositorioRestaurante();
        this.repositorioFuncionario = new RepositorioFuncionario();
        this.repositorioPedido = new RepositorioPedido();
        this.repositorioRefeicao = new RepositorioRefeicao();
        this.repositorioCupom = new RepositorioCupom();
    }
    
    public static synchronized SistemaFacade getInstance() {
        if (instancia == null) {
            instancia = new SistemaFacade();
        }
        return instancia;
    }
    
    // ==================== OPERAÇÕES COM CLIENTES ====================
    
    public void cadastrarCliente(String nome, String cpf, String endereco, String telefone) 
            throws ClienteException {
        Cliente cliente = new Cliente(nome, cpf);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);
        repositorioCliente.adicionar(cliente);
    }
    
    public void atualizarCliente(Cliente cliente) throws ClienteException {
        repositorioCliente.atualizar(cliente);
    }
    
    public void removerCliente(String cpf) throws ClienteException {
        repositorioCliente.remover(cpf);
    }
    
    public Cliente buscarCliente(String cpf) {
        return repositorioCliente.buscar(cpf);
    }
    
    public List<Cliente> listarClientes() {
        return repositorioCliente.listarTodos();
    }
    
    // ==================== OPERAÇÕES COM RESTAURANTES ====================
    
    public void cadastrarRestaurante(String nome, String cnpj, CategoriaRestaurante categoria, 
                                   String endereco) throws RestauranteException {
        Restaurante restaurante = new Restaurante(nome, cnpj, categoria);
        restaurante.setEndereco(endereco);
        repositorioRestaurante.adicionar(restaurante);
    }
    
    public void atualizarRestaurante(Restaurante restaurante) throws RestauranteException {
        repositorioRestaurante.atualizar(restaurante);
    }
    
    public void removerRestaurante(String cnpj) throws RestauranteException {
        repositorioRestaurante.remover(cnpj);
    }
    
    public Restaurante buscarRestaurante(String cnpj) {
        return repositorioRestaurante.buscar(cnpj);
    }
    
    public List<Restaurante> listarRestaurantes() {
        return repositorioRestaurante.listarTodos();
    }
    
    // ==================== OPERAÇÕES COM FUNCIONÁRIOS ====================
    
    public void cadastrarFuncionario(String nome, String cpf, int idade, String cnpjRestaurante) 
            throws FuncionarioException, RestauranteException {
        
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        
        Funcionario funcionario = new Funcionario(nome, cpf, idade);
        funcionario.setRestaurante(restaurante);
        
        repositorioFuncionario.adicionar(funcionario);
        restaurante.adicionarFuncionario(funcionario);
    }
    
    public void atualizarFuncionario(Funcionario funcionario) throws FuncionarioException {
        repositorioFuncionario.atualizar(funcionario);
    }
    
    public void removerFuncionario(String cpf) throws FuncionarioException {
        Funcionario funcionario = repositorioFuncionario.buscar(cpf);
        if (funcionario != null && funcionario.getRestaurante() != null) {
            funcionario.getRestaurante().removerFuncionario(cpf);
        }
        repositorioFuncionario.remover(cpf);
    }
    
    public Funcionario buscarFuncionario(String cpf) {
        return repositorioFuncionario.buscar(cpf);
    }
    
    public List<Funcionario> listarFuncionarios() {
        return repositorioFuncionario.listarTodos();
    }
    
    public List<Funcionario> listarFuncionariosPorRestaurante(String cnpjRestaurante) {
        return repositorioFuncionario.buscarPorRestaurante(cnpjRestaurante);
    }
    
    // ==================== OPERAÇÕES COM REFEIÇÕES ====================
    
    public void cadastrarRefeicao(String nome, String descricao, double valor, String cnpjRestaurante) 
            throws RefeicaoException, RestauranteException {
        
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        
        Refeicao refeicao = new Refeicao(nome, valor, restaurante);
        refeicao.setDescricao(descricao);
        
        repositorioRefeicao.adicionar(refeicao);
        restaurante.adicionarRefeicao(refeicao);
    }
    
    public void atualizarRefeicao(Refeicao refeicao) throws RefeicaoException {
        repositorioRefeicao.atualizar(refeicao);
    }
    
    public void removerRefeicao(String nome, String cnpjRestaurante) throws RefeicaoException {
        String identificador = nome + "_" + cnpjRestaurante;
        Refeicao refeicao = repositorioRefeicao.buscar(identificador);
        
        if (refeicao != null && refeicao.getRestaurante() != null) {
            refeicao.getRestaurante().removerRefeicao(nome);
        }
        
        repositorioRefeicao.remover(identificador);
    }
    
    public Refeicao buscarRefeicao(String nome, String cnpjRestaurante) {
        return repositorioRefeicao.buscarPorNomeERestaurante(nome, cnpjRestaurante);
    }
    
    public List<Refeicao> listarRefeicoes() {
        return repositorioRefeicao.listarTodos();
    }
    
    public List<Refeicao> listarRefeicoesPorRestaurante(String cnpjRestaurante) {
        return repositorioRefeicao.listarPorRestaurante(cnpjRestaurante);
    }
    
    // ==================== OPERAÇÕES COM CUPONS ====================
    
    public void cadastrarCupom(String codigo, String descricao, TipoCupom tipo, double valor, 
                             Date dataVencimento) throws CupomException {
        Cupom cupom = new Cupom(codigo, tipo, valor);
        cupom.setDescricao(descricao);
        cupom.setDataVencimento(dataVencimento);
        repositorioCupom.adicionar(cupom);
    }
    
    public void atualizarCupom(Cupom cupom) throws CupomException {
        repositorioCupom.atualizar(cupom);
    }
    
    public void removerCupom(String codigo) throws CupomException {
        repositorioCupom.remover(codigo);
    }
    
    public Cupom buscarCupom(String codigo) {
        return repositorioCupom.buscar(codigo);
    }
    
    public List<Cupom> listarCupons() {
        return repositorioCupom.listarTodos();
    }
    
    public List<Cupom> listarCuponsValidos() {
        return repositorioCupom.listarCuponsValidos();
    }
    
    public void adicionarCupomAoCliente(String cpfCliente, String codigoCupom) 
            throws ClienteException, CupomException {
        Cliente cliente = repositorioCliente.buscar(cpfCliente);
        if (cliente == null) {
            throw new ClienteException("Cliente não encontrado");
        }
        
        Cupom cupom = repositorioCupom.buscar(codigoCupom);
        if (cupom == null) {
            throw new CupomException("Cupom não encontrado");
        }
        
        cliente.adicionarCupom(cupom);
    }
    
    // ==================== OPERAÇÕES COM PEDIDOS ====================
    
    public int criarPedido(String cpfCliente, String cnpjRestaurante, String enderecoEntrega) 
            throws PedidoException, ClienteException, RestauranteException {
        
        Cliente cliente = repositorioCliente.buscar(cpfCliente);
        if (cliente == null) {
            throw new ClienteException("Cliente não encontrado");
        }
        
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        
        Pedido pedido = new Pedido(cliente, restaurante);
        pedido.setEnderecoEntrega(enderecoEntrega);
        
        repositorioPedido.adicionar(pedido);
        cliente.adicionarPedido(pedido);
        
        return pedido.getId();
    }
    
    public void adicionarRefeicaoAoPedido(int idPedido, String nomeRefeicao, String cnpjRestaurante) 
            throws PedidoException, RefeicaoException {
        
        Pedido pedido = repositorioPedido.buscar(String.valueOf(idPedido));
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        
        Refeicao refeicao = repositorioRefeicao.buscarPorNomeERestaurante(nomeRefeicao, cnpjRestaurante);
        if (refeicao == null) {
            throw new RefeicaoException("Refeição não encontrada");
        }
        
        pedido.adicionarRefeicao(refeicao);
    }
    
    public void aplicarCupomAoPedido(int idPedido, String codigoCupom) 
            throws PedidoException, CupomException {
        
        Pedido pedido = repositorioPedido.buscar(String.valueOf(idPedido));
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        
        Cupom cupom = repositorioCupom.buscar(codigoCupom);
        if (cupom == null) {
            throw new CupomException("Cupom não encontrado");
        }
        
        if (!cupom.isValido()) {
            throw new CupomException("Cupom inválido ou já utilizado");
        }
        
        pedido.aplicarCupom(cupom);
    }
    
    public void alterarStatusPedido(int idPedido, StatusPedido novoStatus) throws PedidoException {
        repositorioPedido.alterarStatusPedido(idPedido, novoStatus);
    }
    
    public void cancelarPedido(int idPedido) throws PedidoException {
        Pedido pedido = repositorioPedido.buscar(String.valueOf(idPedido));
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        pedido.cancelarPedido();
    }
    
    public void confirmarPedido(int idPedido) throws PedidoException {
        Pedido pedido = repositorioPedido.buscar(String.valueOf(idPedido));
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        pedido.confirmarPedido();
    }
    
    public Pedido buscarPedido(int idPedido) {
        return repositorioPedido.buscar(String.valueOf(idPedido));
    }
    
    public List<Pedido> listarPedidos() {
        return repositorioPedido.listarTodos();
    }
    
    public List<Pedido> listarPedidosPorCliente(String cpfCliente) {
        return repositorioPedido.buscarPorCliente(cpfCliente);
    }
    
    public List<Pedido> listarPedidosPorRestaurante(String cnpjRestaurante) {
        return repositorioPedido.buscarPorRestaurante(cnpjRestaurante);
    }
    
    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return repositorioPedido.buscarPorStatus(status);
    }
    
    // ==================== OPERAÇÕES FINANCEIRAS ====================
    
    public void iniciarDiaRestaurante(String cnpjRestaurante) throws RestauranteException {
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        restaurante.iniciarDia();
    }
    
    public double fecharDiaRestaurante(String cnpjRestaurante) throws RestauranteException {
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        return restaurante.fecharDia();
    }
    
    public ControleFinanceiro obterControleFinanceiroRestaurante(String cnpjRestaurante) 
            throws RestauranteException {
        Restaurante restaurante = repositorioRestaurante.buscar(cnpjRestaurante);
        if (restaurante == null) {
            throw new RestauranteException("Restaurante não encontrado");
        }
        return restaurante.getControleFinanceiro();
    }
}