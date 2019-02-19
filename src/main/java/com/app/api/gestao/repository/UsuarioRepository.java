package com.app.api.gestao.repository;

import com.app.api.gestao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  boolean existsByNome(String username);

  Usuario findByNome(String username);

  @Transactional
  void deleteByNome(String username);

}
