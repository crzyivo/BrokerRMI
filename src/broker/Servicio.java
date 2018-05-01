package broker;

public class Servicio {
  private String objeto_servidor;
  private String servicio;
  private Class[] parametros;
  private String retorno;

  public Servicio(String objeto_servidor,String servicio,Class[] parametros, String retorno){
    this.objeto_servidor=objeto_servidor;
    this.servicio=servicio;
    this.parametros=parametros;
    this.retorno=retorno;
  }

  public String getServidor(){
    return objeto_servidor;
  }

  public String getServicio(){
    return servicio;
  }

  public Class[] getParametros(){
    return parametros;
  }

  public String getRetorno(){
    return retorno;
  }
}
