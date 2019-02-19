package com.app.api.gestao.controller;

import com.app.api.gestao.dto.GastoDTO;
import com.app.api.gestao.model.Gasto;
import com.app.api.gestao.model.Usuario;
import com.app.api.gestao.service.GastoService;
import com.app.api.gestao.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/gasto")
@Api(tags = "gasto")
public class GastoController {

    private final GastoService gastoService;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    @Autowired
    public GastoController(GastoService gastoService,
                           UsuarioService usuarioService,
                           ModelMapper modelMapper) {
        this.gastoService = gastoService;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Listar gastos do usuário")
    @ResponseBody
    public List<Gasto> listar(@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data,
                              HttpServletRequest req) {
        Usuario usuario = usuarioService.eu(req);
        return gastoService.listar(usuario.getCodigoUsuario(), data);
    }

    @PostMapping("/")
    @ApiOperation(value = "Cadastrar gasto")
    @ResponseBody
    public Gasto cadastrar(@RequestBody GastoDTO gastoDTO) {
        Gasto gasto = modelMapper.map(gastoDTO, Gasto.class);
        gasto.setId(0);
        gastoService.salvar(gasto);
        return gasto;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Detalhe do gasto")
    @ResponseBody
    public Gasto detalhe(@RequestParam("id") Long id) {
        return gastoService.gastoPorId(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar gasto")
    @ResponseBody
    public ResponseEntity<Gasto> atualizar(@RequestParam("id") Long id, @RequestBody GastoDTO gastoDTO) {
        Gasto gasto = modelMapper.map(gastoDTO, Gasto.class);
        gasto.setId(id);
        gastoService.salvar(gasto);
        return ResponseEntity.ok(gasto);
    }

}
