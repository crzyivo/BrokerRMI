package clientec;

import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClienteC{

  //Ejecucion del servidor
  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    //Fichero java.policy
    System.setProperty("java.security.policy","servidorb/java.policy");
    System.setSecurityManager(new RMISecurityManager());


    //Direccion IP y puerto donde se ejecuta el servidor
    String host = "localhost";
    int port = 32000;
    System.setProperty("java.rmi.server.hostname","127.0.0.1");
    String opcion="";
    do {
      System.out.println("Opciones:\nA. Insertar un libro\nB. Lista de libros,fecha y hora actuales\nC. Salir");
      opcion=reader.next();
      if(opcion.equals("A")){
        try {
            Registry registryBroker = LocateRegistry.getRegistry(32000);
            Remote broker = registryBroker.lookup("Broker");
            System.out.println("Broker obtenido");
            System.out.println("Tiulo del libro: ");
            String titulo = reader.next();
            System.out.println("\n"+broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
            .invoke(broker,"añadir_libro",new Object[]{titulo}));
        } catch (Exception e) {
              e.printStackTrace();
        }
      }
      if(opcion.equals("B")){
        try {
            Registry registryBroker = LocateRegistry.getRegistry(32000);
            Remote broker = registryBroker.lookup("Broker");
            System.out.println("Broker obtenido");

            System.out.println("Hora: "+broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
            .invoke(broker,"dar_hora",null));

            System.out.println("Fecha: "+broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
            .invoke(broker,"dar_fecha",null));

            System.out.println("libros: "+broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
            .invoke(broker,"numero_de_libros",null));

            System.out.println(broker.getClass().
            getDeclaredMethod("ejecutar_servicio",String.class,Object[].class)
            .invoke(broker,"listar_libros",null));
            System.out.println();
        } catch (Exception e) {
              e.printStackTrace();
        }
      }

    } while (!opcion.equals("C"));
  }
}
