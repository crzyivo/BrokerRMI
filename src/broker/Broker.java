package broker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Broker extends Remote {
    public String ejecutar_servicio(String nom_servicio, Object[] parametros) throws RemoteException;
    public void registrar_servidor(String host_remoto_IP,int host_remoto_port,
    String nombre_registrado)throws RemoteException;
    public void registrar_servicio(String nombre_registrado,String nombre_servicio,
    Class[] lista_param,String tipo_retorno) throws RemoteException;
}
