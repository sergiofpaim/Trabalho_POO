
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import model.Evento;
import model.Hospedagem;
import model.Incluso;
import model.Roteiro;
import model.Transporte;
import model.Usuario;
import repo.Repo;
import service.RoteiroService;

public class Interface {

    public static Scanner s = new Scanner(System.in);

    private static String state = "";
    private static String sessao;

    private static HashMap<String, Usuario> usuarios;
    private static HashMap<String, Incluso> inclusos;
    private static HashMap<String, Roteiro> roteiros;

    private static final File USUARIOS_ARQ = new File("storage/Usuarios.dat");
    private static final File INCLUSOS_ARQ = new File("storage/Inclusos.dat");
    private static final File ROTEIROS_ARQ = new File("storage/Roteiros.dat");

    private static final RoteiroService roteiroService = new RoteiroService();

    public static void main(String[] args) {
        desserialize();

        System.out.println("=================================");
        System.out.println("       Agência que viagem        ");
        System.out.println("=================================");

        while (!state.equals("sair")) {
            switch (state) {
                case "" -> {
                    System.out.println("\nRegistrar uma conta ou logar?");
                    System.out.println("0. Registrar");
                    System.out.println("1. Logar");
                    System.out.println("999. Sair e salvar");
                    int opcao = s.nextInt();
                    s.nextLine();
                    switch (opcao) {
                        case -1 ->
                            state = "debug";
                        case 0 ->
                            state = "registro";
                        case 1 ->
                            state = "login";
                        case 999 ->
                            state = "sair";
                        default ->
                            System.out.println("Valor invalido!");
                    }
                }
                case "debug" ->
                    painelDebug();
                case "registro" ->
                    registro();
                case "login" ->
                    login();
                case "Logado" -> {
                    if (usuarios.get(sessao).getCargo().equals("admin")) {
                        painelAdmin();
                    } else {
                        painelCliente();
                    }
                }
                default -> {
                }
            }
        }

        serialize();
    }

    public static void registro() {
        try {
            System.out.println("\n- Registro");

            System.out.print("Nome: ");
            String nome = s.nextLine();

            System.out.print("CPF: ");
            String cpf = s.nextLine();

            System.out.print("Nascimento(dd/MM/yyy): ");
            String nascimento = s.nextLine();

            System.out.print("Endereço: ");
            String endereco = s.nextLine();

            System.out.print("Telefone: ");
            String telefone = s.nextLine();

            System.out.print("Senha: ");
            String senha = s.nextLine();

            try {
                Usuario novo = new Usuario(nome, cpf, nascimento, endereco, telefone, senha, "cliente",
                        UUID.randomUUID().toString());
                usuarios.put(novo.getId(), novo);
            } catch (Exception a) {
                System.out.println(a);
            }

            state = "";
        } catch (Exception e) {
            System.out.println("Entrada Invalida!");
        }
    }

    public static void login() {
        System.out.println("\n- Login");

        System.out.print("Usuário: ");
        String user = s.nextLine();

        System.out.print("Senha: ");
        String pass = s.nextLine();

        String id = Repo.validar(user, pass, usuarios);
        if (id.equals("")) {
            System.out.println("Usuário e senha invalidos!");
            state = "";
        } else {
            sessao = id;
            state = "Logado";
        }
    }

