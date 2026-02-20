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
import model.Transporte;
import model.Usuario;
import model.Viagem;
import repo.Repo;
import service.RoteiroService;

public class Interface {
    public static Scanner s = new Scanner(System.in);

    private static String state = "";
    private static String sessao;

    private static HashMap<String, Usuario> usuarios;
    private static HashMap<String, Incluso> inclusos;
    private static HashMap<String, Viagem> viagens;
    private static RoteiroService roteiroService = new RoteiroService();

    private static File usuarios_arq = new File("storage/Usuarios.dat");
    private static File inclusos_arq = new File("storage/Inclusos.dat");
    private static File viagens_arq = new File("storage/Viagens.dat");

    public static void main(String[] args) {
        usuarios = Repo.desserialize(usuarios_arq);
        inclusos = Repo.desserialize(inclusos_arq);
        viagens = Repo.desserialize(viagens_arq);

        System.out.println("=================================");
        System.out.println("       Agência que viagem        ");
        System.out.println("=================================");

        while (!state.equals("sair")) {
            if (state.equals("")){
                sessao = "";
                System.out.println("\nRegistrar uma conta ou logar?");
                System.out.println("0. Registrar");
                System.out.println("1. Logar");
                System.out.println("999. Sair");

                int opcao = s.nextInt();
                s.nextLine();

                switch (opcao) {
                    case -1 -> state = "debug";
                    case 0 -> state = "registro";
                    case 1 -> state = "login";
                    case 999 -> state = "sair";
                    default -> System.out.println("Valor invalido!");
                }
            }
            else if (state.equals("debug")) PainelDebug();
            else if (state.equals("registro")) registro();
            else if (state.equals("login")) login();
            else if (state.equals("Logado")) {
                if (usuarios.get(sessao).getCargo().equals("admin")) PainelAdmin();
                else PainelCliente();
            }
        }

        Repo.serialize(inclusos_arq, inclusos);
        Repo.serialize(usuarios_arq, usuarios);
    }

    public static void listar_usuarios() {
        for (Map.Entry<String, Usuario> e: usuarios.entrySet()) 
            System.out.println("-Id " + e.getKey() + "\n-Usuario" + e.getValue());
    }

    public static void listar_inclusos() {
        for (Map.Entry<String, Incluso> e: inclusos.entrySet())
            System.out.println(e);
    }

