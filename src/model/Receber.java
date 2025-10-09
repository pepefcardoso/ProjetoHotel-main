package model;

import java.time.LocalDateTime;

public class Receber {
    private Long id;
    private LocalDateTime dataHoraCadastro;
    private Double valorOriginal;
    private Double desconto;
    private Double acrescimo;
    private Double valorPago;
    private String obs;
    private Status status;
    private Check check;

    public Receber() {
        this.status = Status.ATIVO;
    }

    public Receber(Long id, LocalDateTime dataHoraCadastro, Double valorOriginal, Double desconto, Double acrescimo, Double valorPago, String obs, Status status, Check check) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.valorOriginal = valorOriginal;
        this.desconto = desconto;
        this.acrescimo = acrescimo;
        this.valorPago = valorPago;
        this.obs = obs;
        this.status = status;
        this.check = check;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(Double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "Receber [id=" + id + ", dataHoraCadastro=" + dataHoraCadastro + ", valorOriginal=" + valorOriginal + ", desconto=" + desconto + ", acrescimo=" + acrescimo + ", valorPago=" + valorPago + ", obs=" + obs + ", status=" + status + ", check=" + check + "]";
    }
}