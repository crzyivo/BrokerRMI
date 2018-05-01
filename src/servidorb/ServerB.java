package servidorb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerB extends Remote {

    int number_of_books() throws RemoteException;

    String name_of_collection() throws RemoteException;

    void name_of_collection(String _new_value) throws RemoteException;

    void add_book_to_collection() throws RemoteException;
}
