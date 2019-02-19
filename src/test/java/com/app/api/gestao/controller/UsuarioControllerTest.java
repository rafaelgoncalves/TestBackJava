package com.app.api.gestao.controller;

import com.app.api.gestao.dto.UsuarioDataDTO;
import com.app.api.gestao.dto.UsuarioResponseDTO;
import com.app.api.gestao.exception.AuthenticationExceptionImpl;
import com.app.api.gestao.exception.ExcecaoCustomizada;
import com.app.api.gestao.model.Permissao;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.repository.UsuarioRepository;
import com.app.api.gestao.security.JwtTokenProvider;
import com.app.api.gestao.service.UsuarioService;
import static com.app.api.gestao.util.TestUtil.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerTest {

    private static final String TOKEN_MOCK = "TESTE_TOKEN";
    private static final String EXCECAO_AUTENTICACAO = "EXCEÇÂO";

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioController usuarioController;

    private ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setUp() {
        when(jwtTokenProvider.createToken(anyString(), anyList()))
                .thenReturn(TOKEN_MOCK);
        when(usuarioRepository.findByNome(anyString())).thenReturn(getUsuario());
        this.usuarioController = new UsuarioController(usuarioService, modelMapper);
    }

    @Test
    public void logar() {
        String token = usuarioController.logar(NOME_MOCK, SENHA_MOCK);
        assertEquals(TOKEN_MOCK, token);
    }

    @Test(expected = ExcecaoCustomizada.class)
    public void logarAuthenticationException() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new AuthenticationExceptionImpl(EXCECAO_AUTENTICACAO));
        usuarioController.logar(NOME_MOCK, SENHA_MOCK);
    }

    @Test
    public void cadastrar() {
        UsuarioDataDTO usuarioDataDTO = getUsuarioDataDTO();
        String token = usuarioController.cadastrar(usuarioDataDTO);
        assertEquals(TOKEN_MOCK, token);
    }

    @Test(expected = ExcecaoCustomizada.class)
    public void cadastrarUsuarioExiste() {
        when(usuarioRepository.existsByNome(NOME_MOCK)).thenReturn(true);
        UsuarioDataDTO usuarioDataDTO = getUsuarioDataDTO();
        usuarioController.cadastrar(usuarioDataDTO);
    }

    @Test
    public void excluir() {
        String nome = usuarioController.excluir(NOME_MOCK);
        assertEquals(NOME_MOCK, nome);
    }

    @Test
    public void buscar() {
        UsuarioResponseDTO usuarioResponseDTO = usuarioController.buscar(NOME_MOCK);
        assertEquals(usuarioResponseDTO.getNome(), NOME_MOCK);
        assertEquals(usuarioResponseDTO.getEmail(), EMAIL_MOCK);

    }

    @Test(expected = ExcecaoCustomizada.class)
    public void buscarUsuarioNaoExiste() {
        when(usuarioRepository.findByNome(anyString())).thenReturn(null);
        UsuarioResponseDTO usuarioResponseDTO = usuarioController.buscar(NOME_MOCK);
        assertEquals(usuarioResponseDTO.getNome(), NOME_MOCK);
    }

    @Test
    public void eu() {
        Usuario usuario = getUsuario();
        UsuarioResponseDTO usuarioResponseDTO = usuarioController.eu(httpServletRequest);
        assertEquals(usuario.getNome(), usuarioResponseDTO.getNome());
        assertEquals(usuario.getCodigoUsuario(), usuarioResponseDTO.getCodigoUsuario());
        assertEquals(usuario.getEmail(), usuarioResponseDTO.getEmail());
    }

    @Test
    public void atualizarToken() {
        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn(TOKEN_MOCK);
        String token = usuarioController.atualizarToken(httpServletRequest);
        assertEquals(TOKEN_MOCK, token);
    }

    private UsuarioDataDTO getUsuarioDataDTO() {
        UsuarioDataDTO dataDTO = new UsuarioDataDTO();
        dataDTO.setEmail(EMAIL_MOCK);
        dataDTO.setNome(NOME_MOCK);
        dataDTO.setSenha(SENHA_MOCK);
        dataDTO.setPermissoes(Arrays.asList(Permissao.PERMISSAO_ADMIN));
        return dataDTO;
    }

}
