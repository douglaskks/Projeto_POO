package negocio;

public class Funcionario extends Pessoa {
    private int idade;
    private String nomeRestaurante;
    
    public Funcionario(String nome, String cpf, int idade) {
        super(nome, cpf);
        this.idade = idade;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        if (idade < 16) {
            throw new excecoes.FuncionarioException("Funcionário deve ter pelo menos 16 anos");
        }
        this.idade = idade;
    }
    
    public String getNomeRestaurante() {
        return nomeRestaurante;
    }
    
    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               ", Idade: " + idade + 
               ", Restaurante: " + (nomeRestaurante != null ? nomeRestaurante : "Não atribuído");
    }
}