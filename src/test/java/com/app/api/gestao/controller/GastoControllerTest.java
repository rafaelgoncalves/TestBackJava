package com.app.api.gestao.controller;

import com.app.api.gestao.dto.GastoDTO;
import com.app.api.gestao.model.Gasto;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.repository.GastoRepository;
import com.app.api.gestao.repository.UsuarioRepository;
import com.app.api.gestao.security.JwtTokenProvider;
import com.app.api.gestao.service.GastoService;
import com.app.api.gestao.service.UsuarioService;
import com.app.api.gestao.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class GastoControllerTest {

    private static final Long ID_GASTO_MOCK = 1l;
    private static final String CATEGORIA_GASTO_MOCK = "CAT_GASTO_MOCK";
    private static final Long ID_GASTO_MOCK_ATUALIZADO = 2l;

    @Mock
    private GastoRepository gastoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private GastoService gastoService;

    @InjectMocks
    private UsuarioService usuarioService;

    private GastoController gastoController;

    @Mock
    private HttpServletRequest httpServletRequest;

    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Before
    public void setUp() {
        when(usuarioRepository.findByNome(anyString())).thenReturn(TestUtil.getUsuario());
        when(gastoRepository.findOneByDescricaoContainingIgnoreCase(anyString())).thenReturn(getGasto());
        this.gastoService = new GastoService(gastoRepository);
        this.gastoController = new GastoController(gastoService, usuarioService, modelMapper);
    }


    @Test
    public void listar() {
        List<Gasto> gastosTest = new ArrayList<>();
        when(gastoRepository.findByCodigoUsuario(anyInt()))
                .thenReturn(gastosTest);
        List<Gasto> gastos = gastoController.listar(null, httpServletRequest);
        assertNotNull(gastos);
        assertArrayEquals(gastos.toArray(), gastosTest.toArray());
    }

    @Test
    public void listarPorData() {
        List<Gasto> gastosTest = new ArrayList<>();
        when(gastoRepository.findByCodigoUsuarioAndDataAfterAndDataBefore(anyInt(), any(Date.class), any(Date.class)))
                .thenReturn(gastosTest);
        List<Gasto> gastos = gastoController.listar(LocalDate.now(), httpServletRequest);
        assertNotNull(gastos);
        assertArrayEquals(gastos.toArray(), gastosTest.toArray());
    }


    @Test
    public void cadastrar() {
        GastoDTO gastoDTO = getGastoDTO();
        Gasto gasto = gastoController.cadastrar(gastoDTO);
        asserGastWithGastoDTO(gastoDTO, gasto);
    }

    @Test
    public void atualizar() {
        GastoDTO gastoDTO = getGastoDTO();
        Gasto gasto = gastoController.atualizar(ID_GASTO_MOCK_ATUALIZADO, gastoDTO).getBody();
        asserGastWithGastoDTO(gastoDTO, gasto);
    }

    private void asserGastWithGastoDTO(GastoDTO gastoDTO, Gasto gasto) {
        assertEquals(gastoDTO.getCategoria(), gasto.getCategoria());
        assertEquals(gastoDTO.getDescricao(), gasto.getDescricao());
        assertEquals(gastoDTO.getCodigoUsuario(), gasto.getCodigoUsuario());
        assertEquals(gastoDTO.getData(), gasto.getData());
        assertEquals(gastoDTO.getValor(), gasto.getValor());
    }

    @Test
    public void detalhe() {
        Gasto gasto = getGasto();
        when(gastoRepository.findOneById(anyLong())).thenReturn(gasto);
        Gasto gastoRetorno = gastoController.detalhe(ID_GASTO_MOCK);
        assertEquals(gasto, gastoRetorno);
    }

    private Gasto getGasto() {
        Gasto gasto = new Gasto();
        gasto.setId(ID_GASTO_MOCK);
        gasto.setCategoria(CATEGORIA_GASTO_MOCK);
        return gasto;
    }

    private GastoDTO getGastoDTO() {
        GastoDTO gastoDTO = new GastoDTO();
        gastoDTO.setId(ID_GASTO_MOCK);
        gastoDTO.setCategoria(CATEGORIA_GASTO_MOCK);
        return gastoDTO;
    }

}
