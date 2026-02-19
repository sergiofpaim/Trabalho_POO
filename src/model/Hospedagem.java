package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Hospedagem extends Incluso {

    private String hotel;
    private int capacidade;

    public Hospedagem(String hotel, double preco, String checkIn, String checkOut,
                      int capacidade, String local, String id) {

        super(preco,
              LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              local, id);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.hotel = hotel;
        this.capacidade = capacidade;
    }

    public String getHotel(){
        return this.hotel;
    }

    public int getCapacidade(){
        return this.capacidade;
    }

    @Override
    public String toString(){
        return "Hotel: " + this.hotel
                + " Preco: " + this.getPreco();
    }
}
