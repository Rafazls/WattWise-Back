package br.com.fiap.dao.dispositivos;

public class DispositivosDaoFactory {
    public DispositivosDaoFactory() {
    }
    public static DispositivosDao create() {
        return new DispositivosDaoImpl() ;
    }
}
