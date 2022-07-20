package com.start.model;

public class Empresa {
    private String empresaId;
    private String usuarioId;
    private String cnpj;
    private String endereco;

    public Empresa() { }

    public Empresa(String empresaId, String usuarioId, String cnpj, String endereco) {
        this.empresaId = empresaId;
        this.usuarioId = usuarioId;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }

    public String getEmpresaId() {
        return empresaId;
    }

    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getCnpj() {
        return cnpj;
    }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() { return cnpj; }
}