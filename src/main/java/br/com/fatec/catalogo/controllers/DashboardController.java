package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("totalProdutos", produtoRepository.count());
        model.addAttribute("totalCategorias", categoriaRepository.count());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("username", authentication.getName());
        return "dashboard";
    }
}
