package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import fachada.SistemaFacade;
import negocio.*;
import excecoes.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private final SistemaFacade sistema;
    private final JTextArea areaExibicaoClientes, areaExibicaoRestaurantes, areaExibicaoFuncionarios,
            areaExibicaoRefeicoes, areaExibicaoPedidos, areaExibicaoCupons;

    public TelaPrincipal() {
        this.sistema = SistemaFacade.getInstance();

        setTitle("Sistema de Gestão de Delivery");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        // Customização para as abas
        abas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        abas.setTabPlacement(JTabbedPane.TOP);

        // Criar painéis para cada aba
        areaExibicaoClientes = criarAreaDeTexto();
        abas.addTab("Clientes", criarPainelClientes());

        areaExibicaoRestaurantes = criarAreaDeTexto();
        abas.addTab("Restaurantes", criarPainelRestaurantes());

        areaExibicaoFuncionarios = criarAreaDeTexto();
        abas.addTab("Funcionários", criarPainelFuncionarios());

        areaExibicaoRefeicoes = criarAreaDeTexto();
        abas.addTab("Refeições", criarPainelRefeicoes());

        areaExibicaoPedidos = criarAreaDeTexto();
        abas.addTab("Pedidos", criarPainelPedidos());

        areaExibicaoCupons = criarAreaDeTexto();
        abas.addTab("Cupons", criarPainelCupons());

        add(abas);
    }

    private JTextArea criarAreaDeTexto() {
        JTextArea area = new JTextArea("Resultados aparecerão aqui.");
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setMargin(new Insets(10, 10, 10, 10)); // Adiciona uma margem interna
        return area;
    }

    private JPanel criarPainelBase(JTextArea areaDeTexto, JPanel painelDeBotoes) {
        JPanel painelBase = new JPanel(new BorderLayout(15, 15)); // Aumenta o espaçamento
        painelBase.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Aumenta a margem

        painelDeBotoes.setLayout(new GridLayout(0, 1, 8, 8)); // Aumenta o espaçamento dos botões
        painelDeBotoes.setBorder(BorderFactory.createTitledBorder("Operações"));

        JScrollPane scrollPane = new JScrollPane(areaDeTexto);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borda sutil

        painelBase.add(painelDeBotoes, BorderLayout.WEST);
        painelBase.add(scrollPane, BorderLayout.CENTER);

        return painelBase;
    }

    // ================= PAINEL CLIENTES (COMPLETO) =================
    private JPanel criarPainelClientes() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Clientes");
        JButton btnBuscar = new JButton("Buscar Cliente (CPF)");
        JButton btnCadastrar = new JButton("Cadastrar Cliente");
        JButton btnAtualizar = new JButton("Atualizar Cliente");
        JButton btnRemover = new JButton("Remover Cliente");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);

        btnListar.addActionListener(e -> {
            List<Cliente> lista = sistema.listarClientes();
            StringBuilder sb = new StringBuilder("--- CLIENTES CADASTRADOS ---\n\n");
            lista.forEach(item -> sb.append(item.toString()).append("\n\n--------------------\n"));
            areaExibicaoClientes.setText(sb.toString());
        });

        btnBuscar.addActionListener(e -> {
            String cpf = JOptionPane.showInputDialog(this, "CPF do cliente:");
            if (cpf != null) {
                Cliente item = sistema.buscarCliente(cpf);
                areaExibicaoClientes.setText(item != null ? item.toString() : "Cliente não encontrado.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome:"); if(nome == null) return;
                String cpf = JOptionPane.showInputDialog(this, "CPF:"); if(cpf == null) return;
                String endereco = JOptionPane.showInputDialog(this, "Endereço:"); if(endereco == null) return;
                String telefone = JOptionPane.showInputDialog(this, "Telefone:"); if(telefone == null) return;

                sistema.cadastrarCliente(nome, cpf, endereco, telefone);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
                btnListar.doClick();
            } catch (ClienteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                String cpf = JOptionPane.showInputDialog(this, "CPF do cliente a ser atualizado:"); if (cpf == null) return;
                Cliente cliente = sistema.buscarCliente(cpf);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", cliente.getNome());
                String novoEndereco = JOptionPane.showInputDialog(this, "Novo endereço:", cliente.getEndereco());
                String novoTelefone = JOptionPane.showInputDialog(this, "Novo telefone:", cliente.getTelefone());

                if (novoNome != null && !novoNome.trim().isEmpty()) cliente.setNome(novoNome);
                if (novoEndereco != null && !novoEndereco.trim().isEmpty()) cliente.setEndereco(novoEndereco);
                if (novoTelefone != null && !novoTelefone.trim().isEmpty()) cliente.setTelefone(novoTelefone);

                sistema.atualizarCliente(cliente);
                JOptionPane.showMessageDialog(this, "Cliente atualizado!");
                btnListar.doClick();
            } catch (ClienteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener(e -> {
            try {
                String cpf = JOptionPane.showInputDialog(this, "CPF do cliente a ser removido:");
                if (cpf != null) {
                    sistema.removerCliente(cpf);
                    JOptionPane.showMessageDialog(this, "Cliente removido!");
                    btnListar.doClick();
                }
            } catch (ClienteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoClientes, painelBotoes);
    }

    // ... Os outros métodos para criar os painéis (Restaurantes, Funcionários, etc.) continuam aqui ...

    // ================= PAINEL RESTAURANTES (COMPLETO) =================
    private JPanel criarPainelRestaurantes() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Restaurantes");
        JButton btnBuscar = new JButton("Buscar Restaurante (CNPJ)");
        JButton btnCadastrar = new JButton("Cadastrar Restaurante");
        JButton btnAtualizar = new JButton("Atualizar Restaurante");
        JButton btnRemover = new JButton("Remover Restaurante");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);

        btnListar.addActionListener(e -> {
            List<Restaurante> lista = sistema.listarRestaurantes();
            StringBuilder sb = new StringBuilder("--- RESTAURANTES CADASTRADOS ---\n");
            lista.forEach(item -> sb.append(item.toString()).append("\n----------\n"));
            areaExibicaoRestaurantes.setText(sb.toString());
        });

        btnBuscar.addActionListener(e -> {
            String cnpj = JOptionPane.showInputDialog(this, "CNPJ do restaurante:");
            if (cnpj != null) {
                Restaurante item = sistema.buscarRestaurante(cnpj);
                areaExibicaoRestaurantes.setText(item != null ? item.toString() : "Restaurante não encontrado.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome:"); if(nome == null) return;
                String cnpj = JOptionPane.showInputDialog(this, "CNPJ:"); if(cnpj == null) return;
                String endereco = JOptionPane.showInputDialog(this, "Endereço:"); if(endereco == null) return;

                CategoriaRestaurante categoria = (CategoriaRestaurante) JOptionPane.showInputDialog(
                        this, "Escolha a categoria:", "Categoria",
                        JOptionPane.QUESTION_MESSAGE, null,
                        CategoriaRestaurante.values(), CategoriaRestaurante.values()[0]);
                if(categoria == null) return;

                sistema.cadastrarRestaurante(nome, cnpj, categoria, endereco);
                JOptionPane.showMessageDialog(this, "Restaurante cadastrado!");
                btnListar.doClick();
            } catch (RestauranteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                String cnpj = JOptionPane.showInputDialog(this, "CNPJ do restaurante a ser atualizado:"); if (cnpj == null) return;
                Restaurante restaurante = sistema.buscarRestaurante(cnpj);
                if (restaurante == null) {
                    JOptionPane.showMessageDialog(this, "Restaurante não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", restaurante.getNome());
                String novoEndereco = JOptionPane.showInputDialog(this, "Novo endereço:", restaurante.getEndereco());

                if (novoNome != null && !novoNome.trim().isEmpty()) restaurante.setNome(novoNome);
                if (novoEndereco != null && !novoEndereco.trim().isEmpty()) restaurante.setEndereco(novoEndereco);

                sistema.atualizarRestaurante(restaurante);
                JOptionPane.showMessageDialog(this, "Restaurante atualizado!");
                btnListar.doClick();
            } catch (RestauranteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener(e -> {
            try {
                String cnpj = JOptionPane.showInputDialog(this, "CNPJ do restaurante a ser removido:");
                if (cnpj != null) {
                    sistema.removerRestaurante(cnpj);
                    JOptionPane.showMessageDialog(this, "Restaurante removido!");
                    btnListar.doClick();
                }
            } catch (RestauranteException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoRestaurantes, painelBotoes);
    }


    // ================= PAINEL FUNCIONÁRIOS (COMPLETO) =================
    private JPanel criarPainelFuncionarios() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Funcionários");
        JButton btnListarPorRest = new JButton("Listar por Restaurante");
        JButton btnBuscar = new JButton("Buscar Funcionário (CPF)");
        JButton btnCadastrar = new JButton("Cadastrar Funcionário");
        JButton btnRemover = new JButton("Remover Funcionário");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnListarPorRest);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnRemover);

        btnListar.addActionListener(e -> {
            List<Funcionario> lista = sistema.listarFuncionarios();
            StringBuilder sb = new StringBuilder("--- FUNCIONÁRIOS CADASTRADOS ---\n");
            lista.forEach(item -> sb.append(item.toString()).append("\n----------\n"));
            areaExibicaoFuncionarios.setText(sb.toString());
        });

        btnListarPorRest.addActionListener(e -> {
            String cnpj = JOptionPane.showInputDialog(this, "CNPJ do restaurante:");
            if (cnpj != null) {
                List<Funcionario> lista = sistema.listarFuncionariosPorRestaurante(cnpj);
                StringBuilder sb = new StringBuilder("--- FUNCIONÁRIOS DO RESTAURANTE " + cnpj + " ---\n");
                lista.forEach(item -> sb.append(item.toString()).append("\n----------\n"));
                areaExibicaoFuncionarios.setText(sb.toString());
            }
        });

        btnBuscar.addActionListener(e -> {
            String cpf = JOptionPane.showInputDialog(this, "CPF do funcionário:");
            if (cpf != null) {
                Funcionario item = sistema.buscarFuncionario(cpf);
                areaExibicaoFuncionarios.setText(item != null ? item.toString() : "Funcionário não encontrado.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome:"); if(nome == null) return;
                String cpf = JOptionPane.showInputDialog(this, "CPF:"); if(cpf == null) return;
                int idade = Integer.parseInt(JOptionPane.showInputDialog(this, "Idade:"));
                String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do restaurante:"); if(cnpjRestaurante == null) return;

                sistema.cadastrarFuncionario(nome, cpf, idade, cnpjRestaurante);
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado!");
                btnListar.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener(e -> {
            try {
                String cpf = JOptionPane.showInputDialog(this, "CPF do funcionário a ser removido:");
                if (cpf != null) {
                    sistema.removerFuncionario(cpf);
                    JOptionPane.showMessageDialog(this, "Funcionário removido!");
                    btnListar.doClick();
                }
            } catch (FuncionarioException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoFuncionarios, painelBotoes);
    }

    // ================= PAINEL REFEIÇÕES =================
    private JPanel criarPainelRefeicoes() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Todas Refeições");
        JButton btnListarPorRest = new JButton("Listar por Restaurante");
        JButton btnCadastrar = new JButton("Cadastrar Refeição");
        JButton btnBuscar = new JButton("Buscar Refeição");
        JButton btnRemover = new JButton("Remover Refeição");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnListarPorRest);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnRemover);


        btnListar.addActionListener(e -> {
            List<Refeicao> lista = sistema.listarRefeicoes();
            StringBuilder sb = new StringBuilder("--- TODAS AS REFEIÇÕES CADASTRADAS ---\n\n");
            if(lista.isEmpty()) sb.append("Nenhuma refeição cadastrada.");
            for (Refeicao item : lista) {
                sb.append(item.toString())
                        .append(" - Restaurante: ").append(item.getRestaurante().getNome())
                        .append("\n--------------------\n");
            }
            areaExibicaoRefeicoes.setText(sb.toString());
        });

        btnListarPorRest.addActionListener(e -> {
            String cnpj = JOptionPane.showInputDialog(this, "CNPJ do restaurante:");
            if (cnpj != null && !cnpj.trim().isEmpty()) {
                List<Refeicao> lista = sistema.listarRefeicoesPorRestaurante(cnpj.trim());
                StringBuilder sb = new StringBuilder("--- REFEIÇÕES DO RESTAURANTE " + cnpj + " ---\n\n");
                if(lista.isEmpty()) sb.append("Nenhuma refeição encontrada para este restaurante.");
                lista.forEach(item -> sb.append(item.toString()).append("\n--------------------\n"));
                areaExibicaoRefeicoes.setText(sb.toString());
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome da refeição:"); if(nome == null) return;
                String descricao = JOptionPane.showInputDialog(this, "Descrição:"); if(descricao == null) return;
                double valor = Double.parseDouble(JOptionPane.showInputDialog(this, "Valor (ex: 50.99):"));
                String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do restaurante ao qual pertence:"); if(cnpjRestaurante == null) return;

                sistema.cadastrarRefeicao(nome, descricao, valor, cnpjRestaurante);
                JOptionPane.showMessageDialog(this, "Refeição cadastrada com sucesso!");
                btnListar.doClick(); // Atualiza a lista
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: O valor deve ser um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (RefeicaoException | RestauranteException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar refeição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome da refeição a buscar:"); if(nome == null) return;
            String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do restaurante:"); if(cnpjRestaurante == null) return;

            Refeicao refeicao = sistema.buscarRefeicao(nome, cnpjRestaurante);
            if (refeicao != null) {
                areaExibicaoRefeicoes.setText("--- REFEIÇÃO ENCONTRADA ---\n\n" + refeicao.toString());
            } else {
                areaExibicaoRefeicoes.setText("Refeição '" + nome + "' não encontrada no restaurante com CNPJ '" + cnpjRestaurante + "'.");
            }
        });

        btnRemover.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome da refeição a remover:"); if(nome == null) return;
                String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do restaurante:"); if(cnpjRestaurante == null) return;

                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a refeição '" + nome + "'?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sistema.removerRefeicao(nome, cnpjRestaurante);
                    JOptionPane.showMessageDialog(this, "Refeição removida com sucesso!");
                    btnListar.doClick(); // Atualiza a lista
                }
            } catch (RefeicaoException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover refeição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoRefeicoes, painelBotoes);
    }


    // ================= PAINEL PEDIDOS (ESBOÇO) =================
    private JPanel criarPainelPedidos() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnListarPorStatus = new JButton("Listar por Status");
        JButton btnBuscar = new JButton("Buscar Pedido (ID)");
        JButton btnCriar = new JButton("Criar Pedido");
        JButton btnAddRefeicao = new JButton("Add Refeição ao Pedido");
        JButton btnAplicarCupom = new JButton("Aplicar Cupom");
        JButton btnAlterarStatus = new JButton("Alterar Status");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnListarPorStatus);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCriar);
        painelBotoes.add(btnAddRefeicao);
        painelBotoes.add(btnAplicarCupom);
        painelBotoes.add(btnAlterarStatus);

        btnListar.addActionListener(e -> {
            List<Pedido> lista = sistema.listarPedidos();
            StringBuilder sb = new StringBuilder("--- TODOS OS PEDIDOS ---\n\n");
            if(lista.isEmpty()) sb.append("Nenhum pedido cadastrado.");
            lista.forEach(item -> sb.append(item.toString()).append("\n--------------------\n"));
            areaExibicaoPedidos.setText(sb.toString());
        });

        btnListarPorStatus.addActionListener(e -> {
            StatusPedido status = (StatusPedido) JOptionPane.showInputDialog(
                    this, "Escolha o status:", "Listar por Status",
                    JOptionPane.QUESTION_MESSAGE, null,
                    StatusPedido.values(), StatusPedido.values()[0]);
            if(status == null) return;

            List<Pedido> lista = sistema.listarPedidosPorStatus(status);
            StringBuilder sb = new StringBuilder("--- PEDIDOS COM STATUS: " + status.getDescricao().toUpperCase() + " ---\n\n");
            if(lista.isEmpty()) sb.append("Nenhum pedido encontrado com este status.");
            lista.forEach(item -> sb.append(item.toString()).append("\n--------------------\n"));
            areaExibicaoPedidos.setText(sb.toString());
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do pedido:"));
                Pedido item = sistema.buscarPedido(id);
                areaExibicaoPedidos.setText(item != null ? item.toString() : "Pedido não encontrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCriar.addActionListener(e -> {
            try {
                String cpfCliente = JOptionPane.showInputDialog(this, "CPF do Cliente:"); if(cpfCliente == null) return;
                String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do Restaurante:"); if(cnpjRestaurante == null) return;
                String enderecoEntrega = JOptionPane.showInputDialog(this, "Endereço de Entrega:"); if(enderecoEntrega == null) return;

                int idPedido = sistema.criarPedido(cpfCliente, cnpjRestaurante, enderecoEntrega);
                JOptionPane.showMessageDialog(this, "Pedido criado com sucesso! ID: " + idPedido);
                btnListar.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAddRefeicao.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do pedido:"));
                String nomeRefeicao = JOptionPane.showInputDialog(this, "Nome da Refeição:"); if(nomeRefeicao == null) return;
                String cnpjRestaurante = JOptionPane.showInputDialog(this, "CNPJ do Restaurante da Refeição:"); if(cnpjRestaurante == null) return;

                sistema.adicionarRefeicaoAoPedido(idPedido, nomeRefeicao, cnpjRestaurante);
                JOptionPane.showMessageDialog(this, "Refeição adicionada ao pedido!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAplicarCupom.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do pedido:"));
                String codigoCupom = JOptionPane.showInputDialog(this, "Código do Cupom:"); if(codigoCupom == null) return;

                sistema.aplicarCupomAoPedido(idPedido, codigoCupom);
                JOptionPane.showMessageDialog(this, "Cupom aplicado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAlterarStatus.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do pedido:"));
                StatusPedido status = (StatusPedido) JOptionPane.showInputDialog(
                        this, "Escolha o novo status:", "Alterar Status",
                        JOptionPane.QUESTION_MESSAGE, null,
                        StatusPedido.values(), StatusPedido.values()[0]);
                if(status == null) return;

                sistema.alterarStatusPedido(idPedido, status);
                JOptionPane.showMessageDialog(this, "Status do pedido alterado!");
                btnListar.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoPedidos, painelBotoes);
    }


    // ================= PAINEL CUPONS (ESBOÇO) =================
    private JPanel criarPainelCupons() {
        JPanel painelBotoes = new JPanel();
        JButton btnListar = new JButton("Listar Cupons");
        JButton btnListarValidos = new JButton("Listar Cupons Válidos");
        JButton btnBuscar = new JButton("Buscar Cupom");
        JButton btnCadastrar = new JButton("Cadastrar Cupom");
        JButton btnAddAoCliente = new JButton("Adicionar Cupom ao Cliente");
        JButton btnRemover = new JButton("Remover Cupom");

        painelBotoes.add(btnListar);
        painelBotoes.add(btnListarValidos);
        painelBotoes.add(btnBuscar);
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAddAoCliente);
        painelBotoes.add(btnRemover);

        btnListar.addActionListener(e -> {
            List<Cupom> lista = sistema.listarCupons();
            StringBuilder sb = new StringBuilder("--- TODOS OS CUPONS ---\n\n");
            if(lista.isEmpty()) sb.append("Nenhum cupom cadastrado.");
            lista.forEach(item -> sb.append(item.toString()).append("\n--------------------\n"));
            areaExibicaoCupons.setText(sb.toString());
        });

        btnListarValidos.addActionListener(e -> {
            List<Cupom> lista = sistema.listarCuponsValidos();
            StringBuilder sb = new StringBuilder("--- CUPONS VÁLIDOS ---\n\n");
            if(lista.isEmpty()) sb.append("Nenhum cupom válido.");
            lista.forEach(item -> sb.append(item.toString()).append("\n--------------------\n"));
            areaExibicaoCupons.setText(sb.toString());
        });

        btnBuscar.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "Código do cupom:");
            if (codigo != null) {
                Cupom item = sistema.buscarCupom(codigo);
                areaExibicaoCupons.setText(item != null ? item.toString() : "Cupom não encontrado.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            try {
                String codigo = JOptionPane.showInputDialog(this, "Código:"); if(codigo == null) return;
                String descricao = JOptionPane.showInputDialog(this, "Descrição:"); if(descricao == null) return;

                TipoCupom tipo = (TipoCupom) JOptionPane.showInputDialog(
                        this, "Escolha o tipo:", "Tipo de Cupom",
                        JOptionPane.QUESTION_MESSAGE, null,
                        TipoCupom.values(), TipoCupom.values()[0]);
                if(tipo == null) return;

                double valor = Double.parseDouble(JOptionPane.showInputDialog(this, "Valor (Ex: 10.50 ou 15 para %):"));

                // O construtor do seu menu de console usava new Date(), vamos manter isso.
                sistema.cadastrarCupom(codigo, descricao, tipo, valor, new Date());
                JOptionPane.showMessageDialog(this, "Cupom cadastrado!");
                btnListar.doClick();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAddAoCliente.addActionListener(e -> {
            try {
                String cpfCliente = JOptionPane.showInputDialog(this, "CPF do Cliente:"); if(cpfCliente == null) return;
                String codigoCupom = JOptionPane.showInputDialog(this, "Código do Cupom:"); if(codigoCupom == null) return;

                sistema.adicionarCupomAoCliente(cpfCliente, codigoCupom);
                JOptionPane.showMessageDialog(this, "Cupom adicionado ao cliente!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemover.addActionListener(e -> {
            try {
                String codigo = JOptionPane.showInputDialog(this, "Código do cupom a ser removido:");
                if (codigo != null) {
                    sistema.removerCupom(codigo);
                    JOptionPane.showMessageDialog(this, "Cupom removido!");
                    btnListar.doClick();
                }
            } catch (CupomException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return criarPainelBase(areaExibicaoCupons, painelBotoes);
    }


    // ============================ MAIN ============================
    public static void main(String[] args) {

        try {
            //Define o tema base como "Darcula", que é um tema escuro
            UIManager.setLookAndFeel(new FlatDarculaLaf());


            Color vermelhoDestaque = new Color(229, 57, 53);
            UIManager.put("Component.accentColor", vermelhoDestaque);
            UIManager.put("ProgressBar.foreground", vermelhoDestaque);
            UIManager.put("TabbedPane.underlineColor", vermelhoDestaque);
            UIManager.put("Button.default.borderColor", vermelhoDestaque);


            UIManager.put("Component.arc", 8);
            UIManager.put("Button.arc", 8);
            UIManager.put("TextComponent.arc", 8);

        } catch (Exception ex) {
            System.err.println("Falha ao inicializar o tema moderno.");
        }


        // Garante que a interface rode na thread correta
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}