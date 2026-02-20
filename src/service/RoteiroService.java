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
    private static double taxa_lucro;

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
        this.taxa_lucro = 0;
    }

    //Taxa Lucro
    public double getTaxaLucro() { return taxa_lucro; }

    public void setTaxaLucro(double t) { taxa_lucro = t; }

    // Usuarios 
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    private int buscarPosicaoUsuarioPorId(String id){
        for (int i = 0;i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Usuario buscarUsuarioPorId(String id) {
        int pos = buscarPosicaoUsuarioPorId(id);
        if (pos != -1) return usuarios.get(pos);

        return null;
    }

    public boolean removerUsuarioPorId(String id) {
        int pos = buscarPosicaoUsuarioPorId(id);
        if (pos != -1) {
            usuarios.remove(pos);
            return true;
        }

        return false;
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
