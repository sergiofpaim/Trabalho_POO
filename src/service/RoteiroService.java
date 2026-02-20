package service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Incluso;
import model.Roteiro;
import model.Usuario;
import repo.Repo;
import seed.DataSeeder;

public class RoteiroService {

    private List<Usuario> usuarios;
    private List<Incluso> inclusos;
    private List<Roteiro> roteiros;

    private static final File USUARIOS_ARQ = new File("storage/Usuarios.dat");
    private static final File INCLUSOS_ARQ = new File("storage/Inclusos.dat");
    private static final File ROTEIROS_ARQ = new File("storage/Roteiros.dat");

    public RoteiroService() {
        this.usuarios = new ArrayList<>();
        this.inclusos = new ArrayList<>();
        this.roteiros = new ArrayList<>();
    }

    // Usuarios 
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public String validar(String user, String pass) {
        for (Usuario u : usuarios) {
            if (u.getNome().equals(user) && u.getSenha().equals(pass)) {
                return u.getId();
            }
        }
        return "";
    }

    // Inclusos 
    public List<Incluso> getInclusos() {
        return inclusos;
    }

    public void adicionarIncluso(Incluso incluso) {
        inclusos.add(incluso);
    }

    public void removerInclusoPorId(String id) {
        inclusos.removeIf(i -> i.getId().equals(id));
    }

    public List<Incluso> checarInclusosDisponiveis(LocalDate dataInicio, LocalDate dataFinal, String destino) {
        List<Incluso> inclusosDisponiveis = new ArrayList<>();

        for (Incluso incluso : inclusos) {
            if (incluso.checarDisponibilidade(dataInicio, dataFinal, destino)) {
                inclusosDisponiveis.add(incluso);
            }
        }
        return inclusosDisponiveis;
    }

    // Roteiros 
    public List<Roteiro> getRoteiros() {
        return roteiros;
    }

    public void adicionarRoteiro(Roteiro roteiro) {
        roteiros.add(roteiro);
    }

    public List<Roteiro> buscarRoteirosPorTitular(String titularId) {
        List<Roteiro> resultado = new ArrayList<>();
        for (Roteiro r : roteiros) {
            if (r.getTitularId().equals(titularId)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // Serialização 
    public void carregarDados() {
        usuarios = Repo.desserialize(USUARIOS_ARQ);
        inclusos = Repo.desserialize(INCLUSOS_ARQ);
        roteiros = Repo.desserialize(ROTEIROS_ARQ);
    }

    public void salvarTudo() {
        Repo.serialize(USUARIOS_ARQ, new ArrayList<>(usuarios));
        Repo.serialize(INCLUSOS_ARQ, new ArrayList<>(inclusos));
        Repo.serialize(ROTEIROS_ARQ, new ArrayList<>(roteiros));
    }

    public void salvarInclusos() {
        Repo.serialize(INCLUSOS_ARQ, new ArrayList<>(inclusos));
    }

    // Seed Data 
    public void seedData() {
        // Limpa dados existentes
        usuarios.clear();
        inclusos.clear();
        roteiros.clear();

        // Preenche com novos dados via Seeder externo
        DataSeeder.fill(this);

        salvarTudo();
        System.out.println("Seed data criado com sucesso via DataSeeder!");
        System.out.println("Usuarios: " + usuarios.size());
        System.out.println("Inclusos: " + inclusos.size());
    }
}
