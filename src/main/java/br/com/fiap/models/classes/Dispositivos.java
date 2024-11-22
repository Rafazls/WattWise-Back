package br.com.fiap.models.classes;

import br.com.fiap.models.entities.Categoria;

public class Dispositivos {
    private Categoria categoria;
    private Usuarios usuarios;
    private String nome;
    private double consumoDeEnergia;
    private Long id;


    public Dispositivos(Categoria categoria, Usuarios usuarios, String nome, double consumoDeEnergia, Long id) {
        this.categoria = categoria;
        this.usuarios = usuarios;
        this.nome = nome;
        this.consumoDeEnergia = consumoDeEnergia;
        this.id = id;
    }

//metodos


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getConsumoDeEnergia() {
        return consumoDeEnergia;
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public void setConsumoDeEnergia(double consumoDeEnergia) {
        this.consumoDeEnergia = consumoDeEnergia;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    // Método para retornar o email do cliente associado

    public String getEmailUsuario() {
        return usuarios != null ? usuarios.getEmail() : null;
    }

}
