package service;

import java.time.LocalDate;
import java.util.List;
import model.Incluso;

public class RoteiroService {
    
    public List<Incluso> ChecarInclusosDisponiveis(java.util.Collection<Incluso> todosInclusos, 
                                                   LocalDate dataInicio, 
                                                   LocalDate dataFinal, 
                                                   String destino, 
                                                   double orcamento) {
        List<Incluso> inclusosDisponiveis = new java.util.ArrayList<>();
    
        for (Incluso incluso : todosInclusos) {
            if (incluso.checarDisponibilidade(dataInicio, dataFinal, destino, orcamento)) {
                inclusosDisponiveis.add(incluso);
            }
        }
        return inclusosDisponiveis;
    }
}
