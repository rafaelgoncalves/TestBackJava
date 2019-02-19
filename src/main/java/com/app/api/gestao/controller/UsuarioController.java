package com.app.api.gestao.controller;

import com.app.api.gestao.dto.UsuarioDataDTO;
import com.app.api.gestao.dto.UsuarioResponseDTO;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.service.UsuarioService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuario")
@Api(tags = "usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/logar")
    @ApiOperation(value = "Efetuar logon")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ocorreu algo errado"), 
            @ApiResponse(code = 422, message = "Usuário ou senha inválidos")})
    public String logar(
                        @ApiParam("nome") @RequestParam String nome, 
                        @ApiParam("senha") @RequestParam String senha) {
        return usuarioService.logar(nome, senha);
    }

    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ocorreu algo errado"), 
            @ApiResponse(code = 403, message = "Acesso negado"), 
            @ApiResponse(code = 422, message = "Nome já em uso"), 
            @ApiResponse(code = 500, message = "Token inválido ou expirado")})
    public String cadastrar(@ApiParam("Usuario") @RequestBody UsuarioDataDTO usuario) {
        return usuarioService.cadastrar(modelMapper.map(usuario, Usuario.class));
    }

    @DeleteMapping(value = "/{nome}")
    @PreAuthorize("hasAuthority('PERMISSAO_ADMIN')")
    @ApiOperation(value = "Excluir usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ocorreu algo errado"), 
            @ApiResponse(code = 403, message = "Acesso negado"), 
            @ApiResponse(code = 404, message = "O usuário não existe"), 
            @ApiResponse(code = 500, message = "Token inválido ou expirado")})
    public String excluir(@ApiParam("nome") @PathVariable String nome) {
        usuarioService.excluir(nome);
        return nome;
    }

    @GetMapping(value = "/{nome}")
    @PreAuthorize("hasAuthority('PERMISSAO_ADMIN')")
    @ApiOperation(value = "Buscar usuário", response = UsuarioResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ocorreu algo errado"), 
            @ApiResponse(code = 403, message = "Acesso negado"), 
            @ApiResponse(code = 404, message = "O usuário não existe"), 
            @ApiResponse(code = 500, message = "Token inválido ou expirado")})
    public UsuarioResponseDTO buscar(@ApiParam("nome") @PathVariable String nome) {
        return modelMapper.map(usuarioService.buscar(nome), UsuarioResponseDTO.class);
    }

    @GetMapping(value = "/eu")
    @PreAuthorize("hasAuthority('PERMISSAO_ADMIN') or hasAuthority('PERMISSAO_CLIENT')")
    @ApiOperation(value = "Detalhes do usuário", response = UsuarioResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ocorreu algo errado"), 
            @ApiResponse(code = 403, message = "Acesso negado"), 
            @ApiResponse(code = 500, message = "Token inválido ou expirado")})
    public UsuarioResponseDTO eu(HttpServletRequest req) {
        return modelMapper.map(usuarioService.eu(req), UsuarioResponseDTO.class);
    }

    @GetMapping("/atualizarToken")
    @PreAuthorize("hasAuthority('PERMISSAO_ADMIN') or hasAuthority('PERMISSAO_CLIENT')")
    @ApiOperation(value = "Atualizar token")
    public String atualizarToken(HttpServletRequest req) {
        return usuarioService.atualizarToken(req.getRemoteUser());
    }

}
