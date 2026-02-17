import java.time.LocalDate;
import java.time.Period;

abstract class Inclusos{

    private double preco;
    private LocalDate dataC;
    private LocalDate dataF;
    private String local;

    public void setPreco(double p){
        this.preco = p;
    }
    public void setdataC(LocalDate d){
        this.dataC = d;
    }
    public void setdataF(LocalDate f){
        this.dataF = f;
    }
    public void setLocal(String local){
        this.local = local;
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
    public String getLocal() {
        return this.local;
    }

    public Inclusos(double p, LocalDate c, LocalDate f, String l){
        setPreco(p);
        setdataC(c);
        setdataF(f);
        setLocal(l);
    }

    public double calcularPrecoTotal(){
        return Period.between(this.dataC, this.dataF).getDays() * this.preco;
    }
}
