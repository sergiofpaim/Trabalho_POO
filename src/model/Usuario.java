package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String cargo;
    private String senha;
    private LocalDate nascimento;

    public Usuario(String nome, String cpf, LocalDate dataNascimento,
            String endereco, String telefone,
            String senha, String cargo) {

        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.nascimento = dataNascimento;
        this.senha = senha;
        this.cargo = cargo;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getCargo() {
        return this.cargo;
    }

    public LocalDate getNascimento() {
        return this.nascimento;
    }

    public String getSenha() {
        return this.senha;
    }

    @Override
    public String toString() {
        return "\nNome: " + this.nome
                + "\nCPF: " + this.cpf
                + "\nEndereco: " + this.endereco
                + "\nTelefone: " + this.telefone
                + "\nNascimento: " + this.nascimento
                + "\nCargo: " + this.cargo;
    }
}
