package dados;

import negocio.Cupom;
import excecoes.CupomException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCupom implements IRepositorio<Cupom> {
    private List<Cupom> cupons;
    
    public RepositorioCupom() {
        this.cupons = new ArrayList<>();
    }
    
    @Override
    public void adicionar(Cupom cupom) throws CupomException {
        if (cupom == null) {
            throw new CupomException("Cupom não pode ser nulo");
        }
        
        if (cupom.getCodigo() == null || cupom.getCodigo().trim().isEmpty()) {
            throw new CupomException("Código do cupom é obrigatório");
        }
        
        if (existe(cupom.getCodigo())) {
            throw new CupomException("Código de cupom já existe");
        }
        
        cupons.add(cupom);
    }
    
    @Override
    public void remover(String codigo) throws CupomException {
        Cupom cupom = buscar(codigo);
        if (cupom == null) {
            throw new CupomException("Cupom não encontrado");
        }
        cupons.remove(cupom);
    }
    
    @Override
    public void atualizar(Cupom cupom) throws CupomException {
        if (cupom == null) {
            throw new CupomException("Cupom não pode ser nulo");
        }
        
        Cupom existente = buscar(cupom.getCodigo());
        if (existente == null) {
            throw new CupomException("Cupom não encontrado para atualização");
        }
        
        int index = cupons.indexOf(existente);
        cupons.set(index, cupom);
    }
    
    @Override
    public Cupom buscar(String codigo) {
        return cupons.stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Cupom> listarTodos() {
        return new ArrayList<>(cupons);
    }
    
    @Override
    public boolean existe(String codigo) {
        return buscar(codigo) != null;
    }
    
    public List<Cupom> listarCuponsValidos() {
        return cupons.stream()
                .filter(Cupom::isValido)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
