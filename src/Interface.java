
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

    private static File usuarios_arq = new File("storage/Usuarios.dat");
    private static File inclusos_arq = new File("storage/Inclusos.dat");
    private static File roteiros_arq = new File("storage/Roteiros.dat");

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

                List<Incluso> inclusos = roteiro.getInclusos();
                if (inclusos != null && !inclusos.isEmpty()) {
                    System.out.println("======================================");
                    System.out.println("\nINCLUSOS:");
                    for (Incluso item : inclusos) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("Itens Inclusos: Nenhum");
                }
                System.out.println("======================================\n");
            }
        }

        if (!encontrou)
            System.out.println("Não há roteiros cadastrados para esta sessão.");
    }

    private static void criaRoteiro() {
        System.out.println("=================================");
        System.out.println("         Adicionar Viagem        ");
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

    public static void CriaInclusos(){
        String id, loc, cIn, cOut;
        double preco;
        System.out.println("ID: ");
        id = s.nextLine();
        System.out.println("Cidade: ");
        loc = s.nextLine();
        System.out.println("Preço: ");
        preco = s.nextDouble();
        System.out.println("Data de CheckIn: ");
        cIn = s.nextLine();
        System.out.println("Data de CheckOut: ");
        cOut = s.nextLine();
        System.out.println("Deseja criar \n[0] Hospedagem\n[1] Eventos\n[2] Transporte");
        switch(s.nextInt()){
            case 0:
                try{
                    String h;
                    int cap;
                    System.out.println("Nome do Hotel: ");
                    h = s.nextLine();
                    System.out.println("Capacidade: ");
                    cap = s.nextInt();
                    Hospedagem hosp = new Hospedagem(h,  preco,  cIn, cOut, cap, loc, id);
                    inclusos.put(hosp.getId(), hosp);
                } catch (Exception e){
                    System.out.println("Erro ao criar hospedagem");
                }
                break;
            case 1:
                try{
                    String n;
                    String data;
                    String desc;
                    String tema;
                    System.out.println("Nome do Evento: ");
                    n = s.nextLine();
                    System.out.println("Data: ");
                    data = s.nextLine();
                    System.out.println("Descricao: ");
                    desc = s.nextLine();
                    System.out.println("Tema: ");
                    tema = s.nextLine();
                    Evento ev = new Evento( n,  data,  desc,  preco,  cIn,  cOut,  loc,  tema,  id);
                    inclusos.put(ev.getId(), ev);
                } catch (Exception e) {
                    System.out.println("Erro ao criar hospedagem");
                }
                break;
            case 2:
                try{
                    String tipoTransporte;
                    String destino;
                    int tempo;
                    System.out.println("Tipo de Transporte: ");
                    tipoTransporte = s.nextLine();
                    System.out.println("Destino: ");
                    destino = s.nextLine();
                    System.out.println("Tempo: ");
                    tempo = s.nextInt();
                    Transporte trnsp = new Transporte(preco,  cIn,  loc,  tipoTransporte,  destino, tempo, id);
                    inclusos.put(trnsp.getId(), trnsp);
                } catch (Exception e) {
                    System.out.println("Erro ao criar hospedagem");
                }
                break;
            default:
                System.out.println("Valor invalido!");
                break;
        }
        System.out.println("Objeto criado com sucesso!");
    }

    public static void ApagaInclusos(){
        System.out.println("Deseja apagar \n[0] Hospedagem\n[1] Eventos\n[2] Transporte");
        List<Incluso> transportes = new ArrayList<>();
        List<Incluso> hospedagens = new ArrayList<>();
        List<Incluso> eventos = new ArrayList<>();

        for (Incluso i : disponiveis) {
            if (i instanceof Transporte) transportes.add(i);
            if (i instanceof Hospedagem) hospedagens.add(i);
            if (i instanceof Evento) eventos.add(i);
        }
        try{
            switch(s.nextInt()) {
                case 0:
                    mostrarList(hospedagens);
                    System.out.println("\nQual hospedagem você deseja apagar? (Digite o ID)");
                    int opcao = s.nextInt();
                    inclusos.remove(opcao);
                    break;
                case 1:
                    mostrarList(eventos);
                    System.out.println("\nQual evento você deseja apagar? (Digite o ID)");
                    int opcao = s.nextInt();
                    inclusos.remove(opcao);
                    break;
                case 2:
                    mostrarList(transportes);
                    System.out.println("\nQual transporte você deseja apagar? (Digite o ID)");
                    int opcao = s.nextInt();
                    inclusos.remove(opcao);
                    break;
            }
        } catch (Exception e){
            System.out.println("Erro ao apagar hospedagem");
        }
    }

    public static void CadastraHospedagem(){
        String h, loc, cIn, cOut;
        double preco;
        int cap;
        try{
            System.out.println("Nome do Hotel: ");
            h = s.nextLine();
            System.out.println("Preco da diária: ");
            preco = s.nextDouble();
            System.out.println("Local do Hotel: ");
            loc = s.nextLine();
            System.out.println("Capacidade de reservas simultaneas: ");
            cap = s.nextInt();
            System.out.println("Data de CheckIn: ");
            cIn = s.nextLine();
            System.out.println("Data de CheckOut: ");
            cOut = s.nextLine();
            Hospedagem hosp = new Hospedagem(h, preco, cIn, cOut, cap, loc, UUID.randomUUID().toString());
            inclusos.put(hosp.getId(), hosp);
        } catch (Exception a) {
            System.out.println("Hospedagem nao cadastrada!");
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
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 999 ->
                state = "";

            default -> {
            }
        }
    }

    public static void cadastraHospedagem() {
        try {
            System.out.println("Nome do hotel: ");
            String nomeHotel = s.nextLine();

            System.out.println("Preço da diária: ");
            double precoDiaria = s.nextDouble();
            s.nextLine();

            System.out.println("Local do hotel: ");
            String localHotel = s.nextLine();

            System.out.println("Capacidade de reservas simultâneas: ");
            int capacidade = s.nextInt();
            s.nextLine();

            System.out.println("Data de Check-in: ");
            String checkIn = s.nextLine();

            System.out.println("Data de Check-out: ");
            String checkOut = s.nextLine();

            Hospedagem hosp = new Hospedagem(
                    nomeHotel, precoDiaria, checkIn, checkOut, capacidade, localHotel, UUID.randomUUID().toString());

            inclusos.put(hosp.getId(), hosp);
            System.out.println("Hospedagem cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Hospedagem não cadastrada! Erro: " + e.getMessage());
            s.nextLine();
        }
    }

    private static void serialize() {
        Repo.serialize(usuarios_arq, usuarios);
        Repo.serialize(inclusos_arq, inclusos);
        Repo.serialize(roteiros_arq, roteiros);
    }

    private static void desserialize() {
        usuarios = Repo.desserialize(usuarios_arq);
        inclusos = Repo.desserialize(inclusos_arq);
        roteiros = Repo.desserialize(roteiros_arq);
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
