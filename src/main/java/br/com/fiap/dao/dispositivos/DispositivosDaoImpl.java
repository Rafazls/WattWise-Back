package br.com.fiap.dao.dispositivos;

import br.com.fiap.config_database.DatabaseConfig;
import br.com.fiap.dao.usuarios.UsuariosDao;
import br.com.fiap.dao.usuarios.UsuariosDaoFactory;
import br.com.fiap.models.classes.Dispositivos;
import br.com.fiap.models.classes.Usuarios;
import br.com.fiap.models.entities.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DispositivosDaoImpl implements DispositivosDao {


    UsuariosDao usuariosDao = UsuariosDaoFactory.create();


    @Override
    public void create(Dispositivos dispositivos) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "INSERT INTO dispositivos (id, nome, categoria, consumo_energia, email_usuario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, dispositivos.getId());
            stmt.setString(2, dispositivos.getNome());
            stmt.setString(3, dispositivos.getCategoria().getDescricao());
            stmt.setDouble(4, dispositivos.getConsumoDeEnergia());
            stmt.setString(5, dispositivos.getEmailUsuario());  // Obtém o email do cliente
            stmt.executeUpdate();
        }
    }

    @Override
    public Dispositivos readById(Long id) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "SELECT * FROM dispositivos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = Categoria.valueOf(rs.getString("categoria").toUpperCase());
                    String emailUsuarios = rs.getString("email_usuario");
                    Usuarios usuarios = usuariosDao.readByEmail(emailUsuarios);

                    return new Dispositivos(
                            categoria,
                            usuarios,
                            rs.getString("nome"),
                            rs.getDouble("consumo_energia"),
                            rs.getLong("id")
                    );
                }
                return null;
            }
        }
    }


    @Override
    public List<Dispositivos> readByUsuarios(String emailUsuarios) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        List<Dispositivos> dispositivos = new ArrayList<>();
        String sql = "SELECT * FROM dispositivos WHERE email_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emailUsuarios);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = Categoria.valueOf(rs.getString("categoria").toUpperCase());

                    // Cria um objeto Cliente usando o email
                    Usuarios usuarios = usuariosDao.readByEmail(emailUsuarios);

                    dispositivos.add( new Dispositivos(
                            categoria,
                            usuarios,
                            rs.getString("nome"),
                            rs.getDouble("consumo_energia"),
                            rs.getLong("id")
                    ));
                }
            }
        }
        return dispositivos;
    }

    @Override
    public void update(Dispositivos dispositivos) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "UPDATE dispositivos SET nome = ?, categoria = ?, consumo_energia = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Converte os enums Marca e Modelo para strings antes de inserir no banco de dados
            stmt.setString(1, dispositivos.getNome());
            stmt.setString(2, dispositivos.getCategoria().name());
            stmt.setDouble(3, dispositivos.getConsumoDeEnergia());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "DELETE FROM dispositivos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
