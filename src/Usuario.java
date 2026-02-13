import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int contador = 0;
    private int id;
    private String nome, cpf, telefone, endereco, cargo, senha;
    private LocalDate nascimento;

    private void setId(int i) { id = i; }
    public int getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String n) { nome = n; }

    public String getCPF() { return cpf; }
    public void setCPF(String c) { cpf = c; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String t) { telefone = t; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String e) { endereco = e; }

    public String getCargo() { return cargo; }
    public void setCargo(String c) { cargo = c; }

    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(String n) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        nascimento = LocalDate.parse(n, formato);
    }

    public String getSenha() { return senha; }
    public void setSenha(String s) { senha = s; }

    public static void setContador(int c) { contador = c; }

    public Usuario(String nome, String cpf, String data, String end, String tele, String sen, String role) {
        setNome(nome);
        setCPF(cpf);
        setEndereco(end);
        setTelefone(tele);
        setNascimento(data);
        setSenha(sen);
        setCargo(role);
        setId(contador);
        contador++;
    }

    public String toString() {
        return "\nNome: " + nome + "\nCpf: " + cpf + "\nEndereco: " + endereco + "\nTelefone: " + telefone + "\nNascimento: " + nascimento.toString() + "\nCargo: " + cargo;
    }
}