    public static void painelCliente() {
        System.out.println("=================================");
        System.out.println("         Painel de viagem        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
        System.out.println("1. Listar roteiros de viagem");
        System.out.println("2. Criar roteiro de viagem");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        s.nextLine();
        switch (opcao) {
            case 1 ->
                listarRoteiros();
            case 2 ->
                criaRoteiro();
            case 999 ->
                state = "";

            default -> {
            }
        }
    }

    public static void listarRoteiros() {
        if (roteiros.isEmpty()) {
            System.out.println("Não há roteiros cadastrados.");
            return;
        }

        boolean encontrou = false;

        for (Map.Entry<String, Roteiro> entry : roteiros.entrySet()) {
            if (entry.getValue().getTitularId().equals(sessao)) {
                String id = entry.getKey();
                Roteiro roteiro = entry.getValue();

                encontrou = true;

                System.out.println("======================================");
                System.out.println("ID do Roteiro: " + id);
                System.out.println("Titular: " + roteiro.getTitularId());
                System.out.println("Origem: " + roteiro.getOrigem());
                System.out.println("Destino: " + roteiro.getDestino());
                System.out.println("Preço: R$ " + String.format("%.2f", roteiro.getPreco()));
                System.out.println("Data de Início: " + roteiro.getDataComeco());
                System.out.println("Data de Fim: " + roteiro.getDataFim());

                List<Incluso> inclusosRoteiro = roteiro.getInclusos();
                if (inclusosRoteiro != null && !inclusosRoteiro.isEmpty()) {
                    System.out.println("======================================");
                    System.out.println("\nINCLUSOS:");
                    for (Incluso item : inclusosRoteiro) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("Itens Inclusos: Nenhum");
                }
                System.out.println("======================================\n");
            }
        }

        if (!encontrou) {
            System.out.println("Não há roteiros cadastrados para esta sessão.");
        }
    }

    private static void criaRoteiro() {
        System.out.println("=================================");
        System.out.println("           Criar Roteiro         ");
        System.out.println("=================================");

        System.out.print("Origem: ");
        String origem = s.nextLine();

        System.out.print("Destino: ");
        String destino = s.nextLine();

        System.out.print("Data inicio (dd/MM/yyyy): ");
        String dataInicioStr = s.nextLine();

        System.out.print("Data final (dd/MM/yyyy): ");
        String dataFinalStr = s.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataInicio = LocalDate.parse(dataInicioStr, formatter);
        LocalDate dataFinal = LocalDate.parse(dataFinalStr, formatter);

        List<Incluso> inclusosDisponiveis = roteiroService.ChecarInclusosDisponiveis(inclusos.values(), dataInicio,
                dataFinal, destino);

        if (inclusosDisponiveis.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        }

        List<Incluso> selecionados = escolheIncluso(inclusosDisponiveis, dataInicio, dataFinal);
        if (selecionados == null) {
            return;
        }

        double precoTotal = 0.0;

        for (Incluso incluso : selecionados) {
            precoTotal += incluso.calcularPrecoTotal();
        }

        Roteiro viagem = new Roteiro(
                sessao,
                origem,
                destino,
                precoTotal,
                dataInicio,
                dataFinal,
                selecionados);

        roteiros.put(UUID.randomUUID().toString(), viagem);

        System.out.println("\nViagem criada com sucesso!");
    }

    private static List<Incluso> escolheIncluso(List<Incluso> disponiveis, LocalDate Inicio, LocalDate Final) {
        List<Incluso> transportes = new ArrayList<>();
        List<Incluso> hospedagens = new ArrayList<>();
        List<Incluso> eventos = new ArrayList<>();
        List<Incluso> inclusosSelecionados = new ArrayList<>();

        for (Incluso i : disponiveis) {
            if (i instanceof Transporte) {
                transportes.add(i);
            }
            if (i instanceof Hospedagem) {
                hospedagens.add(i);
            }
            if (i instanceof Evento) {
                eventos.add(i);
            }
        }

        // ================= Transporte =================
        System.out.println("\nTransporte disponível:");
        mostrarLista(transportes);
        System.out.println("\nQual transporte você deseja?");
        int opcao = s.nextInt();
        if (opcao > 0 && opcao <= transportes.size()) {
            inclusosSelecionados.add(transportes.get(opcao - 1));
        }

        // ================= Hospedagem =================
        System.out.println("\nHospedagens disponíveis:");
        mostrarLista(hospedagens);
        System.out.println("\nQual hospedagem você deseja?");
        opcao = s.nextInt();
        if (opcao > 0 && opcao <= hospedagens.size()) {
            Hospedagem atual = (Hospedagem) hospedagens.get(opcao - 1);
            atual.setDataInicio(Inicio);
            atual.setDataFim(Final);
            inclusosSelecionados.add(atual);
        }

        // ================= Evento =================
        System.out.println("\nEventos disponíveis:");
        mostrarLista(eventos);
        System.out.println("\nQual evento você deseja?");
        opcao = s.nextInt();
        if (opcao > 0 && opcao <= eventos.size()) {
            inclusosSelecionados.add(eventos.get(opcao - 1));
        }

        // ================= Resumo =================
        System.out.println("\nResumo do roteiro:\n");
        if (inclusosSelecionados.isEmpty()) {
            System.out.println("Nenhum serviço selecionado.");
        } else {
            for (Incluso i : inclusosSelecionados) {
                System.out.println(i);
            }
        }

        double precoTotal = inclusosSelecionados.stream()
                .mapToDouble(Incluso::calcularPrecoTotal)
                .sum();

        System.out.println("\nPreço do roteiro: R$ " + precoTotal);
        System.out.println("\nDeseja finalizar este roteiro? (Não tem volta!)");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        opcao = s.nextInt();
        if (opcao == 1) {
            return inclusosSelecionados;
        } else {
            return null;
        }
    }

    public static void painelAdmin() {
        System.out.println("=================================");
        System.out.println("          Painel de Admin        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
        System.out.println("1. Criar inclusos");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 1 ->
                criaInclusos();
            case 999 ->
                state = "";

            default -> {
            }
        }
    }

    public static void criaInclusos() {
        String id;
        String localidade;
        String dataInicio;
        String dataFim;
        double preco;

        System.out.println("ID: ");
        id = s.nextLine();

        System.out.println("Localidade: ");
        localidade = s.nextLine();

        System.out.println("Preço: ");
        preco = s.nextDouble();
        s.nextLine();

        System.out.println("Data de Check-in: ");
        dataInicio = s.nextLine();

        System.out.println("Data de Check-out: ");
        dataFim = s.nextLine();

        System.out.println("Deseja criar \n[0] Hospedagem\n[1] Evento\n[2] Transporte");

        switch (s.nextInt()) {
            case 0 -> {
                try {
                    s.nextLine();
                    String nomeHotel;
                    int capacidade;

                    System.out.println("Nome do Hotel: ");
                    nomeHotel = s.nextLine();

                    System.out.println("Capacidade: ");
                    capacidade = s.nextInt();

                    Hospedagem hospedagem = new Hospedagem(
                            nomeHotel, preco, dataInicio, dataFim,
                            capacidade, localidade, id
                    );

                    inclusos.put(hospedagem.getId(), hospedagem);

                } catch (Exception e) {
                    System.out.println("Erro ao criar hospedagem");
                }
            }

            case 1 -> {
                try {
                    s.nextLine();
                    String nomeEvento;
                    String dataEvento;
                    String descricao;
                    String tema;

                    System.out.println("Nome do Evento: ");
                    nomeEvento = s.nextLine();

                    System.out.println("Data do Evento: ");
                    dataEvento = s.nextLine();

                    System.out.println("Descrição: ");
                    descricao = s.nextLine();

                    System.out.println("Tema: ");
                    tema = s.nextLine();

                    Evento evento = new Evento(
                            nomeEvento, dataEvento, descricao,
                            preco, dataInicio, dataFim,
                            localidade, tema, id
                    );

                    inclusos.put(evento.getId(), evento);

                } catch (Exception e) {
                    System.out.println("Erro ao criar evento");
                }
            }

            case 2 -> {
                try {
                    s.nextLine();
                    String tipoTransporte;
                    String destino;
                    int tempo;

                    System.out.println("Tipo de Transporte: ");
                    tipoTransporte = s.nextLine();

                    System.out.println("Destino: ");
                    destino = s.nextLine();

                    System.out.println("Tempo (em minutos): ");
                    tempo = s.nextInt();

                    Transporte transporte = new Transporte(
                            preco, dataInicio, localidade,
                            tipoTransporte, destino, tempo, id
                    );

                    inclusos.put(transporte.getId(), transporte);

                } catch (Exception e) {
                    System.out.println("Erro ao criar transporte");
                }
            }

            default ->
                System.out.println("Valor inválido!");
        }

        System.out.println("Objeto criado com sucesso!");
    }

    public static void apagaInclusos() {

        System.out.println("Deseja apagar \n[0] Hospedagem\n[1] Evento\n[2] Transporte");

        List<Incluso> listaTransportes = new ArrayList<>();
        List<Incluso> listaHospedagens = new ArrayList<>();
        List<Incluso> listaEventos = new ArrayList<>();

        for (Incluso incluso : inclusos.values()) {
            if (incluso instanceof Transporte) {
                listaTransportes.add(incluso);
            }
            if (incluso instanceof Hospedagem) {
                listaHospedagens.add(incluso);
            }
            if (incluso instanceof Evento) {
                listaEventos.add(incluso);
            }
        }

        try {
            switch (s.nextInt()) {

                case 0 -> {
                    mostrarLista(listaHospedagens);
                    System.out.println("\nQual hospedagem você deseja apagar? (Digite o ID)");
                    String idSelecionado = s.next();
                    inclusos.remove(idSelecionado);
                }

                case 1 -> {
                    mostrarLista(listaEventos);
                    System.out.println("\nQual evento você deseja apagar? (Digite o ID)");
                    String idSelecionado = s.next();
                    inclusos.remove(idSelecionado);
                }

                case 2 -> {
                    mostrarLista(listaTransportes);
                    System.out.println("\nQual transporte você deseja apagar? (Digite o ID)");
                    String idSelecionado = s.next();
                    inclusos.remove(idSelecionado);
                }

                default ->
                    System.out.println("Valor inválido!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao apagar incluso");
        }
    }

    private static void serialize() {
        Repo.serialize(USUARIOS_ARQ, usuarios);
        Repo.serialize(INCLUSOS_ARQ, inclusos);
        Repo.serialize(ROTEIROS_ARQ, roteiros);
    }

    private static void desserialize() {
        usuarios = Repo.desserialize(USUARIOS_ARQ);
        inclusos = Repo.desserialize(INCLUSOS_ARQ);
        roteiros = Repo.desserialize(ROTEIROS_ARQ);
    }

    public static void listarUsuarios() {
        for (Map.Entry<String, Usuario> e : usuarios.entrySet()) {
            System.out.println("-Id " + e.getKey() + "\n-Usuario" + e.getValue());
        }
    }

    public static void listarInclusos() {
        for (Map.Entry<String, Incluso> e : inclusos.entrySet()) {
            System.out.println(e);
        }
    }

    private static void mostrarLista(List<Incluso> lista) {
        System.out.println("0 - Nenhum");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }
    }

    public static void painelDebug() {
        System.out.println("=================================");
        System.out.println("         Painel de Debug         ");
        System.out.println("=================================");
        System.out.println("\n1. Listar Usuarios");
        System.out.println("2. Adicionar Admin");
        System.out.println("3. Listar Inclusos");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 1 ->
                listarUsuarios();

            case 2 -> {
                Usuario admin = new Usuario("admin", "", "01/01/1999", "", "", "admin", "admin", "admin");
                usuarios.put(admin.getId(), admin);
            }

            case 3 ->
                listarInclusos();

            case 999 ->
                state = "";

            default -> {
            }
        }
    }
}
