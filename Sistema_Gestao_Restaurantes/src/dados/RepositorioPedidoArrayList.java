package dados;

import java.util.ArrayList;
import java.util.List;
import negocio.Pedido;

public class RepositorioPedidoArrayList {
    private List<Pedido> pedidos; 

    public RepositorioPedidoArrayList() {
        this.pedidos = new ArrayList<>();
    }

    public void inserir(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Pedido buscarPorId(int id) { 
        for (Pedido p : pedidos) {
            if (p.getId() == id) { 
                return p;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        Pedido pedidoParaRemover = buscarPorId(id);
        if (pedidoParaRemover != null) {
            return this.pedidos.remove(pedidoParaRemover);
        }
        return false;
    }

    public List<Pedido> listarTodos() {
        return this.pedidos;
    }
}