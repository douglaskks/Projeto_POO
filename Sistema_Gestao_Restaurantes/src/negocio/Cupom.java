package negocio;

import java.util.Date;

public class Cupom {
    private String codigo;
    private String descricao;
    private TipoCupom tipo;
    private double valor;
    private Date dataVencimento;
    private boolean usado;
    
    public Cupom(String codigo, TipoCupom tipo, double valor) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.valor = valor;
        this.usado = false;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public TipoCupom getTipo() {
        return tipo;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public Date getDataVencimento() {
        return dataVencimento;
    }
    
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    public boolean isUsado() {
        return usado;
    }
    
    public void setUsado(boolean usado) {
        this.usado = usado;
    }
    
    public boolean isValido() {
        return !usado && (dataVencimento == null || dataVencimento.after(new Date()));
    }
    
    public double calcularDesconto(double valorPedido) {
        if (!isValido()) return 0.0;
        
        switch (tipo) {
            case PERCENTUAL:
                return valorPedido * (valor / 100.0);
            case VALOR_FIXO:
                return Math.min(valor, valorPedido);
            case FRETE_GRATIS:
                return 0.0;
            default:
                return 0.0;
        }
    }
    
    public void usar() {
        this.usado = true;
    }
}
