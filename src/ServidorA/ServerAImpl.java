package ServidorA;

import ServidorA.ServerA;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        System.setProperty("java.security.policy", "java.policy");
        System.setSecurityManager(new RMISecurityManager());


        //Direccion IP y puerto donde se ejecuta el servidor
        String host = "localhost";
        int port = 32000;
        try {
            //Creacion del objeto remoto
            ServerAImpl obj = new ServerAImpl();
            ServerA serverStub = (ServerA) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println("Objeto remoto creado");

            //Registro del objeto remoto
            Registry registry = LocateRegistry.getRegistry(host, port);
            registry.bind("ServerA", serverStub);
            System.out.println("Objeto remoto registrado");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Registry registryBroker = LocateRegistry.getRegistry(host,port);
            Class broker = registryBroker.lookup("Broker").getClass();
            System.out.printf((String) broker.getDeclaredMethod("ejecutar_servicio",String.class,String[].class)
                    .invoke(broker,"dar_hora",null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
