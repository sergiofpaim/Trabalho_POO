import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Hospedagem extends Inclusos{
    private String hotel;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int capacidade;

    public Hospedagem(String hotel, double preco,String checkIn, String checkOut, int capacidade,  String dataCStr, String dataFStr, String local){
        super(preco, LocalDate.parse(dataCStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalDate.parse(dataFStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")), local);
        setHotel(hotel);
        setCheckIn(checkIn);
        setCheckOut(checkOut);
        setCapacidade(capacidade);
        
    }

    public void setHotel(String hotel){
        this.hotel = hotel;
    }
    public void setCheckIn(String checkIn){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.checkIn = LocalDate.parse(checkIn, formato);
    }
    public void setCheckOut(String checkOut){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.checkOut = LocalDate.parse(checkOut, formato);
    }
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
    public String getHotel(){
        return hotel;
    }
    public LocalDate getCheckIn(){
        return checkIn;
    }
    public LocalDate getCheckOut(){
        return checkOut;
    }
    public int getCapacidade(){
        return capacidade;
    }
}
