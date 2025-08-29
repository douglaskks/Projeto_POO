package negocio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Restaurante implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String cnpj;
    private Cardapio cardapio;
    private List<Funcionario> funcionarios;
    public Restaurante(String nome, String cnpj) { this.nome = nome; this.cnpj = cnpj; this.cardapio = new Cardapio(); this.funcionarios = new ArrayList<>(); }
    public void adicionarFuncionario(Funcionario f) { this.funcionarios.add(f); }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public Cardapio getCardapio() { return cardapio; }
    public List<Funcionario> getFuncionarios() { return funcionarios; }
}