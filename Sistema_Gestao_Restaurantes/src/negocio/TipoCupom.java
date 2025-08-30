package negocio;

public enum TipoCupom {
    PERCENTUAL("Desconto Percentual"),
    VALOR_FIXO("Valor Fixo"),
    FRETE_GRATIS("Frete Grátis");
    
    private String descricao;
    
    TipoCupom(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
