package model;

import java.time.LocalDateTime;

public class CopaQuarto {
    
    private int id;
    private int quantidade;
    private LocalDateTime dataHoraPedido;
    private Status status;
    private String obs;
    private Produto produto;
    private Quarto quarto;

    public CopaQuarto() {
        this.status = Status.ATIVO;
    }

    public CopaQuarto(int id, int quantidade, LocalDateTime dataHoraPedido, Status status, String obs, Produto produto, Quarto quarto) {
        this.id = id;
        this.quantidade = quantidade;
        this.dataHoraPedido = dataHoraPedido;
        this.status = status;
        this.obs = obs;
        this.produto = produto;
        this.quarto = quarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(LocalDateTime dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "CopaQuarto [id=" + id + ", quantidade=" + quantidade + ", dataHoraPedido=" + dataHoraPedido + ", status=" + status + ", obs=" + obs + ", produto=" + produto + ", quarto=" + quarto + "]";
    }
}