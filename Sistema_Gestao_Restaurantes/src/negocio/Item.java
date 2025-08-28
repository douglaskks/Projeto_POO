package negocio;
import java.io.Serializable;
public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double preco;
    public Item(String nome, double preco) { this.nome = nome; this.preco = preco; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    @Override public String toString() { return nome + " - R$" + String.format("%.2f", preco); }
}