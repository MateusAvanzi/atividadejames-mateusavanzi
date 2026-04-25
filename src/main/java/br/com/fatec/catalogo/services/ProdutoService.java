package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAll();
    }

    public List<ProdutoModel> buscar(String nome, Long categoriaId) {
        boolean semNome = nome == null || nome.trim().isEmpty();
        boolean semCategoria = categoriaId == null;

        if (semNome && semCategoria) {
            return repository.findAll();
        }
        if (!semNome && !semCategoria) {
            return repository.findByNomeContainingIgnoreCaseAndCategoriaId(nome.trim(), categoriaId);
        }
        if (!semNome) {
            return repository.findByNomeContainingIgnoreCase(nome.trim());
        }
        return repository.findByCategoriaId(categoriaId);
    }

    public ProdutoModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado"));
    }

    public void salvar(ProdutoModel produto) {
        boolean isNovo = produto.getIdProduto() == null;

        if (isNovo && repository.existsByNome(produto.getNome())) {
            throw new IllegalArgumentException("Ja existe um produto com o nome \"" + produto.getNome() + "\"");
        }

        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria obrigatoria");
        }

        CategoriaModel categoria = categoriaRepository.findById(produto.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));
        produto.setCategoria(categoria);

        repository.save(produto);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
