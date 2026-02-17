import java.time.LocalDate;
import java.time.Period;

public class Inclusos{

    private double preco;
    private LocalDate dataC;
    private LocalDate dataF;
    private String local;

    public Inclusos(){}

    public void setPreco(double p){
        this.preco = p;
    }
    public void setdataC(LocalDate d){
        this.dataC = d;
    }
    public void setdataF(LocalDate f){
        this.dataF = f;
    }

    public double getPreco(){
        return this.preco;
    }
    public LocalDate getdataC(){
        return this.dataC;
    }
    public LocalDate getdataF(){
        return this.dataF;
    }

    public Inclusos(double p, LocalDate c, LocalDate f){
        setPreco(p);
        setdataC(c);
        setdataF(f);
    }

    public double calcularPrecoTotal(){
        return Period.between(this.dataC, this.dataF).getDays() * this.preco;
    }
}