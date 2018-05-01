package broker;

import java.rmi.Remote;
import java.lang.reflect.Method;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class BrokerImpl implements Broker {

    private static HashMap<String,Remote> objetos;
    private static HashMap<String,Servicio> servicios;
    @Override
    public String ejecutar_servicio(String nom_servicio, Object[] parametros) {
      if(servicios.containsKey(nom_servicio)){
        try {
            Servicio llamado = servicios.get(nom_servicio);
            Remote servidor = objetos.get(llamado.getServidor());
            return (String) servidor.getClass().getDeclaredMethod(llamado.getServicio(),llamado.getParametros())
            .invoke(servidor,parametros);
        }catch (Exception e){
          return "oops";
        }
      }else{
        return "Servicio desconocido";
      }
    }
    public void registrar_servidor(String host_remoto_IP,int host_remoto_port,
    String nombre_registrado){
      try {
        Registry registry = LocateRegistry.getRegistry(host_remoto_IP,host_remoto_port);
        objetos.put(nombre_registrado,registry.lookup(nombre_registrado));
        System.out.println("Servidor "+nombre_registrado+" registrado");
      }catch (Exception e) {
        System.out.println(e);
      }
    }
    public void registrar_servicio(String nombre_registrado,String nombre_servicio,
    Class[] lista_param,String tipo_retorno){
      Servicio s =  new Servicio(nombre_registrado,nombre_servicio,lista_param,tipo_retorno);
      servicios.put(nombre_servicio,s);
      System.out.println("Servicio "+nombre_servicio+" registrado");
    }
    //Ejecucion del servidor
    public static void main(String[] args) {
        //Fichero java.policy
        System.setProperty("java.security.policy","broker/java.policy");
        System.setSecurityManager(new RMISecurityManager());


        //Direccion IP y puerto donde se ejecuta el servidor
        String host = "localhost";
        int port = 32000;
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        try {
            //Creacion del objeto remoto
            Broker obj = new BrokerImpl();
            Broker serverStub = (Broker) UnicastRemoteObject.exportObject(obj,0);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.createRegistry(32000);
            try{
              registry.lookup("Broker");
            }catch(NotBoundException e){
              registry.bind("Broker", serverStub);
            }
            System.out.println("Objeto remoto registrado");
            objetos = new HashMap<>();
            servicios = new HashMap<>();
            //objetos.put("ServerA",registry.lookup("ServerA"));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
