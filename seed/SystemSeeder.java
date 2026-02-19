
import java.io.File;
import java.util.HashMap;
import model.Hospedagem;
import model.Evento;
import model.Transporte;
import model.Incluso;
import repo.Repo;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SystemSeeder {
    
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Random random = new Random(42);

    public static void main(String[] args) {
        
        File arquivo = new File("c:\\Users\\sergi_qla3gir\\source\\repos\\Trabalho_POO\\storage\\Inclusos.dat");
        HashMap<String, Incluso> inclusos = new HashMap<>();

        System.out.println("Generating mock data (1000+ items)...");
        System.out.println("Period: 19/02/2025 - 25/02/2025");

        String[] cidades = {"Paris", "Nova York", "Tokyo", "Rio de Janeiro", "Londres"};

        // Dates: 19/02/2025 to 25/02/2025 (7 days)
        LocalDate inicioGeral = LocalDate.of(2025, 2, 19);
        LocalDate fimGeral = LocalDate.of(2025, 2, 25);

        // Hospedagens: parceria longa (1 ano antes ate 1 ano depois)
        LocalDate parceriaInicio = inicioGeral.minusYears(1); // 19/02/2024
        LocalDate parceriaFim = fimGeral.plusYears(1);        // 25/02/2026

        String[][] hoteis = {
            // {nome, cidade, preco}
            {"Hotel Lumière",          "Paris",           "350"},
            {"Le Petit Palais",        "Paris",           "520"},
            {"Maison de la Tour",      "Paris",           "280"},
            {"Résidence Champs",       "Paris",           "410"},
            {"Auberge du Marais",      "Paris",           "195"},
            {"The Manhattan Grand",    "Nova York",       "480"},
            {"Brooklyn Heights Inn",   "Nova York",       "320"},
            {"Central Park Lodge",     "Nova York",       "650"},
            {"Times Square Suites",    "Nova York",       "550"},
            {"Harlem Heritage Hotel",  "Nova York",       "210"},
            {"Sakura Ryokan",          "Tokyo",           "300"},
            {"Shibuya Tower Hotel",    "Tokyo",           "420"},
            {"Asakusa Traditional",    "Tokyo",           "180"},
            {"Ginza Premium Stay",     "Tokyo",           "600"},
            {"Shinjuku Capsule Plus",  "Tokyo",           "90"},
            {"Copacabana Palace",      "Rio de Janeiro",  "700"},
            {"Ipanema Beach Hotel",    "Rio de Janeiro",  "450"},
            {"Hotel Santa Teresa",     "Rio de Janeiro",  "380"},
            {"Lapa Hostel Premium",    "Rio de Janeiro",  "120"},
            {"Barra da Tijuca Resort", "Rio de Janeiro",  "530"},
            {"The Royal Westminster",  "Londres",         "550"},
            {"Camden Town Lodge",      "Londres",         "280"},
            {"Kensington Suites",      "Londres",         "620"},
            {"Soho Boutique Hotel",    "Londres",         "400"},
            {"Thames View Inn",        "Londres",         "340"},
        };

        // --- HOSPEDAGENS (25 hoteis, parceria de ~2 anos) ---
        for (String[] h : hoteis) {
            Hospedagem hosp = new Hospedagem(
                h[0],                                    // nome hotel
                Double.parseDouble(h[2]),                // preco/dia
                parceriaInicio.format(fmt),               // checkIn (parceria)
                parceriaFim.format(fmt),                  // checkOut (parceria)
                3 + random.nextInt(5),                   // capacidade 3-7
                h[1],                                    // cidade
                UUID.randomUUID().toString()
            );
            inclusos.put(hosp.getId(), hosp);
        }
        System.out.println("Hospedagens criadas: " + hoteis.length);

        // --- TRANSPORTES ---
        // Para cada cidade, para cada dia (19-25), vários voos/transportes
        String[][] transporteTemplates = {
            // {tipo, tempoBase}
            {"aereo", "8"},
            {"aereo", "10"},
            {"aereo", "12"},
            {"rodoviario", "6"},
        };

        int transporteCount = 0;
        for (String cidade : cidades) {
            for (int dia = 0; dia <= 6; dia++) { // 19 a 25 fev
                LocalDate data = inicioGeral.plusDays(dia);
                String dataStr = data.format(fmt);

                for (String[] tmpl : transporteTemplates) {
                    // Varias opcoes de horario/preco por template
                    for (int v = 0; v < 5; v++) {
                        double preco;
                        int tempo = Integer.parseInt(tmpl[1]) + random.nextInt(4);
                        if (tmpl[0].equals("aereo")) {
                            preco = 400 + random.nextInt(800);
                        } else {
                            preco = 80 + random.nextInt(200);
                        }

                        Transporte t = new Transporte(
                            preco,
                            dataStr,
                            cidade,
                            tmpl[0],
                            cidade,
                            tempo,
                            UUID.randomUUID().toString()
                        );
                        inclusos.put(t.getId(), t);
                        transporteCount++;
                    }
                }
            }
        }
        System.out.println("Transportes criados: " + transporteCount);

        // --- EVENTOS ---
        String[][] eventoTemplates = {
            // {nomeBase, descricao, tema, precoBase}
            {"Show Musical",           "Apresentação musical ao vivo",    "Musica",     "120"},
            {"Exposição de Arte",      "Mostra de arte contemporânea",   "Arte",       "80"},
            {"Feira Gastronômica",     "Degustação de comidas típicas",  "Gastronomia","60"},
            {"Tour Histórico",         "Passeio guiado por pontos históricos", "Historia", "90"},
            {"Festival de Cinema",     "Exibição de filmes independentes","Cinema",    "70"},
            {"Workshop de Fotografia", "Aula prática de fotografia",     "Educacao",   "150"},
            {"Stand Up Comedy",        "Show de humor ao vivo",          "Entretenimento","100"},
            {"Feira de Tecnologia",    "Exposição de inovações tech",    "Tecnologia", "110"},
            {"Corrida de Rua",         "Competição esportiva",           "Esporte",    "40"},
            {"Degustação de Vinhos",   "Harmonização de vinhos",         "Gastronomia","200"},
            {"Concerto Sinfônico",     "Orquestra ao vivo",              "Musica",     "180"},
            {"Peça de Teatro",         "Espetáculo teatral",             "Teatro",     "130"},
        };

        int eventoCount = 0;
        for (String cidade : cidades) {
            for (int dia = 0; dia <= 6; dia++) {
                LocalDate data = inicioGeral.plusDays(dia);
                String dataStr = data.format(fmt);

                // 4-6 eventos por dia por cidade
                int numEventos = 4 + random.nextInt(3);
                for (int e = 0; e < numEventos; e++) {
                    String[] tmpl = eventoTemplates[random.nextInt(eventoTemplates.length)];
                    double preco = Double.parseDouble(tmpl[3]) + random.nextInt(50) - 25;
                    if (preco < 0) preco = 10;

                    String nomeEvento = tmpl[0] + " em " + cidade + " - " + dataStr;
                    
                    Evento ev = new Evento(
                        nomeEvento,
                        dataStr,          // data do evento
                        tmpl[1],          // descricao
                        preco,            // preco
                        dataStr,          // dataCStr (inicio disponibilidade)
                        dataStr,          // dataFStr (fim disponibilidade)
                        cidade,           // local
                        tmpl[2],          // tema
                        UUID.randomUUID().toString()
                    );
                    inclusos.put(ev.getId(), ev);
                    eventoCount++;
                }
            }
        }
        System.out.println("Eventos criados: " + eventoCount);

        // Completar ate passar de 1000 se necessario
        while (inclusos.size() < 1000) {
            String cidade = cidades[random.nextInt(cidades.length)];
            LocalDate d = inicioGeral.plusDays(random.nextInt(7)); // 19-25 fev
            Transporte t = new Transporte(
                300 + random.nextInt(500),
                d.format(fmt),
                cidade,
                "aereo",
                cidade,
                8 + random.nextInt(6),
                UUID.randomUUID().toString()
            );
            inclusos.put(t.getId(), t);
        }

        Repo.serialize(arquivo, inclusos);
        System.out.println("\nTotal de Inclusos gerados: " + inclusos.size());
        System.out.println("Serialização completa em: " + arquivo.getAbsolutePath());

        System.out.println("\n--- Localidades Disponíveis ---");
        for (String c : cidades) {
            System.out.println("- " + c);
        }
    }
}
