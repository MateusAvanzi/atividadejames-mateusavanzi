package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaModel> listarTodas() {
        return repository.findAll();
    }

    public CategoriaModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));
    }

    public void salvar(CategoriaModel categoria) {
        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria obrigatorio");
        }

        boolean isNova = categoria.getId() == null;
        if (isNova && repository.existsByNomeIgnoreCase(categoria.getNome().trim())) {
            throw new IllegalArgumentException("Ja existe uma categoria com esse nome");
        }

        categoria.setNome(categoria.getNome().trim());
        repository.save(categoria);
    }

    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Nao e possivel excluir categoria vinculada a produto");
        }
    }
}
