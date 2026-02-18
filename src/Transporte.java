import java.time.LocalDate;

public class Transporte extends Inclusos {
    private String tipo_transporte, destino;
    private int tempo;
    
    public Transporte (double preco, LocalDate data_c, LocalDate data_f, String local, String tipo_transporte, String destino, int tempo) {
        super(preco, data_c, data_f, local);
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
                "\nDuração da viagem: " + this.getTempo() + "\n";
    }
}
