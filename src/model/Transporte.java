package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transporte extends Incluso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoTransporte;
    private String destino;
    private int tempo;

    public Transporte(double preco, LocalDate data, String origem, String tipoTransporte, String destino, int tempo) {
        super(preco, data, data, origem);

        this.tipoTransporte = tipoTransporte;
        this.destino = destino;
        this.tempo = (tempo > 0) ? tempo : 0;
    }

    public String getTipoTransporte() {
        return this.tipoTransporte;
    }

    public String getDestino() {
        return this.destino;
    }

    public int getTempo() {
        return this.tempo;
    }

    @Override
    public double calcularPrecoTotal() {
        return this.tempo * this.getPreco();
    }

    @Override
    public String toString() {
        return "\nTransporte: " + this.tipoTransporte
                + "\nPreço por hora: R$ " + this.getPreco()
                + "\nPreço total: R$ " + this.calcularPrecoTotal()
                + "\nOrigem: " + super.getCidade()
                + "\nDestino: " + this.destino
                + "\nDuração da viagem em horas: " + this.tempo + "\n";
    }

    @Override
    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String origem, String destino) {
        return (this.getDataInicio().isEqual(comeco) || this.getDataInicio().isAfter(comeco)) && ((this.getDataInicio().isEqual(fim) || this.getDataInicio().isBefore(fim)))
                && super.getCidade().equals(origem) && this.destino.equals(destino);
    }
}
