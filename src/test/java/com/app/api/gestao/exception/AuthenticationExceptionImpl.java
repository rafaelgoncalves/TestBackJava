package com.app.api.gestao.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionImpl extends AuthenticationException {

    public AuthenticationExceptionImpl(String msg) {
        super(msg);
    }
}
