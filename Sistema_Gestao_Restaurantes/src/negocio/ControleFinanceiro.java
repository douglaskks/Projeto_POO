package negocio;

import java.util.Date;

public class ControleFinanceiro {
    private Date dataInicio;
    private Date dataFim;
    private double saldoInicial;
    private double totalVendas;
    private double totalDescontos;
    private double totalTaxasEntrega;
    
    public ControleFinanceiro(Date dataInicio, double saldoInicial) {
        this.dataInicio = dataInicio;
        this.saldoInicial = saldoInicial;
        this.totalVendas = 0.0;
        this.totalDescontos = 0.0;
        this.totalTaxasEntrega = 0.0;
    }
    
    public Date getDataInicio() {
        return dataInicio;
    }
    
    public Date getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public double getSaldoInicial() {
        return saldoInicial;
    }
    
    public double getTotalVendas() {
        return totalVendas;
    }
    
    public double getTotalDescontos() {
        return totalDescontos;
    }
    
    public double getTotalTaxasEntrega() {
        return totalTaxasEntrega;
    }
    
    public double calcularSaldoFinal() {
        return saldoInicial + totalVendas - totalDescontos + totalTaxasEntrega;
    }
    
    public double fecharCaixa() {
        this.dataFim = new Date();
        return calcularSaldoFinal();
    }
    
    public void adicionarVenda(double valor) {
        this.totalVendas += valor;
    }
    
    public void adicionarDesconto(double valor) {
        this.totalDescontos += valor;
    }
    
    public void adicionarTaxaEntrega(double valor) {
        this.totalTaxasEntrega += valor;
    }
}