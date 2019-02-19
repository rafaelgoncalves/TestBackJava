package com.app.api.gestao.repository;

import com.app.api.gestao.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByCodigoUsuario(int codigoUsuario);
    Gasto findOneByDescricaoContainingIgnoreCase(String descricao);
    List<Gasto> findByCategoriaContainingIgnoreCase(String categoria);
    Gasto findOneById(Long id);
    List<Gasto> findByCodigoUsuarioAndDataAfterAndDataBefore(int codigoUsuario, Date dataDe, Date dataAte);
    @Query("SELECT categoria, COUNT(0) AS qtd FROM Gasto WHERE categoria LIKE '%:categoria%' GROUP BY categoria ORDER BY qtd DESC")
    Set<String> getCategoriasDistintasByCategoria(String categoria);
}
