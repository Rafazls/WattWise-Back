package br.com.fiap.dao.usuarios;
import br.com.fiap.models.classes.Usuarios;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.fiap.config_database.DatabaseConfig;

class UsuariosDaoImpl implements UsuariosDao {

    // Verifica se um email já está cadastrado no banco de dados
    private boolean isEmailRegistered(String email) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        // Verifica se existe algum registro com o email informado
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    @Override
    public void create( Usuarios usuarios) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();

        if (isEmailRegistered(usuarios.getEmail())) {
            throw new SQLException("O email já está cadastrado!");
        }

        String sql= "insert into usuarios(nome,email,senha,CEP) values (?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,usuarios.getNome());
        pstmt.setString(2,usuarios.getEmail());
        pstmt.setString(3,usuarios.getSenha());
        pstmt.setString(4,usuarios.getCEP());
        pstmt.executeUpdate();
    }

    @Override
    public List<Usuarios> readAll() throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        List<Usuarios> result= new ArrayList<>();
        String sql = "select * from usuarios";
        Statement stmt = connection.createStatement();
        ResultSet rs= stmt.executeQuery(sql);
        while(rs.next()){
            String nome= rs.getString("nome");
            String email= rs.getString("email");
            String senha= rs.getString("senha");
            String CEP= rs.getString("CEP");
            result.add(new Usuarios(nome,email,senha,CEP));
        }
        return result;
    }

    @Override
    public void update(Usuarios usuarios) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql="update usuarios set nome=?,senha=?,CEP=? where email=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,usuarios.getNome());
        pstmt.setString(2,usuarios.getSenha());
        pstmt.setString(3,usuarios.getCEP());
        pstmt.setString(4, usuarios.getEmail());
        pstmt.executeUpdate();
    }

    @Override
    public void delete(String email) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql ="delete from usuarios where email=?";
        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1,email);
        pstmt.executeUpdate();
    }


    // Novo metodo para buscar um cliente por email
    @Override
    public Usuarios readByEmail(String email) throws SQLException {
        Connection connection = new DatabaseConfig(
                "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL",
                "rm557888",
                "021205"
        ).getConnection();
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();

        // Verifica se encontrou o cliente
        if (rs.next()) {
            String nome = rs.getString("nome");
            String senha = rs.getString("senha");
            String CEP = rs.getString("CEP");

            // Retorna o cliente encontrado
            return new Usuarios(nome, email, senha, CEP);
        } else {
            // Se não encontrar, retorna null
            return null;
        }
    }
}
