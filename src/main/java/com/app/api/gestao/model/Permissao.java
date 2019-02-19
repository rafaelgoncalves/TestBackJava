package com.app.api.gestao.model;

import org.springframework.security.core.GrantedAuthority;

public enum  Permissao implements GrantedAuthority {
    PERMISSAO_ADMIN, PERMISSAO_CLIENT;

    public String getAuthority() {
        return name();
    }

}
