import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Interface {
    public Scanner s = System.in;
    private static String state = "";
    private static int usuario = ;
    private static HashMap<int, Usuario> usuarios = new HashMap<>();
    private static sair = 0;

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("       Agência que viagem        ");
        System.out.println("=================================");

        while (sair != 1) {
            if (state.equals("")){
                System.out.println("Registrar uma conta ou logar?");
                System.out.println("0. Registrar");
                System.out.println("1. Logar")
                int opcao = s.nextInt();
                if (opcao == 0) state = "registro";
                if (opcao == 1) state = "login";
                else System.out.println("Valor invalido!");
            }
            else if (state.equals("registro")) registro();
            else if (state.equals("login")) login();
        }
    }

    public void registro() {
        
    }

    public void login() {
        System.out.println("Login:");
        System.out.print("Usuario: ");
        String user = s.nextLine();
        System.out.print("Senha: ");
        String pass = s.nextLine();

        //id = validar(user, pass);
        //if (id == 0) {
        //  System.out.println("Usuario e senha invalidos!");
        //  state = "";
        //}
        //else usuario = id;
    }

}
