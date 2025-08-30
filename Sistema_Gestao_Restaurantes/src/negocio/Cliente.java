package negocio;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String endereco;
    private String telefone;
    private List<Pedido> pedidos;
    private List<Cupom> cuponsDisponiveis;
    
    public Cliente(String nome, String cpf) {
        super(nome, cpf);
        this.pedidos = new ArrayList<>();
        this.cuponsDisponiveis = new ArrayList<>();
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public List<Cupom> getCuponsDisponiveis() {
        return cuponsDisponiveis;
    }
    
    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }
    
    public void removerPedido(int id) {
        pedidos.removeIf(pedido -> pedido.getId() == id);
    }
    
    public void adicionarCupom(Cupom cupom) {
        this.cuponsDisponiveis.add(cupom);
    }
    
    public boolean usarCupom(Cupom cupom) {
        if (cuponsDisponiveis.contains(cupom) && cupom.isValido()) {
            cupom.usar();
            cuponsDisponiveis.remove(cupom);
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Telefone: " + telefone + ", Endereço: " + endereco;
    }
}
