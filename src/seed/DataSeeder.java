package seed;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Evento;
import model.Hospedagem;
import model.Transporte;
import model.Usuario;
import service.RoteiroService;

public class DataSeeder {

    private static final DateTimeFormatter BR
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static LocalDate d(String data) {
        return LocalDate.parse(data, BR);
    }

    public static void fill(RoteiroService service) {

        // =========================
        // Usuarios (6 usuarios)
        // =========================
        service.adicionarUsuario(new Usuario("admin", "000.000.000-00", d("01/01/1990"), "Rua Central, 1", "11999990000", "admin", "admin"));
        service.adicionarUsuario(new Usuario("joao", "111.222.333-44", d("15/05/1995"), "Av. Brasil, 500", "21988887777", "1234", "cliente"));
        service.adicionarUsuario(new Usuario("maria", "222.333.444-55", d("10/10/1988"), "Rua das Flores, 12", "31977776666", "password", "cliente"));
        service.adicionarUsuario(new Usuario("pedro", "333.444.555-66", d("20/02/2000"), "Rua do Sol, 45", "41966665555", "pedro123", "cliente"));
        service.adicionarUsuario(new Usuario("ana", "444.555.666-77", d("05/12/1992"), "Av. Paulista, 1000", "11955554444", "ana77", "cliente"));
        service.adicionarUsuario(new Usuario("carlos", "555.666.777-88", d("30/07/1985"), "Rua da Paz, 30", "71944443333", "charles", "cliente"));

        // =========================
        // Hospedagens (12)
        // =========================
        service.adicionarIncluso(new Hospedagem("Hotel Copacabana Palace", 450.0, d("20/02/2025"), d("25/02/2025"), 2, "Rio de Janeiro"));
        service.adicionarIncluso(new Hospedagem("Ibis Budget Centro", 150.0, d("20/02/2025"), d("25/02/2025"), 3, "Rio de Janeiro"));

        service.adicionarIncluso(new Hospedagem("Hotel Fasano", 800.0, d("20/02/2025"), d("25/02/2025"), 2, "São Paulo"));
        service.adicionarIncluso(new Hospedagem("Tivoli Mofarrej", 650.0, d("20/02/2025"), d("25/02/2025"), 2, "São Paulo"));

        service.adicionarIncluso(new Hospedagem("Pousada Floripa", 200.0, d("20/02/2025"), d("25/02/2025"), 4, "Florianópolis"));
        service.adicionarIncluso(new Hospedagem("Majestic Palace Hotel", 400.0, d("20/02/2025"), d("25/02/2025"), 2, "Florianópolis"));

        service.adicionarIncluso(new Hospedagem("Fera Palace Hotel", 500.0, d("20/02/2025"), d("25/02/2025"), 2, "Salvador"));
        service.adicionarIncluso(new Hospedagem("Grande Hotel da Barra", 280.0, d("20/02/2025"), d("25/02/2025"), 3, "Salvador"));

        service.adicionarIncluso(new Hospedagem("Quality Hotel Pampulha", 320.0, d("20/02/2025"), d("25/02/2025"), 2, "Belo Horizonte"));
        service.adicionarIncluso(new Hospedagem("Savassi Hotel", 220.0, d("20/02/2025"), d("25/02/2025"), 2, "Belo Horizonte"));

        service.adicionarIncluso(new Hospedagem("Radisson Hotel Curitiba", 380.0, d("20/02/2025"), d("25/02/2025"), 2, "Curitiba"));
        service.adicionarIncluso(new Hospedagem("Nomaa Hotel", 550.0, d("20/02/2025"), d("25/02/2025"), 2, "Curitiba"));

        // =========================
        // Eventos (12)
        // =========================
        service.adicionarIncluso(new Evento("Show Anitta", d("22/02/2025"), "Show ao vivo na praia", 250.0, d("20/02/2025"), d("25/02/2025"), "Rio de Janeiro", "Musical"));
        service.adicionarIncluso(new Evento("Bloco de Carnaval", d("21/02/2025"), "Bloco de rua no centro", 50.0, d("20/02/2025"), d("25/02/2025"), "Rio de Janeiro", "Carnaval"));

        service.adicionarIncluso(new Evento("Festival Gastronômico SP", d("23/02/2025"), "Degustação de pratos típicos", 120.0, d("20/02/2025"), d("25/02/2025"), "São Paulo", "Gastronomia"));
        service.adicionarIncluso(new Evento("Teatro Municipal", d("24/02/2025"), "Peça clássica", 90.0, d("20/02/2025"), d("25/02/2025"), "São Paulo", "Cultura"));

        service.adicionarIncluso(new Evento("Trilha Ecológica", d("24/02/2025"), "Trilha na mata atlântica", 80.0, d("20/02/2025"), d("25/02/2025"), "Florianópolis", "Aventura"));
        service.adicionarIncluso(new Evento("Surf Master", d("22/02/2025"), "Competição de surf", 0.0, d("20/02/2025"), d("25/02/2025"), "Florianópolis", "Esporte"));

        service.adicionarIncluso(new Evento("Carnaval Pelourinho", d("21/02/2025"), "Festa histórica no Pelô", 0.0, d("20/02/2025"), d("25/02/2025"), "Salvador", "Carnaval"));
        service.adicionarIncluso(new Evento("Trios Elétricos Barra", d("23/02/2025"), "Desfile de trios", 350.0, d("20/02/2025"), d("25/02/2025"), "Salvador", "Carnaval"));

        service.adicionarIncluso(new Evento("Feira de Artesanato BH", d("22/02/2025"), "Maior feira da América Latina", 0.0, d("20/02/2025"), d("25/02/2025"), "Belo Horizonte", "Cultura"));
        service.adicionarIncluso(new Evento("Visita ao Inhotim", d("23/02/2025"), "Museu de arte contemporânea", 60.0, d("20/02/2025"), d("25/02/2025"), "Belo Horizonte", "Arte"));

        service.adicionarIncluso(new Evento("Ópera de Arame", d("21/02/2025"), "Concerto filarmônico", 150.0, d("20/02/2025"), d("25/02/2025"), "Curitiba", "Musical"));
        service.adicionarIncluso(new Evento("Jardim Botânico", d("24/02/2025"), "Tour guiado estufa", 30.0, d("20/02/2025"), d("25/02/2025"), "Curitiba", "Turismo"));

        // =========================
        // Transportes (12)
        // =========================
        service.adicionarIncluso(new Transporte(200.0, d("20/02/2025"), "São Paulo", "aereo", "Rio de Janeiro", 2));
        service.adicionarIncluso(new Transporte(50.0, d("20/02/2025"), "Florianópolis", "rodoviario", "Rio de Janeiro", 6));
        service.adicionarIncluso(new Transporte(250.0, d("20/02/2025"), "Rio de Janeiro", "aereo", "São Paulo", 1));
        service.adicionarIncluso(new Transporte(40.0, d("20/02/2025"), "Belo Horizonte", "rodoviario", "São Paulo", 8));
        service.adicionarIncluso(new Transporte(150.0, d("20/02/2025"), "Salvador", "aereo", "Florianópolis", 1));
        service.adicionarIncluso(new Transporte(30.0, d("20/02/2025"), "Curitiba", "rodoviario", "Florianópolis", 12));
        service.adicionarIncluso(new Transporte(220.0, d("20/02/2025"), "Rio de Janeiro", "aereo", "Salvador", 2));
        service.adicionarIncluso(new Transporte(60.0, d("20/02/2025"), "São Paulo", "rodoviario", "Salvador", 24));
        service.adicionarIncluso(new Transporte(180.0, d("20/02/2025"), "Florianópolis", "aereo", "Belo Horizonte", 1));
        service.adicionarIncluso(new Transporte(45.0, d("20/02/2025"), "Curitiba", "rodoviario", "Belo Horizonte", 9));
        service.adicionarIncluso(new Transporte(190.0, d("20/02/2025"), "Salvador", "aereo", "Curitiba", 1));
        service.adicionarIncluso(new Transporte(35.0, d("20/02/2025"), "Rio de Janeiro", "rodoviario", "Curitiba", 10));
    }
}
