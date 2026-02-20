package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Roteiro implements Serializable {

    private String titularId;
    private String origem;
    private String destino;
    private Double preco;
    private LocalDate dataComeco;
    private LocalDate dataFim;
    private List<Incluso> inclusos;
    private double taxaLucro;

    public Roteiro(String titularId, String origem, String destino, Double preco, LocalDate dataComeco, LocalDate dataFim, List<Incluso> inclusos, double taxaLucro) {
        this.titularId = titularId;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
        this.dataComeco = dataComeco;
        this.dataFim = dataFim;
        this.inclusos = inclusos;
        this.taxaLucro = taxaLucro;
    }

    public double getTaxaLucro() {
        return taxaLucro;
    }

    public void setTaxaLucro(double t) {
        taxaLucro = t;
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

    public double getLucro() {
        return (taxaLucro != 0) ? preco / (1 + taxaLucro) : 0;
    }
}
