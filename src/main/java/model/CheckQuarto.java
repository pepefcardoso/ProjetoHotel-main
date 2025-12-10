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
@Table(name = "check_quarto")
public class CheckQuarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private char status;

    @ManyToOne
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne
    @JoinColumn(name = "reserva_quarto_id")
    private ReservaQuarto reservaQuarto;

    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public CheckQuarto() {
    }

    public CheckQuarto(int id, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String obs, char status, Check check, ReservaQuarto reservaQuarto, Quarto quarto) {
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
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
        return "id               = " + this.getId()
                + "\nData/Hora Inicio  = " + this.getDataHoraInicio()
                + "\nData/Hora Fim     = " + this.getDataHoraFim()
                + "\nObs               = " + this.getObs()
                + "\nStatus            = " + this.getStatus()
                + "\nCheck ID          = " + (this.getCheck() != null ? this.getCheck().getId() : "null")
                + "\nReservaQuarto ID  = " + (this.getReservaQuarto() != null ? this.getReservaQuarto().getId() : "null")
                + "\nQuarto            = " + (this.getQuarto() != null ? this.getQuarto().getIdentificacao() : "null");
    }
}
