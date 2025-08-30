package negocio;

public class Funcionario extends Pessoa {
    private int idade;
    private Restaurante restaurante;
    
    public Funcionario(String nome, String cpf, int idade) {
        super(nome, cpf);
        this.idade = idade;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public Restaurante getRestaurante() {
        return restaurante;
    }
    
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Idade: " + idade + 
               (restaurante != null ? ", Restaurante: " + restaurante.getNome() : "");
    }
}
