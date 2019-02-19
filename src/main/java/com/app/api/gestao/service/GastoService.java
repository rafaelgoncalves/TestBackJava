package com.app.api.gestao.service;

import com.app.api.gestao.model.Gasto;
import com.app.api.gestao.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;

    @Autowired
    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    public void salvar(Gasto gasto) {
        Gasto gastoMesmaDescricao = gastoPorDescricao(gasto.getDescricao());
        if(gastoMesmaDescricao != null) {
            gasto.setCategoria(gastoMesmaDescricao.getCategoria());
        }
        gastoRepository.save(gasto);
    }

    public List<Gasto> listar(int codigoUsuario, LocalDate data) {
        if(data == null)
            return gastoRepository.findByCodigoUsuario(codigoUsuario);
        else {
            ZonedDateTime dataDe = data.atStartOfDay(ZoneId.systemDefault());
            ZonedDateTime dataAte = data.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusMinutes(1);
            return gastoRepository.findByCodigoUsuarioAndDataAfterAndDataBefore(codigoUsuario,
                    Date.from(dataDe.toInstant()),
                    Date.from(dataAte.toInstant()));
        }
    }

    public Gasto gastoPorDescricao(String descricao) {
        return gastoRepository.findOneByDescricaoContainingIgnoreCase(descricao);
    }

    public Gasto gastoPorId(Long id) {
        return gastoRepository.findOneById(id);
    }

    public Set<String> listarCategorias(String categoria) {
        return gastoRepository.getCategoriasDistintasByCategoria(categoria);
    }

}
