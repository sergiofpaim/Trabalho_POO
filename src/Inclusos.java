import java.time.LocalDate;
import java.time.Period;
import java.io.Serializable;

abstract class Inclusos implements Serializable{
    private static final long serialVersionUID = 1L;

    private static int contador = 0;
    private int id;
    private double preco;
    private LocalDate dataC;
    private LocalDate dataF;
    private String local;

    public void setId() { this.id = this.contador; }

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

    public static void setContador(int v) { contador = v; }

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

    public int getContador() { return contador; }

    public int getId() { return id; }

    public Inclusos(double p, LocalDate c, LocalDate f, String l){
        setPreco(p);
        setdataC(c);
        setdataF(f);
        setLocal(l);
        setId();
        this.contador++;
    }

    public double calcularPrecoTotal(){
        return Period.between(this.dataC, this.dataF).getDays() * this.preco;
    }
}
