package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento extends Incluso {

    private static final long serialVersionUID = 1L;

    private String nome;
    private LocalDate data;
    private String descricao;
    private String tema;

    public Evento(String nome, String dataStr, String descricao, double preco,
            String dataCStr, String dataFStr, String local, String tema) {
        super(preco, LocalDate.parse(dataCStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse(dataFStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")), local);

        this.nome = nome;
        this.data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
}
