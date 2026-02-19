import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Interface {
    public static Scanner s = new Scanner(System.in);
    public static int id_inclusos;
    private static String state = "";
    private static int sessao;
    private static HashMap<Integer, Usuario> usuarios;
    private static HashMap<Integer, Inclusos> inclusos;
    private static File usuarios_arq = new File("storage/Usuarios.dat");
    private static File inclusos_arq = new File("storage/Inclusos.dat");

    public static void main(String[] args) {
        //System.out.println("Executando em: " + System.getProperty("user.dir"));
        //System.out.println("Arquivo em: " + usuarios_arq.getAbsolutePath());
        if (usuarios_arq.exists() && usuarios_arq.length() != 0) {
            usuarios = (HashMap<Integer, Usuario>) desserialize(usuarios_arq);
            int maiorid = usuarios.keySet().stream().max(Integer::compare).orElse(0);
            Usuario.setContador(maiorid + 1);
        }
        else {
            usuarios = new HashMap<>();
            try { usuarios_arq.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }

        if (inclusos_arq.exists() && inclusos_arq.length() != 0) {
            inclusos = (HashMap<Integer, Inclusos>) desserialize(inclusos_arq);
            id_inclusos = inclusos.keySet().stream().max(Integer::compare).orElse(0);
        }
        else {
            inclusos = new HashMap<>();
            try { inclusos_arq.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }

        System.out.println("=================================");
        System.out.println("       Agência que viagem        ");
        System.out.println("=================================");

        while (!state.equals("sair")) {
            if (state.equals("")){
                sessao = -1;
                System.out.println("\nRegistrar uma conta ou logar?");
                System.out.println("0. Registrar");
                System.out.println("1. Logar");
                System.out.println("999. Sair");
                int opcao = s.nextInt();
                s.nextLine();
                if (opcao == -1) state = "debug";
                else if (opcao == 0) state = "registro";
                else if (opcao == 1) state = "login";
                else if (opcao == 999) state = "sair";
                else System.out.println("Valor invalido!");
            }
            else if (state.equals("debug")) PainelDebug();
            else if (state.equals("registro")) registro();
            else if (state.equals("login")) login();
            else if (state.equals("Logado")) {
                if (usuarios.get(sessao).getCargo().equals("admin")) PainelAdmin();
                else PainelCliente();
            }
        }

        serialize(inclusos_arq, inclusos);
        serialize(usuarios_arq, usuarios);
    }

    public static void listar_usuarios() {
        for (Map.Entry<Integer, Usuario> e: usuarios.entrySet()) 
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
                Usuario novo = new Usuario(n, c, d, e, t, p, "cliente");
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

        int id = validar(user, pass);
        if (id < 0) {
          System.out.println("Usuario e senha invalidos!");
          state = "";
        }
        else {
            sessao = id;
            state = "Logado";
        }
    }

    public static int validar(String user, String pass) {
        for (Map.Entry<Integer, Usuario> e: usuarios.entrySet())
            if (e.getValue().getNome().equals(user) && e.getValue().getSenha().equals(pass)) 
                return e.getKey();
        return -1;
    }

    public static void PainelCliente() {
        System.out.println("=================================");
        System.out.println("         Painel de viagem        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
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

    public static void PainelAdmin() {
        System.out.println("=================================");
        System.out.println("          Painel de Admin        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(sessao).getNome() + "?");
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
        System.out.println("999. Sair");

        int opcao = s.nextInt();
        switch (opcao) {
            case 1:
                listar_usuarios();
                break;

            case 2:
                Usuario admin = new Usuario("admin", "", "01/01/1999", "", "", "admin", "admin");
                usuarios.put(admin.getId(), admin);
                break;

            case 999:
                state = "";
                break;

            default:
                break;
        }
    }

    private static void serialize(File arquivo, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
            oos.writeObject(o);
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Object desserialize(File arquivo) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo));
            Object object = ois.readObject();
            ois.close();

            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
