package dados;

import negocio.Pedido;
import negocio.StatusPedido;
import excecoes.PedidoException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPedido implements IRepositorio<Pedido> {
    private List<Pedido> pedidos;
    
    public RepositorioPedido() {
        this.pedidos = new ArrayList<>();
    }
    
    @Override
    public void adicionar(Pedido pedido) throws PedidoException {
        if (pedido == null) {
            throw new PedidoException("Pedido não pode ser nulo");
        }
        
        if (pedido.getCliente() == null) {
            throw new PedidoException("Cliente deve estar cadastrado no sistema para fazer pedidos");
        }
        
        if (pedido.getRefeicoes().isEmpty()) {
            throw new PedidoException("Pedido deve conter pelo menos uma refeição");
        }
        
        pedidos.add(pedido);
    }
    
    @Override
    public void remover(String id) throws PedidoException {
        Pedido pedido = buscar(id);
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        
        pedidos.remove(pedido);
    }
    
    @Override
    public void atualizar(Pedido pedido) throws PedidoException {
        if (pedido == null) {
            throw new PedidoException("Pedido não pode ser nulo");
        }
        
        Pedido existente = buscar(String.valueOf(pedido.getId()));
        if (existente == null) {
            throw new PedidoException("Pedido não encontrado para atualização");
        }
        
        // Pedido só pode ser editado se estiver pendente
        if (existente.getStatus() != StatusPedido.PENDENTE) {
            throw new PedidoException("Pedido só pode ser editado se estiver com status PENDENTE");
        }
        
        int index = pedidos.indexOf(existente);
        pedidos.set(index, pedido);
    }
    
    @Override
    public Pedido buscar(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return pedidos.stream()
                    .filter(p -> p.getId() == idInt)
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }
    
    @Override
    public boolean existe(String id) {
        return buscar(id) != null;
    }
    
    public List<Pedido> buscarPorCliente(String cpfCliente) {
        return pedidos.stream()
                .filter(p -> p.getCliente().getDocumento().equals(cpfCliente))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Pedido> buscarPorRestaurante(String cnpjRestaurante) {
        return pedidos.stream()
                .filter(p -> p.getRestaurante().getCnpj().equals(cnpjRestaurante))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidos.stream()
                .filter(p -> p.getStatus() == status)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    public void alterarStatusPedido(int idPedido, StatusPedido novoStatus) throws PedidoException {
        Pedido pedido = buscar(String.valueOf(idPedido));
        if (pedido == null) {
            throw new PedidoException("Pedido não encontrado");
        }
        
        // Validar transição de status
        if (!validarTransicaoStatus(pedido.getStatus(), novoStatus)) {
            throw new PedidoException("Transição de status inválida de " + 
                                     pedido.getStatus().getDescricao() + " para " + 
                                     novoStatus.getDescricao());
        }
        
        pedido.setStatus(novoStatus);
        
        // Se foi entregue, definir data de entrega
        if (novoStatus == StatusPedido.ENTREGUE) {
            pedido.setDataEntrega(new java.util.Date());
        }
    }
    
    private boolean validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        // Implementar lógica de validação de transição
        // Por simplicidade, permitimos qualquer transição exceto de CANCELADO
        return statusAtual != StatusPedido.CANCELADO;
    }
}
