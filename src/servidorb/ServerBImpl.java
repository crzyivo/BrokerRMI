package servidorb;

import java.rmi.Remote;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerBImpl implements ServerB {
    //Atributos privados
    private List<String> coleccion;

    //Constructor por defecto
    public ServerBImpl() throws RemoteException
    {
        this.coleccion = new ArrayList<String>();
    }

    /*Implementación de los metodos de la interfaz remota ServerB*/

    //Devuelve el numero de libros en la coleccion
    public int numero_de_libros() throws RemoteException{
      return coleccion.size();
    }

    //Lista los libros en la coleccion
    public String listar_libros() throws RemoteException{
      String lista="\n";
      for(String s:coleccion){
        lista=lista+s+"\n";
      }
      return lista;
    }
    //Añade un libro a la coleccion
    public void añadir_libro(String nombre) throws RemoteException{
      coleccion.add(nombre);
    }

    //Ejecucion del servidor
    public static void main(String[] args) {
        //Fichero java.policy
        System.setProperty("java.security.policy","servidorb/java.policy");
        System.setSecurityManager(new RMISecurityManager());


        //Direccion IP y puerto donde se ejecuta el servidor
        String host = "localhost";
        int port = 32000;
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        try {
            //Creacion del objeto remoto
            //LocateRegistry.createRegistry(1099);
            ServerBImpl obj = new ServerBImpl();
            ServerB serverStub = (ServerB) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.getRegistry(32000);
            try{
              registry.lookup("ServerB");
            }catch(NotBoundException e){
              registry.bind("ServerB", serverStub);
            }
            System.out.println("Objeto remoto registrado");
        try {
            Registry registryBroker = LocateRegistry.getRegistry(32000);
            Remote broker = registryBroker.lookup("Broker");
            System.out.println("Broker obtenido");
            //System.out.println(broker.ejecutar_servicio("dar_hora",null));
            System.out.println(broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
                   .invoke(broker,"dar_fecha",null));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
