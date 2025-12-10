package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "copa_quarto")
public class CopaQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "data_hora_pedido")
    private LocalDateTime dataHoraPedido;

    @Column(length = 1)
    private char status;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public CopaQuarto() {
    }

    public CopaQuarto(int id, int quantidade, LocalDateTime dataHoraPedido, char status, String obs, Produto produto, Quarto quarto) {
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
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
        return "id               = " + this.getId()
                + "\nQuantidade      = " + this.getQuantidade()
                + "\nData/Hora Pedido= " + this.getDataHoraPedido()
                + "\nStatus          = " + this.getStatus()
                + "\nObs             = " + this.getObs()
                + "\nProduto ID      = " + (this.getProduto() != null ? this.getProduto().getId() : "null")
                + "\nQuarto ID       = " + (this.getQuarto() != null ? this.getQuarto().getId() : "null");
    }
}
