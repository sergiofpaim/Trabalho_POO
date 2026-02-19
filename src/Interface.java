
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import model.Hospedagem;
import model.Incluso;
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
                addViagem();
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

            case 999:
                state = "";
                break;

            default:
                break;
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
    }

   private static void addViagem() {
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

        System.out.print("Orcamento maximo: ");
        double orcamento = s.nextDouble();
        s.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataInicio = LocalDate.parse(dataInicioStr, formatter);
        LocalDate dataFinal = LocalDate.parse(dataFinalStr, formatter);

        List<Incluso> inclusosDisponiveis = roteiroService.ChecarInclusosDisponiveis(inclusos.values(), dataInicio, dataFinal, destino, orcamento);

        if (inclusosDisponiveis.isEmpty()) {
            System.out.println("Nenhum servico encontrado.");
            return;
        }

        System.out.println("\nServicos encontrados:");
        for (Incluso incluso : inclusosDisponiveis) {
            System.out.println(incluso);
        }

        double precoTotal = 0.0;

        for (Incluso incluso : inclusosDisponiveis) {
            precoTotal += incluso.calcularPrecoTotal();
        }

        String titularId = sessao;

        Viagem viagem = new Viagem(
                titularId,
                origem,
                destino,
                precoTotal,
                dataInicio,
                dataFinal,
                inclusosDisponiveis
        );

        viagens.put(UUID.randomUUID().toString(), viagem);

        System.out.println("\nViagem criada com sucesso!");
        System.out.println("Preco total: " + precoTotal);
    }
}
