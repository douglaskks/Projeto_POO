package dados;

import negocio.Funcionario;
import excecoes.FuncionarioException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioFuncionario implements IRepositorio<Funcionario> {
    private List<Funcionario> funcionarios;

    public RepositorioFuncionario() {
        this.funcionarios = new ArrayList<>();
    }

    @Override
    public void adicionar(Funcionario funcionario) throws FuncionarioException {
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não pode ser nulo");
        }

        if (!validarCPF(funcionario.getDocumento())) {
            throw new FuncionarioException("CPF deve ter 11 dígitos");
        }

        if (funcionario.getIdade() < 16) {
            throw new FuncionarioException("Idade deve ser maior ou igual a 16 anos");
        }

        if (existe(funcionario.getDocumento())) {
            throw new FuncionarioException("CPF já está cadastrado no sistema");
        }

        if (funcionario.getRestaurante() == null) {
            throw new FuncionarioException("Funcionário deve estar vinculado a um restaurante");
        }

        funcionarios.add(funcionario);
    }

    @Override
    public void remover(String cpf) throws FuncionarioException {
        Funcionario funcionario = buscar(cpf);
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não encontrado");
        }

        funcionarios.remove(funcionario);
    }

    @Override
    public void atualizar(Funcionario funcionario) throws FuncionarioException {
        if (funcionario == null) {
            throw new FuncionarioException("Funcionário não pode ser nulo");
        }

        Funcionario existente = buscar(funcionario.getDocumento());
        if (existente == null) {
            throw new FuncionarioException("Funcionário não encontrado para atualização");
        }

        if (funcionario.getIdade() < 16) {
            throw new FuncionarioException("Idade deve ser maior ou igual a 16 anos");
        }

        int index = funcionarios.indexOf(existente);
        funcionarios.set(index, funcionario);
    }

    @Override
    public Funcionario buscar(String cpf) {
        return funcionarios.stream()
                .filter(f -> f.getDocumento().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }

    @Override
    public boolean existe(String cpf) {
        return buscar(cpf) != null;
    }

    public List<Funcionario> buscarPorRestaurante(String cnpjRestaurante) {
        return funcionarios.stream()
                .filter(f -> f.getRestaurante() != null &&
                        f.getRestaurante().getCnpj().equals(cnpjRestaurante))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private boolean validarCPF(String cpf) {
        return cpf != null && cpf.replaceAll("[^0-9]", "").length() == 11;
    }
}
