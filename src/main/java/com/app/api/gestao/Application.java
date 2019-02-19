package com.app.api.gestao;

import com.app.api.gestao.model.Permissao;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories
public class Application implements CommandLineRunner {

    @Autowired
    UsuarioService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... params) throws Exception {
        Usuario admin = new Usuario();
        admin.setNome("admin");
        admin.setSenha("admin");
        admin.setEmail("admin@email.com");
        admin.setPermissoes(Arrays.asList(Permissao.PERMISSAO_ADMIN));

        userService.cadastrar(admin);

        Usuario client = new Usuario();
        client.setNome("client");
        client.setSenha("client");
        client.setEmail("client@email.com");
        client.setPermissoes(Arrays.asList(Permissao.PERMISSAO_CLIENT));

        userService.cadastrar(client);
    }

}