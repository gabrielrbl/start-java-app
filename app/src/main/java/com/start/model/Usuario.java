package com.start.model;

public class Usuario {
    private String usuarioId;
    private String nome;
    private String telefone;
    private String email;
    private String senha;

    public Usuario () { }

    public Usuario(String usuarioId, String nome, String telefone, String email, String senha) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioId() { return usuarioId; }

    public void setNome(String nome) { this.nome = nome; }

    public String getNome() {
        return nome;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getTelefone() {
        return telefone;
    }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) { this.senha = senha; }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() { return nome; }
}