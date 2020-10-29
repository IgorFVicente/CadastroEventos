package br.senai.sc.cadastroeventos.modelo;

import java.io.Serializable;

public class Local implements Serializable {

    private int id;
    private String nome_local;
    private String bairro;
    private String cidade;
    private int capacidade;

    public Local(int id, String nome, String bairro, String cidade, int capacidade) {
        this.id = id;
        this.nome_local = nome;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome_local;
    }

    public void setNome(String nome) {
        this.nome_local = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return nome_local;
    }
}
