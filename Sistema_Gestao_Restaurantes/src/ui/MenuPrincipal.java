package ui;

import fachada.SistemaFacade;
import negocio.*;
import excecoes.*;
import java.util.Scanner;
import java.util.List;
import java.util.Date;

public class MenuPrincipal {
    private SistemaFacade sistema;
    private Scanner scanner;
    
    public MenuPrincipal() {
        this.sistema = SistemaFacade.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        System.out.println("=== SISTEMA DE GESTÃO DE DELIVERY ===");
        
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuRestaurantes();
                    break;
                case 3:
                    menuFuncionarios();
                    break;
                case 4:
                    menuRefeicoes();
                    break;
                case 5:
                    menuPedidos();
                    break;
                case 6:
                    menuCupons();
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Restaurantes");
        System.out.println("3. Gerenciar Funcionários");
        System.out.println("4. Gerenciar Refeições");
        System.out.println("5. Gerenciar Pedidos");
        System.out.println("6. Gerenciar Cupons");
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
        scanner.nextLine();
        return scanner.nextLine();
    }
    
    private int lerInt(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextInt();
    }
    
    private double lerDouble(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextDouble();
    }

    // ==================== MENU CLIENTES ====================
    
    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente por CPF");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Remover Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarCliente(); break;
                case 4: atualizarCliente(); break;
                case 5: removerCliente(); break;
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
            
        } catch (ClienteException e) { // CORRIGIDO: ClienteException
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
        Cliente cliente = sistema.buscarCliente(cpf); // CORRIGIDO: método correto
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            System.out.println("\n=== CLIENTE ENCONTRADO ===");
            System.out.println(cliente);
        }
    }
    
    private void atualizarCliente() {
        try {
            String cpf = lerString("CPF do cliente: ");
            Cliente cliente = sistema.buscarCliente(cpf);
            
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }
            
            String novoNome = lerString("Novo nome (atual: " + cliente.getNome() + "): ");
            String novoEndereco = lerString("Novo endereço (atual: " + cliente.getEndereco() + "): ");
            String novoTelefone = lerString("Novo telefone (atual: " + cliente.getTelefone() + "): ");
            
            if (!novoNome.trim().isEmpty()) cliente.setNome(novoNome);
            if (!novoEndereco.trim().isEmpty()) cliente.setEndereco(novoEndereco);
            if (!novoTelefone.trim().isEmpty()) cliente.setTelefone(novoTelefone);
            
            sistema.atualizarCliente(cliente);
            System.out.println("Cliente atualizado com sucesso!");
            
        } catch (ClienteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void removerCliente() {
        try {
            String cpf = lerString("CPF do cliente a remover: ");
            sistema.removerCliente(cpf);
            System.out.println("Cliente removido com sucesso!");
        } catch (ClienteException e) { // CORRIGIDO: Tratar exceção
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    // ==================== MENU RESTAURANTES ====================
    
    private void menuRestaurantes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR RESTAURANTES ===");
            System.out.println("1. Cadastrar Restaurante");
            System.out.println("2. Listar Restaurantes");
            System.out.println("3. Buscar Restaurante por CNPJ");
            System.out.println("4. Atualizar Restaurante");
            System.out.println("5. Remover Restaurante");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarRestaurante(); break;
                case 2: listarRestaurantes(); break;
                case 3: buscarRestaurante(); break;
                case 4: atualizarRestaurante(); break;
                case 5: removerRestaurante(); break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarRestaurante() {
        try {
            String nome = lerString("Nome do restaurante: ");
            String cnpj = lerString("CNPJ: ");
            String endereco = lerString("Endereço: ");
            
            System.out.println("Categorias disponíveis:");
            CategoriaRestaurante[] categorias = CategoriaRestaurante.values();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println((i + 1) + ". " + categorias[i].getDescricao());
            }
            
            int indiceCat = lerInt("Escolha a categoria (1-" + categorias.length + "): ") - 1;
            if (indiceCat < 0 || indiceCat >= categorias.length) {
                System.out.println("Categoria inválida!");
                return;
            }
            
            sistema.cadastrarRestaurante(nome, cnpj, categorias[indiceCat], endereco);
            System.out.println("Restaurante cadastrado com sucesso!");
            
        } catch (RestauranteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarRestaurantes() {
        List<Restaurante> restaurantes = sistema.listarRestaurantes();
        if (restaurantes.isEmpty()) {
            System.out.println("Nenhum restaurante cadastrado.");
            return;
        }
        
        System.out.println("\n=== RESTAURANTES CADASTRADOS ===");
        for (Restaurante r : restaurantes) {
            System.out.println(r);
            System.out.println("------------------------");
        }
    }
    
    private void buscarRestaurante() {
        String cnpj = lerString("CNPJ do restaurante: ");
        Restaurante restaurante = sistema.buscarRestaurante(cnpj);
        
        if (restaurante == null) {
            System.out.println("Restaurante não encontrado.");
        } else {
            System.out.println("\n=== RESTAURANTE ENCONTRADO ===");
            System.out.println(restaurante);
        }
    }
    
    private void atualizarRestaurante() {
        try {
            String cnpj = lerString("CNPJ do restaurante: ");
            Restaurante restaurante = sistema.buscarRestaurante(cnpj);
            
            if (restaurante == null) {
                System.out.println("Restaurante não encontrado.");
                return;
            }
            
            String novoNome = lerString("Novo nome (atual: " + restaurante.getNome() + "): ");
            String novoEndereco = lerString("Novo endereço (atual: " + restaurante.getEndereco() + "): ");
            
            if (!novoNome.trim().isEmpty()) restaurante.setNome(novoNome);
            if (!novoEndereco.trim().isEmpty()) restaurante.setEndereco(novoEndereco);
            
            sistema.atualizarRestaurante(restaurante);
            System.out.println("Restaurante atualizado com sucesso!");
            
        } catch (RestauranteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void removerRestaurante() {
        try {
            String cnpj = lerString("CNPJ do restaurante a remover: ");
            sistema.removerRestaurante(cnpj);
            System.out.println("Restaurante removido com sucesso!");
        } catch (RestauranteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    // ==================== MENU FUNCIONÁRIOS ====================
    
    private void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR FUNCIONÁRIOS ===");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Buscar Funcionário por CPF");
            System.out.println("4. Listar por Restaurante");
            System.out.println("5. Remover Funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: listarFuncionarios(); break;
                case 3: buscarFuncionario(); break;
                case 4: listarFuncionariosPorRestaurante(); break;
                case 5: removerFuncionario(); break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarFuncionario() {
        try {
            String nome = lerString("Nome do funcionário: ");
            String cpf = lerString("CPF: ");
            int idade = lerInt("Idade: ");
            String cnpjRestaurante = lerString("CNPJ do restaurante: ");
            
            sistema.cadastrarFuncionario(nome, cpf, idade, cnpjRestaurante);
            System.out.println("Funcionário cadastrado com sucesso!");
            
        } catch (FuncionarioException | RestauranteException e) {
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
        Funcionario funcionario = sistema.buscarFuncionario(cpf); // CORRIGIDO: método correto
        
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
        } else {
            System.out.println("\n=== FUNCIONÁRIO ENCONTRADO ===");
            System.out.println(funcionario);
        }
    }
    
    private void listarFuncionariosPorRestaurante() {
        String cnpjRestaurante = lerString("CNPJ do restaurante: ");
        List<Funcionario> funcionarios = sistema.listarFuncionariosPorRestaurante(cnpjRestaurante);
        
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado para este restaurante.");
            return;
        }
        
        System.out.println("\n=== FUNCIONÁRIOS DO RESTAURANTE ===");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
            System.out.println("------------------------");
        }
    }
    
    private void removerFuncionario() {
        try {
            String cpf = lerString("CPF do funcionário a remover: ");
            sistema.removerFuncionario(cpf);
            System.out.println("Funcionário removido com sucesso!");
        } catch (FuncionarioException e) { // CORRIGIDO: Tratar exceção
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    // ==================== MENU REFEIÇÕES ====================
    
    private void menuRefeicoes() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR REFEIÇÕES ===");
            System.out.println("1. Cadastrar Refeição");
            System.out.println("2. Listar Todas as Refeições");
            System.out.println("3. Listar por Restaurante");
            System.out.println("4. Buscar Refeição");
            System.out.println("5. Remover Refeição");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarRefeicao(); break;
                case 2: listarRefeicoes(); break;
                case 3: listarRefeicoesPorRestaurante(); break;
                case 4: buscarRefeicao(); break;
                case 5: removerRefeicao(); break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarRefeicao() {
        try {
            String nome = lerString("Nome da refeição: ");
            String descricao = lerString("Descrição: ");
            double valor = lerDouble("Valor: R$ ");
            String cnpjRestaurante = lerString("CNPJ do restaurante: ");
            
            sistema.cadastrarRefeicao(nome, descricao, valor, cnpjRestaurante);
            System.out.println("Refeição cadastrada com sucesso!");
            
        } catch (RefeicaoException | RestauranteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarRefeicoes() {
        List<Refeicao> refeicoes = sistema.listarRefeicoes();
        if (refeicoes.isEmpty()) {
            System.out.println("Nenhuma refeição cadastrada.");
            return;
        }
        
        System.out.println("\n=== REFEIÇÕES CADASTRADAS ===");
        for (Refeicao r : refeicoes) {
            System.out.println(r + " - Restaurante: " + r.getRestaurante().getNome());
            System.out.println("------------------------");
        }
    }
    
    private void listarRefeicoesPorRestaurante() {
        String cnpjRestaurante = lerString("CNPJ do restaurante: ");
        List<Refeicao> refeicoes = sistema.listarRefeicoesPorRestaurante(cnpjRestaurante);
        
        if (refeicoes.isEmpty()) {
            System.out.println("Nenhuma refeição encontrada para este restaurante.");
            return;
        }
        
        System.out.println("\n=== REFEIÇÕES DO RESTAURANTE ===");
        for (Refeicao r : refeicoes) {
            System.out.println(r);
            System.out.println("------------------------");
        }
    }
    
    private void buscarRefeicao() {
        String nome = lerString("Nome da refeição: ");
        String cnpjRestaurante = lerString("CNPJ do restaurante: ");
        Refeicao refeicao = sistema.buscarRefeicao(nome, cnpjRestaurante);
        
        if (refeicao == null) {
            System.out.println("Refeição não encontrada.");
        } else {
            System.out.println("\n=== REFEIÇÃO ENCONTRADA ===");
            System.out.println(refeicao);
        }
    }
    
    private void removerRefeicao() {
        try {
            String nome = lerString("Nome da refeição: ");
            String cnpjRestaurante = lerString("CNPJ do restaurante: ");
            sistema.removerRefeicao(nome, cnpjRestaurante);
            System.out.println("Refeição removida com sucesso!");
        } catch (RefeicaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    // ==================== MENU PEDIDOS ====================
    
    private void menuPedidos() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR PEDIDOS ===");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Adicionar Refeição ao Pedido");
            System.out.println("3. Aplicar Cupom ao Pedido");
            System.out.println("4. Alterar Status do Pedido");
            System.out.println("5. Listar Todos os Pedidos");
            System.out.println("6. Buscar Pedido por ID");
            System.out.println("7. Listar Pedidos por Status");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: criarPedido(); break;
                case 2: adicionarRefeicaoAoPedido(); break;
                case 3: aplicarCupomAoPedido(); break;
                case 4: alterarStatusPedido(); break;
                case 5: listarPedidos(); break;
                case 6: buscarPedido(); break;
                case 7: listarPedidosPorStatus(); break;
            }
        } while (opcao != 0);
    }
    
    private void criarPedido() {
        try {
            String cpfCliente = lerString("CPF do cliente: ");
            String cnpjRestaurante = lerString("CNPJ do restaurante: ");
            String enderecoEntrega = lerString("Endereço de entrega: ");
            
            int idPedido = sistema.criarPedido(cpfCliente, cnpjRestaurante, enderecoEntrega);
            System.out.println("Pedido criado com sucesso! ID: " + idPedido);
            
        } catch (PedidoException | ClienteException | RestauranteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void adicionarRefeicaoAoPedido() {
        try {
            int idPedido = lerInt("ID do pedido: ");
            String nomeRefeicao = lerString("Nome da refeição: ");
            String cnpjRestaurante = lerString("CNPJ do restaurante: ");
            
            sistema.adicionarRefeicaoAoPedido(idPedido, nomeRefeicao, cnpjRestaurante);
            System.out.println("Refeição adicionada ao pedido com sucesso!");
            
        } catch (PedidoException | RefeicaoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void aplicarCupomAoPedido() {
        try {
            int idPedido = lerInt("ID do pedido: ");
            String codigoCupom = lerString("Código do cupom: ");
            
            sistema.aplicarCupomAoPedido(idPedido, codigoCupom);
            System.out.println("Cupom aplicado ao pedido com sucesso!");
            
        } catch (PedidoException | CupomException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void alterarStatusPedido() {
        try {
            int idPedido = lerInt("ID do pedido: ");
            
            System.out.println("Status disponíveis:");
            StatusPedido[] status = StatusPedido.values();
            for (int i = 0; i < status.length; i++) {
                System.out.println((i + 1) + ". " + status[i].getDescricao());
            }
            
            int indiceStatus = lerInt("Escolha o novo status (1-" + status.length + "): ") - 1;
            if (indiceStatus < 0 || indiceStatus >= status.length) {
                System.out.println("Status inválido!");
                return;
            }
            
            sistema.alterarStatusPedido(idPedido, status[indiceStatus]);
            System.out.println("Status do pedido alterado com sucesso!");
            
        } catch (PedidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarPedidos() {
        List<Pedido> pedidos = sistema.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }
        
        System.out.println("\n=== PEDIDOS CADASTRADOS ===");
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println("------------------------");
        }
    }
    
    private void buscarPedido() {
        int idPedido = lerInt("ID do pedido: ");
        Pedido pedido = sistema.buscarPedido(idPedido);
        
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
        } else {
            System.out.println("\n=== PEDIDO ENCONTRADO ===");
            System.out.println(pedido);
            System.out.println("Refeições:");
            for (Refeicao r : pedido.getRefeicoes()) {
                System.out.println("- " + r);
            }
        }
    }
    
    private void listarPedidosPorStatus() {
        System.out.println("Status disponíveis:");
        StatusPedido[] status = StatusPedido.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + ". " + status[i].getDescricao());
        }
        
        int indiceStatus = lerInt("Escolha o status (1-" + status.length + "): ") - 1;
        if (indiceStatus < 0 || indiceStatus >= status.length) {
            System.out.println("Status inválido!");
            return;
        }
        
        List<Pedido> pedidos = sistema.listarPedidosPorStatus(status[indiceStatus]);
        
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com este status.");
            return;
        }
        
        System.out.println("\n=== PEDIDOS COM STATUS: " + status[indiceStatus].getDescricao() + " ===");
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println("------------------------");
        }
    }
    
    // ==================== MENU CUPONS ====================
    
    private void menuCupons() {
        int opcao;
        do {
            System.out.println("\n=== GERENCIAR CUPONS ===");
            System.out.println("1. Cadastrar Cupom");
            System.out.println("2. Listar Todos os Cupons");
            System.out.println("3. Listar Cupons Válidos");
            System.out.println("4. Buscar Cupom");
            System.out.println("5. Adicionar Cupom ao Cliente");
            System.out.println("6. Remover Cupom");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            
            switch (opcao) {
                case 1: cadastrarCupom(); break;
                case 2: listarCupons(); break;
                case 3: listarCuponsValidos(); break;
                case 4: buscarCupom(); break;
                case 5: adicionarCupomAoCliente(); break;
                case 6: removerCupom(); break;
            }
        } while (opcao != 0);
    }
    
    private void cadastrarCupom() {
        try {
            String codigo = lerString("Código do cupom: ");
            String descricao = lerString("Descrição: ");
            
            System.out.println("Tipos de cupom disponíveis:");
            TipoCupom[] tipos = TipoCupom.values();
            for (int i = 0; i < tipos.length; i++) {
                System.out.println((i + 1) + ". " + tipos[i].getDescricao());
            }
            
            int indiceTipo = lerInt("Escolha o tipo (1-" + tipos.length + "): ") - 1;
            if (indiceTipo < 0 || indiceTipo >= tipos.length) {
                System.out.println("Tipo inválido!");
                return;
            }
            
            double valor = lerDouble("Valor do cupom: ");
            
            sistema.cadastrarCupom(codigo, descricao, tipos[indiceTipo], valor, new Date());
            System.out.println("Cupom cadastrado com sucesso!");
            
        } catch (CupomException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void listarCupons() {
        List<Cupom> cupons = sistema.listarCupons();
        if (cupons.isEmpty()) {
            System.out.println("Nenhum cupom cadastrado.");
            return;
        }
        
        System.out.println("\n=== CUPONS CADASTRADOS ===");
        for (Cupom c : cupons) {
            System.out.println("Código: " + c.getCodigo() + " | Tipo: " + c.getTipo().getDescricao() + 
                             " | Valor: " + c.getValor() + " | Válido: " + (c.isValido() ? "Sim" : "Não"));
            System.out.println("------------------------");
        }
    }
    
    private void listarCuponsValidos() {
        List<Cupom> cupons = sistema.listarCuponsValidos();
        if (cupons.isEmpty()) {
            System.out.println("Nenhum cupom válido encontrado.");
            return;
        }
        
        System.out.println("\n=== CUPONS VÁLIDOS ===");
        for (Cupom c : cupons) {
            System.out.println("Código: " + c.getCodigo() + " | Tipo: " + c.getTipo().getDescricao() + 
                             " | Valor: " + c.getValor());
            System.out.println("------------------------");
        }
    }
    
    private void buscarCupom() {
        String codigo = lerString("Código do cupom: ");
        Cupom cupom = sistema.buscarCupom(codigo);
        
        if (cupom == null) {
            System.out.println("Cupom não encontrado.");
        } else {
            System.out.println("\n=== CUPOM ENCONTRADO ===");
            System.out.println("Código: " + cupom.getCodigo());
            System.out.println("Descrição: " + cupom.getDescricao());
            System.out.println("Tipo: " + cupom.getTipo().getDescricao());
            System.out.println("Valor: " + cupom.getValor());
            System.out.println("Válido: " + (cupom.isValido() ? "Sim" : "Não"));
        }
    }
    
    private void adicionarCupomAoCliente() {
        try {
            String cpfCliente = lerString("CPF do cliente: ");
            String codigoCupom = lerString("Código do cupom: ");
            
            sistema.adicionarCupomAoCliente(cpfCliente, codigoCupom);
            System.out.println("Cupom adicionado ao cliente com sucesso!");
            
        } catch (ClienteException | CupomException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void removerCupom() {
        try {
            String codigo = lerString("Código do cupom a remover: ");
            sistema.removerCupom(codigo);
            System.out.println("Cupom removido com sucesso!");
        } catch (CupomException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new MenuPrincipal().executar();
    }
}