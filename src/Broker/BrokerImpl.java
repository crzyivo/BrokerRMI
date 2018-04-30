package Broker;

import ServidorA.ServerA;

import java.lang.reflect.Method;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
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
                Class servidor = objetos.get("ServerA").getClass();
                return (String) servidor.getDeclaredMethod("dar_fecha",null).invoke(servidor,null);
            }catch (Exception e){

            }
        }
            return "fuck";
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
            Broker obj = new BrokerImpl();
            Broker serverStub = (Broker ) UnicastRemoteObject.exportObject(obj);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.getRegistry(host,port);
            registry.bind("Broker",serverStub);
            System.out.println("Objeto remoto registrado");
            objetos = new HashMap<>();
            objetos.put("ServerA",registry.lookup("ServerA"));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
