package br.com.fiap.models.classes;

public class Usuarios {
    private String nome;
    private String email;
    private String senha;
    private String CEP;

    //Construtor
    public Usuarios(String nome,String email,String senha,String CEP) {

        this.nome = nome;
        this.email = email;
        this.senha= senha;
        this.CEP = CEP;
    }

    //Metodos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCEP() {return CEP;}

    public void setCEP(String CEP) {this.CEP = CEP;}


    //TODO
    public boolean login(String email, String senha){

        // Verifica se os parâmetros de entrada são nulos
        if (email == null || senha == null) {
            return false; // Retorna falso se algum dos parâmetros for nulo
        }

        // Usa equals para comparação de conteúdo de strings
        if (email.equals(this.email) && senha.equals(this.senha)) {
            return true; // Login bem-sucedido
        }
        return false; // Login falhou
    }
}
