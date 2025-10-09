package model;

import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private LocalDateTime dataHoraReserva;
    private LocalDateTime dataPrevistaEntrada;
    private LocalDateTime dataPrevistaSaida;
    private String obs;
    private Status status;

    public Reserva() {
        this.status = Status.ATIVO;
    }

    public Reserva(int id, LocalDateTime dataHoraReserva, LocalDateTime dataPrevistaEntrada, LocalDateTime dataPrevistaSaida, String obs, Status status) {
        this.id = id;
        this.dataHoraReserva = dataHoraReserva;
        this.dataPrevistaEntrada = dataPrevistaEntrada;
        this.dataPrevistaSaida = dataPrevistaSaida;
        this.obs = obs;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraReserva() {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(LocalDateTime dataHoraReserva) {
        this.dataHoraReserva = dataHoraReserva;
    }

    public LocalDateTime getDataPrevistaEntrada() {
        return dataPrevistaEntrada;
    }

    public void setDataPrevistaEntrada(LocalDateTime dataPrevistaEntrada) {
        this.dataPrevistaEntrada = dataPrevistaEntrada;
    }

    public LocalDateTime getDataPrevistaSaida() {
        return dataPrevistaSaida;
    }

    public void setDataPrevistaSaida(LocalDateTime dataPrevistaSaida) {
        this.dataPrevistaSaida = dataPrevistaSaida;
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

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", dataHoraReserva=" + dataHoraReserva + ", dataPrevistaEntrada=" + dataPrevistaEntrada + ", dataPrevistaSaida=" + dataPrevistaSaida + ", obs=" + obs + ", status=" + status + "]";
    }
}