package br.com.fiap.models.entities;

public enum Categoria {
    ELETRODOMESTICOS("Eletrodomesticos"),
    ILUMINACAO("Iluminacao"),
    AQUECEDOR("Aquecedor"),
    REFRIGERACAO("refrigeracao");

    private final String descricao;

    // Construtor do enum
    Categoria(String descricao) {
        this.descricao = descricao;
    }

    // Método para obter a descrição do dispositivo
    public String getDescricao() { // Remover static
        return descricao;
    }

    // Método para obter um enum a partir da descrição
    public static Categoria fromDescricao(String descricao) {
        for (Categoria dispositivos : Categoria.values()) {
            if (dispositivos.getDescricao().equalsIgnoreCase(descricao)) {
                return dispositivos;
            }
        }
        throw new IllegalArgumentException("Dispositivo não encontrada para a descrição: " + descricao);
    }
}
