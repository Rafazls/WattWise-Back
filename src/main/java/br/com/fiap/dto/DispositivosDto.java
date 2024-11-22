package br.com.fiap.dto;

import br.com.fiap.models.entities.Categoria;

public class DispositivosDto {
    private Long id;
    private String nome;
    private String categoria;
    private double consumoDeEnergia; // Em watts
    private String emailUsuario;


    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getConsumoDeEnergia() {
        return consumoDeEnergia;
    }

    public void setConsumoDeEnergia(double consumoDeEnergia) {
        this.consumoDeEnergia = consumoDeEnergia;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
