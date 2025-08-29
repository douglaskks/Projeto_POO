package negocio; 

public enum CategoriaComida {
    PRATO_FEITO("Prato Feito"),
    LANCHE("Lanche"),
    SOBREMESA("Sobremesa"),
    VEGANO("Vegano");

    private final String descricao;

    CategoriaComida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}