package negocio;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Cliente cliente;
    private List<Item> itens;
    private double valorTotal;
    private LocalDateTime dataPedido;
    public Pedido(int id, Cliente cliente) { this.id = id; this.cliente = cliente; this.itens = new ArrayList<>(); this.dataPedido = LocalDateTime.now(); this.valorTotal = 0; }
    public void adicionarItem(Item item) { this.itens.add(item); this.valorTotal += item.getPreco(); }
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Item> getItens() { return itens; }
    public double getValorTotal() { return valorTotal; }
}