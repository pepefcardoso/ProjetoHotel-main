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
@Table(name = "`check`")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dataHoraCadastro;

    @Column(name = "data_hora_entrada")
    private LocalDateTime dataHoraEntrada;

    @Column(name = "data_hora_saida")
    private LocalDateTime dataHoraSaida;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @Column(length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "check_quarto_id", nullable = false)
    private CheckQuarto checkQuarto;

    public Check() {
    }

    public Check(int id, LocalDateTime dataHoraCadastro, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, String obs, String status, Reserva reserva) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return "id              = " + this.getId()
                + "\nData/Hora Cad. = " + this.getDataHoraCadastro()
                + "\nData/Hora Ent. = " + this.getDataHoraEntrada()
                + "\nData/Hora Sai. = " + this.getDataHoraSaida()
                + "\nObs            = " + this.getObs()
                + "\nStatus         = " + this.getStatus()
                + "\nReserva ID     = " + (this.getReserva() != null ? this.getReserva().getId() : "null");
    }
}
