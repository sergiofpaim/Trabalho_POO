package seed;

import model.Evento;
import model.Hospedagem;
import model.Transporte;
import model.Usuario;
import service.RoteiroService;

public class DataSeeder {

    public static void fill(RoteiroService service) {
        //  Usuarios (6 usuarios) 
        service.adicionarUsuario(new Usuario("admin", "000.000.000-00", "01/01/1990", "Rua Central, 1", "11999990000", "admin", "admin"));
        service.adicionarUsuario(new Usuario("joao", "111.222.333-44", "15/05/1995", "Av. Brasil, 500", "21988887777", "1234", "cliente"));
        service.adicionarUsuario(new Usuario("maria", "222.333.444-55", "10/10/1988", "Rua das Flores, 12", "31977776666", "password", "cliente"));
        service.adicionarUsuario(new Usuario("pedro", "333.444.555-66", "20/02/2000", "Rua do Sol, 45", "41966665555", "pedro123", "cliente"));
        service.adicionarUsuario(new Usuario("ana", "444.555.666-77", "05/12/1992", "Av. Paulista, 1000", "11955554444", "ana77", "cliente"));
        service.adicionarUsuario(new Usuario("carlos", "555.666.777-88", "30/07/1985", "Rua da Paz, 30", "71944443333", "charles", "cliente"));

        //  Hospedagens (12 hospedagens - 2 por cidade) 
        // Rio de Janeiro
        service.adicionarIncluso(new Hospedagem("Hotel Copacabana Palace", 450.0, "20/02/2025", "25/02/2025", 2, "Rio de Janeiro"));
        service.adicionarIncluso(new Hospedagem("Ibis Budget Centro", 150.0, "20/02/2025", "25/02/2025", 3, "Rio de Janeiro"));
        // São Paulo
        service.adicionarIncluso(new Hospedagem("Hotel Fasano", 800.0, "20/02/2025", "25/02/2025", 2, "São Paulo"));
        service.adicionarIncluso(new Hospedagem("Tivoli Mofarrej", 650.0, "20/02/2025", "25/02/2025", 2, "São Paulo"));
        // Florianópolis
        service.adicionarIncluso(new Hospedagem("Pousada Floripa", 200.0, "20/02/2025", "25/02/2025", 4, "Florianópolis"));
        service.adicionarIncluso(new Hospedagem("Majestic Palace Hotel", 400.0, "20/02/2025", "25/02/2025", 2, "Florianópolis"));
        // Salvador
        service.adicionarIncluso(new Hospedagem("Fera Palace Hotel", 500.0, "20/02/2025", "25/02/2025", 2, "Salvador"));
        service.adicionarIncluso(new Hospedagem("Grande Hotel da Barra", 280.0, "20/02/2025", "25/02/2025", 3, "Salvador"));
        // Belo Horizonte
        service.adicionarIncluso(new Hospedagem("Quality Hotel Pampulha", 320.0, "20/02/2025", "25/02/2025", 2, "Belo Horizonte"));
        service.adicionarIncluso(new Hospedagem("Savassi Hotel", 220.0, "20/02/2025", "25/02/2025", 2, "Belo Horizonte"));
        // Curitiba
        service.adicionarIncluso(new Hospedagem("Radisson Hotel Curitiba", 380.0, "20/02/2025", "25/02/2025", 2, "Curitiba"));
        service.adicionarIncluso(new Hospedagem("Nomaa Hotel", 550.0, "20/02/2025", "25/02/2025", 2, "Curitiba"));

        //  Eventos (12 eventos) 
        service.adicionarIncluso(new Evento("Show Anitta", "22/02/2025", "Show ao vivo na praia", 250.0, "20/02/2025", "25/02/2025", "Rio de Janeiro", "Musical"));
        service.adicionarIncluso(new Evento("Bloco de Carnaval", "21/02/2025", "Bloco de rua no centro", 50.0, "20/02/2025", "25/02/2025", "Rio de Janeiro", "Carnaval"));
        service.adicionarIncluso(new Evento("Festival Gastronômico SP", "23/02/2025", "Degustação de pratos típicos", 120.0, "20/02/2025", "25/02/2025", "São Paulo", "Gastronomia"));
        service.adicionarIncluso(new Evento("Teatro Municipal", "24/02/2025", "Peça clássica", 90.0, "20/02/2025", "25/02/2025", "São Paulo", "Cultura"));
        service.adicionarIncluso(new Evento("Trilha Ecológica", "24/02/2025", "Trilha na mata atlântica", 80.0, "20/02/2025", "25/02/2025", "Florianópolis", "Aventura"));
        service.adicionarIncluso(new Evento("Surf Master", "22/02/2025", "Competição de surf", 0.0, "20/02/2025", "25/02/2025", "Florianópolis", "Esporte"));
        service.adicionarIncluso(new Evento("Carnaval Pelourinho", "21/02/2025", "Festa histórica no Pelô", 0.0, "20/02/2025", "25/02/2025", "Salvador", "Carnaval"));
        service.adicionarIncluso(new Evento("Trios Elétricos Barra", "23/02/2025", "Desfile de trios", 350.0, "20/02/2025", "25/02/2025", "Salvador", "Carnaval"));
        service.adicionarIncluso(new Evento("Feira de Artesanato BH", "22/02/2025", "Maior feira da América Latina", 0.0, "20/02/2025", "25/02/2025", "Belo Horizonte", "Cultura"));
        service.adicionarIncluso(new Evento("Visita ao Inhotim", "23/02/2025", "Museu de arte contemporânea", 60.0, "20/02/2025", "25/02/2025", "Belo Horizonte", "Arte"));
        service.adicionarIncluso(new Evento("Ópera de Arame", "21/02/2025", "Concerto filarmônico", 150.0, "20/02/2025", "25/02/2025", "Curitiba", "Musical"));
        service.adicionarIncluso(new Evento("Jardim Botânico", "24/02/2025", "Tour guiado estufa", 30.0, "20/02/2025", "25/02/2025", "Curitiba", "Turismo"));

        //  Transportes (12 transportes) 
        service.adicionarIncluso(new Transporte(200.0, "20/02/2025", "Rio de Janeiro", "aereo", "Rio de Janeiro", 2));
        service.adicionarIncluso(new Transporte(50.0, "20/02/2025", "Rio de Janeiro", "rodoviario", "Rio de Janeiro", 6));
        service.adicionarIncluso(new Transporte(250.0, "20/02/2025", "São Paulo", "aereo", "São Paulo", 1));
        service.adicionarIncluso(new Transporte(40.0, "20/02/2025", "São Paulo", "rodoviario", "São Paulo", 8));
        service.adicionarIncluso(new Transporte(150.0, "20/02/2025", "Florianópolis", "aereo", "Florianópolis", 1));
        service.adicionarIncluso(new Transporte(30.0, "20/02/2025", "Florianópolis", "rodoviario", "Florianópolis", 12));
        service.adicionarIncluso(new Transporte(220.0, "20/02/2025", "Salvador", "aereo", "Salvador", 2));
        service.adicionarIncluso(new Transporte(60.0, "20/02/2025", "Salvador", "rodoviario", "Salvador", 24));
        service.adicionarIncluso(new Transporte(180.0, "20/02/2025", "Belo Horizonte", "aereo", "Belo Horizonte", 1));
        service.adicionarIncluso(new Transporte(45.0, "20/02/2025", "Belo Horizonte", "rodoviario", "Belo Horizonte", 9));
        service.adicionarIncluso(new Transporte(190.0, "20/02/2025", "Curitiba", "aereo", "Curitiba", 1));
        service.adicionarIncluso(new Transporte(35.0, "20/02/2025", "Curitiba", "rodoviario", "Curitiba", 10));
    }
}
