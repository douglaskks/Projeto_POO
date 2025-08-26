package dados;

import java.util.ArrayList;
import java.util.List;
import negocio.Cliente; 

public class RepositorioClienteArrayList {
	private List<Cliente> clientes;

	public RepositorioClienteArrayList() {
		this.clientes = new ArrayList<>();
	}

	public void inserir(Cliente cliente) {
		this.clientes.add(cliente);
	}

	public Cliente buscarPorCpf(String cpf) {
		for (Cliente c : clientes) {
			if (c.getCpf().equals(cpf)) {
				return c;
			}
		}
		return null;
	}

	public boolean remover(String cpf) {
		Cliente clienteParaRemover = buscarPorCpf(cpf);
		if (clienteParaRemover != null) {
			return this.clientes.remove(clienteParaRemover);
		}
		return false;
	}

	public List<Cliente> listarTodos() {
		return this.clientes;
	}
}