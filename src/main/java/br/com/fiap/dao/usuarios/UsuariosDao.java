package br.com.fiap.dao.usuarios;

import br.com.fiap.models.classes.Usuarios;
import java.sql.SQLException;
import java.util.List;

public interface UsuariosDao {
    void create(Usuarios usuarios) throws SQLException;

    List<Usuarios> readAll() throws SQLException;

    void update(Usuarios usuarios) throws SQLException;

    void delete(String email) throws SQLException;

    Usuarios readByEmail(String email) throws SQLException;
}