    public static void registro() {
        try {
            System.out.println("\n- Registro");
            System.out.print("Nome: ");
            String n = s.nextLine();
            System.out.print("CPF: ");
            String c = s.nextLine();
            System.out.print("Nascimento(dd/MM/yyy): ");
            String d = s.nextLine();
            System.out.print("Endereco: ");
            String e = s.nextLine();
            System.out.print("Telefone: ");
            String t = s.nextLine();
            System.out.print("Senha: ");
            String p = s.nextLine();

            try {
                Usuario novo = new Usuario(n, c, d, e, t, p, "cliente", UUID.randomUUID().toString());
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
        System.out.print("Usuario: ");
        String user = s.nextLine();
        System.out.print("Senha: ");
        String pass = s.nextLine();

        String id = validar(user, pass);
        if (id.equals("")) {
          System.out.println("Usuario e senha invalidos!");
          state = "";
        }
        else {
            sessao = id;
            state = "Logado";
        }
    }

    public static String validar(String user, String pass) {
        for (Map.Entry<String, Usuario> e: usuarios.entrySet())
            if (e.getValue().getNome().equals(user) && e.getValue().getSenha().equals(pass)) 
                return e.getKey();
        return "";
    }

    public static void PainelCliente() {
        System.out.println("=================================");
        System.out.println("         Painel de viagem        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
        System.out.println("1. Checar roteiro de viagem");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        s.nextLine();
        switch (opcao) {
            case 1:
                criaViagem();
                break;
            case 999:
                state = "";
                break;

            default:
                break;
        }
    }

    public static void PainelAdmin() {
        System.out.println("=================================");
        System.out.println("          Painel de Admin        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
        System.out.println("1. Adicionar localidade");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 999:
                state = "";
                break;

            default:
                break;
        }
    }

    public static void PainelDebug() {
        System.out.println("=================================");
        System.out.println("         Painel de Debug         ");
        System.out.println("=================================");
        System.out.println("\n1. Listar Usuarios");
        System.out.println("2. Adicionar Admin");
        System.out.println("3. Listar Inclusos");
        System.out.println("4. Adicionar Transporte");
        System.out.println("5. Visualizar Dados Mockados");
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 1:
                listar_usuarios();
                break;

            case 2:
                Usuario admin = new Usuario("admin", "", "01/01/1999", "", "", "admin", "admin", "admin");
                usuarios.put(admin.getId(), admin);
                break;

            case 3:
                listar_inclusos();
                break;

            case 5:
                int h = 0, t = 0, ev = 0;
                HashMap<String, Integer> porCidade = new HashMap<>();
                for (Incluso inc : inclusos.values()) {
                    if (inc instanceof Hospedagem) h++;
                    else if (inc instanceof Transporte) t++;
                    else if (inc instanceof Evento) ev++;
                    porCidade.merge(inc.getcidade(), 1, Integer::sum);
                }
                System.out.println("\n=== Resumo dos Inclusos ===");
                System.out.println("Total: " + inclusos.size());
                System.out.println("Hospedagens: " + h + " | Transportes: " + t + " | Eventos: " + ev);
                for (Map.Entry<String, Integer> entry : porCidade.entrySet())
                    System.out.println("  " + entry.getKey() + ": " + entry.getValue());
                break;

            case 999:
                state = "";
                break;

            default:
                break;
        }
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
    }

    private static void mostrarList(List<Incluso> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("Opcao "+ i + "\n" + lista.get(i));
        }
    }

    private static List<Incluso> escolhaDisponiveis(List<Incluso> disponiveis, LocalDate Inicio, LocalDate Final) {
        List<Incluso> transportes = new ArrayList<>();
        List<Incluso> hospedagens = new ArrayList<>();
        List<Incluso> eventos = new ArrayList<>();
        List<Incluso> inclusosSelecionados = new ArrayList<>();

        for (Incluso i : disponiveis) {
            if (i instanceof Transporte) transportes.add(i);
            if (i instanceof Hospedagem) hospedagens.add(i);
            if (i instanceof Evento) eventos.add(i);
        }

        mostrarList(transportes);

        System.out.println("\nQual transporte você deseja?");
        int opcao = s.nextInt();
        inclusosSelecionados.add(transportes.get(opcao));

        mostrarList(hospedagens);

        System.out.println("\nQual Hospedagem você deseja?");
        opcao = s.nextInt();
        Hospedagem atual = (Hospedagem) hospedagens.get(opcao);
        atual.setDataInicio(Inicio); // Modificando a data de ultilização
        atual.setDataFim(Final);     // de acordo com necessidade do cliente
        inclusosSelecionados.add(atual);

        mostrarList(hospedagens);

        System.out.println("\nQual Evento você deseja?");
        opcao = s.nextInt();
        inclusosSelecionados.add(eventos.get(opcao));

        for (Incluso i : inclusosSelecionados) {
            System.out.println(i);
        }
        
        double precoTotal = 0.0;

        for (Incluso incluso : inclusosSelecionados) {
            precoTotal += incluso.calcularPrecoTotal();
        }

        System.out.println("\nPreço do roteiro: R$ " + precoTotal);
        System.out.println("\nDeseja finalizar este roteiro? (Não tem volta!)");
        System.out.println("\n1. Sim");
        System.out.println("\n2. Não");
        opcao = s.nextInt();
        if (opcao == 1) return inclusosSelecionados;
        else return null;
    }

    private static void criaViagem() {
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

        List<Incluso> inclusosDisponiveis = roteiroService.ChecarInclusosDisponiveis(inclusos.values(), dataInicio, dataFinal, destino);

        if (inclusosDisponiveis.isEmpty()) {
            System.out.println("Nenhum servico encontrado.");
            return;
        }

        List<Incluso> selecionados = escolhaDisponiveis(inclusosDisponiveis, dataInicio, dataFinal);
        if (selecionados == null) return;

        double precoTotal = 0.0;

        String titularId = sessao;

        Viagem viagem = new Viagem(
                titularId,
                origem,
                destino,
                precoTotal,
                dataInicio,
                dataFinal,
                selecionados
        );

        viagens.put(UUID.randomUUID().toString(), viagem);


        //TODO: Serializar viagem como arquivo roteiro com id do usuário
        
        System.out.println("\nViagem criada com sucesso!");
        System.out.println("Preco total: " + precoTotal);
    }
}
