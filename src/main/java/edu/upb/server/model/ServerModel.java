package edu.upb.server.model;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import edu.upb.common.TicketInterface;
import edu.upb.server.service.TicketService;
import edu.upb.common.observer.Subject;

/**
 * Modelo del servidor en la arquitectura MVC.
 * Configura y despliega el objeto remoto RMI en el registro.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class ServerModel extends Subject {

  private String ip;
  private int port;
  private String serviceName;
  private String uri;

  public ServerModel(String ip, int port, String serviceName) {
    this.ip = ip;
    this.port = port;
    this.serviceName = serviceName;
    this.uri = "rmi://" + ip + ":" + port + "/" + serviceName;
  }

  public boolean deploy() {
    try {
      System.setProperty("java.rmi.server.hostname", ip);
      TicketInterface service = new TicketService();
      LocateRegistry.createRegistry(port);
      Naming.rebind(uri, service);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}