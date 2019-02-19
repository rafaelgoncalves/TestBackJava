package com.app.api.gestao.dto;

public class UsuarioResponseDTO {

  private Integer codigoUsuario;
  private String nome;
  private String email;

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
}
