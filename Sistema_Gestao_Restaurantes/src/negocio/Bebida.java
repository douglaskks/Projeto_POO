package negocio;

import dados.CategoriaBebida; 

public class Bebida extends Item {
    private CategoriaBebida categoria;

    public Bebida(String nome, double preco, String descricao, double valorNutricional, CategoriaBebida categoria) {
        super(nome, preco, descricao, valorNutricional);
        this.categoria = categoria;
    }

    public CategoriaBebida getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaBebida categoria) {
        this.categoria = categoria;
    }
}