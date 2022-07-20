package com.start.model;

public class Pessoa {
    private String pessoaId;
    private String usuarioId;
    private String cpf;

    public Pessoa() { }

    public Pessoa(String pessoaId, String usuarioId, String cpf) {
        this.pessoaId = pessoaId;
        this.usuarioId = usuarioId;
        this.cpf = cpf;
    }

    public void setPessoaId(String pessoaId) { this.pessoaId = pessoaId; }

    public String getPessoaId() {
        return pessoaId;
    }

    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() { return cpf; }
}
