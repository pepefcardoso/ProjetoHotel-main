
package model;

import java.time.LocalDateTime;


public class OrdemServico {
    private int id;
    private LocalDateTime dataHoraCadastro;
    private LocalDateTime dataHoraPrevisaoInicio;
    private LocalDateTime dataHoraPrevisaoTermino;
    private String obs;
    private Status status;
    private Servico servico;
    private Check check;
    private Quarto quarto;
    
    public OrdemServico() {
        this.status = Status.ATIVO;
    }
    
    public OrdemServico(int id,LocalDateTime dataHoraCadastro,LocalDateTime dataHoraPrevisaoInicio,LocalDateTime dataHoraPrevisaoTermino,String obs,Status status,Servico servico,Check check,Quarto quarto){
        this.id = id;
        this.dataHoraCadastro = dataHoraCadastro;
        this.dataHoraPrevisaoInicio = dataHoraPrevisaoInicio;
        this.dataHoraPrevisaoTermino = dataHoraPrevisaoTermino;
        this.obs = obs;
        this.status = status;
        this.servico = servico;
        this.check = check;
        this.quarto = quarto;
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

    public LocalDateTime getDataHoraPrevisaoInicio() {
        return dataHoraPrevisaoInicio;
    }

    public void setDataHoraPrevisaoInicio(LocalDateTime dataHoraPrevisaoInicio) {
        this.dataHoraPrevisaoInicio = dataHoraPrevisaoInicio;
    }

    public LocalDateTime getDataHoraPrevisaoTermino() {
        return dataHoraPrevisaoTermino;
    }

    public void setDataHoraPrevisaoTermino(LocalDateTime dataHoraPrevisaoTermino) {
        this.dataHoraPrevisaoTermino = dataHoraPrevisaoTermino;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    @Override
    public String toString() {
        return "OrdemServico [id=" + id + ", dataHoraCadastro=" + dataHoraCadastro + ", dataHoraPrevisaoInicio=" + dataHoraPrevisaoInicio + ", dataHoraPrevisaoTermino=" + dataHoraPrevisaoTermino + ", obs=" + obs + ", status=" + status + ", servico=" + servico + ", check=" + check + ", quarto=" + quarto + "]";
    }
}
    



