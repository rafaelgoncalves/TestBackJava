package com.app.api.gestao.controller;

import com.app.api.gestao.service.GastoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/categoria")
@Api(tags = "categoria")
public class CategoriaController {

    private final GastoService gastoService;

    @Autowired
    public CategoriaController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Listar categorias")
    @ResponseBody
    public Set<String> listar(@RequestParam String descricao) {
        return gastoService.listarCategorias(descricao);
    }

}
