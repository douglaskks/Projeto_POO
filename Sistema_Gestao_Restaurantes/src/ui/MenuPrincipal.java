package ui;

import fachada.SistemaFacade;
import negocio.*; 
import excecoes.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList; 

public class MenuPrincipal {
    private SistemaFacade sistema;
    private Scanner scanner;
    
    public MenuPrincipal() {
        this.sistema = new SistemaFacade();
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        System.out.println("=== SISTEMA DE GESTÃO===");
        System.out.println("Funcionalidades: Clientes, Funcionários, Restaurante e Pedidos");
        
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
                case 3: 
                    menuRestaurante();
                    break;
                case 4: 
                    menuPedidos();
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
        System.out.println("3. Gerenciar Restaurante");
        System.out.println("4. Gerenciar Pedidos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (Exception e) {
            return -1;
        }
    }
    
    private String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    private int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido.");
            }
        }
    }
    
    private double lerDouble(String mensagem) {
         while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número válido (ex: 10.50).");
            }
        }
    }
    
    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente por CPF");
            System.out.println("4. Remover Cliente");
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
                    removerCliente();
                    break;
            }
        } while (opcao != 0);
    }
    
    //  O método adicionarPedidoCliente() FOI REMOVIDO DAQUI
    
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
    
    private void removerCliente() {
        String cpf = lerString("CPF do cliente a remover: ");
        sistema.removerCliente(cpf);
        System.out.println("Cliente removido!");
    }
    
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

    private void menuRestaurante() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR RESTAURANTE ===");
            System.out.println("Restaurante: " + sistema.getRestaurante().getNome());
            System.out.println("1. Alterar dados do Restaurante");
            System.out.println("2. Adicionar item ao Cardápio");
            System.out.println("3. Listar Cardápio");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    configurarRestaurante();
                    break;
                case 2:
                    adicionarItemCardapio();
                    break;
                case 3:
                    listarCardapio();
                    break;
            }
        } while (opcao != 0);
    }


    private void configurarRestaurante() {
        System.out.println("\n--- Alterar Dados do Restaurante ---");
        String nome = lerString("Novo nome (deixe em branco para não alterar): ");
        String cnpj = lerString("Novo CNPJ (deixe em branco para não alterar): ");
        sistema.configurarRestaurante(nome, cnpj);
        System.out.println("Dados do restaurante atualizados!");
    }

    private void adicionarItemCardapio() {
        System.out.println("\n--- Adicionar Item ao Cardápio ---");
        String nome = lerString("Nome do item: ");
        double preco = lerDouble("Preço: ");
        String descricao = lerString("Descrição: ");
        double valorNutricional = lerDouble("Valor Nutricional (calorias): ");

        System.out.println("O item é uma (1) Comida ou (2) Bebida?");
        int tipo = lerOpcao();

        if (tipo == 1) {
           
            System.out.println("Escolha a categoria da comida:");
            for (CategoriaComida c : CategoriaComida.values()) {
                System.out.println((c.ordinal() + 1) + ". " + c.getDescricao());
            }
            int catOpcao = lerOpcao() - 1;
            if (catOpcao >= 0 && catOpcao < CategoriaComida.values().length) {
                CategoriaComida categoria = CategoriaComida.values()[catOpcao];
                Comida comida = new Comida(nome, preco, descricao, valorNutricional, categoria);
                sistema.adicionarItemCardapio(comida);
                System.out.println("Comida adicionada ao cardápio!");
            } else {
                System.out.println("Opção de categoria inválida.");
            }
        } else if (tipo == 2) {
            
             System.out.println("Escolha a categoria da bebida:");
            for (CategoriaBebida b : CategoriaBebida.values()) {
                System.out.println((b.ordinal() + 1) + ". " + b.getDescricao());
            }
            int catOpcao = lerOpcao() - 1;
             if (catOpcao >= 0 && catOpcao < CategoriaBebida.values().length) {
                CategoriaBebida categoria = CategoriaBebida.values()[catOpcao];
                Bebida bebida = new Bebida(nome, preco, descricao, valorNutricional, categoria);
                sistema.adicionarItemCardapio(bebida);
                System.out.println("Bebida adicionada ao cardápio!");
            } else {
                System.out.println("Opção de categoria inválida.");
            }
        } else {
            System.out.println("Tipo de item inválido.");
        }
    }
    
    private void listarCardapio() {
        List<Item> cardapio = sistema.listarCardapio();
        if (cardapio.isEmpty()) {
            System.out.println("O cardápio está vazio.");
            return;
        }
        System.out.println("\n=== CARDÁPIO ===");
        for (Item item : cardapio) {
            System.out.println(item); 
        }
        System.out.println("----------------");
    }

    private void menuPedidos() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR PEDIDOS ===");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Listar Todos os Pedidos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
            }
        } while (opcao != 0);
    }
    
    private void criarPedido() {
        System.out.println("\n--- Criar Novo Pedido ---");
        String cpf = lerString("CPF do cliente: ");
        
        if (sistema.buscarClientePorCpf(cpf) == null) {
            System.out.println("Erro: Cliente não encontrado.");
            return;
        }

        List<Item> cardapio = sistema.listarCardapio();
        if (cardapio.isEmpty()) {
            System.out.println("Não é possível criar um pedido, o cardápio está vazio.");
            return;
        }
        
        List<Item> itensPedido = new ArrayList<>();
        int itemOpcao;
        
        do {
            System.out.println("\n--- Adicionar Item ao Pedido ---");
            for (int i = 0; i < cardapio.size(); i++) {
                System.out.println((i + 1) + ". " + cardapio.get(i));
            }
            System.out.println("0. Finalizar Pedido");
            itemOpcao = lerInt("Escolha um item para adicionar (ou 0 para finalizar): ");

            if (itemOpcao > 0 && itemOpcao <= cardapio.size()) {
                Item itemSelecionado = cardapio.get(itemOpcao - 1);
                itensPedido.add(itemSelecionado);
                System.out.println("'" + itemSelecionado.getNome() + "' adicionado ao pedido.");
            } else if (itemOpcao != 0) {
                System.out.println("Opção inválida.");
            }
        } while (itemOpcao != 0);

        if (itensPedido.isEmpty()) {
            System.out.println("Nenhum item adicionado. Pedido cancelado.");
        } else {
            try {
                sistema.criarPedido(cpf, itensPedido);
                System.out.println("Pedido criado com sucesso!");
            } catch (FuncionarioException e) {
                System.out.println("Erro ao criar pedido: " + e.getMessage());
            }
        }
    }

    private void listarPedidos() {
        List<Pedido> pedidos = sistema.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido foi registrado.");
            return;
        }

        System.out.println("\n=== TODOS OS PEDIDOS ===");
        for (Pedido p : pedidos) {
            System.out.println("------------------------");
            System.out.println("ID do Pedido: " + p.getId());
            System.out.println("Cliente: " + p.getCliente().getNome());
            System.out.println("Itens:");
            for (Item i : p.getItens()) {
                System.out.println("  - " + i.getNome());
            }
            System.out.printf("Valor Total: R$%.2f\n", p.getValorTotal());
        }
        System.out.println("------------------------");
    }
    
    public static void main(String[] args) {
        new MenuPrincipal().executar();
    }
}