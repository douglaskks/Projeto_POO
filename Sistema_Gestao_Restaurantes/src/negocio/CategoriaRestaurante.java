package negocio;

public enum CategoriaRestaurante {
    COMIDA_CHINESA("Comida Chinesa"),
    PIZZARIA("Pizzaria"),
    HAMBURGUERIA("Hamburgueria"),
    COMIDA_BRASILEIRA("Comida Brasileira"),
    COMIDA_ITALIANA("Comida Italiana"),
    COMIDA_JAPONESA("Comida Japonesa"),
    LANCHONETE("Lanchonete"),
    CHURRASCARIA("Churrascaria");
    
    private String descricao;
    
    CategoriaRestaurante(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}