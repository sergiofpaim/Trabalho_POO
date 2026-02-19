import java.io.Serializable; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transporte extends Inclusos implements Serializable {
    private static final long serialVersionUID=1L;
    private String tipo_transporte, destino;
    private int tempo;
    
    public Transporte (double preco, String data, String local, String tipo_transporte, String destino, int tempo) {
        super(preco, LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), local);
        setLocal(local);
        setTipoTransporte(tipo_transporte);
        setDestino(destino);
        setTempo(tempo);
    }
    
    public String getTipoTransporte() {
        return this.tipo_transporte;
    }
    
    public String getDestino() {
        return this.destino;
    }
    
    public int getTempo() {
        return this.tempo;
    }
    
    public void setTipoTransporte(String tipo_transporte) {
        this.tipo_transporte = tipo_transporte;
    }
    
    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public void setTempo(int tempo) {
        if (tempo > 0) this.tempo = tempo;
        else this.tempo = 0;
    }     
    
    @Override
    public String toString() {
        return "\nTransporte: \nTipo: " + this.getTipoTransporte() + 
                "\nDestino: " + this.getDestino() + 
                "\nDuracao da viagem: " + this.getTempo() + "\n";
    }
}
