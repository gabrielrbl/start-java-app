package com.start.model;

public class VagaEmpresa {
    private String vagaEmpresaId;
    private String vaga;
    private String empresaId;

    public VagaEmpresa() { }

    public VagaEmpresa(String vagaEmpresaId, String vaga, String empresaId) {
        this.vagaEmpresaId = vagaEmpresaId;
        this.vaga = vaga;
        this.empresaId = empresaId;
    }

    public void setVagaEmpresaId(String vagaEmpresaId) { this.vagaEmpresaId = vagaEmpresaId; }

    public String getVagaEmpresaId() { return vagaEmpresaId; }

    public void setVaga(String vaga) { this.vaga = vaga; }

    public String getVaga() {
        return vaga;
    }

    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }

    public String getEmpresaId() { return empresaId; }

    @Override
    public String toString() {
        return vaga;
    }
}