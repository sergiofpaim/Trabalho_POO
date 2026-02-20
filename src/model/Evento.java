package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento extends Incluso {

    private String nome;
    private LocalDate data;
    private String descricao;
    private String tema;

    public Evento(String nome, LocalDate dataStr, String descricao, double preco, LocalDate dataCStr, LocalDate dataFStr, String local, String tema) {
        super(preco, dataCStr, dataFStr, local);

        this.nome = nome;
        this.data = dataStr;
        this.descricao = descricao;
        this.tema = tema;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public double calcularPrecoTotal() {
        return getPreco();
    }

    @Override
    public String toString() {
        return "\nEvento: " + nome
                + "\nData: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\nDescrição: " + descricao
                + "\nTema: " + tema
                + "\nPreço diario: R$ " + getPreco();
    }

    @Override
    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String origem, String destino) {
        boolean dataValida = (this.getDataInicio().isBefore(comeco) || this.getDataInicio().isEqual(comeco))
                && (this.getDataFim().isAfter(fim) || this.getDataFim().isEqual(fim));

        return dataValida && this.getCidade().equals(origem);
    }
}
