package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarProdutos(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) Long categoriaId,
                                 Model model) {
        model.addAttribute("produtos", service.buscar(nome, categoriaId));
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("termoBusca", nome);
        model.addAttribute("categoriaSelecionada", categoriaId);
        return "lista-produtos";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("produto", new ProdutoModel());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "cadastro-produto";
    }

    @PostMapping("/novo")
    public String salvarProduto(@Valid @ModelAttribute("produto") ProdutoModel produto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "cadastro-produto";
        }

        try {
            service.salvar(produto);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("categoria")) {
                result.rejectValue("categoria.id", "erro.categoria", e.getMessage());
            } else {
                result.rejectValue("nome", "erro.nome", e.getMessage());
            }
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "cadastro-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "editar-produto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                   @Valid @ModelAttribute("produto") ProdutoModel produto,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "editar-produto";
        }

        try {
            produto.setIdProduto(id);
            service.salvar(produto);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("categoria")) {
                result.rejectValue("categoria.id", "erro.categoria", e.getMessage());
            } else {
                result.rejectValue("nome", "erro.nome", e.getMessage());
            }
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "editar-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/produtos";
    }
}
