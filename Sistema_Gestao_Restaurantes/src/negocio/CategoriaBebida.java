package negocio; 


public enum CategoriaBebida {
    REFRIGERANTE("Refrigerante"),
    SUCO("Suco"),
    AGUA("Água"),
    ALCOOLICA("Bebida Alcoólica");

    private final String descricao;

    CategoriaBebida(String descricao) {
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