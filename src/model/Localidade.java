package model;

import java.util.List;

public class Localidade {
    private String nome;
    private List<String> hoteis;
    private List<Evento> eventos;
    private List<Transporte> rotas;

    public Localidade(String nome, List<String> hoteis, List<Evento> eventos, List<Transporte> rotas)
    {
        this.nome = nome;
        this.hoteis = hoteis;
        this.eventos = eventos;
        this.rotas = rotas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getHoteis() {
        return hoteis;
    }

    public void setHoteis(List<String> hoteis) {
        this.hoteis = hoteis;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Transporte> getRotas() {
        return rotas;
    }

    public void setRotas(List<Transporte> rotas) {
        this.rotas = rotas;
    }
}
