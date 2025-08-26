package dados;

import java.util.ArrayList;
import java.util.List;
import negocio.Funcionario;

public class RepositorioFuncionarioArrayList {
	private List<Funcionario> funcionarios;

	public RepositorioFuncionarioArrayList() {
		this.funcionarios = new ArrayList<>();
	}

	public void inserir(Funcionario funcionario) {
		this.funcionarios.add(funcionario);
	}

	public Funcionario buscarPorCpf(String cpf) {
		for (Funcionario f : funcionarios) {
			if (f.getCpf().equals(cpf)) {
				return f;
			}
		}
		return null;
	}

	public boolean remover(String cpf) {
		Funcionario funcionarioParaRemover = buscarPorCpf(cpf);
		if (funcionarioParaRemover != null) {
			return this.funcionarios.remove(funcionarioParaRemover);
		}
		return false;

	}

	public List<Funcionario> listarTodos() {
		return this.funcionarios;
	}
}
