package br.com.fiap.services;

import br.com.fiap.dao.dispositivos.DispositivosDao;
import br.com.fiap.dao.dispositivos.DispositivosDaoFactory;
import br.com.fiap.models.classes.Dispositivos;

import java.sql.SQLException;
import java.util.List;

public class DispositivosService {
    private final DispositivosDao dispositivosDao= DispositivosDaoFactory.create();

    public void cadastrarDispositivo(Dispositivos dispositivos) throws SQLException {
        if (dispositivos.getId() == null) {
            throw new IllegalArgumentException("Número do identificador não pode ser vazio");
        }

        if (dispositivos.getCategoria() == null) {
            throw new IllegalArgumentException("A categoria não pode ser vazia");
        }
        dispositivosDao.create(dispositivos);
    }

    public List<Dispositivos> listarDispositivos(String emailUsuarios) throws SQLException {
        return dispositivosDao.readByUsuarios(emailUsuarios);
    }

    public Dispositivos buscarDispositivoPorId(Long identificador) throws SQLException {
        return dispositivosDao.readById(identificador);
    }

    public void atualizarDispositivo(Dispositivos dispositivos) throws SQLException {
        if (dispositivos.getId() == null) {
            throw new IllegalArgumentException("Número do identificador não pode ser vazio");
        }
        dispositivosDao.update(dispositivos);
    }

    public void removerDispositivo(Long id) throws SQLException {
        dispositivosDao.delete(id);
    }
}
