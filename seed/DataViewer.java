
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.Incluso;
import model.Hospedagem;
import model.Transporte;
import model.Evento;
import repo.Repo;

/**
 * Utilitário para visualizar os dados mockados serializados em Inclusos.dat.
 * 
 * Como rodar:
 *   cd src
 *   javac DataViewer.java
 *   java DataViewer
 */
public class DataViewer {
    public static void main(String[] args) {
        File arquivo = new File("storage/Inclusos.dat");

        // Tenta o path relativo primeiro; se não achar, tenta absoluto
        if (!arquivo.exists()) {
            arquivo = new File("c:\\Users\\sergi_qla3gir\\source\\repos\\Trabalho_POO\\storage\\Inclusos.dat");
        }

        if (!arquivo.exists()) {
            System.out.println("Arquivo Inclusos.dat nao encontrado!");
            return;
        }

        HashMap<String, Incluso> inclusos = Repo.desserialize(arquivo);
        mostrarResumo(inclusos);
    }

    /**
     * Mostra um resumo completo dos dados mockados.
     * Pode ser chamada de qualquer lugar passando o HashMap de inclusos.
     */
    public static void mostrarResumo(HashMap<String, Incluso> inclusos) {
        System.out.println("==============================================");
        System.out.println("        VISUALIZADOR DE DADOS MOCKADOS        ");
        System.out.println("==============================================");
        System.out.println("Total de Inclusos: " + inclusos.size());
        System.out.println();

        // Contadores por tipo e por cidade
        int totalHospedagens = 0, totalTransportes = 0, totalEventos = 0;
        HashMap<String, int[]> porCidade = new HashMap<>();
        // int[0] = hospedagens, int[1] = transportes, int[2] = eventos

        for (Map.Entry<String, Incluso> entry : inclusos.entrySet()) {
            Incluso i = entry.getValue();
            String cidade = i.getcidade();

            porCidade.putIfAbsent(cidade, new int[]{0, 0, 0});
            int[] contadores = porCidade.get(cidade);

            if (i instanceof Hospedagem) {
                totalHospedagens++;
                contadores[0]++;
            } else if (i instanceof Transporte) {
                totalTransportes++;
                contadores[1]++;
            } else if (i instanceof Evento) {
                totalEventos++;
                contadores[2]++;
            }
        }

        System.out.println("--- Totais por Tipo ---");
        System.out.println("  Hospedagens: " + totalHospedagens);
        System.out.println("  Transportes: " + totalTransportes);
        System.out.println("  Eventos:     " + totalEventos);
        System.out.println();

        System.out.println("--- Detalhes por Cidade ---");
        for (Map.Entry<String, int[]> entry : porCidade.entrySet()) {
            String cidade = entry.getKey();
            int[] c = entry.getValue();
            System.out.println();
            System.out.println("  [" + cidade + "]");
            System.out.println("    Hospedagens: " + c[0]);
            System.out.println("    Transportes: " + c[1]);
            System.out.println("    Eventos:     " + c[2]);
            System.out.println("    Subtotal:    " + (c[0] + c[1] + c[2]));
        }

        System.out.println();
        System.out.println("--- Amostra (primeiros 5 de cada tipo) ---");
        System.out.println();

        int showH = 0, showT = 0, showE = 0;
        for (Map.Entry<String, Incluso> entry : inclusos.entrySet()) {
            Incluso i = entry.getValue();
            if (i instanceof Hospedagem && showH < 5) {
                Hospedagem h = (Hospedagem) i;
                System.out.println("  HOSPEDAGEM | " + h.getHotel()
                    + " | Cidade: " + h.getcidade()
                    + " | Preco/dia: R$" + String.format("%.2f", h.getPreco())
                    + " | CheckIn: " + h.getDataInicio()
                    + " | CheckOut: " + h.getDataFim()
                    + " | Capacidade: " + h.getCapacidade());
                showH++;
            } else if (i instanceof Transporte && showT < 5) {
                Transporte t = (Transporte) i;
                System.out.println("  TRANSPORTE | Tipo: " + t.getTipoTransporte()
                    + " | Cidade: " + t.getcidade()
                    + " | Destino: " + t.getDestino()
                    + " | Data: " + t.getDataInicio()
                    + " | Preco: R$" + String.format("%.2f", t.getPreco())
                    + " | Tempo: " + t.getTempo() + "h");
                showT++;
            } else if (i instanceof Evento && showE < 5) {
                Evento e = (Evento) i;
                System.out.println("  EVENTO     | " + e.getNome()
                    + " | Cidade: " + e.getcidade()
                    + " | Data: " + e.getData()
                    + " | Tema: " + e.getTema()
                    + " | Preco: R$" + String.format("%.2f", e.getPreco()));
                showE++;
            }
        }

        System.out.println();
        System.out.println("==============================================");
    }
}
