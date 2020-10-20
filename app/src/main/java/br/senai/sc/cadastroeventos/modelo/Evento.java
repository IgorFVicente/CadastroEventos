package br.senai.sc.cadastroeventos.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Evento implements Serializable {

    private int id;
    private String nome;
    private String data_evento;
    private String local;

    public Evento(int id, String nome, String data_evento, String local) {
        this.id = id;
        this.nome = nome;
        this.data_evento = data_evento;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data_evento;
    }

    public void setData(String data_evento) {
        this.data_evento = data_evento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return nome + " - " + data_evento;
    }
}
