package negocio;

import dados.CategoriaComida; // Supondo que a enum esteja em 'dados'

public class Comida extends Item {
    private CategoriaComida categoria;

    public Comida(String nome, double preco, String descricao, double valorNutricional, CategoriaComida categoria) {
        super(nome, preco, descricao, valorNutricional);
        this.categoria = categoria;
    }

    public CategoriaComida getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaComida categoria) {
        this.categoria = categoria;
    }
}