
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Evento;
import model.Hospedagem;
import model.Incluso;
import model.Roteiro;
import model.Transporte;
import model.Usuario;
import service.RoteiroService;

public class Interface {

    public static Scanner s = new Scanner(System.in);

    private static String state = "";
    private static String sessao;

    private static final RoteiroService service = new RoteiroService();

    public static void main(String[] args) {
        service.carregarDados();

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
                    Usuario usuario = service.buscarUsuarioPorId(sessao);
                    if (usuario != null && usuario.getCargo().equals("admin")) {
                        painelAdmin();
                    } else {
                        painelCliente();
                    }
                }
                default -> {
                }
            }
        }

        service.salvarTudo();
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
                Usuario novo = new Usuario(nome, cpf, nascimento, endereco, telefone, senha, "cliente");
                service.adicionarUsuario(novo);
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

        String id = service.validar(user, pass);
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

        Usuario usuario = service.buscarUsuarioPorId(sessao);
        System.out.println("\nComo podemos te ajudar " + usuario.getNome() + "?");
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

    public static void mostrarRoteiros(List<Roteiro> roteiros) {
        for (Roteiro roteiro : roteiros) {
            System.out.println("======================================");
            System.out.println("Titular: " + service.buscarUsuarioPorId(roteiro.getTitularId()).getNome());
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

    public static void listarRoteiros() {
        List<Roteiro> meusRoteiros = service.buscarRoteirosPorTitular(sessao);

        if (meusRoteiros.isEmpty()) {
            System.out.println("Não há roteiros cadastrados para esta sessão.");
            return;
        }

        mostrarRoteiros(meusRoteiros);
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

        List<Incluso> inclusosDisponiveis = service.checarInclusosDisponiveis(dataInicio, dataFinal, destino);

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

        service.adicionarRoteiro(viagem);

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

        Usuario usuario = service.buscarUsuarioPorId(sessao);
        System.out.println("\nComo podemos te ajudar " + usuario.getNome() + "?");
        System.out.println("1. Listar inclusos");
        System.out.println("2. Criar inclusos");
        System.out.println("3. Remover inclusos");
        System.out.println("4. Listar usuarios");
        System.out.println("5. Remover usuario");
        System.out.println("6. Relatorio de vendas");
        System.out.println("7. Modificar taxa de lucro da Agencia");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        s.nextLine();
        switch (opcao) {
            case 1 ->
                listarInclusos();
            case 2 ->
                criaInclusos();
            case 3 ->
                apagaInclusos();
            case 4 ->
                listarUsuarios();
            case 5 ->
                removerUsuario();
            case 6 -> 
                gerarRelatorioClientes();
            case 7 ->
                modificarTaxaLucro();
            case 999 ->
                state = "";
            default -> {
            }
        }
    }

    public static void modificarTaxaLucro() {
        System.out.println("A taxa atual é de: " + service.getTaxaLucro());
        System.out.print("Qual será a nova taxa de lucro: ");
        try {
            double taxa = s.nextDouble();
            service.setTaxaLucro(taxa);
        } catch (Exception e) {
        System.out.print("Valor Invalido");
        }
    }

    public static void gerarRelatorioClientes() {
        List<Usuario> users = service.getUsuarios();

        for (Usuario u : users) {
            List<Roteiro> roteiros = service.buscarRoteirosPorTitular(u.getId());
            mostrarRoteiros(roteiros);
        }
    }

    public static void removerUsuario() {
        listarUsuarios();

        System.out.println("Qual o id do Usuario: ");
        String id = s.nextLine();
        boolean resultado = service.removerUsuarioPorId(id);
        if(resultado) System.out.println("Usuario removido com sucesso!");
        else System.out.println("Código do usuario não encontrado.");
    }

    public static void criaInclusos() {
        String localidade;
        String dataInicio;
        String dataFim;
        double preco;

        System.out.println("Localidade: ");
        localidade = s.nextLine();

        System.out.println("Taxa preço: ");
        preco = s.nextDouble();
        s.nextLine();

        System.out.println("Data de início: ");
        dataInicio = s.nextLine();

        System.out.println("Data de fim: ");
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
                            capacidade, localidade);

                    service.adicionarIncluso(hospedagem);

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
                            localidade, tema);

                    service.adicionarIncluso(evento);

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

                    System.out.println("Tempo (em horas): ");
                    tempo = s.nextInt();

                    Transporte transporte = new Transporte(
                            preco, dataInicio, localidade,
                            tipoTransporte, destino, tempo);

                    service.adicionarIncluso(transporte);

                } catch (Exception e) {
                    System.out.println("Erro ao criar transporte");
                }
            }

            default ->
                System.out.println("Valor inválido!");
        }

        System.out.println("Objeto criado com sucesso!");
        service.salvarInclusos();
    }

    public static void apagaInclusos() {

        System.out.println("Deseja apagar \n[0] Hospedagem\n[1] Evento\n[2] Transporte");

        List<Incluso> listaTransportes = new ArrayList<>();
        List<Incluso> listaHospedagens = new ArrayList<>();
        List<Incluso> listaEventos = new ArrayList<>();

        s.nextLine();

        for (Incluso incluso : service.getInclusos()) {
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
                    service.removerInclusoPorId(idSelecionado);
                }

                case 1 -> {
                    mostrarLista(listaEventos);
                    System.out.println("\nQual evento você deseja apagar? (Digite o ID)");
                    String idSelecionado = s.next();
                    service.removerInclusoPorId(idSelecionado);
                }

                case 2 -> {
                    mostrarLista(listaTransportes);
                    System.out.println("\nQual transporte você deseja apagar? (Digite o ID)");
                    String idSelecionado = s.next();
                    service.removerInclusoPorId(idSelecionado);
                }

                default ->
                    System.out.println("Valor inválido!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao apagar incluso");
        }
    }

    public static void listarUsuarios() {
        for (Usuario u : service.getUsuarios()) {
            System.out.println("-Id " + u.getId() + "\n-Usuario" + u);
        }
    }

    public static void listarInclusos() {
        for (Incluso i : service.getInclusos()) {
            System.out.println(i);
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
        System.out.println("2. Gerar Seed Data (Usuarios e Inclusos)");
        System.out.println("3. Listar Inclusos");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 1 ->
                listarUsuarios();

            case 2 -> {
                service.seedData();
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
