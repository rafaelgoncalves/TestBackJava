package com.app.api.gestao.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Table(indexes = {
        @Index(name = "idx_gasto_categoria", columnList = "categoria"),
        @Index(name = "idx_gasto_codigo_usuario", columnList = "codigousuario"),
        @Index(name = "idx_gasto_data", columnList = "data"),
})
@Entity
public class Gasto {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 300)
    private String descricao;

    private BigDecimal valor;

    private int codigoUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Column(length = 300)
    private String categoria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
