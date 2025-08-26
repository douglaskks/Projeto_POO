package ui;

import fachada.SistemaFacade;
import negocio.*;
import excecoes.*;
import java.util.Scanner;
import java.util.List;

public class MenuPrincipal {
    private SistemaFacade sistema;
    private Scanner scanner;
    
    public MenuPrincipal() {
        this.sistema = new SistemaFacade();
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        System.out.println("=== SISTEMA DE GESTÃO===");
        System.out.println("Funcionalidades: Clientes e Funcionários");
        
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuFuncionarios();
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Funcionários");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private String lerString(String mensagem) {
        System.out.print(mensagem);
        scanner.nextLine(); // Limpar buffer
        return scanner.nextLine();
    }
    
    private int lerInt(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextInt();
    }
    
    // Menu Clientes
    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente por CPF");
            System.out.println("4. Adicionar Pedido ao Histórico");
            System.out.println("5. Remover Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarCliente();
                    break;
                case 4:
                    adicionarPedidoCliente();
                    break;
                case 5:
                    removerCliente();
                    break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarCliente() {
        try {
            String nome = lerString("Nome do cliente: ");
            String cpf = lerString("CPF: ");
            String endereco = lerString("Endereço: ");
            String telefone = lerString("Telefone: ");
            
            sistema.cadastrarCliente(nome, cpf, endereco, telefone);
            System.out.println("Cliente cadastrado com sucesso!");
            
        } catch (FuncionarioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarClientes() {
        List<Cliente> clientes = sistema.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        System.out.println("\n=== CLIENTES CADASTRADOS ===");
        for (Cliente c : clientes) {
            System.out.println(c);
            System.out.println("------------------------");
        }
    }
    
    private void buscarCliente() {
        String cpf = lerString("CPF do cliente: ");
        Cliente cliente = sistema.buscarClientePorCpf(cpf);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            System.out.println("\n=== CLIENTE ENCONTRADO ===");
            System.out.println(cliente);
        }
    }
    
    private void adicionarPedidoCliente() {
        try {
            String cpf = lerString("CPF do cliente: ");
            String descricao = lerString("Descrição do pedido: ");
            
            sistema.adicionarPedidoCliente(cpf, descricao);
            System.out.println("Pedido adicionado ao histórico!");
            
        } catch (FuncionarioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void removerCliente() {
        String cpf = lerString("CPF do cliente a remover: ");
        sistema.removerCliente(cpf);
        System.out.println("Cliente removido!");
    }
    
    // Menu Funcionários
    private void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR FUNCIONÁRIOS ===");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Buscar Funcionário por CPF");
            System.out.println("4. Remover Funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    listarFuncionarios();
                    break;
                case 3:
                    buscarFuncionario();
                    break;
                case 4:
                    removerFuncionario();
                    break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarFuncionario() {
        try {
            String nome = lerString("Nome do funcionário: ");
            String cpf = lerString("CPF: ");
            int idade = lerInt("Idade: ");
            String nomeRestaurante = lerString("Nome do restaurante: ");
            
            sistema.cadastrarFuncionario(nome, cpf, idade, nomeRestaurante);
            System.out.println("Funcionário cadastrado com sucesso!");
            
        } catch (FuncionarioException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarFuncionarios() {
        List<Funcionario> funcionarios = sistema.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        
        System.out.println("\n=== FUNCIONÁRIOS CADASTRADOS ===");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            System.out.println("------------------------");
        }
    }
    
    private void buscarFuncionario() {
        String cpf = lerString("CPF do funcionário: ");
        Funcionario funcionario = sistema.buscarFuncionarioPorCpf(cpf);
        
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
        } else {
            System.out.println("\n=== FUNCIONÁRIO ENCONTRADO ===");
            System.out.println(funcionario);
        }
    }
    
    private void removerFuncionario() {
        String cpf = lerString("CPF do funcionário a remover: ");
        sistema.removerFuncionario(cpf);
        System.out.println("Funcionário removido!");
    }
    
    public static void main(String[] args) {
        new MenuPrincipal().executar();
    }
}