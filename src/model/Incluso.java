package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public abstract class Incluso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private double preco;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String local;

    public Incluso(double preco, LocalDate dataInicio, LocalDate dataFim, 
                   String local, String id) {

        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.id = id;
    }

    public double getPreco(){
        return this.preco;
    }

    public LocalDate getDataInicio(){
        return this.dataInicio;
    }

    public LocalDate getDataFim(){
        return this.dataFim;
    }

    public String getLocal() {
        return this.local;
    }

    public String getId() {
        return this.id;
    }

    public double calcularPrecoTotal(){
        return Period.between(this.dataInicio, this.dataFim).getDays() * this.preco;
    }

    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String local, double preco){
        if ((this.dataInicio.isEqual(comeco) || this.dataInicio.isAfter(comeco)) && ((this.dataInicio.isEqual(fim) || this.dataInicio.isBefore(fim))) &&
             this.local.equals(local) &&
             this.preco <= preco)
            return true;

        else 
            return false;
    }
}
