package com.app.api.gestao.controller;

import com.app.api.gestao.repository.GastoRepository;
import com.app.api.gestao.service.GastoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class CategoriaControllerTest {

    private static final String DESCRICAO_TESTE = "cat";
    @Mock
    private GastoRepository gastoRepository;

    private GastoService gastoService;

    private CategoriaController categoriaController;


    @Before
    public void setUp() {
        this.gastoService = new GastoService(gastoRepository);
        this.categoriaController = new CategoriaController(gastoService);
    }

    @Test
    public void listar() {
        Set<String> categorias = new HashSet<>();
        when(gastoRepository.getCategoriasDistintasByCategoria(anyString())).thenReturn(categorias);
        Set<String> retorno = categoriaController.listar(DESCRICAO_TESTE);
        Assert.assertEquals(categorias, retorno);
    }

}
