package model;

import java.time.LocalDate;
import java.util.List;

public class Viagem {
    private String titularId;
    private String origem;
    private String destino;
    private Double preco;
    private LocalDate dataComeco;
    private LocalDate dataFim;
    private List<Incluso> inclusos;

    public Viagem(String titularId,String origem, String destino, Double preco, LocalDate dataComeco, LocalDate dataFim, List<Incluso> inclusos)
    {
        this.titularId = titularId;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
        this.dataComeco = dataComeco;
        this.dataFim = dataFim;
        this.inclusos = inclusos;
    }

    public String getTitularId() {
        return titularId;
    }

    public void setTitularId(String titularId) {
        this.titularId = titularId;
    }


    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public LocalDate getDataComeco() {
        return dataComeco;
    }

    public void setDataComeco(LocalDate dataComeco) {
        this.dataComeco = dataComeco;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<Incluso> getInclusos() {
        return inclusos;
    }

    public void setInclusos(List<Incluso> inclusos) {
        this.inclusos = inclusos;
    }
}
