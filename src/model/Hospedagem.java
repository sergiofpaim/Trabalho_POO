package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Hospedagem extends Incluso {

    private String hotel;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int capacidade;

    public Hospedagem(String hotel, double preco, String checkIn, String checkOut,
                      int capacidade, String local, String id) {

        super(preco,
              LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
              local, id);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.hotel = hotel;
        this.checkIn = LocalDate.parse(checkIn, formato);
        this.checkOut = LocalDate.parse(checkOut, formato);
        this.capacidade = capacidade;
    }

    public Hospedagem(String hotel, double preco, String local, String id) {
        this(hotel, preco, "01/01/2026", "02/01/2026", 1, local, id);
    }

    public String getHotel(){
        return this.hotel;
    }

    public LocalDate getCheckIn(){
        return this.checkIn;
    }

    public LocalDate getCheckOut(){
        return this.checkOut;
    }

    public int getCapacidade(){
        return this.capacidade;
    }

    @Override
    public String toString(){
        return "Hotel: " + this.hotel
                + " Periodo: " + this.checkIn + " - " + this.checkOut
                + " Preco: " + this.getPreco();
    }
}
