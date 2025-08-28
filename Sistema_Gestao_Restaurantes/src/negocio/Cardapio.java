package negocio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Cardapio implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> itens;
    public Cardapio() { this.itens = new ArrayList<>(); }
    public void adicionarItem(Item item) { this.itens.add(item); }
    public List<Item> getItens() { return itens; }
}