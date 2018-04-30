package Broker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Broker extends Remote {
    public String ejecutar_servicio(String nom_servicio, String[] parametros) throws RemoteException;
}
