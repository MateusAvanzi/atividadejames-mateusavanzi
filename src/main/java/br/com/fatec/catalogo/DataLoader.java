package br.com.fatec.catalogo;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner carregarUsuariosPadrao(
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!categoriaRepository.existsByNomeIgnoreCase("Eletronicos")) {
                CategoriaModel eletronicos = new CategoriaModel();
                eletronicos.setNome("Eletronicos");
                categoriaRepository.save(eletronicos);
            }

            if (!categoriaRepository.existsByNomeIgnoreCase("Informatica")) {
                CategoriaModel informatica = new CategoriaModel();
                informatica.setNome("Informatica");
                categoriaRepository.save(informatica);
            }

            if (!usuarioRepository.existsByUsername("jose")) {
                UsuarioModel jose = new UsuarioModel();
                jose.setUsername("jose");
                jose.setPassword(passwordEncoder.encode("Jose@1234"));
                jose.setRole("ROLE_USER");
                usuarioRepository.save(jose);
            }

            if (!usuarioRepository.existsByUsername("mateus")) {
                UsuarioModel mateus = new UsuarioModel();
                mateus.setUsername("mateus");
                mateus.setPassword(passwordEncoder.encode("Mateus@1234"));
                mateus.setRole("ROLE_ADMIN");
                usuarioRepository.save(mateus);
            }

        };
    }
}
