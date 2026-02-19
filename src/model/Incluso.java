package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public abstract class Incluso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String cidade;
    private double preco;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Incluso(double preco, LocalDate dataInicio, LocalDate dataFim, 
                   String cidade, String id) {

        this.id = id;
        this.cidade = cidade;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public void setDataInicio(LocalDate inicio) { dataInicio = inicio; }

    public void setDataFim(LocalDate fim) { dataFim = fim; }

    public double getPreco(){
        return this.preco;
    }

    public LocalDate getDataInicio(){
        return this.dataInicio;
    }

    public LocalDate getDataFim(){
        return this.dataFim;
    }

    public String getcidade() {
        return this.cidade;
    }

    public String getId() {
        return this.id;
    }

    public double calcularPrecoTotal(){
        return Period.between(this.dataInicio, this.dataFim).getDays() * this.preco;
    }

    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String cidade){
        if ((this.dataInicio.isEqual(comeco) || this.dataInicio.isAfter(comeco)) && ((this.dataInicio.isEqual(fim) || this.dataInicio.isBefore(fim))) &&
             this.cidade.equals(cidade))
            return true;

        else 
            return false;
    }
}
