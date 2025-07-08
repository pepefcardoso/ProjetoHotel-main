package model;

public class Marca {

    private int id;
    private String descricao;
    private Status status;

    public Marca() {
        this.status = Status.ATIVO;
    }

    public Marca(int id, String descricao, Status status) {
        this.id = id;
        this.setDescricao(descricao);
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
        }
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Marca [id=" + id + ", descricao=" + descricao + ", status=" + status + "]";
    }
}