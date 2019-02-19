package com.app.api.gestao.util;

import com.app.api.gestao.model.Usuario;

public class TestUtil {
    public static final String NOME_MOCK = "ADMIN";
    public static final String EMAIL_MOCK = "ADMIN@ADMIN.COM.BR";
    public static final Integer CODIGO_USUARIO_MOCK = 1;
    public static final String SENHA_MOCK = "SENHA";

    public static Usuario getUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCodigoUsuario(CODIGO_USUARIO_MOCK);
        usuario.setNome(NOME_MOCK);
        usuario.setEmail(EMAIL_MOCK);
        usuario.setSenha(SENHA_MOCK);
        return usuario;
    }
}
