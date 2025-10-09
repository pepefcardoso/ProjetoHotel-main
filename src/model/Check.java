package model;

import java.time.LocalDateTime;

public class Check {
    private int id;
    private LocalDateTime dataHoraCadastro;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String obs;
    private Status status;
    private Reserva reserva;

    public Check() {
        this.status = Status.ATIVO;

    }

    public Check(int id, LocalDateTime dataHoraCadastro, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, String obs, Status status, Reserva reserva) {
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.obs = obs;
        this.status = status;
        this.reserva = reserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
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

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "Check [id=" + id + ", dataHoraCadastro=" + dataHoraCadastro + ", dataHoraEntrada=" + dataHoraEntrada + ", dataHoraSaida=" + dataHoraSaida + ", obs=" + obs + ", status=" + status + ", reserva=" + reserva + "]";
    }
}