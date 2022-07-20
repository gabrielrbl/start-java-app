package com.start.model;

public class VagaPessoa {
    private String vagaPessoaId;
    private String vagaEmpresaId;
    private String pessoaId;

    public VagaPessoa() { }

    public VagaPessoa(String vagaPessoaId, String vagaEmpresaId, String pessoaId) {
        this.vagaPessoaId = vagaPessoaId;
        this.vagaEmpresaId = vagaEmpresaId;
        this.pessoaId = pessoaId;
    }

    public void setVagaPessoaId(String vagaPessoaId) { this.vagaPessoaId = vagaPessoaId; }

    public String getVagaPessoaId() { return vagaPessoaId; }

    public void setVagaEmpresaId(String vagaEmpresaId) { this.vagaEmpresaId = vagaEmpresaId; }

    public String getVagaEmpresaId() { return vagaEmpresaId; }

    public void setPessoaId(String pessoaId) { this.pessoaId = pessoaId; }

    public String getPessoaId() { return pessoaId; }

    @Override
    public String toString() {
        return vagaPessoaId;
    }
}