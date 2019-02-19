package com.app.api.gestao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class SwaggerController {

    @GetMapping("/")
    public void redirecionaParaSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }

}
