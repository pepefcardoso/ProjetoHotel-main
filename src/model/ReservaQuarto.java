package model;

import java.time.LocalDateTime;

public class ReservaQuarto {
    private int id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String obs;
    private Status status;
    private Reserva reserva;
    private Quarto quarto;

    public ReservaQuarto() {
        this.status = Status.ATIVO;
    }

    public ReservaQuarto(int id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String obs, Status status, Reserva reserva, Quarto quarto) {
        this.id = id;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.obs = obs;
        this.status = status;
        this.reserva = reserva;
        this.quarto = quarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
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

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "ReservaQuarto [id=" + id + ", dataHoraInicio=" + dataHoraInicio + ", dataHoraFim=" + dataHoraFim + ", obs=" + obs + ", status=" + status + ", reserva=" + reserva + ", quarto=" + quarto + "]";
    }
}