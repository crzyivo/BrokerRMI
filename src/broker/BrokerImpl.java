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
    @Override
    public String ejecutar_servicio(String nom_servicio, String[] parametros) {
        if(nom_servicio.equals("dar_fecha")){
            try {
                Remote servidor = objetos.get("ServerA");
                return (String) servidor.getClass().getDeclaredMethod("dar_fecha",null).invoke(servidor,null);
            }catch (Exception e){

            }
        }
            return "fuck";
    }
    public void registrar_servidor(String host_remoto_IP,int host_remoto_port,
    String nombre_registrado){
      try {
        Registry registry = LocateRegistry.getRegistry(host_remoto_IP,host_remoto_port);
        objetos.put(nombre_registrado,registry.lookup(nombre_registrado));
      }catch (Exception e) {
        System.out.println(e);
      }
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
            //objetos.put("ServerA",registry.lookup("ServerA"));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
