package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Restaurante {
    private String nome;
    private String cnpj;
    private String endereco;
    private CategoriaRestaurante categoria;
    private List<Funcionario> funcionarios;
    private List<Refeicao> refeicoes;
    private ControleFinanceiro controleFinanceiro;
    
    public Restaurante(String nome, String cnpj, CategoriaRestaurante categoria) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.categoria = categoria;
        this.funcionarios = new ArrayList<>();
        this.refeicoes = new ArrayList<>();
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public CategoriaRestaurante getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaRestaurante categoria) {
        this.categoria = categoria;
    }
    
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    
    public List<Refeicao> getRefeicoes() {
        return refeicoes;
    }
    
    public ControleFinanceiro getControleFinanceiro() {
        return controleFinanceiro;
    }
    
    public void adicionarFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setRestaurante(this);
    }
    
    public void removerFuncionario(String cpf) {
        funcionarios.removeIf(func -> func.getDocumento().equals(cpf));
    }
    
    public void adicionarRefeicao(Refeicao refeicao) {
        this.refeicoes.add(refeicao);
        refeicao.setRestaurante(this);
    }
    
    public void removerRefeicao(String nome) {
        refeicoes.removeIf(refeicao -> refeicao.getNome().equals(nome));
    }
    
    public void iniciarDia() {
        this.controleFinanceiro = new ControleFinanceiro(new Date(), 0.0);
    }
    
    public double fecharDia() {
        if (controleFinanceiro != null) {
            return controleFinanceiro.fecharCaixa();
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        return nome + " (" + categoria.getDescricao() + ") - CNPJ: " + cnpj;
    }
}
