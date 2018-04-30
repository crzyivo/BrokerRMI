package ServidorB;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerBImpl implements ServerB {
    //Atributos privados
    private int m_number_of_books;
    private String m_name_of_collection;

    //Constructor por defecto
    public ServerBImpl() throws RemoteException
    {
        super();
        this.m_number_of_books = 0;
        this.m_name_of_collection = "Mi coleccion";
    }

    //Constructor con nombre de coleccion
    public ServerBImpl(String name) throws RemoteException
    {
        super();
        this.m_number_of_books = 0;
        this.m_name_of_collection = name;
    }

    /*Implementación de los metodos de la interfaz remota Collection*/

    //Devuelve el numero de libros en la coleccion
    public int number_of_books() throws RemoteException
    {
        return this.m_number_of_books;
    }

    //Devuelve el nombre de la coleccion
    public String name_of_collection() throws RemoteException
    {
        return this.m_name_of_collection;
    }

    //Actualiza el nombre de la coleccion
    public void name_of_collection(String _new_value) throws RemoteException
    {
        this.m_name_of_collection=_new_value;
    }

    //Añade un libro a la coleccion
    public void add_book_to_collection() throws RemoteException
    {
        this.m_number_of_books++;
    }


    //Ejecucion del servidor
    public static void main(String[] args) {
        //Fichero java.policy
        System.setProperty("java.security.policy","java.policy");
        System.setSecurityManager(new RMISecurityManager());


        //Direccion IP y puerto donde se ejecuta el servidor
        String host = "localhost";
        int port = 32000;
        try {
            //Creacion del objeto remoto
            ServerBImpl obj = new ServerBImpl();
            ServerB serverStub = (ServerB ) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.getRegistry(host,port);
            registry.bind("ServerB",serverStub);
            System.out.println("Objeto remoto registrado");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
