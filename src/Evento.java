import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento extends Inclusos implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nome;
    private LocalDate data;
    private String descricao;
    private String tema;

    public Evento(String nome, LocalDate dataEvento, String descricao, double preco,
                  LocalDate dataC, LocalDate dataF, String local, String tema){
        super(preco, dataC, dataF);
        this.nome = nome;
        this.data = dataEvento;
        this.descricao = descricao;
        this.tema = tema;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public LocalDate getData(){
        return data;
    }

    public void setData(LocalDate data){
        this.data = data;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getTema(){
        return tema;
    }

    public void setTema(String tema){
        this.tema = tema;
    }

    @Override
    public double calcularPrecoTotal(){
        return getPreco();
    }

    @Override
    public String toString(){
        return "Evento: " + nome +
                "\nData: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                "\nDescricao: " + descricao +
                "\nTema: " + tema +
                "\nPeriodo: de " + getdataC().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " a " + getdataF().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                "\nPreco diario: R$ " + getPreco() +
                "\nPreco total: R$ " + calcularPrecoTotal();
    }
}