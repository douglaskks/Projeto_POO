package negocio;
import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double preco;
    // ATRIBUTOS ADICIONADOS:
    private String descricao; 
    private double valorNutricional;

    // CONSTRUTOR CORRIGIDO:
    public Item(String nome, double preco, String descricao, double valorNutricional) { 
        this.nome = nome; 
        this.preco = preco; 
        this.descricao = descricao;
        this.valorNutricional = valorNutricional;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    public double getValorNutricional() { return valorNutricional; }

    @Override 
    public String toString() { 
        return nome + " - R$" + String.format("%.2f", preco); 
    }
}