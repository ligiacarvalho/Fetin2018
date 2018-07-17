package com.fetin.securityapp.model;

public class Usuario {

    private String nome,email,telefone,rg,contatoProximo,cidade;

     /*
        Getters And Setters
    */

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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
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
