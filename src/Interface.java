import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Interface {
    public static Scanner s = new Scanner(System.in);
    private static String state = "";
    private static int usuario;
    private static HashMap<Integer, Usuario> usuarios = new HashMap<>();
    private static int sair = 0;

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("       Agência que viagem        ");
        System.out.println("=================================");

        while (sair != 1) {
            if (state.equals("")){
                System.out.println("\nRegistrar uma conta ou logar?");
                System.out.println("0. Registrar");
                System.out.println("1. Logar");
                int opcao = s.nextInt();
                s.nextLine();
                if (opcao == -1) listar_usuarios();
                else if (opcao == 0) state = "registro";
                else if (opcao == 1) state = "login";
                else System.out.println("Valor invalido!");
            }
            else if (state.equals("registro")) registro();
            else if (state.equals("login")) login();
            else if (state.equals("Logado")) Painel();
        }
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
                usuarios.put(Usuario.getContador(), new Usuario(n, c, d, e, t, p, "cliente"));
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
            usuario = id;
            state = "Logado";
        }
    }

    public static int validar(String user, String pass) {
        for (Map.Entry<Integer, Usuario> e: usuarios.entrySet())
            if (e.getValue().getNome().equals(user) && e.getValue().getSenha().equals(pass)) 
                return e.getKey();
        return -1;
    }

    public static void Painel() {
        System.out.println("=================================");
        System.out.println("         Painel de viagem        ");
        System.out.println("=================================");
        System.out.println("\nComo podemos te ajudar " + usuarios.get(usuario).getNome() + "?");

        s.nextLine();
    }
}
