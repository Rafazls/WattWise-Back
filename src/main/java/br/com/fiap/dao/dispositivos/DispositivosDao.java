package br.com.fiap.dao.dispositivos;

import java.sql.SQLException;
import java.util.List;
import br.com.fiap.models.classes.Dispositivos;

public interface DispositivosDao {
    void create(Dispositivos dispositivos) throws SQLException;
    Dispositivos readById(Long id) throws SQLException;
    List<Dispositivos> readByUsuarios(String emailUsuarios) throws SQLException;
    void update(Dispositivos dispositivos) throws SQLException;
    void delete(Long dispositivos) throws SQLException;
}
