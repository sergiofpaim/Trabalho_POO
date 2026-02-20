package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Hospedagem extends Incluso {

    private String hotel;
    private int capacidade;

    public Hospedagem(String hotel, double preco, String checkIn, String checkOut,
            int capacidade, String local) {

        super(preco,
                LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                local);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.hotel = hotel;
        this.capacidade = capacidade;
    }

    public String getHotel() {
        return this.hotel;
    }

    public int getCapacidade() {
        return this.capacidade;
    }

    @Override
    public String toString() {
        return "\nHotel: " + this.hotel
                + "\nDiária: R$ " + this.getPreco()
                + "\nCapacidade: " + this.capacidade;
    }

    @Override
    public boolean checarDisponibilidade(LocalDate comeco, LocalDate fim, String cidade) {
        boolean dataValida = (this.getDataInicio().isBefore(comeco) || this.getDataInicio().isEqual(comeco))
                && (this.getDataFim().isAfter(fim) || this.getDataFim().isEqual(fim));

        return dataValida && this.getcidade().equals(cidade);
    }
}
