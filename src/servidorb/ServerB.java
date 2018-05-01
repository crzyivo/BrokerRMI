package servidorb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerB extends Remote {

    int numero_de_libros() throws RemoteException;

    String listar_libros() throws RemoteException;

    void añadir_libro(String nombre) throws RemoteException;
}
