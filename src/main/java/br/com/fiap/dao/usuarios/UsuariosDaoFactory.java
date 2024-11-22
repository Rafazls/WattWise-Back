package br.com.fiap.dao.usuarios;

public class UsuariosDaoFactory {
    private UsuariosDaoFactory() {
    }

    public static UsuariosDao create() {
        return new UsuariosDaoImpl();
    }
}
