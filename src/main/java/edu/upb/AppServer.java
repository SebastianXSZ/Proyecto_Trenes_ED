package edu.upb;

import edu.upb.server.controller.ServerController;
import edu.upb.server.factory.ServerFactory;

/**
 * Punto de entrada del servidor RMI.
 * Crea y despliega el servicio de gestión de trenes.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class AppServer {
  public static void main(String[] args) {
    try {
      ServerController server = ServerFactory.create();
      server.init();
      System.out.println("Servidor RMI iniciado y listo.");
    } catch (Exception e) {
      System.err.println("Error al iniciar el servidor: " + e.getMessage());
      e.printStackTrace();
    }
  }
}