package model;

import java.time.LocalDateTime;

public class CheckQuarto {
    private int id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String obs;
    private Status status;
    private Check check;
    private ReservaQuarto reservaQuarto;
    private Quarto quarto;

    public CheckQuarto() {
        this.status = Status.ATIVO;
    }

    public CheckQuarto(int id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String obs, Status status, Check check, ReservaQuarto reservaQuarto, Quarto quarto) {
        this.id = id;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.obs = obs;
        this.status = status;
        this.check = check;
        this.reservaQuarto = reservaQuarto;
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

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public ReservaQuarto getReservaQuarto() {
        return reservaQuarto;
    }

    public void setReservaQuarto(ReservaQuarto reservaQuarto) {
        this.reservaQuarto = reservaQuarto;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "CheckQuarto [id=" + id + ", dataHoraInicio=" + dataHoraInicio + ", dataHoraFim=" + dataHoraFim + ", obs=" + obs + ", status=" + status + ", check=" + check + ", reservaQuarto=" + reservaQuarto + ", quarto=" + quarto + "]";
    }
}