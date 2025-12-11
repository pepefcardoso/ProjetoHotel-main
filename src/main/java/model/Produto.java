package model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto_copa")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    public Produto() {
    }

    public Produto(int id, String descricao, BigDecimal valor, String obs, char status) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "id           = " + id
                + "\ndescricao = " + descricao
                + "\nvalor     = " + valor
                + "\nobs       = " + obs
                + "status      = " + status;
    }
}