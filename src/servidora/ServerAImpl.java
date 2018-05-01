package servidora;

import java.rmi.Remote;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ServerAImpl implements ServerA {

    public ServerAImpl() {
    }

    @Override
    public String dar_fecha() throws RemoteException {
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("ES"));
        return s.format(Calendar.getInstance().getTime());
    }

    @Override
    public String dar_hora() throws RemoteException {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss", Locale.forLanguageTag("ES"));
        return s.format(Calendar.getInstance().getTime());
    }

    //Ejecucion del servidor
    public static void main(String[] args) {
        //Fichero java.policy
        System.setProperty("java.security.policy", "servidora/java.policy");
        System.setSecurityManager(new RMISecurityManager());


        //Direccion IP y puerto donde se ejecuta el servidor
        String host = "localhost";
        int port = 32000;
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        try {
            //Creacion del objeto remoto
            ServerAImpl obj = new ServerAImpl();
            ServerA serverStub = (ServerA) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.getRegistry(port);
            try{
              registry.lookup("ServerA");
            }catch(NotBoundException e){
              registry.bind("ServerA", serverStub);
            }
            System.out.println("Objeto remoto registrado");
            Registry registryBroker = LocateRegistry.getRegistry(32000);
            Remote broker = registryBroker.lookup("Broker");
            System.out.println("Broker obtenido");
            //System.out.println(broker.ejecutar_servicio("dar_hora",null));
            broker.getClass().
            getDeclaredMethod("registrar_servidor",String.class,int.class,String.class)
                   .invoke(broker,"127.0.0.1",32000,"ServerA");
            System.out.println("Servidor registrado en broker");
            broker.getClass().
            getDeclaredMethod("registrar_servicio",String.class,String.class,Class[].class,
            String.class).invoke(broker,"ServerA","dar_fecha",null,"String");
            System.out.println("Servicio registrado en broker");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
