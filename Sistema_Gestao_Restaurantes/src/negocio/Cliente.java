package negocio;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String endereco;
    private String telefone;
    private List<String> historicoPedidos;
    
    public Cliente(String nome, String cpf) {
        super(nome, cpf);
        this.historicoPedidos = new ArrayList<>();
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
    
    public List<String> getHistoricoPedidos() {
        return historicoPedidos;
    }
    
    public void adicionarPedido(String pedido) {
        this.historicoPedidos.add(pedido);
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", Endereço: " + endereco + 
               ", Telefone: " + telefone +
               ", Pedidos: " + historicoPedidos.size();
    }
}