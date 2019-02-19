package com.app.api.gestao.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codigoUsuario;

  @Size(min = 4, max = 255, message = "Tamanho máximo do nome de usuario: 4 caracteres")
  @Column(unique = true, nullable = false)
  private String nome;

  @Column(unique = true, nullable = false)
  private String email;

  private String senha;

  @ElementCollection(fetch = FetchType.EAGER)
  List<Permissao> permissoes;

  public Integer getCodigoUsuario() {
    return codigoUsuario;
  }

  public void setCodigoUsuario(Integer codigoUsuario) {
    this.codigoUsuario = codigoUsuario;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public List<Permissao> getPermissoes() {
    return permissoes;
  }

  public void setPermissoes(List<Permissao> permissoes) {
    this.permissoes = permissoes;
  }
}
