package com.fetin.securityapp.model;

public class Usuario {

    private String chave, nome, email, telefone, CPF, contatoProximo, cidade, senha;
    private Celular celularP;

     /*
        Getters And Setters
    */

    public Celular getCelularP() {
        return celularP;
    }

    public void setCelularP(Celular celularP) {
        this.celularP = celularP;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getContatoProximo() {
        return contatoProximo;
    }

    public void setContatoProximo(String contatoProximo) {
        this.contatoProximo = contatoProximo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
