package model;

public enum Status {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
