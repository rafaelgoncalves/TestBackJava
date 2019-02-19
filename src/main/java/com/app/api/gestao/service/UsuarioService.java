package com.app.api.gestao.service;

import com.app.api.gestao.exception.ExcecaoCustomizada;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.repository.UsuarioRepository;
import com.app.api.gestao.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  public String logar(String nome, String senha) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nome, senha));
      Usuario usuario = usuarioRepository.findByNome(nome);
      return jwtTokenProvider.createToken(nome, usuario.getPermissoes());
    } catch (AuthenticationException e) {
      throw new ExcecaoCustomizada("Usuário ou senha inválidos", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String cadastrar(Usuario usuario) {
    if (!usuarioRepository.existsByNome(usuario.getNome())) {
      usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
      usuarioRepository.save(usuario);
      return jwtTokenProvider.createToken(usuario.getNome(), usuario.getPermissoes());
    } else {
      throw new ExcecaoCustomizada("Nome de usuário já em uso", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void excluir(String username) {
    usuarioRepository.deleteByNome(username);
  }

  public Usuario buscar(String username) {
    Usuario user = usuarioRepository.findByNome(username);
    if (user == null) {
      throw new ExcecaoCustomizada("O usuário não existe", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public Usuario eu(HttpServletRequest req) {
    return usuarioRepository.findByNome(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String atualizarToken(String nome) {
    Usuario usuario = usuarioRepository.findByNome(nome);
    return jwtTokenProvider.createToken(nome, usuario.getPermissoes());
  }

}
