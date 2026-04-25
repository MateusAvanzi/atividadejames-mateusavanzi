package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioModel> listarTodos() {
        return repository.findAll();
    }

    public UsuarioModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));
    }

    public void salvar(UsuarioModel usuario) {
        boolean isNovo = usuario.getIdUsuario() == null;

        if (isNovo && repository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Ja existe um usuario com esse username.");
        }

        if (usuario.getRole() != null && !usuario.getRole().startsWith("ROLE_")) {
            usuario.setRole("ROLE_" + usuario.getRole());
        }

        if (isNovo) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            UsuarioModel usuarioExistente = repository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));

            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(usuarioExistente.getPassword());
            } else if (!usuario.getPassword().startsWith("$2a$")
                    && !usuario.getPassword().startsWith("$2b$")
                    && !usuario.getPassword().startsWith("$2y$")) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }

        repository.save(usuario);
    }

    public void excluir(Long id) {
        UsuarioModel usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado"));

        String username = usuario.getUsername();
        if ("mateus".equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("O usuario admin principal (mateus) nao pode ser excluido.");
        }

        repository.deleteById(id);
    }
}
