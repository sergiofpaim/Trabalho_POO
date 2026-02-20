package model;

import java.time.LocalDate;
import java.time.Period;

public class Hospedagem extends Incluso {

    private String hotel;
    private int capacidade;
    private int diarias;

    public Hospedagem(String hotel, double preco, LocalDate checkIn, LocalDate checkOut,
            int capacidade, String local) {

        super(preco, checkIn, checkOut, local);

        this.hotel = hotel;
        this.capacidade = capacidade;
        this.diarias = Period.between(super.getDataInicio(), super.getDataFim()).getDays();
    }

    public String getHotel() {
        return this.hotel;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    public void calcularDiarias() {
        this.diarias = Period.between(super.getDataInicio(), super.getDataFim()).getDays();
    }

    @Override
    public String toString() {
        return "\nHotel: " + this.hotel
                + "\nDiária: R$ " + this.getPreco()
                + "\nCapacidade: " + this.capacidade
                + "\nDiárias: " + this.diarias;
    }

    @Override
    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String origem, String destino) {
        boolean dataValida = (this.getDataInicio().isBefore(comeco) || this.getDataInicio().isEqual(comeco))
                && (this.getDataFim().isAfter(fim) || this.getDataFim().isEqual(fim));

        return dataValida && this.getCidade().equals(origem);
    }
}
