package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {
    private static int contadorId = 1;
    
    private int id;
    private Cliente cliente;
    private List<Refeicao> refeicoes;
    private Restaurante restaurante;
    private StatusPedido status;
    private Date dataPedido;
    private Date dataEntrega;
    private double valorTotal;
    private double valorDesconto;
    private Cupom cupomUtilizado;
    private String enderecoEntrega;
    private double taxaEntrega;
    
    public Pedido(Cliente cliente, Restaurante restaurante) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.status = StatusPedido.PENDENTE;
        this.dataPedido = new Date();
        this.refeicoes = new ArrayList<>();
        this.valorTotal = 0.0;
        this.valorDesconto = 0.0;
        this.taxaEntrega = 0.0;
    }
    
    public int getId() {
        return id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public List<Refeicao> getRefeicoes() {
        return refeicoes;
    }
    
    public Restaurante getRestaurante() {
        return restaurante;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    
    public Date getDataPedido() {
        return dataPedido;
    }
    
    public Date getDataEntrega() {
        return dataEntrega;
    }
    
    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public double getValorDesconto() {
        return valorDesconto;
    }
    
    public Cupom getCupomUtilizado() {
        return cupomUtilizado;
    }
    
    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }
    
    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public double getTaxaEntrega() {
        return taxaEntrega;
    }
    
    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }
    
    public void adicionarRefeicao(Refeicao refeicao) {
        this.refeicoes.add(refeicao);
        calcularTotal();
    }
    
    public void removerRefeicao(Refeicao refeicao) {
        this.refeicoes.remove(refeicao);
        calcularTotal();
    }
    
    public void aplicarCupom(Cupom cupom) {
        this.cupomUtilizado = cupom;
        this.valorDesconto = cupom.calcularDesconto(calcularSubtotal());
        calcularTotal();
    }
    
    private double calcularSubtotal() {
        return refeicoes.stream().mapToDouble(Refeicao::getValor).sum();
    }
    
    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        this.valorTotal = subtotal - valorDesconto + taxaEntrega;
        return valorTotal;
    }
    
    public void confirmarPedido() {
        this.status = StatusPedido.CONFIRMADO;
    }
    
    public void cancelarPedido() {
        this.status = StatusPedido.CANCELADO;
    }
    
    @Override
    public String toString() {
        return "Pedido #" + id + " - Cliente: " + cliente.getNome() + 
               " - Status: " + status.getDescricao() + 
               " - Total: R$ " + String.format("%.2f", valorTotal);
    }
}
