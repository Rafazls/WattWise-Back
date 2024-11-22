package br.com.fiap.services;

import br.com.fiap.dao.usuarios.UsuariosDao;
import br.com.fiap.dao.usuarios.UsuariosDaoFactory;
import br.com.fiap.models.classes.Usuarios;

import java.sql.SQLException;
import java.util.List;

public class UsuariosService {
    private final UsuariosDao usuariosDao = UsuariosDaoFactory.create();

    public void cadastrarUsuarios(Usuarios usuarios) throws SQLException {
        if (usuarios.getNome() == null || usuarios.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (usuarios.getEmail() == null || usuarios.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (usuarios.getSenha() == null || usuarios.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        usuariosDao.create(usuarios);
    }

    public Usuarios loginUsuarios(String email, String senha) throws SQLException {
        Usuarios usuarios = usuariosDao.readByEmail(email);
        if (usuarios != null && usuarios.getSenha().equals(senha)) {
            return usuarios;
        }
        return null;
    }

    public List<Usuarios> listarUsuarios() throws SQLException {
        return usuariosDao.readAll();
    }

    public void atualizarUsuarios(Usuarios usuarios) throws SQLException {
        if (usuarios.getNome() == null || usuarios.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (usuarios.getSenha() == null || usuarios.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        usuariosDao.update(usuarios);
    }

    public void removerUsuarios(String email) throws SQLException {
        usuariosDao.delete(email);
    }

    // Implementação do método buscarUsuariosPorEmail
    public Usuarios buscarUsuariosPorEmail(String email) throws SQLException {
        return usuariosDao.readByEmail(email);
    }
}
