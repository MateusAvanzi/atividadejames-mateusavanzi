package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        return "lista-usuarios";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastro-usuario";
    }

    @PostMapping("/novo")
    public String salvarUsuario(@Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "cadastro-usuario";
        }
        try {
            service.salvar(usuario);
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", null, e.getMessage());
            return "cadastro-usuario";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.buscarPorId(id));
        return "cadastro-usuario";
    }

    @PostMapping("/editar/{id}")
    public String atualizarUsuario(@PathVariable Long id,
                                   @Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "cadastro-usuario";
        }
        try {
            usuario.setIdUsuario(id);
            service.salvar(usuario);
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", null, e.getMessage());
            return "cadastro-usuario";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, Model model) {
        try {
            service.excluir(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuarios", service.listarTodos());
            return "lista-usuarios";
        }
        return "redirect:/usuarios";
    }
}
