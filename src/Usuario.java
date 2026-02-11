import java.time.LocalDate;

public class Usuario {
    private static int contador = 0;
    private int id;
    private String nome, cpf, telefone, endereco, cargo;
    private LocalDate nascimento;

    private void setId() { id = contador++; }
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
    public void setNascimento(LocalDate n) { nascimento = n; }

    public Usuario(String nome, String cpf, String end, String tele, LocalDate data, String role) {
        setNome(nome);
        setCPF(cpf);
        setEndereco(end);
        setTelefone(tele);
        setNascimento(data);
        setCargo(role);
    }


}
