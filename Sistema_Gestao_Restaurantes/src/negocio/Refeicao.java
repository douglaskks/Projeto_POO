package negocio;

public class Refeicao {
    private String nome;
    private String descricao;
    private double valor;
    private Restaurante restaurante;
    
    public Refeicao(String nome, double valor, Restaurante restaurante) {
        this.nome = nome;
        this.valor = valor;
        this.restaurante = restaurante;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public Restaurante getRestaurante() {
        return restaurante;
    }
    
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
    
    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", valor) + 
               (descricao != null && !descricao.isEmpty() ? " (" + descricao + ")" : "");
    }
}